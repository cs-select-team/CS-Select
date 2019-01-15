package com.csselect.gamification;

/**
 * Represents an abstract Achievement. Achievements change their state depending on
 * the player's current progress. All subclasses must implement the checkProgress method.
 */
public abstract class Achievement {

    private String name;
    private String description;
    private AchievementState state;

    /**
     * Determines the current achievement's progress of a player represented by an AchievementState.
     * @param stats The PlayerStats object of the player.
     * @return The current state of the achievement.
     */
    public abstract AchievementState checkProgress(PlayerStats stats);

    /**
     * Gets the name of the achievement.
     * @return The name of the achievement.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the achievement.
     * @return The description of the achievement.
     */
    public String getDescription() {
        return description;
    }
}
