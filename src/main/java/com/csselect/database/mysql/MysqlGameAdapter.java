package com.csselect.database.mysql;

import com.csselect.Injector;
import com.csselect.database.DatabaseAdapter;
import com.csselect.database.GameAdapter;
import com.csselect.game.Feature;
import com.csselect.game.FeatureSet;
import com.csselect.game.Gamemode;
import com.csselect.game.NumberOfRoundsTermination;
import com.csselect.game.Round;
import com.csselect.game.Termination;
import com.csselect.game.TerminationComposite;
import com.csselect.game.TimeTermination;
import com.csselect.user.Organiser;
import com.csselect.user.Player;
import com.csselect.utils.FeatureSetUtils;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.HashSet;
import java.util.StringJoiner;

/**
 * Mysql-Implementation of the {@link GameAdapter} Interface
 */
public class MysqlGameAdapter extends MysqlAdapter implements GameAdapter {

    private static final MysqlDatabaseAdapter DATABASE_ADAPTER
            = (MysqlDatabaseAdapter) Injector.getInjector().getInstance(DatabaseAdapter.class);

    private String databaseName;

    /**
     * Creates a new {@link MysqlGameAdapter} with the given id
     * @param id adapters id
     */
    MysqlGameAdapter(int id) {
        super(id);
    }

    /**
     * Creates a new {@link MysqlGameAdapter} with the next available id
     * @throws SQLException Thrown if an error occurs while communicating with the database
     */
    MysqlGameAdapter() throws SQLException {
        super(DATABASE_ADAPTER.getNextGameID());
        DATABASE_ADAPTER.executeMysqlUpdate("INSERT INTO games () VALUES ();");
    }

    @Override
    public int getID() {
        return super.getID();
    }

    @Override
    public String getTitle() {
        return getString("title");
    }

    @Override
    public String getDescription() {
        return getString("description");
    }

    @Override
    public String getDatabaseName() {
        if (databaseName != null) {
            return databaseName;
        } else {
            databaseName = getString("databaseName");
            return databaseName;
        }
    }

    @Override
    public FeatureSet getFeatures() {
        try {
            return FeatureSetUtils.loadFeatureSet("dataSetName");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getNumberOfRounds() {
        ResultSet set;
        try {
            set = DATABASE_ADAPTER.executeMysqlQuery("SELECT MAX(id) AS id FROM rounds;", getDatabaseName());
            if (!set.next()) {
                return 0;
            } else {
                return set.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Collection<String> getInvitedPlayers() {
        Collection<String> emails = new HashSet<>();
        try {
            ResultSet resultSet = DATABASE_ADAPTER
                    .executeMysqlQuery("SELECT email FROM players WHERE invited='1'", databaseName);
            while (resultSet.next()) {
                emails.add(resultSet.getString("email"));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emails;
    }

    @Override
    public Collection<Player> getPlayingPlayers() {
        Collection<Player> players = new HashSet<>();
        try {
            ResultSet resultSet = DATABASE_ADAPTER
                    .executeMysqlQuery("SELECT email FROM players WHERE invited='0'", databaseName);
            while (resultSet.next()) {
                players.add(DATABASE_ADAPTER.getPlayer(resultSet.getString("email")));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    @Override
    public Collection<Round> getRounds() {
        return null;
    }

    @Override
    public Gamemode getGamemode() {
        return null;
    }

    @Override
    public Termination getTermination() {
        String[] terminations = getString("termination").split(",");
        TerminationComposite termination = new TerminationComposite();
        for (String t : terminations) {
            if (t.startsWith("time")) {
                termination.add(new TimeTermination(LocalDateTime.ofEpochSecond(
                        Long.parseLong(t.replace("time:", "")), 0, ZoneOffset.UTC)));
            } else if (t.startsWith("rounds")) {
                termination.add(
                        new NumberOfRoundsTermination(Integer.parseInt(t.replace("rounds:", ""))));
            }
        }
        return termination;
    }

    @Override
    public Organiser getOrganiser() {
        return null;
    }

    @Override
    public void setTermination(Termination termination) {
        setString("termination", termination.toString());

    }

    @Override
    public void setFeatures(FeatureSet featureSet) {
        setString("dataset", featureSet.getIdentifier());
    }

    @Override
    public void setFinished() {
        setBoolean("isTerminated", true);
    }

    @Override
    public void setTitle(String title) {
        setString("title", title);
    }

    @Override
    public void setDescription(String description) {
        setString("description", description);
    }

    @Override
    public void setDatabase(String name) {
        databaseName = name;
        setString("databaseName", name);
        try {
            createPlayersTable();
            createRoundsTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setGamemode(Gamemode gamemode) {

    }

    @Override
    public void setOrganiser(Organiser organiser) {
        setInt("organiser_id", organiser.getId());
    }

    @Override
    public boolean isFinished() {
        return getBoolean("isTerminated");
    }

    @Override
    public void addRound(Round round) {
        try {
            DATABASE_ADAPTER.executeMysqlUpdate("INSERT INTO rounds ("
                    + "playerid,'time',quality,points,uselessFeatures,chosenFeatures,shownFeatures)"
                    + "VALUES (" + round.getPlayer().getId() + "," + "NOW()," + round.getQuality() + ","
                    + round.getPoints() + "," + featuresToString(round.getUselessFeatures()) + ","
                    + featuresToString(round.getChosenFeatures()) + "," + featuresToString(round.getShownFeatures())
                    + ");", databaseName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addInvitedPlayers(Collection<String> emails) {
        StringJoiner joiner = new StringJoiner(",");
        emails.forEach(e -> joiner.add("('" + e + "',1)"));
        String values = joiner.toString();
        try {
            DATABASE_ADAPTER.executeMysqlUpdate("INSERT INTO players (email,invited) VALUES " + values
                    + " ON DUPLICATE KEY UPDATE email=email;", databaseName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPlayingPlayers(Collection<String> emails) {
        StringJoiner joiner = new StringJoiner(",");
        emails.forEach(e -> joiner.add("('" + e + "',false)"));
        String values = joiner.toString();
        try {
            DATABASE_ADAPTER.executeMysqlUpdate(
                    "REPLACE INTO players (email,invited) VALUES " + values + ";", databaseName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPlayingPlayer(int id) {
        String email = DATABASE_ADAPTER.getPlayerAdapter(id).getEmail();
        try {
            DATABASE_ADAPTER.executeMysqlUpdate(
                            "REPLACE INTO players (email,invited) VALUES ('" + email + "','0');", databaseName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeInvitedPlayers(Collection<String> emails) {
        emails.forEach(e -> {
            try {
                DATABASE_ADAPTER.executeMysqlUpdate(
                        "DELETE FROM players WHERE email='" + e + "' AND invited='1';", databaseName);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
    }

    @Override
    ResultSet getRow() throws SQLException {
        return DATABASE_ADAPTER.executeMysqlQuery("SELECT * FROM games WHERE (ID='" + getID() + "');");
    }

    @Override
    String getTableName() {
        return "games";
    }

    private void createRoundsTable() throws SQLException {
        DATABASE_ADAPTER.executeMysqlUpdate(Query.CREATE_ROUNDS_TABLE, getDatabaseName());
    }

    private void createPlayersTable() throws SQLException {
        DATABASE_ADAPTER.executeMysqlUpdate(Query.CREATE_GAMES_PLAYERS_TABLE, getDatabaseName());
    }

    private String featuresToString(Collection<Feature> features) {
        StringJoiner joiner = new StringJoiner(",");
        features.forEach(f -> joiner.add("" + f.getID()));
        return joiner.toString();
    }
}
