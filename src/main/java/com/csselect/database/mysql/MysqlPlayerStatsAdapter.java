package com.csselect.database.mysql;

import com.csselect.inject.Injector;
import com.csselect.database.PlayerStatsAdapter;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mysql-Implementation of the {@link PlayerStatsAdapter} Interface
 */
public class MysqlPlayerStatsAdapter extends MysqlAdapter implements PlayerStatsAdapter {

    private static final MysqlDatabaseAdapter DATABASE_ADAPTER = (MysqlDatabaseAdapter) Injector.getInstance()
            .getDatabaseAdapter();

    /**
     * Creates a new {@link MysqlPlayerStatsAdapter}
     *
     * @param id id of the adapter
     * @throws SQLException Thrown if an error occurs while communicating with the database
     */
    MysqlPlayerStatsAdapter(int id) throws SQLException {
        super(id);
        DATABASE_ADAPTER.executeMysqlUpdate("INSERT INTO " + TableNames.PLAYERSTATS
                + " (id,score,roundsPlayed,dailiesCompleted,maxRoundScore,lastScore,highestStreak) "
                + "VALUES (" + id + ",0,0,0,0,0,0) ON DUPLICATE KEY UPDATE id = id;");
    }

    @Override
    public int getScore() {
        return getInt("score");
    }

    @Override
    public int getRoundsPlayed() {
        return getInt("roundsPlayed");
    }

    @Override
    public int getDailiesCompleted() {
        return getInt("dailiesCompleted");
    }

    @Override
    public int getMaxRoundScore() {
        return getInt("maxRoundScore");
    }

    @Override
    public int getLastScore() {
        return getInt("lastScore");
    }

    @Override
    public int getHighestStreak() {
        return getInt("highestStreak");
    }

    @Override
    public void addScore(int score) {
        addInt("score", score);
    }

    @Override
    public void playRound() {
        incrementValue("roundsPlayed");
    }

    @Override
    public void completeDaily() {
        incrementValue("dailiesCompleted");
    }

    @Override
    public void setMaxRoundScore(int maxRoundScore) {
        setInt("maxRoundScore", maxRoundScore);
    }

    @Override
    public void setLastScore(int lastScore) {
        setInt("lastScore", lastScore);
    }

    @Override
    public void setHighestStreak(int highestStreak) {
        setInt("highestStreak", highestStreak);
    }

    @Override
    final ResultSet getRow() throws SQLException {
        return DATABASE_ADAPTER.executeMysqlQuery("SELECT * FROM " + TableNames.PLAYERSTATS + " WHERE (id=?);",
                new IntParam(getID()));
    }

    @Override
    String getTableName() {
        return TableNames.PLAYERSTATS;
    }
}
