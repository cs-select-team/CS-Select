package com.csselect.gamification;

/**
 * A daily challenge that will be completed once a player reaches a streak
 * of 3 in one day.
 */
public class DailyGetStreakThree extends DailyChallenge {

    /**
     * Creates a daily challenge by setting the necessary values.
     */
    public DailyGetStreakThree() {

    }

    @Override
    public boolean checkFinished(PlayerStats stats) {
        return false;
    }

    @Override
    public void resetDaily() {

    }
}
