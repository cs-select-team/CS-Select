package com.csselect.gamification;

import java.util.Date;

/**
 *  * A daily challenge that will be completed once a player reaches 80 points in a single
 *  round without gamification mechanics.
 */
public class DailyReachRoundScoreEighty extends DailyChallenge {

    /**
     * Creates a daily challenge by setting the necessary values.
     */
    public DailyReachRoundScoreEighty() {
        this.name = "";
        this.description = "";
        this.date = new Date();
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

    @Override
    public void resetDaily() {
        completed = false;
    }
}
