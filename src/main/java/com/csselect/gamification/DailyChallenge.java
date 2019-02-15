package com.csselect.gamification;

import com.csselect.utils.Localisation;

import java.time.LocalDate;

/**
 * Represents an abstract DailyChallenge. This is a task that a player
 * can finish in order to get a higher score. All subclasses must
 * implement the checkFinished and resetDaily method.
 */
public abstract class DailyChallenge {

    String descriptionKey;
    LocalDate date;
    boolean completed;
    int reward;

    /**
     * This method is abstract and has to be implemented in all subclasses. This method
     * determines whether the goal to finish the daily challenge has been reached.
     * @param stats The corresponding PlayerStats object of a player.
     * @return True, if challenge has been completed (after playing a round). False, otherwise.
     */
    public abstract boolean checkFinished(PlayerStats stats);

    /**
     * Gets the description of the task of the daily challenge in the specified language.
     * @param lang The language code in ISO 369-1 format.
     * @return The description in the specified language. Assertion error if language code is not known.
     */
    public final String getDescription(String lang) {
        return Localisation.get(lang, descriptionKey);
    }

    /**
     * Gets the date of the daily challenge.
     * @return The date if the daily.
     */
    LocalDate getDate() {
        return date;
    }

    /**
     * Checks if the daily challenge has already been completed.
     * @return True, if challenge is completed. False, if challenge has not been completed yet.
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Gets the reward for the challenge. This is a small amount of points between 50 and 100.
     * @return The reward for the daily.
     */
    public int getReward() {
        return reward;
    }

}