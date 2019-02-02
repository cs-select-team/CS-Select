package com.csselect.gamification;

import java.time.LocalDate;

/**
 * A daily challenge that will be completed once a player reaches 150 points
 * in one day.
 */
public class DailyReachScoreHundredFifty extends DailyChallenge {

    private static final String DESCRIPTION_KEY = "reachScoreHundredFiftyDaily";
    private int dailyScore;

    /**
     * Creates a daily challenge by setting the necessary values.
     */
    DailyReachScoreHundredFifty() {
        this.descriptionKey = DESCRIPTION_KEY;
        this.date = LocalDate.now();
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
}
