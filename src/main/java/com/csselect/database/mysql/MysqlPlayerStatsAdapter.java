package com.csselect.database.mysql;

import com.csselect.inject.Injector;
import com.csselect.database.PlayerStatsAdapter;

import java.sql.ResultSet;

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
     */
    MysqlPlayerStatsAdapter(int id) {
        super(id);
        DATABASE_ADAPTER.executeMysqlUpdate("INSERT INTO " + TableNames.PLAYERSTATS
                + " (" + ColumnNames.ID + "," + ColumnNames.SCORE + "," + ColumnNames.ROUNDS_PLAYED + ","
                + ColumnNames.DAILIES_COMPLETED + "," + ColumnNames.MAX_ROUND_SCORE + "," + ColumnNames.LAST_SCORE
                + "," + ColumnNames.HIGHEST_STREAK + ") "
                + "VALUES (?,0,0,0,0,0,0) ON DUPLICATE KEY UPDATE " + ColumnNames.ID + " = " + ColumnNames.ID + ";",
                new IntParam(id));
    }

    @Override
    public int getScore() {
        return getInt(ColumnNames.SCORE);
    }

    @Override
    public int getRoundsPlayed() {
        return getInt(ColumnNames.ROUNDS_PLAYED);
    }

    @Override
    public int getDailiesCompleted() {
        return getInt(ColumnNames.DAILIES_COMPLETED);
    }

    @Override
    public int getMaxRoundScore() {
        return getInt(ColumnNames.MAX_ROUND_SCORE);
    }

    @Override
    public int getLastScore() {
        return getInt(ColumnNames.LAST_SCORE);
    }

    @Override
    public int getHighestStreak() {
        return getInt(ColumnNames.HIGHEST_STREAK);
    }

    @Override
    public void addScore(int score) {
        addInt(ColumnNames.SCORE, score);
    }

    @Override
    public void playRound() {
        incrementValue(ColumnNames.ROUNDS_PLAYED);
    }

    @Override
    public void completeDaily() {
        incrementValue(ColumnNames.DAILIES_COMPLETED);
    }

    @Override
    public void setMaxRoundScore(int maxRoundScore) {
        setInt(ColumnNames.MAX_ROUND_SCORE, maxRoundScore);
    }

    @Override
    public void setLastScore(int lastScore) {
        setInt(ColumnNames.LAST_SCORE, lastScore);
    }

    @Override
    public void setHighestStreak(int highestStreak) {
        setInt(ColumnNames.HIGHEST_STREAK, highestStreak);
    }

    @Override
    final ResultSet getRow() {
        return DATABASE_ADAPTER.executeMysqlQuery("SELECT * FROM " + TableNames.PLAYERSTATS
                        + " WHERE (" + ColumnNames.ID + "=?);", new IntParam(getID()));
    }

    @Override
    String getTableName() {
        return TableNames.PLAYERSTATS;
    }
}
