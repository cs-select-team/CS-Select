package com.csselect.gamification;

import java.time.LocalDate;

/**
 *  * A daily challenge that will be completed once a player reaches 80 points in a single
 *  round without gamification mechanics.
 */
public class DailyReachRoundScoreEighty extends DailyChallenge {

    private final static String GERMAN_NAME = "Erreiche 80 Punkte nach einer einzelnen Runde.";
    private final static String ENGLISH_NAME = "Reach a score of 80 after a single round.";

    /**
     * Creates a daily challenge by setting the necessary values.
     */
    public DailyReachRoundScoreEighty() {
        this.germanName = GERMAN_NAME;
        this.englishName = ENGLISH_NAME;
        this.date = null;
        this.completed = false;
        this.reward = 50;
    }

    public DailyReachRoundScoreEighty(LocalDate date, boolean completed, int number) {
        this.date = date;
        this.completed = completed;
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
