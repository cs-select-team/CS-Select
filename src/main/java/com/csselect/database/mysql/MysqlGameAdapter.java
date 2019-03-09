package com.csselect.database.mysql;

import com.csselect.inject.Injector;
import com.csselect.database.GameAdapter;
import com.csselect.game.Feature;
import com.csselect.game.FeatureSet;
import com.csselect.game.Gamemode;
import com.csselect.game.Round;
import com.csselect.game.Termination;
import com.csselect.user.Organiser;
import com.csselect.user.Player;
import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.StringJoiner;

/**
 * Mysql-Implementation of the {@link GameAdapter} Interface
 */
public class MysqlGameAdapter extends MysqlAdapter implements GameAdapter {

    private static final MysqlDatabaseAdapter DATABASE_ADAPTER
            = (MysqlDatabaseAdapter) Injector.getInstance().getDatabaseAdapter();
    private static final String COUNT = "count";

    private String databaseName;
    private String title;
    private String description;

    /**
     * Creates a new {@link MysqlGameAdapter} with the given id
     * @param id adapters id
     */
    MysqlGameAdapter(int id) {
        super(id);
    }

    /**
     * Creates a new {@link MysqlGameAdapter} with the next available id
     */
    MysqlGameAdapter() {
        super(DATABASE_ADAPTER.getNextGameID());
        DATABASE_ADAPTER.executeMysqlUpdate("INSERT INTO " + TableNames.GAMES
                + " (" + ColumnNames.IS_TERMINATED + ") VALUES (0);");
    }

    @Override
    public String getTitle() {
        if (title == null) {
            title = getString(ColumnNames.TITLE);
        }
        return title;

    }

    @Override
    public String getDescription() {
        if (description == null) {
            description = getString(ColumnNames.DESCRIPTION);
        }
        return description;
    }

    @Override
    public String getDatabaseName() {
        if (databaseName != null) {
            return databaseName;
        } else {
            databaseName = getString(ColumnNames.DATABASE_NAME);
            return databaseName;
        }
    }

    @Override
    public FeatureSet getFeatures() {
        try {
            ResultSet set = DATABASE_ADAPTER.executeMysqlQuery(
                    "SELECT " + ColumnNames.DATASET + " AS " + ColumnNames.DATASET + " FROM " + TableNames.GAMES
                            + " WHERE " + ColumnNames.ID + "=?;", new IntParam(getID()));
            if (set.next()) {
                return Injector.getInstance().getMLServer().getFeatures(set.getString(ColumnNames.DATASET));
            } else {
                return null;
            }
        } catch (IOException | SQLException e) {
            Logger.error(e);
            return null;
        }
    }

    @Override
    public int getNumberOfRounds() {
        ResultSet set;
        try {
            set = DATABASE_ADAPTER.executeMysqlQuery("SELECT COUNT(*) AS " + COUNT + " FROM " + TableNames.GAME_ROUNDS
                            + " WHERE " + ColumnNames.SKIPPED + "=0;", getDatabaseName());
            if (!set.next()) {
                return 0;
            } else {
                return set.getInt(COUNT);
            }
        } catch (SQLException e) {
            Logger.error(e);
            return 0;
        }
    }

    @Override
    public Collection<String> getInvitedPlayers() {
        Collection<String> emails = new HashSet<>();
        try {
            ResultSet resultSet = DATABASE_ADAPTER
                    .executeMysqlQuery("SELECT " + ColumnNames.EMAIL + " FROM " + TableNames.GAME_PLAYERS
                                    + " WHERE " + ColumnNames.INVITED + "=1", getDatabaseName());
            while (resultSet.next()) {
                emails.add(resultSet.getString(ColumnNames.EMAIL));
            }
        } catch (SQLException e) {
            Logger.error(e);
        }
        return emails;
    }

