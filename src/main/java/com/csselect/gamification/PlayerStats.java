package com.csselect.gamification;

import com.csselect.database.PlayerStatsAdapter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Implements the Gamification Interface. PlayerStats combines the defined gamification mechanics
 * and is responsible for the calculation of the score reached by a player.
 */
public class PlayerStats implements Gamification {

    private PlayerStatsAdapter playerStatsAdapter;
    private Streak streak;
    private DailyChallenge activeDaily;

    /**
     * Creates a new {@link PlayerStats} object with the given {@link PlayerStatsAdapter}
     * @param playerStatsAdapter playerstatsadapter for connection to the database
     */
    public PlayerStats(PlayerStatsAdapter playerStatsAdapter) {
        this.playerStatsAdapter = playerStatsAdapter;
        this.streak = new Streak();
        this.activeDaily = chooseRandomDaily();
    }

    @Override
    public int finishRound(double score) {
        int commutedScore = commuteScore(score);
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

        selectDaily();
        gamificationScore = addDailyBonus(activeDaily, gamificationScore);

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
        List<AchievementType> achievementTypes = Arrays.asList(AchievementType.values());
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

    /**
     * Gets the amount of played rounds of the player.
     * @return The amount of played rounds of the player.
     */
    public int getRoundsPlayed() {
        return playerStatsAdapter.getRoundsPlayed();
    }

    /**
     * Gets the amount of dailies that have been completed by the player.
     * @return The amount of dailies completed.
     */
    public int getDailiesCompleted() {
        return playerStatsAdapter.getDailiesCompleted();
    }

    /**
     * Gets the maximum score ever achieved by the player in a single round. This
     * does not take into account any gamification mechanics.
     * @return The maximum score of the player.
     */
    public int getMaxRoundScore() {
        return playerStatsAdapter.getMaxRoundScore();
    }

    /**
     * Gets the latest score of the player, meaning the points he received after
     * having completed a round. This does not take into account any gamification
     * mechanics.
     * @return The last score of the player.
     */
    public int getLastScore() {
        return playerStatsAdapter.getLastScore();
    }

    /**
     * Gets the highest streak ever reached by the player.
     * @return The highest streak of the player.
     */
    public int getHighestStreak() {
        return playerStatsAdapter.getHighestStreak();
    }

    /**
     * Algorithm to convert the score given by the ML-Server into the points that the player
     * will receive (without gamification mechanics). If the ML-Server score was originally lower
     * than 0.5, the player will obtain less points.
     * @param score Score given by the ML-Server.
     * @return Points the player will receive.
     */
    private int commuteScore(double score) {
        if (score < 0 || score > 1) {
            return 0;
        }

        if (score <= 0.5) {
            return (int) Math.ceil(score * 50);
        }

        return (int) Math.ceil(score * 100);
    }

    /**
     * Calculates bonus points if the streak is high enough.
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
        if (!dailyToCheck.isCompleted()) {

            if (dailyToCheck.checkFinished(this)) {
                newScore += dailyToCheck.getReward();
                playerStatsAdapter.completeDaily();
            }
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
     * Chooses a daily challenge randomly from the list of dailies.
     * @return The chosen daily.
     */
    private DailyChallenge chooseRandomDaily() {
        List<DailyChallenge> dailies = loadDailies();
        int randomIndex = (int) (Math.random() * dailies.size()); // Index between 0 and dailies.size() - 1
        return dailies.get(randomIndex);
    }

    /**
     * Loads the available dailies and puts them into the list.
     */
    private List<DailyChallenge> loadDailies() {
        List<DailyChallenge> allDailies = new LinkedList<>();
        allDailies.add(new DailyGetStreakThree());
        allDailies.add(new DailyPlayThreeRounds());
        allDailies.add(new DailyReachRoundScoreEighty());
        allDailies.add(new DailyReachScoreHundredFifty());
        return allDailies;
    }

}
