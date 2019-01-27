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
    public Streak() {
        this.counter = 0;
    }

    /**
     * Creates a streak that already has a counter. For database purposes.
     * @param counter The streak's counter.
     */
    public Streak(int counter) {
        this.counter = counter;
    }

    /**
     * Sets the counter of the streak back to zero.
     */
    public void setZero() {
        counter = 0;
    }

    /**
     * Increases the counter of the streak by 1.
     */
    public void increaseStreak() {
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
