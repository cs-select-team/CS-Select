package com.csselect.gamification;

import java.util.List;

/**
 * This interface is an abstraction of the implementation of gamification mechanics.
 */
public interface Gamification {

    /**
     * Calculates the player's points based on the given score by the ML-Server and to do so
     * the method takes into account all the gamification mechanics.
     * @param score The score evaluated by the ML-Server. Always in range [0,1].
     * @return The calculated score that the player will receive.
     */
    int finishRound(double score);

    /**
     * Simulates the impacts of a skipped round to the gamification mechanics.
     */
    void skipRound();

    /**
     * Gets the current score of a player.
     * @return Current score.
     */
    int getScore();

    /**
     * Gets the list of achievements.
     * @return List of Achievements.
     */
    List<Achievement> getAchievements();

    /**
     * Gets the current streak of a player.
     * @return Current streak.
     */
    Streak getStreak();

    /**
     * Gets the active daily challenge of a player for the current day.
     * @return The active daily challenge.
     */
    DailyChallenge getDaily();

    /**
     * Simulates the impacts of a player logging out to the gamification mechanics.
     */
    void logout();
}