    @Override
    public Collection<Player> getPlayingPlayers() {
        Collection<Player> players = new HashSet<>();
        try {
            ResultSet resultSet = DATABASE_ADAPTER
                    .executeMysqlQuery("SELECT " + ColumnNames.EMAIL + " FROM " + TableNames.GAME_PLAYERS
                                    + " WHERE " + ColumnNames.INVITED + "=0", getDatabaseName());
            while (resultSet.next()) {
                players.add(DATABASE_ADAPTER.getPlayer(resultSet.getString(ColumnNames.EMAIL)));
            }
        } catch (SQLException e) {
            Logger.error(e);
        }
        return players;
    }

    @Override
    public Collection<Round> getRounds() {
        Collection<Round> rounds = new HashSet<>();
        try {
            ResultSet set = DATABASE_ADAPTER.executeMysqlQuery("SELECT * FROM " + TableNames.GAME_ROUNDS
                            + " WHERE " + ColumnNames.SKIPPED + "=0", getDatabaseName());
            while (set.next()) {
                Round round = getGamemode().createRound(new Player(new MysqlPlayerAdapter(
                        set.getInt(ColumnNames.PLAYER_ID))));
                round.setTime(set.getTimestamp(ColumnNames.TIME).toLocalDateTime());
                round.setPoints(set.getInt(ColumnNames.POINTS));
                rounds.add(round);
            }
        } catch (SQLException e) {
            Logger.error(e);
        }
        return rounds;
    }

    @Override
    public Gamemode getGamemode() {
        return Gamemode.parseGamemode(getString(ColumnNames.GAMEMODE));
    }

    @Override
    public Termination getTermination() {
        return Termination.parseTermination(getString(ColumnNames.TERMINATION));
    }

    @Override
    public Organiser getOrganiser() {
        return new Organiser(new MysqlOrganiserAdapter(getInt(ColumnNames.ORGANISER_ID)));
    }

    @Override
    public void setTermination(Termination termination) {
        setString(ColumnNames.TERMINATION, termination.toString());
    }

    @Override
    public void setFeatures(FeatureSet featureSet) {
        setString(ColumnNames.DATASET, featureSet.getIdentifier());
        /* We only set the name of the featureset as the featureset itself is saved on the disk and we load it from
        there. The column is named dataset, as that's the name used by the MLServer for the data we get from it and we
        use it interchangeably */
    }

    @Override
    public void setFinished() {
        setBoolean(ColumnNames.IS_TERMINATED, true);
    }

    @Override
    public void setTitle(String title) {
        setString(ColumnNames.TITLE, title);
    }

    @Override
    public void setDescription(String description) {
        setString(ColumnNames.DESCRIPTION, description);
    }

    @Override
    public void setDatabase(String name) {
        try {
            ResultSet set = DATABASE_ADAPTER.executeMysqlQuery(
                    "SELECT * FROM " + TableNames.GAMES + " WHERE " + ColumnNames.DATABASE_NAME + "=?",
                    new StringParam(databaseName));
            if (!set.next()) {
                databaseName = name;
                setString(ColumnNames.DATABASE_NAME, name);
                createPlayersTable();
                createRoundsTable();
            }
        } catch (SQLException e) {
            Logger.error(e);
        }
    }

    @Override
    public void setGamemode(Gamemode gamemode) {
        setString(ColumnNames.GAMEMODE, gamemode.toString());
    }

    @Override
    public void setOrganiser(Organiser organiser) {
        setInt(ColumnNames.ORGANISER_ID, organiser.getId());
    }

    @Override
    public boolean isFinished() {
        return getBoolean(ColumnNames.IS_TERMINATED);
    }

    @Override
    public void addRound(Round round) {
        DATABASE_ADAPTER.executeMysqlUpdate("INSERT INTO " + TableNames.GAME_ROUNDS + " ("
                    + ColumnNames.PLAYER_ID + "," + ColumnNames.TIME + "," + ColumnNames.SKIPPED + ","
                + ColumnNames.QUALITY + "," + ColumnNames.POINTS + "," + ColumnNames.USELESS_FEATURES
                            + "," + ColumnNames.CHOSEN_FEATURES + "," + ColumnNames.SHOWN_FEATURES + ")"
                + "VALUES (?,NOW(),?,?,?,?,?,?);", getDatabaseName(), new IntParam(round.getPlayer().getId()),
                new BooleanParam(round.isSkipped()), new DoubleParam(round.getQuality()),
                new IntParam(round.getPoints()), new StringParam(featuresToString(round.getUselessFeatures())),
                new StringParam(featuresToString(round.getChosenFeatures())),
                new StringParam(featuresToString(round.getShownFeatures())));
    }

