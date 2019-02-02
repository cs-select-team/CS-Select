package com.csselect.gamification;

import java.time.LocalDate;

/**
 * A daily challenge that will be completed once a player reaches a streak
 * of 3 in one day.
 */
public class DailyGetStreakThree extends DailyChallenge {

    private static final String DESCRIPTION_KEY = "getStreakThreeDaily";
    private int dailyStreak;

    /**
     * Creates a daily challenge by setting the necessary values.
     */
    DailyGetStreakThree() {
        this.descriptionKey = DESCRIPTION_KEY;
        this.date = LocalDate.now();
        this.completed = false;
        this.reward = 50;
        this.dailyStreak = 0;
    }

    @Override
    public boolean checkFinished(PlayerStats stats) {
        if (stats.getStreak().getCounter() == 1) {
            dailyStreak = 1;
        } else {
            dailyStreak++;
        }

        boolean finished = dailyStreak >= 3;

        if (finished) {
            completed = true;
        }

        return finished;
    }
}
