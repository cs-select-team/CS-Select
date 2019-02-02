package com.csselect.gamification;

import java.time.LocalDate;

/**
 * A daily challenge that will be completed once a player reaches a streak
 * of 3 in one day.
 */
public class DailyGetStreakThree extends DailyChallenge {

    private static final String GERMAN_DESC = "Spiele drei Runden am Stück.";
    private static final String ENGLISH_DESC = "Play three rounds in a row.";
    private int dailyStreak;

    /**
     * Creates a daily challenge by setting the necessary values.
     */
    DailyGetStreakThree() {
        this.descriptionMap.put("de", GERMAN_DESC);
        this.descriptionMap.put("en", ENGLISH_DESC);
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
