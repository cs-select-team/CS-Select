package com.csselect.gamification;

import com.csselect.database.mock.MockPlayerStatsAdapter;
import com.csselect.inject.TestClass;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class PlayerStatsTests extends TestClass {

    private PlayerStats stats;

    @Override
    public void setUp() {
        stats = new PlayerStats(new MockPlayerStatsAdapter());
    }

    @Override
    public void reset() {

    }

    @Test
    public void loadingTest() {
        Assert.assertNotNull(stats);
    }

    @Test
    public void testLastScore() {
        Assert.assertEquals(0, stats.getLastScore()); // Initialised with 0.
        stats.finishRound(0.8);
        Assert.assertEquals(stats.computeScore(0.8), stats.getLastScore());
        stats.finishRound(0.44);
        Assert.assertEquals(stats.computeScore(0.44), stats.getLastScore());
        stats.finishRound(0.9);
        Assert.assertEquals(stats.computeScore(0.9), stats.getLastScore());
    }

    @Test
    public void testMaxRoundScore() {
        Assert.assertEquals(0, stats.getMaxRoundScore()); // Initialised with 0.
        stats.finishRound(0.8);
        int firstComputedScore = stats.computeScore(0.8);
        Assert.assertEquals(firstComputedScore, stats.getMaxRoundScore());
        stats.finishRound(0.44); // Score is lower, maxRoundScore should not change.
        Assert.assertEquals(firstComputedScore, stats.getMaxRoundScore());

        stats.finishRound(0.9);
        int secondComputedScore = stats.computeScore(0.9);
        Assert.assertEquals(secondComputedScore, stats.getMaxRoundScore());
        stats.finishRound(0.9); // Score is the same, maxRoundScore should not change.
        Assert.assertEquals(secondComputedScore, stats.getMaxRoundScore());
        stats.finishRound(0.55); // Score is lower, maxRoundScore should not change.
        Assert.assertEquals(secondComputedScore, stats.getMaxRoundScore());
    }

    @Test
    public void testRoundsPlayed() {
        Assert.assertEquals(0, stats.getRoundsPlayed()); // Initialised with 0.
        stats.finishRound(0.8);
        Assert.assertEquals(1, stats.getRoundsPlayed());
        stats.finishRound(0.44);
        Assert.assertEquals(2, stats.getRoundsPlayed());
        stats.finishRound(0.92);
        stats.finishRound(0.77);
        stats.finishRound(0.47);
        Assert.assertEquals(5, stats.getRoundsPlayed());
        stats.skipRound();
        // Skipping a round does not count as playing a round.
        Assert.assertEquals(5, stats.getRoundsPlayed());
    }

    @Test
    public void testHighestStreak() {
        Assert.assertEquals(0, stats.getHighestStreak()); // Initialised with 0.
        stats.finishRound(0.8);
        Assert.assertEquals(1, stats.getHighestStreak());
        stats.finishRound(0.2);
        Assert.assertEquals(2, stats.getHighestStreak());
        stats.getStreak().setZero();
        // Current streak resets, e.g. by logging out.
        stats.finishRound(0.44);
        Assert.assertEquals(2, stats.getHighestStreak());
        stats.finishRound(0.9);
        Assert.assertEquals(2, stats.getHighestStreak());
        stats.finishRound(0.82);
        Assert.assertEquals(3, stats.getHighestStreak());
    }

    /**
     * This test only works with the four dailies that currently exist, every one of them would be completed
     * after this simulation of played rounds.
     */
    @Test
    public void testDailiesCompleted() {
        Assert.assertEquals(0, stats.getDailiesCompleted()); // Initialised with 0.
        stats.finishRound(0.84);
        stats.finishRound(0.2);
        stats.finishRound(0.75);
        Assert.assertEquals(1, stats.getDailiesCompleted());
        stats.finishRound(0.84);
        stats.finishRound(0.2);
        stats.finishRound(0.75);
        // Daily was already completed on this day.
        Assert.assertEquals(1, stats.getDailiesCompleted());
    }

    @Test
    public void testDailies() {
        Assert.assertNotNull(stats.getDaily());
        Assert.assertEquals(stats.getDaily().getDate(), LocalDate.now());
        Assert.assertFalse(stats.getDaily().isCompleted());
    }

    @Test
    public void testStreak() {
        Assert.assertNotNull(stats.getStreak());
        Assert.assertEquals(0, stats.getStreak().getCounter());
        stats.finishRound(0.2);
        Assert.assertEquals(1, stats.getStreak().getCounter());
        stats.finishRound(0.9);
        Assert.assertEquals(2, stats.getStreak().getCounter());
    }

    @Test
    public void testAchievements() {
        Assert.assertNotNull(stats.getAchievements());
        Assert.assertEquals(AchievementType.values().length, stats.getAchievements().size());
    }

    @Test
    public void testSkipRound() {
        stats.finishRound(0.3);
        int computedScore = stats.computeScore(0.3);

        Assert.assertEquals(1, stats.getStreak().getCounter());
        Assert.assertEquals(computedScore, stats.getScore());
        stats.skipRound();
        // Score stays the same, but the streak resets.
        Assert.assertEquals(0, stats.getStreak().getCounter());
        Assert.assertEquals(computedScore, stats.getScore());
    }

    @Test
    public void testLogout() {
        stats.finishRound(0.3);
        int computedScore = stats.computeScore(0.3);

        Assert.assertEquals(1, stats.getStreak().getCounter());
        Assert.assertEquals(computedScore, stats.getScore());

        stats.logout();
        // Score stays the same, but the streak resets.
        Assert.assertEquals(0, stats.getStreak().getCounter());
        Assert.assertEquals(computedScore, stats.getScore());
    }

    @Test
    public void testComputeScore() {
        Assert.assertEquals(0, stats.computeScore(0));
        Assert.assertEquals(10, stats.computeScore(0.2));
        Assert.assertEquals(25, stats.computeScore(0.5));
        Assert.assertEquals(70, stats.computeScore(0.7));
        Assert.assertEquals(98, stats.computeScore(0.98));
        Assert.assertEquals(100, stats.computeScore(1));
    }

    @Test(expected = AssertionError.class)
    public void testComputeWrongScore() {
        stats.computeScore(-1);
        stats.computeScore(0);
        stats.computeScore(44);
    }

}