    @Override
    public void addInvitedPlayers(Collection<String> emails) {
        StringJoiner joiner = new StringJoiner(",");
        Param[] params = new Param[emails.size()];
        int i = 0;
        for (String email : emails) {
            joiner.add("(?,1)");
            params[i] = new StringParam(email);
            i++;
        }
        String values = joiner.toString();
        DATABASE_ADAPTER.executeMysqlUpdate("INSERT INTO " + TableNames.GAME_PLAYERS + " ("
                    + ColumnNames.EMAIL + "," + ColumnNames.INVITED + ") VALUES " + values
                + " ON DUPLICATE KEY UPDATE "
                    + ColumnNames.EMAIL + "=" + ColumnNames.EMAIL + ";", getDatabaseName(), params);
    }

    @Override
    public void addPlayingPlayers(Collection<String> emails) {
        StringJoiner joiner = new StringJoiner(",");
        Param[] params = new Param[emails.size()];
        int i = 0;
        for (String email : emails) {
            joiner.add("(?,0)");
            params[i] = new StringParam(email);
            i++;
        }
        String values = joiner.toString();
        DATABASE_ADAPTER.executeMysqlUpdate(
                "REPLACE INTO " + TableNames.GAME_PLAYERS + " (" + ColumnNames.EMAIL + ","
                            + ColumnNames.INVITED + ") VALUES " + values + ";", getDatabaseName(), params);
    }

    @Override
    public void addPlayingPlayer(int id) {
        StringParam email = new StringParam(DATABASE_ADAPTER.getPlayerAdapter(id).getEmail());
        DATABASE_ADAPTER.executeMysqlUpdate(
                        "REPLACE INTO " + TableNames.GAME_PLAYERS + " (" + ColumnNames.EMAIL + ","
                    + ColumnNames.INVITED + ") VALUES (?,0);", getDatabaseName(), email);
    }

    @Override
    public void removeInvitedPlayers(Collection<String> emails) {
        emails.forEach(email -> DATABASE_ADAPTER.executeMysqlUpdate(
                "DELETE FROM " + TableNames.GAME_PLAYERS + " WHERE " + ColumnNames.EMAIL + "=? AND "
                            + ColumnNames.INVITED + "=1;", getDatabaseName(),
                new StringParam(email)));
    }

    @Override
    public boolean checkDuplicateFeatureProvision(Collection<Feature> features) {
        try {
            ResultSet set = DATABASE_ADAPTER.executeMysqlQuery("SELECT " + ColumnNames.ID + " FROM "
                    + TableNames.GAME_ROUNDS + " WHERE " + ColumnNames.SHOWN_FEATURES + "=?", getDatabaseName(),
                    new StringParam(featuresToString(features)));
            return set.next();
        } catch (SQLException e) {
            Logger.error(e);
            return false;
        }
    }

    @Override
    ResultSet getRow() {
        return DATABASE_ADAPTER.executeMysqlQuery("SELECT * FROM " + TableNames.GAMES
                + " WHERE (" + ColumnNames.ID + "=?);", new IntParam(getID()));
    }

    @Override
    String getTableName() {
        return TableNames.GAMES;
    }

    private void createRoundsTable() {
        DATABASE_ADAPTER.executeMysqlUpdate(CreationQueries.CREATE_ROUNDS_TABLE, getDatabaseName());
    }

    private void createPlayersTable() {
        DATABASE_ADAPTER.executeMysqlUpdate(CreationQueries.CREATE_GAMES_PLAYERS_TABLE, getDatabaseName());
    }

    private String featuresToString(Collection<Feature> features) {
        StringJoiner joiner = new StringJoiner(",");
        features.stream().sorted().forEach(f -> joiner.add("" + f.getID()));
        return joiner.toString();
    }
}
