package com.csselect.database.mock;

import com.csselect.database.PlayerStatsAdapter;

/**
 * Mock-Implementation of the {@link PlayerStatsAdapter} Interface
 */
public class MockPlayerStatsAdapter implements PlayerStatsAdapter {

    private int score;
    private int roundsPlayed;
    private int dailiesCompleted;
    private int maxRoundScore;
    private int lastScore;
    private int highestStreak;

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
    public void addScore(int score) {
        this.score = this.score + score;
    }

    @Override
    public void playRound() {
        this.roundsPlayed++;
    }

    @Override
    public void completeDaily() {
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
