package com.csselect.gamification;

import com.csselect.database.PlayerStatsAdapter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Implements the Gamification Interface. PlayerStats combines he defined gamification mechanics
 * and is responsible for the calculation of the score reached by a player.
 */
public class PlayerStats implements Gamification {

    private PlayerStatsAdapter databaseAdapter;
    private Streak streak;
    private DailyChallenge activeDaily;
    private List<DailyChallenge> dailies;
    private int score;
    private int roundsPlayed;
    private int dailiesCompleted;
    private int maxRoundScore;
    private int lastScore;
    private int highestStreak;

    public PlayerStats(PlayerStatsAdapter databaseAdapter) {
        this.databaseAdapter = databaseAdapter;
        // TODO
    }

    public PlayerStats() {
        this.streak = new Streak();
        this.dailies = loadDailies();
        this.activeDaily = chooseRandomDaily();
        this.score = 0;
        this.roundsPlayed = 0;
        this.dailiesCompleted = 0;
        this.maxRoundScore = 0;
        this.lastScore = 0;
        this.highestStreak = 0;
    }


    @Override
    public int finishRound(double score) {
        int commutedScore = commuteScore(score);
        lastScore = commutedScore;

        if (maxRoundScore < commutedScore) {
            maxRoundScore = commutedScore;
        }

        streak.increaseStreak();
        int newStreak = streak.getCounter();
        if (highestStreak < newStreak) {
            highestStreak = newStreak;
        }

        int gamificationScore = addStreakBonus(newStreak, commutedScore);

        selectDaily();
        gamificationScore = addDailyBonus(activeDaily, gamificationScore);

        roundsPlayed += 1;
        this.score += gamificationScore;

        return gamificationScore;
    }

    @Override
    public void skipRound() {
        streak.setZero();
    }

    @Override
    public int getScore() {
        return score;
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
        return roundsPlayed;
    }

    /**
     * Gets the amount of dailies that have been completed by the player.
     * @return The amount of dailies completed.
     */
    public int getDailiesCompleted() {
        return dailiesCompleted;
    }

    /**
     * Gets the maximum score achieved by the player in a single round. This
     * does not take into account any gamification mechanics.
     * @return The maximum score of the player.
     */
    public int getMaxRoundScore() {
        return maxRoundScore;
    }

    /**
     * Gets the latest score of the player, meaning the points he received after
     * having completed a round. This does not take into account any gamification
     * mechanics.
     * @return The last score of the player.
     */
    public int getLastScore() {
        return lastScore;
    }

    /**
     * Gets the highest streak ever reached of the player.
     * @return The highest streak of the player.
     */
    public int getHighestStreak() {
        return highestStreak;
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
            int lowScore = (int) Math.ceil(score * 50);
            return lowScore;
        }

        int commutedScore = (int) Math.ceil(score * 100);
        return commutedScore;
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

    private int addDailyBonus(DailyChallenge dailyToCheck, int oldScore) {
        int newScore = oldScore;
        if (!dailyToCheck.isCompleted()) {

            if (dailyToCheck.checkFinished(this)) {
                newScore += dailyToCheck.getReward();
                dailiesCompleted++;
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
     * Chooses a daily challenge randomly from the list of dailies and sets its date to today.
     * @return The chosen daily.
     */
    private DailyChallenge chooseRandomDaily() {
        int randomIndex = (int) (Math.random() * dailies.size()); // Index between 0 and dailies.size() - 1
        DailyChallenge newDaily = dailies.get(randomIndex);
        newDaily.resetDaily();
        newDaily.setDate(LocalDate.now());
        return newDaily;
    }

    /**
     * Loads the available dailies and puts them into the list.
     */
    private List<DailyChallenge> loadDailies() {
        // Erstmal hartkodieren fürs Testen
        List<DailyChallenge> allDailies = new LinkedList<>();
        allDailies.add(new DailyGetStreakThree());
        allDailies.add(new DailyPlayThreeRounds());
        allDailies.add(new DailyReachRoundScoreEighty());
        allDailies.add(new DailyReachScoreHundredFifty());
        return allDailies;
    }

}
