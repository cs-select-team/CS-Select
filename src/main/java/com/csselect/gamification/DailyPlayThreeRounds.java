package com.csselect.gamification;

/**
 * A daily challenge that will be completed once a player plays three games
 * in one day.
 */
public class DailyPlayThreeRounds extends DailyChallenge {

    private final static String GERMAN_NAME = "Spiele heute drei Runden.";
    private final static String ENGLISH_NAME = "Play three rounds today.";
    private int dailyRounds;

    /**
     * Creates a daily challenge by setting the necessary values.
     */
    public DailyPlayThreeRounds() {
        this.germanName = GERMAN_NAME;
        this.englishName = ENGLISH_NAME;
        this.date = null;
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

    @Override
    public void resetDaily() {
        completed = false;
        dailyRounds = 0;
    }
}
