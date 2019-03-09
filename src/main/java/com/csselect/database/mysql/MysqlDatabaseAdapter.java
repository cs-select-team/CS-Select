package com.csselect.database.mysql;

import com.csselect.configuration.Configuration;
import com.csselect.database.DatabaseAdapter;
import com.csselect.database.DatabaseException;
import com.csselect.database.GameAdapter;
import com.csselect.database.OrganiserAdapter;
import com.csselect.database.PlayerAdapter;
import com.csselect.game.Game;
import com.csselect.inject.Injector;
import com.csselect.user.Organiser;
import com.csselect.user.Player;
import com.csselect.user.management.PlayerManagement;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.intellij.lang.annotations.Language;
import org.pmw.tinylog.Logger;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Mysql-Implementation of the {@link DatabaseAdapter} Interface
 */
public class MysqlDatabaseAdapter implements DatabaseAdapter {

    private static final String PRODUCT_DATABASE_NAME = "CS_SELECT";

    private final String hostname;
    private final int port;
    private final String username;
    private final String password;
    private boolean tablesCreated;
    private final Map<String, MysqlDataSource> dataSources;

    private final Map<Game, Organiser> gameMap;
    private final Map<Integer, GameAdapter> gameAdapterMap;

    /**
     * Creates a new MysqlDatabaseAdapter. Only to be used by the {@link Injector}
     * @param configuration configuration to use
     */
    public MysqlDatabaseAdapter(Configuration configuration) {
        gameMap = new ConcurrentHashMap<>();
        gameAdapterMap = new ConcurrentHashMap<>();
        this.hostname = configuration.getDatabaseHostname();
        this.port = configuration.getDatabasePort();
        this.username = configuration.getDatabaseUsername();
        this.password = configuration.getDatabasePassword();
        this.tablesCreated = false;
        this.dataSources = new ConcurrentHashMap<>();
        this.dataSources.put(PRODUCT_DATABASE_NAME, createDataSource(PRODUCT_DATABASE_NAME));
    }

    @Override
    public PlayerAdapter getPlayerAdapter(int id) {
        return new MysqlPlayerAdapter(id);
    }

    @Override
    public OrganiserAdapter getOrganiserAdapter(int id) {
        return new MysqlOrganiserAdapter(id);
    }

    @Override
    public GameAdapter getGameAdapter(int id) {
        if (gameAdapterMap.containsKey(id)) {
            return gameAdapterMap.get(id);
        } else {
            MysqlGameAdapter adapter = new MysqlGameAdapter(id);
            gameAdapterMap.put(id, adapter);
            return new MysqlGameAdapter(id);
        }
    }

    @Override
    public GameAdapter getNewGameAdapter() {
        return new MysqlGameAdapter();
    }

    @Override
    public Player getPlayer(String email) {
        try {
            ResultSet set = executeMysqlQuery("SELECT * FROM " + TableNames.PLAYERS
                            + " WHERE (" + ColumnNames.EMAIL + "=?);", new StringParam(email));
            return getPlayer(set);
        } catch (SQLException e) {
            Logger.error(e);
            return null;
        }
    }

    @Override
    public Player getPlayer(int id) {
        try {
            ResultSet set = executeMysqlQuery("SELECT * FROM " + TableNames.PLAYERS
                            + " WHERE (" + ColumnNames.ID + "=?);", new IntParam(id));
            return getPlayer(set);
        } catch (SQLException e) {
            Logger.error(e);
            return null;
        }
    }

    private Player getPlayer(ResultSet set) throws SQLException {
        if (set.next()) {
            return new Player(new MysqlPlayerAdapter(set.getInt(ColumnNames.ID)));
        } else {
            return null;
        }
    }

    @Override
    public Organiser getOrganiser(String email) {
        try {
            ResultSet set = executeMysqlQuery("SELECT * FROM " + TableNames.ORGANISERS
                            + " WHERE (" + ColumnNames.EMAIL + "=?);", new StringParam(email));
            if (set.next()) {
                return new Organiser(new MysqlOrganiserAdapter(set.getInt(ColumnNames.ID)));
            } else {
                return null;
            }
        } catch (SQLException e) {
            Logger.error(e);
            return null;
        }
    }

    private Map<Integer, Organiser> getOrganisers() {
        Map<Integer, Organiser> organisers = new HashMap<>();
        try {
            ResultSet set = executeMysqlQuery("SELECT * FROM " + TableNames.ORGANISERS + ";");
            while (set.next()) {
                organisers.put(set.getInt(ColumnNames.ID),
                        new Organiser(new MysqlOrganiserAdapter(set.getInt(ColumnNames.ID))));
            }
        } catch (SQLException e) {
            Logger.error(e);
        }
        return organisers;
    }

