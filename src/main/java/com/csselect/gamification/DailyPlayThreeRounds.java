package com.csselect.gamification;

import java.time.LocalDate;

/**
 * A daily challenge that will be completed once a player plays three games
 * in one day.
 */
public class DailyPlayThreeRounds extends DailyChallenge {

    private static final String GERMAN_DESC = "Spiele heute drei Runden.";
    private static final String ENGLISH_DESC = "Play three rounds today.";
    private int dailyRounds;

    /**
     * Creates a daily challenge by setting the necessary values.
     */
    DailyPlayThreeRounds() {
        this.descriptionMap.put("de", GERMAN_DESC);
        this.descriptionMap.put("en", ENGLISH_DESC);
        this.date = LocalDate.now();
        this.completed = false;
        this.reward = 50;
        this.dailyRounds = 0;
    }

    @Override
    public boolean checkFinished(PlayerStats stats) {
        dailyRounds++;

        boolean finished = dailyRounds >= 3;

        if (finished) {
            completed = true;
        }

        return finished;
    }

}
