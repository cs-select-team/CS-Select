package com.csselect.database.mysql;

import com.csselect.Injector;
import com.csselect.database.DatabaseAdapter;
import com.csselect.database.PlayerAdapter;
import com.csselect.game.Game;
import com.csselect.game.Round;
import com.csselect.gamification.PlayerStats;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

/**
 * Mysql-Implementation of the {@link PlayerAdapter} Interface
 */
public class MysqlPlayerAdapter extends MysqlUserAdapter implements PlayerAdapter {

    private static final MysqlDatabaseAdapter DATABASE_ADAPTER
            = (MysqlDatabaseAdapter) Injector.getInjector().getInstance(DatabaseAdapter.class);

    /**
     * Creates a new {@link MysqlPlayerAdapter} with the given id
     *
     * @param id id of the adapter
     */
    MysqlPlayerAdapter(int id) {
        super(id);
    }

    /**
     * Creates a new {@link MysqlPlayerAdapter} with the next available id
     * @param username players username
     * @param email players email
     * @param hash players password hash
     * @param salt players password salt
     * @throws SQLException Thrown if an error occurs while communicating with the database
     */
    MysqlPlayerAdapter(String username, String email, String hash, String salt) throws SQLException {
        super(DATABASE_ADAPTER.getNextIdOfTable("players"));
        DATABASE_ADAPTER.executeMysqlUpdate("INSERT INTO players (username,email,hash,salt) VALUES (?,?,?,?);",
                new StringParam(username), new StringParam(email), new StringParam(hash), new StringParam(salt));
    }

    @Override
    public String getUsername() {
        return getString("username");
    }

    /**
     * Sets {@link com.csselect.user.Player}s username
     * As usernames should be unique this method is only available in package to prevent foreign classes from calling it
     *
     * @param username username to set
     */
    void setUsername(String username) {
        setString("username", username);
    }

    @Override
    public PlayerStats getPlayerStats() {
        try {
            return new PlayerStats(new MysqlPlayerStatsAdapter(getID()));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection<Game> getInvitedGames() {
        Collection<Game> allGames = new HashSet<>(DATABASE_ADAPTER.getActiveGames(this));
        allGames.addAll(DATABASE_ADAPTER.getActiveGames(this));
        return allGames;
    }

    @Override
    public Collection<Round> getRounds() {
        Collection<Round> rounds = new HashSet<>();
        DATABASE_ADAPTER.getGames(getID()).forEach(
                game -> game.getRounds().stream().filter(round -> round.getPlayer().getId() == getID())
                        .forEach(rounds::add));
        return rounds;
    }

    @Override
    String getTableName() {
        return "players";
    }

    @Override
    public Collection<Game> getActiveGames() {
        return DATABASE_ADAPTER.getActiveGames(this);
    }

    @Override
    public Collection<Game> getTerminatedGames() {
        return DATABASE_ADAPTER.getTerminatedGames(this);
    }
}
