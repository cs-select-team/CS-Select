package com.csselect.gamification;

/**
 * Represents a streak that gets higher or lower, depending on whether the player
 * plays multiple rounds in a row or not.
 */
public class Streak {

    private int counter;

    /**
     * Creates a new Streak by initialising the counter to 0.
     */
    Streak() {
        this.counter = 0;
    }

    /**
     * Sets the counter of the streak back to zero.
     */
    void setZero() {
        counter = 0;
    }

    /**
     * Increases the counter of the streak by 1.
     */
    void increaseStreak() {
        counter += 1;
    }

    /**
     * Gets the current counter of the streak.
     * @return The current counter.
     */
    public int getCounter() {
        return counter;
    }
}
