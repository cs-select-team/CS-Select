package com.csselect.gamification;

import java.util.List;

/**
 * Implements the Gamification Interface. PlayerStats combines he defined gamification mechanics
 * and is responsible for the calculation of the score reached by a player.
 */
public class PlayerStats implements Gamification {

    private Streak streak;
    private DailyChallenge activeDaily;
    private List<DailyChallenge> dailies;
    private static List<Achievement> achievements;
    private int score;
    private int roundsPlayed;
    private int dailiesCompleted;
    private int maxRoundScore;
    private int lastScore;
    private int highestStreak;

    public PlayerStats() {
        this.streak = new Streak();
        this.activeDaily = null;
        this.dailies = null;
        achievements = null;
        this.score = 0;
        this.roundsPlayed = 0;
        this.dailiesCompleted = 0;
        this.maxRoundScore = 0;
        this.lastScore = 0;
        this.highestStreak = 0;
    }


    @Override
    public int finishRound(double score) {
        int commutedScore = commuteScore(score);

        lastScore = commutedScore;

        if (maxRoundScore < commutedScore) {
            maxRoundScore = commutedScore;
        }

        streak.increaseStreak();
        int newStreak = streak.getCounter();

        int gamificationScore = commutedScore;

        if (newStreak >= 5) {
            gamificationScore = gamificationScore * 2;
        } else if (newStreak >= 3) {
            gamificationScore = gamificationScore + (int) Math.ceil(gamificationScore / 2);
        }

        if (highestStreak < newStreak) {
            highestStreak = newStreak;
        }

        // Dailies checken

        roundsPlayed += 1;
        this.score += gamificationScore;

        return gamificationScore;
    }

    @Override
    public void skipRound() {
        streak.setZero();
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public List<Achievement> getAchievements() {
        return achievements;
    }

    @Override
    public Streak getStreak() {
        return streak;
    }

    @Override
    public DailyChallenge getDaily() {
        return activeDaily;
    }

    /**
     * Gets the amount of played rounds of the player.
     * @return The amount of played rounds of the player.
     */
    public int getRoundsPlayed() {
        return roundsPlayed;
    }

    /**
     * Gets the amount of dailies that have been completed by the player.
     * @return The amount of dailies completed.
     */
    public int getDailiesCompleted() {
        return dailiesCompleted;
    }

    /**
     * Gets the maximum score achieved by the player in a single round. This
     * does not take into account any gamification mechanics.
     * @return The maximum score of the player.
     */
    public int getMaxRoundScore() {
        return maxRoundScore;
    }

    /**
     * Gets the latest score of the player, meaning the points he received after
     * having completed a round. This does not take into account any gamification
     * mechanics.
     * @return The last score of the player.
     */
    public int getLastScore() {
        return lastScore;
    }

    /**
     * Gets the highest streak ever reached of the player.
     * @return The highest streak of the player.
     */
    public int getHighestStreak() {
        return highestStreak;
    }

    /**
     * Algorithm to convert the score given by the ML-Server into the points that the player
     * will receive (without gamification mechanics). If the ML-Server score was originally lower
     * than 0.5, the player will obtain less points.
     * @param score Score given by the ML-Server.
     * @return Points the player will receive.
     */
    private int commuteScore(double score) {
        if (score < 0 || score > 1) {
            return 0;
        }

        if (score <= 0.5) {
            int lowScore = (int) Math.ceil(score * 50);
            return lowScore;
        }

        int commutedScore = (int) Math.ceil(score * 100);
        return commutedScore;
    }
}
