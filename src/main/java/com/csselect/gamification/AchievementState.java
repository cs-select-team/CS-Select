package com.csselect.gamification;

/**
 * An enumeration for the four possible states of an achievement.
 */
public enum AchievementState {

    /**
     * The achievement is invisible for the player.
     */
    INVISIBLE,

    /**
     * The achievement is concealed, meaning the player has no clue about what he has to do
     * to complete it, but the player knows it exists.
     */
    CONCEALED,

    /**
     * The achievement is fully shown, but not finished.
     */
    SHOWN,

    /**
     * The achievement has been finished successfully.
     */
    FINISHED
}
