package com.csselect.database.mock;

import com.csselect.database.PlayerStatsAdapter;
import com.csselect.gamification.DailyChallenge;
import com.csselect.gamification.Streak;

/**
 * Mock-Implementation of the {@link PlayerStatsAdapter} Interface
 */
public class MockPlayerStatsAdapter implements PlayerStatsAdapter {

    private Streak streak;
    private DailyChallenge dailyChallenge;
    private int score;
    private int roundsPlayed;
    private int dailiesCompleted;
    private int maxRoundScore;
    private int lastScore;
    private int highestStreak;

    @Override
    public Streak getStreak() {
        return streak;
    }

    @Override
    public DailyChallenge getDaily() {
        return dailyChallenge;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public int getRoundsPlayed() {
        return roundsPlayed;
    }

    @Override
    public int getDailiesCompleted() {
        return dailiesCompleted;
    }

    @Override
    public int getMaxRoundScore() {
        return maxRoundScore;
    }

    @Override
    public int getLastScore() {
        return lastScore;
    }

    @Override
    public int getHighestStreak() {
        return highestStreak;
    }

    @Override
    public void setStreak(Streak streak) {
        this.streak = streak;
    }

    @Override
    public void setDaily(DailyChallenge daily) {
        this.dailyChallenge = daily;
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public void setRoundsPlayed(int roundsPlayed) {
        this.roundsPlayed = roundsPlayed;
    }

    @Override
    public void dailyCompleted() {
        this.dailiesCompleted++;
    }

    @Override
    public void setMaxRoundScore(int maxRoundScore) {
        this.maxRoundScore = maxRoundScore;
    }

    @Override
    public void setLastScore(int lastScore) {
        this.lastScore = lastScore;
    }

    @Override
    public void setHighestStreak(int highestStreak) {
        this.highestStreak = highestStreak;
    }
}
