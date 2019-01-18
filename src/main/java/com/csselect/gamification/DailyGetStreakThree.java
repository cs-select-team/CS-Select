package com.csselect.gamification;

/**
 * A daily challenge that will be completed once a player reaches a streak
 * of 3 in one day.
 */
public class DailyGetStreakThree extends DailyChallenge {

    private int dailyStreak;

    /**
     * Creates a daily challenge by setting the necessary values.
     */
    public DailyGetStreakThree() {
        this.name = "";
        this.description = "";
        this.date = null;
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

    @Override
    public void resetDaily() {
        completed = false;
        dailyStreak = 0;
    }
}
