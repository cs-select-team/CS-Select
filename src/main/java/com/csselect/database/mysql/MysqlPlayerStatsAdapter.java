package com.csselect.database.mysql;

import com.csselect.Injector;
import com.csselect.database.DatabaseAdapter;
import com.csselect.database.PlayerStatsAdapter;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mysql-Implementation of the {@link PlayerStatsAdapter} Interface
 */
public class MysqlPlayerStatsAdapter extends MysqlAdapter implements PlayerStatsAdapter {

    private static final MysqlDatabaseAdapter DATABASE_ADAPTER = (MysqlDatabaseAdapter) Injector.getInjector()
            .getInstance(DatabaseAdapter.class);

    /**
     * Creates a new {@link MysqlPlayerStatsAdapter}
     *
     * @param id id of the adapter
     */
    MysqlPlayerStatsAdapter(int id) {
        super(id);
    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public int getRoundsPlayed() {
        return 0;
    }

    @Override
    public int getDailiesCompleted() {
        return 0;
    }

    @Override
    public int getMaxRoundScore() {
        return 0;
    }

    @Override
    public int getLastScore() {
        return 0;
    }

    @Override
    public int getHighestStreak() {
        return 0;
    }

    @Override
    public void setScore(int score) {

    }

    @Override
    public void setRoundsPlayed(int roundsPlayed) {

    }

    @Override
    public void dailyCompleted() {

    }

    @Override
    public void setMaxRoundScore(int maxRoundScore) {

    }

    @Override
    public void setLastScore(int lastScore) {

    }

    @Override
    public void setHighestStreak(int highestStreak) {

    }

    @Override
    final ResultSet getRow() throws SQLException {
        return DATABASE_ADAPTER.executeMysqlQuery("SELECT * FROM " + getTableName() + " WHERE (ID='" + getID() + "');");
    }

    @Override
    String getTableName() {
        return "playerstats";
    }
}
