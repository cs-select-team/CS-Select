package com.csselect.gamification;

import com.csselect.database.PlayerStatsAdapter;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Implements the Gamification Interface. PlayerStats combines the defined gamification mechanics
 * and is responsible for the calculation of the score reached by a player.
 */
public class PlayerStats implements Gamification {

    private static final int NUMBER_OF_DAILIES = 4;
    private final PlayerStatsAdapter playerStatsAdapter;
    private final Streak streak;
    private DailyChallenge activeDaily;

    /**
     * Creates a new {@link PlayerStats} object with the given {@link PlayerStatsAdapter}.
     * @param playerStatsAdapter PlayerStatsAdapter for connection to the database.
     */
    public PlayerStats(PlayerStatsAdapter playerStatsAdapter) {
        this.playerStatsAdapter = playerStatsAdapter;
        this.streak = new Streak();
        this.activeDaily = chooseRandomDaily();
    }

    @Override
    public int finishRound(double score) {
        int commutedScore = computeScore(score);
        playerStatsAdapter.setLastScore(commutedScore);

        if (playerStatsAdapter.getMaxRoundScore() < commutedScore) {
            playerStatsAdapter.setMaxRoundScore(commutedScore);
        }

        streak.increaseStreak();
        int newStreak = streak.getCounter();
        if (playerStatsAdapter.getHighestStreak() < newStreak) {
            playerStatsAdapter.setHighestStreak(newStreak);
        }

        int gamificationScore = addStreakBonus(newStreak, commutedScore);

        gamificationScore = addDailyBonus(getDaily(), gamificationScore);

        playerStatsAdapter.playRound();
        playerStatsAdapter.addScore(gamificationScore);

        return gamificationScore;
    }

    @Override
    public void skipRound() {
        streak.setZero();
    }

    @Override
    public int getScore() {
        return playerStatsAdapter.getScore();
    }

    @Override
    public List<Achievement> getAchievements() {
        AchievementType[] achievementTypes = AchievementType.values();
        List<Achievement> achievements = new LinkedList<>();
        for (AchievementType type : achievementTypes) {
            achievements.add(type.checkProgress(this));
        }
        return achievements;
    }

    @Override
    public Streak getStreak() {
        return streak;
    }

    @Override
    public DailyChallenge getDaily() {
        selectDaily();
        return activeDaily;
    }

    @Override
    public void logout() {
        streak.setZero();
    }

    /**
     * Gets the amount of played rounds of the player.
     * @return The amount of played rounds of the player.
     */
    int getRoundsPlayed() {
        return playerStatsAdapter.getRoundsPlayed();
    }

    /**
     * Gets the amount of dailies that have been completed by the player.
     * @return The amount of dailies completed.
     */
    int getDailiesCompleted() {
        return playerStatsAdapter.getDailiesCompleted();
    }

    /**
     * Gets the maximum score ever achieved by the player in a single round. This
     * does not take into account any gamification mechanics.
     * @return The maximum score of the player.
     */
    int getMaxRoundScore() {
        return playerStatsAdapter.getMaxRoundScore();
    }

    /**
     * Gets the latest score of the player, meaning the points he received after
     * having completed a round. This does not take into account any gamification
     * mechanics.
     * @return The last score of the player.
     */
    int getLastScore() {
        return playerStatsAdapter.getLastScore();
    }

    /**
     * Gets the highest streak ever reached by the player.
     * @return The highest streak of the player.
     */
    int getHighestStreak() {
        return playerStatsAdapter.getHighestStreak();
    }

    /**
     * Algorithm to convert the score given by the ML-Server into the points that the player
     * will receive (without gamification mechanics). If the ML-Server score was originally lower
     * than 0.5, the player will obtain less points. Otherwise it is multiplied by 100.
     * @param score Score given by the ML-Server.
     * @return Points the player will receive.
     */
    int computeScore(double score) {
        if (score < 0 || score > 1) {
            // Score should always be in [0,1].
            assert false;
            return 0;
        }

        if (score <= 0.5) {
            return (int) Math.ceil(score * 50);
        }

        return (int) Math.ceil(score * 100);
    }

    /**
     * Calculates bonus points if the streak is high enough. Score is multiplied by 1.5 if streak is at least 3
     * and doubled if streak is at least 5.
     * @param currentStreak The current streak.
     * @param oldScore The current score.
     * @return The new score. If the streak is too low, the score stays the same.
     */
    private int addStreakBonus(int currentStreak, int oldScore) {
        int newScore = oldScore;
        if (currentStreak >= 5) {
            newScore = newScore * 2;
        } else if (currentStreak >= 3) {
            newScore = newScore + newScore / 2;
        }
        return newScore;
    }

    /**
     * Calculates bonus points if the daily challenge has been finished.
     * @param dailyToCheck The current daily challenge.
     * @param oldScore The current score.
     * @return The new score. If the daily has not been finished or already has been finished, the
     * score stays the same.
     */
    private int addDailyBonus(DailyChallenge dailyToCheck, int oldScore) {
        int newScore = oldScore;
        if (!dailyToCheck.isCompleted() && dailyToCheck.checkFinished(this)) {
            newScore += dailyToCheck.getReward();
            playerStatsAdapter.completeDaily();
        }
        return newScore;
    }

    /**
     * Changes the current daily if it is still from another day. Otherwise the active daily stays
     * the same.
     */
    private void selectDaily() {
        if (activeDaily == null) {
            activeDaily = chooseRandomDaily();
            return;
        }

        LocalDate today = LocalDate.now();
        if (!today.isEqual(activeDaily.getDate())) {
            activeDaily = chooseRandomDaily();
        }
    }

    /**
     * Chooses a daily challenge randomly.
     * @return The chosen daily.
     */
    private DailyChallenge chooseRandomDaily() {
        /* If you add a new daily, do not
           forget to update the field NUMBER_OF_DAILIES
           and to modify the switch case. */
        int randomIndex = new Random().nextInt(NUMBER_OF_DAILIES);

        switch (randomIndex) {
            case 0:
                return new DailyGetStreakThree();

            case 1:
                return new DailyPlayThreeRounds();

            case 2:
                return new DailyReachRoundScoreEighty();

            case 3:
                return new DailyReachScoreHundredFifty();

            default:
                assert false;
                return null;
        }
    }

}
