package com.csselect.gamification;

import java.time.LocalDate;

/**
 * A daily challenge that will be completed once a player reaches 150 points
 * in one day.
 */
public class DailyReachScoreHundredFifty extends DailyChallenge {

    private final static String GERMAN_DESC = "Erreiche heute insgesamt 150 Punkte.";
    private final static String ENGLISH_DESC = "Achieve a total score of 150 today.";
    private int dailyScore;

    /**
     * Creates a daily challenge by setting the necessary values.
     */
    DailyReachScoreHundredFifty() {
        this.descriptionMap.put("de", GERMAN_DESC);
        this.descriptionMap.put("en", ENGLISH_DESC);
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