    @Override
    public Collection<Player> getPlayers() {
        Collection<Player> players = new HashSet<>();
        try {
            ResultSet set = executeMysqlQuery("SELECT * FROM " + TableNames.PLAYERS + ";");
            while (set.next()) {
                players.add(new Player(new MysqlPlayerAdapter(set.getInt(ColumnNames.ID))));
            }
        } catch (SQLException e) {
            Logger.error(e);
        }
        return players;
    }

    @Override
    public int getNextGameID() {
        try {
            return getNextIdOfTable(TableNames.GAMES);
        } catch (SQLException e) {
            Logger.error(e);
            return 1;
        }
    }

    @Override
    public String getPlayerHash(int id) {
        try {
            ResultSet set = executeMysqlQuery("SELECT " + ColumnNames.HASH + " AS " + ColumnNames.HASH + " FROM "
                            + TableNames.PLAYERS + " WHERE (" + ColumnNames.ID + "=?);", new IntParam(id));
            String hash;
            if (set.next()) {
                hash = set.getString(ColumnNames.HASH);
            } else {
                hash = null;
            }
            return hash;
        } catch (SQLException e) {
            Logger.error(e);
            return null;
        }
    }

    @Override
    public String getPlayerSalt(int id) {
        try {
            ResultSet set = executeMysqlQuery("SELECT " + ColumnNames.SALT + " AS " + ColumnNames.SALT + " FROM "
                            + TableNames.PLAYERS + " WHERE (" + ColumnNames.ID + "=?);", new IntParam(id));
            String salt;
            if (set.next()) {
                salt = set.getString(ColumnNames.SALT);
            } else {
                salt = null;
            }
            return salt;
        } catch (SQLException e) {
            Logger.error(e);
            return null;
        }
    }

    @Override
    public String getOrganiserHash(int id) {
        try {
            ResultSet set = executeMysqlQuery("SELECT " + ColumnNames.HASH + " AS " + ColumnNames.HASH + " FROM "
                            + TableNames.ORGANISERS + " WHERE (" + ColumnNames.ID + "=?);", new IntParam(id));
            String hash;
            if (set.next()) {
                hash = set.getString(ColumnNames.HASH);
            } else {
                hash = null;
            }
            return hash;
        } catch (SQLException e) {
            Logger.error(e);
            return null;
        }
    }

    @Override
    public String getOrganiserSalt(int id) {
        try {
            ResultSet set = executeMysqlQuery("SELECT " + ColumnNames.SALT + " AS " + ColumnNames.SALT + " FROM "
                            + TableNames.ORGANISERS + " WHERE (" + ColumnNames.ID + "=?);", new IntParam(id));
            String salt;
            if (set.next()) {
                salt = set.getString(ColumnNames.SALT);
            } else {
                salt = null;
            }
            return salt;
        } catch (SQLException e) {
            Logger.error(e);
            return null;
        }
    }

    @Override
    public Player createPlayer(String email, String hash, String salt, String username) {
        try {
            ResultSet set = executeMysqlQuery("SELECT * FROM " + TableNames.PLAYERS
                            + " WHERE (" + ColumnNames.EMAIL + "=?) OR (" + ColumnNames.USERNAME + "=?);",
                    new StringParam(email), new StringParam(username));
            if (set.next()) {
                if (set.getString(ColumnNames.EMAIL).equals(email)) {
                    throw new IllegalArgumentException(PlayerManagement.EMAIL_IN_USE);
                } else if (set.getString(ColumnNames.USERNAME).equals(username)) {
                    throw new IllegalArgumentException(PlayerManagement.USERNAME_IN_USE);
                }
                return null;
            }
        } catch (SQLException e) {
            Logger.error(e);
            return null;
        }
        MysqlPlayerAdapter adapter;
        try {
            adapter = new MysqlPlayerAdapter(username, email, hash, salt);
        } catch (SQLException e) {
            Logger.error(e);
            return null;
        }
        return new Player(adapter);
    }

