package com.csselect.database;

/**
 * Interface abstracting a {@link com.csselect.gamification.PlayerStats} object from its database representation
 */
public interface PlayerStatsAdapter {

    /**
     * Gets the current score of a player
     * @return Current score
     */
    int getScore();

    /**
     * Gets the amount of played rounds of the player
     * @return The amount of played rounds of the player
     */
    int getRoundsPlayed();

    /**
     * Gets the amount of dailies that have been completed by the player
     * @return The amount of dailies completed
     */
    int getDailiesCompleted();

    /**
     * Gets the maximum score achieved by the player in a single round. This
     * does not take into account any gamification mechanics
     * @return The maximum score of the player
     */
    int getMaxRoundScore();

    /**
     * Gets the latest score of the player, meaning the points he received after
     * having completed a round. This does not take into account any gamification
     * mechanics
     * @return The last score of the player
     */
    int getLastScore();

    /**
     * Gets the highest streak ever reached of the player
     * @return The highest streak of the player
     */
    int getHighestStreak();

    /**
     * Sets the current score of a player
     * @param score Current score
     */
    void addScore(int score);

    /**
     * Increases the count of played rounds
     */
    void playRound();

    /**
     * Executed when a daily is completed, increases dailyCompleted count by 1
     */
    void completeDaily();

    /**
     * Sets the maximum score achieved by the player in a single round. This
     * does not take into account any gamification mechanics
     * @param maxRoundScore The maximum score of the player
     */
    void setMaxRoundScore(int maxRoundScore);

    /**
     * Sets the latest score of the player, meaning the points he received after
     * having completed a round. This does not take into account any gamification
     * mechanics
     * @param lastScore The last score of the player
     */
    void setLastScore(int lastScore);

    /**
     * Sets the highest streak ever reached of the player
     * @param highestStreak The highest streak of the player
     */
    void setHighestStreak(int highestStreak);

}
