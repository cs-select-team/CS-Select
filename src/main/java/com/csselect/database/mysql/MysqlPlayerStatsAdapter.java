package com.csselect.database.mysql;

import com.csselect.database.PlayerStatsAdapter;
import com.csselect.gamification.DailyChallenge;
import com.csselect.gamification.Streak;

/**
 * Mysql-Implementation of the {@link PlayerStatsAdapter} Interface
 */
public class MysqlPlayerStatsAdapter implements PlayerStatsAdapter {
    @Override
    public Streak getStreak() {
        return null;
    }

    @Override
    public DailyChallenge getDaily() {
        return null;
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
    public void setStreak(Streak streak) {

    }

    @Override
    public void setDaily(DailyChallenge daily) {

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
}
