package com.csselect.gamification;

/**
 * Represents an Achievement. Achievements have a type and
 * a certain state depending on the player's current progress.
 */
public class Achievement {

    private final AchievementState state;
    private final AchievementType type;

    /**
     * Creates a new Achievement by setting its type and the current state.
     * @param state The current state of the achievement for the player.
     * @param type The type of the achievement.
     */
    Achievement(AchievementState state, AchievementType type) {
        this.state = state;
        this.type = type;
    }

    /**
     * Gets the current state of the achievement.
     * @return The current state of the achievement.
     */
    public AchievementState getState() {
        return state;
    }

    /**
     * Gets the type of the achievement.
     * @return The type of the achievement.
     */
    public AchievementType getType() {
        return type;
    }
}