    @Override
    public Organiser createOrganiser(String email, String hash, String salt) {
        try {
            ResultSet set
                    = executeMysqlQuery("SELECT * FROM " + TableNames.ORGANISERS
                            + " WHERE (" + ColumnNames.EMAIL + "=?);", new StringParam(email));
            if (set.next()) {
                return null;
            }
        } catch (SQLException e) {
            Logger.error(e);
            return null;
        }
        MysqlOrganiserAdapter adapter;
        try {
            adapter = new MysqlOrganiserAdapter(email, hash, salt);
        } catch (SQLException e) {
            Logger.error(e);
            return null;
        }
        return new Organiser(adapter);
    }

    @Override
    public Game createGame(Organiser organiser) {
        Game game = new Game();
        if (!gameMap.containsKey(game)) {
            gameMap.put(game, organiser);
            executeMysqlUpdate("UPDATE " + TableNames.GAMES + " SET " + ColumnNames.ORGANISER_ID + "=? WHERE id=?;",
                    new IntParam(organiser.getId()), new IntParam(game.getId()));
        }
        return game;
    }

    @Override
    public void removeGame(Game game) {
        gameMap.remove(game);
        executeMysqlUpdate("DELETE FROM " + TableNames.GAMES + " WHERE (" + ColumnNames.ID + "=?);",
                    new IntParam(game.getId()));
    }

    @Override
    public boolean checkDuplicateDatabase(String databaseName) {
        try {
            ResultSet set = executeMysqlQuery("SHOW DATABASES LIKE ?", new StringParam(databaseName));
            return set.next();
        } catch (SQLException e) {
            Logger.error(e);
            return true; //We don't want to overwrite anything in case of errors
        }
    }

    /**
     * Executes the given Mysql-query on the main database
     * @param query query to execute
     * @param params params to execute the query with
     * @return ResultSet of the operation
     */
    ResultSet executeMysqlQuery(@Language("sql") String query, Param... params) {
        return executeMysqlQuery(query, PRODUCT_DATABASE_NAME, params);
    }

