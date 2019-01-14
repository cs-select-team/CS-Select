package com.csselect.gamification;

import java.util.Date;

/**
 * A daily challenge that will be completed once a player reaches 150 points
 * in one day.
 */
public class DailyReachScoreHundredFifty extends DailyChallenge {

    private int dailyScore;

    /**
     * Creates a daily challenge by setting the necessary values.
     */
    public DailyReachScoreHundredFifty() {
        this.name = "";
        this.description = "";
        this.date = new Date();
        this.completed = false;
        this.reward = 50;
        this.dailyScore = 0;
    }

    @Override
    public boolean checkFinished(PlayerStats stats) {
        dailyScore += stats.getLastScore();

        boolean finished = dailyScore >= 150;

        if (finished) {
            completed = true;
        }

        return finished;
    }

    @Override
    public void resetDaily() {
        completed = false;
        dailyScore = 0;
    }
}
