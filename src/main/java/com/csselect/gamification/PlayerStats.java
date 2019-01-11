package com.csselect.gamification;

import java.util.List;

/**
 * Implements the Gamification Interface. PlayerStats combines he defined gamification mechanics
 * and is responsible for the calculation of the score reached by a player.
 */
public class PlayerStats implements Gamification {

    @Override
    public int finishRound(double score) {
        return 0;
    }

    @Override
    public void skipRound() {

    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public List<Achievement> getAchievements() {
        return null;
    }

    @Override
    public Streak getStreak() {
        return null;
    }

    @Override
    public DailyChallenge getDaily() {
        return null;
    }
}
