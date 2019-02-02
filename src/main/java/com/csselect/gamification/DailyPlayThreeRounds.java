package com.csselect.gamification;

import java.time.LocalDate;

/**
 * A daily challenge that will be completed once a player plays three games
 * in one day.
 */
public class DailyPlayThreeRounds extends DailyChallenge {

    private static final String DESCRIPTION_KEY = "playRoundsThreeDaily";
    private int dailyRounds;

    /**
     * Creates a daily challenge by setting the necessary values.
     */
    DailyPlayThreeRounds() {
        this.descriptionKey = DESCRIPTION_KEY;
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
