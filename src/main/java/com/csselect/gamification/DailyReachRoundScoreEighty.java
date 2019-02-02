package com.csselect.gamification;

import java.time.LocalDate;

/**
 *  A daily challenge that will be completed once a player reaches 80 points in a single
 *  round without gamification mechanics.
 */
public class DailyReachRoundScoreEighty extends DailyChallenge {

    private static final String DESCRIPTION_KEY = "reachRoundScoreEightyDaily";

    /**
     * Creates a daily challenge by setting the necessary values.
     */
    DailyReachRoundScoreEighty() {
        this.descriptionKey = DESCRIPTION_KEY;
        this.date = LocalDate.now();
        this.completed = false;
        this.reward = 50;
    }

    @Override
    public boolean checkFinished(PlayerStats stats) {
        boolean finished = stats.getLastScore() >= 80;

        if (finished) {
            completed = true;
        }

        return finished;
    }
}
