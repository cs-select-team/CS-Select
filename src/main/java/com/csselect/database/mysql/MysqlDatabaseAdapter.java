package com.csselect.database.mysql;

import com.csselect.configuration.Configuration;
import com.csselect.database.DatabaseAdapter;
import com.csselect.database.GameAdapter;
import com.csselect.database.OrganiserAdapter;
import com.csselect.database.PlayerAdapter;
import com.csselect.game.Game;
import com.csselect.user.Organiser;
import com.csselect.user.Player;
import com.google.inject.Inject;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
    private final Map<String, MysqlDataSource> dataSources;

    private final Map<Game, Organiser> gameMap;

    /**
     * Creates a new MysqlDatabaseAdapter
     * @param configuration configuration to use
     */
    @Inject
    MysqlDatabaseAdapter(Configuration configuration) {
        gameMap = new HashMap<>();
        this.hostname = configuration.getDatabaseHostname();
        this.port = configuration.getDatabasePort();
        this.username = configuration.getDatabaseUsername();
        this.password = configuration.getDatabasePassword();
        this.dataSources = new HashMap<>();
        this.dataSources.put(PRODUCT_DATABASE_NAME, createDataSource(PRODUCT_DATABASE_NAME));
        try {
            executeMysqlUpdate(Query.CREATE_PLAYER_TABLE);
            executeMysqlUpdate(Query.CREATE_ORGANISER_TABLE);
            executeMysqlUpdate(Query.CREATE_GAME_TABLE);
            executeMysqlUpdate(Query.CREATE_PATTERN_TABLE);
            executeMysqlUpdate(Query.CREATE_PLAYERSTATS_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
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
        return new MysqlGameAdapter(id);
    }

    @Override
    public Player getPlayer(String email) {
        try {
            ResultSet set = executeMysqlQuery("SELECT * FROM players WHERE (email='" + email + "');");
            return getPlayer(set);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Player getPlayer(int id) {
        try {
            ResultSet set = executeMysqlQuery("SELECT * FROM players WHERE (id='" + id + "';");
            return getPlayer(set);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Player getPlayer(ResultSet set) throws SQLException {
        if (set.next()) {
            Player p = new Player(new MysqlPlayerAdapter(set.getInt("id")));
            set.close();
            return p;
        } else {
            return null;
        }
    }

    @Override
    public Organiser getOrganiser(String email) {
        try {
            ResultSet set = executeMysqlQuery("SELECT * FROM organisers WHERE (email='" + email + "');");
            if (set.next()) {
                Organiser o = new Organiser(new MysqlOrganiserAdapter(set.getInt("id")));
                set.close();
                return o;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection<Player> getPlayers() {
        Collection<Player> players = new HashSet<>();
        try {
            ResultSet set = executeMysqlQuery("SELECT * FROM players");
            while (set.next()) {
                players.add(new Player(new MysqlPlayerAdapter(set.getInt("id"))));
            }
            set.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    @Override
    public int getNextGameID() {
        try {
            return getNextIdOfTable("games");
        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }
    }

    @Override
    public String getPlayerHash(int id) {
        try {
            ResultSet set = executeMysqlQuery("SELECT hash AS hash FROM players WHERE (id='" + id + "');");
            set.next();
            String hash = set.getString("hash");
            set.close();
            return hash;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getPlayerSalt(int id) {
        try {
            ResultSet set = executeMysqlQuery("SELECT salt AS salt FROM players WHERE (id='" + id + "');");
            set.next();
            String salt = set.getString("salt");
            set.close();
            return salt;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getOrganiserHash(int id) {
        try {
            ResultSet set = executeMysqlQuery("SELECT hash AS hash FROM organisers WHERE (id='" + id + "');");
            set.next();
            String hash = set.getString("hash");
            set.close();
            return hash;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getOrganiserSalt(int id) {
        try {
            ResultSet set = executeMysqlQuery("SELECT salt AS salt FROM organisers WHERE (id='" + id + "');");
            set.next();
            String salt = set.getString("salt");
            set.close();
            return salt;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Player createPlayer(String email, String hash, String salt, String username) {
        try {
            ResultSet set = executeMysqlQuery("SELECT * FROM players WHERE (email='" + email + "');");
            if (set.next()) {
                return null;
            }
            set.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        MysqlPlayerAdapter adapter;
        try {
            adapter = new MysqlPlayerAdapter();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        adapter.setEmail(email);
        adapter.setPassword(hash, salt);
        adapter.setUsername(username);
        return new Player(adapter);
    }

    @Override
    public Organiser createOrganiser(String email, String hash, String salt) {
        try {
            ResultSet set = executeMysqlQuery("SELECT * FROM organisers WHERE (email='" + email + "');");
            if (set.next()) {
                return null;
            }
            set.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        MysqlOrganiserAdapter adapter;
        try {
            adapter = new MysqlOrganiserAdapter();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        adapter.setEmail(email);
        adapter.setPassword(hash, salt);
        return new Organiser(adapter);
    }

    @Override
    public void registerGame(Organiser organiser, Game game) {
        if (!gameMap.containsKey(game)) {
            gameMap.put(game, organiser);
            try {
                executeMysqlUpdate("UPDATE organiser_id SET " + organiser.getId() + " FROM games WHERE"
                        + " (id='" + game.getId() + "');");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeGame(Game game) {
        gameMap.remove(game);
        try {
            executeMysqlUpdate("DELETE FROM games WHERE (id='" + game.getId() + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes the given Mysql-query on the main database
     * @param query query to execute
     * @return ResultSet of the operation
     * @throws SQLException Thrown when there is an error executing the given statement
     */
    ResultSet executeMysqlQuery(@Language("sql") String query) throws SQLException {
        return executeMysqlQuery(query, PRODUCT_DATABASE_NAME);
    }

    /**
     * Executes the given Mysql-query on the given database
     * @param query query to execute
     * @param databaseName database to execute the query on
     * @return ResultSet of the operation
     * @throws SQLException Thrown when there is an error executing the given statement
     */
    ResultSet executeMysqlQuery(@Language("sql") String query, String databaseName) throws SQLException {
        MysqlDataSource dataSource = dataSources.getOrDefault(databaseName, createDataSource(databaseName));
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    /**
     * Executes the given Mysql-Update on the main database
     * @param update update query to execute
     * @throws SQLException Thrown when there is an error executing the given statement
     */
    void executeMysqlUpdate(@Language("sql") String update) throws SQLException {
        executeMysqlUpdate(update, PRODUCT_DATABASE_NAME);
    }

    /**
     * Executes the given Mysql-Update on the given database
     * @param update update query to execute
     * @param databaseName database to execute the query on
     * @throws SQLException Thrown when there is an error executing the given statement
     */
    void executeMysqlUpdate(@Language("sql") String update, String databaseName) throws SQLException {
        MysqlDataSource dataSource = dataSources.getOrDefault(databaseName, createDataSource(databaseName));
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(update);
        statement.close();
        connection.close();
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
            e.printStackTrace();
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
        ResultSet set = executeMysqlQuery("SELECT MAX(id) AS id FROM " + tableName + ";");
        if (!set.next()) {
            return 1;
        } else {
            return set.getInt("id") + 1;
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
        Collection<Game> games = new HashSet<>();
        gameMap.forEach((game, organiser) -> {
            if (organiser.getId() == adapter.getID() && game.isTerminated()) {
                games.add(game);
            }
        });
        return games;
    }

    private Collection<Game> getActiveGames() {
        return gameMap.keySet().stream().filter(game -> !game.isTerminated()).collect(Collectors.toSet());
    }

    private Collection<Game> getTerminatedGames() {
        return gameMap.keySet().stream().filter(Game::isTerminated).collect(Collectors.toSet());

    }
}