    /**
     * Executes the given Mysql-query on the given database
     * @param query prepared-statement query to execute
     * @param databaseName database to execute the query on
     * @param params parameters to execute the query with
     * @return ResultSet of the operation
     */
    ResultSet executeMysqlQuery(@Language("sql") String query, String databaseName, Param... params) {
        if (!tablesCreated) {
            createTables();
        }
        MysqlDataSource dataSource = dataSources.getOrDefault(databaseName, createDataSource(databaseName));
        CachedRowSet rowSet;
        try {
            rowSet = RowSetProvider.newFactory().createCachedRowSet();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            applyParams(statement, params);
            rowSet.populate(statement.executeQuery());
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return rowSet;
    }

    /**
     * Executes the given Mysql-Update on the main database
     * @param update update query to execute
     * @param params params to execute the query with
     */
    void executeMysqlUpdate(@Language("sql") String update, Param... params) {
        executeMysqlUpdate(update, PRODUCT_DATABASE_NAME, params);
    }

    /**
     * Executes the given Mysql-Update on the given database
     * @param update prepared-statement update to execute
     * @param databaseName database to execute the query on
     * @param params params to execute the query with
     */
    void executeMysqlUpdate(@Language("sql") String update, String databaseName, Param... params) {
        if (!tablesCreated) {
            createTables();
        }
        MysqlDataSource dataSource = dataSources.getOrDefault(databaseName, createDataSource(databaseName));
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(update);
            applyParams(statement, params);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    private void applyParams(PreparedStatement statement, Param... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            params[i].apply(statement, i + 1);
        }
    }

    private MysqlDataSource createDataSource(String databaseName) {
        MysqlDataSource source = new MysqlDataSource();
        source.setServerName(hostname);
        source.setPort(port);
        source.setUser(username);
        source.setPassword(password);
        source.setDatabaseName(databaseName);
        try {
            source.setServerTimezone("CET");
            source.setCreateDatabaseIfNotExist(true);
        } catch (SQLException e) {
            Logger.error(e);
        }
        dataSources.put(databaseName, source);
        return source;
    }

    /**
     * Gets the next id an object from the given table will have
     * @param tableName name of the table to get the next id from
     * @return next id
     * @throws SQLException Thrown if an error occurs while communicating with the database
     */
    int getNextIdOfTable(String tableName) throws SQLException {
        ResultSet set = executeMysqlQuery(
                "SELECT AUTO_INCREMENT FROM information_schema.TABLES"
                        + " WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?;", new StringParam(PRODUCT_DATABASE_NAME),
                new StringParam(tableName));
        if (set.next()) {
            return set.getInt("AUTO_INCREMENT");
        } else {
            throw new DatabaseException("Next table id couldn't be resolved!");
        }
    }

    /**
     * Gets the active games the given {@link PlayerAdapter} participates in or is invited to
     * @param adapter adapter to get games for
     * @return active games
     */
    Collection<Game> getActiveGames(PlayerAdapter adapter) {
        return getActiveGames().stream().filter(game ->
                game.getPlayingPlayers().stream().anyMatch(player -> player.getId() == adapter.getID())
                        || game.getInvitedPlayers().contains(adapter.getEmail()))
                .collect(Collectors.toSet());
    }

    /**
     * Gets the terminated games the given {@link PlayerAdapter} participates in or is invited to
     * @param adapter adapter to get games for
     * @return terminated games
     */
    Collection<Game> getTerminatedGames(PlayerAdapter adapter) {
        return getTerminatedGames().stream().filter(game ->
                game.getPlayingPlayers().stream().anyMatch(player -> player.getId() == adapter.getID())
                        || game.getInvitedPlayers().contains(adapter.getEmail()))
                .collect(Collectors.toSet());
    }

    /**
     * Gets the active games owned by the given {@link OrganiserAdapter}
     * @param adapter adapter to get games for
     * @return active games
     */
    Collection<Game> getActiveGames(OrganiserAdapter adapter) {
        if (gameAdapterMap.isEmpty()) {
            populateGameMap();
        }
        Collection<Game> games = new HashSet<>();
        gameMap.forEach((game, organiser) -> {
            if (organiser.getId() == adapter.getID() && !game.isTerminated()) {
                games.add(game);
            }
        });
        return games;
    }

    /**
     * Gets the terminated games owned by the given {@link OrganiserAdapter}
     * @param adapter adapter to get games for
     * @return terminated games
     */
    Collection<Game> getTerminatedGames(OrganiserAdapter adapter) {
        if (gameAdapterMap.isEmpty()) {
            populateGameMap();
        }
        Collection<Game> games = new HashSet<>();
        gameMap.forEach((game, organiser) -> {
            if (organiser.getId() == adapter.getID() && game.isTerminated()) {
                games.add(game);
            }
        });
        return games;
    }

    private Collection<Game> getActiveGames() {
        if (gameAdapterMap.isEmpty()) {
            populateGameMap();
        }
        return gameMap.keySet().stream().filter(game -> !game.isTerminated()).collect(Collectors.toSet());
    }

    private Collection<Game> getTerminatedGames() {
        if (gameAdapterMap.isEmpty()) {
            populateGameMap();
        }
        return gameMap.keySet().stream().filter(Game::isTerminated).collect(Collectors.toSet());
    }

    /**
     * Gets all games a player is associated with
     * @param playerId players id
     * @return games
     */
    Collection<Game> getGames(int playerId) {
        if (gameAdapterMap.isEmpty()) {
            populateGameMap();
        }
        return gameMap.keySet().stream().filter(game ->
                game.getPlayingPlayers().stream().anyMatch(player -> player.getId() == playerId))
                .collect(Collectors.toSet());
    }

    private void populateGameMap() {
        try {
            ResultSet gameSet = executeMysqlQuery("SELECT * FROM " + TableNames.GAMES + ";");
            Map<Integer, Organiser> organisers = getOrganisers();
            while (gameSet.next()) {
                gameMap.put(new Game(gameSet.getInt(ColumnNames.ID)),
                        organisers.get(gameSet.getInt(ColumnNames.ORGANISER_ID)));
            }
        } catch (SQLException e) {
            Logger.error(e);
        }
    }

    private boolean createTables() {
        MysqlDataSource dataSource = dataSources.getOrDefault(PRODUCT_DATABASE_NAME, //We can't use the executeUpdate
                createDataSource(PRODUCT_DATABASE_NAME));                            //method as we'd have cyclic calls
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(CreationQueries.CREATE_PLAYER_TABLE);
            statement.executeUpdate();
            statement = connection.prepareStatement(CreationQueries.CREATE_ORGANISER_TABLE);
            statement.executeUpdate();
            statement = connection.prepareStatement(CreationQueries.CREATE_GAME_TABLE);
            statement.executeUpdate();
            statement = connection.prepareStatement(CreationQueries.CREATE_PATTERN_TABLE);
            statement.executeUpdate();
            statement = connection.prepareStatement(CreationQueries.CREATE_PLAYERSTATS_TABLE);
            statement.executeUpdate();
            this.tablesCreated = true;
            return true;
        } catch (SQLException e) {
            this.tablesCreated = false;
            return false;
        }
    }
}
