package com.csselect.gamification;

import com.csselect.TestClass;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class PlayerStatsTests extends TestClass {

    private PlayerStats stats;


    @Override
    public void setUp() {
        stats = new PlayerStats();
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
        Assert.assertEquals(0, stats.getLastScore());
        stats.finishRound(0.8);
        Assert.assertEquals(80, stats.getLastScore());
        stats.finishRound(0.44);
        Assert.assertEquals(22, stats.getLastScore());
        stats.finishRound(0.9);
        Assert.assertEquals(90, stats.getLastScore());
    }

    @Test
    public void testMaxRoundScore() {
        Assert.assertEquals(0, stats.getMaxRoundScore());
        stats.finishRound(0.8);
        Assert.assertEquals(80, stats.getMaxRoundScore());
        stats.finishRound(0.44);
        Assert.assertEquals(80, stats.getMaxRoundScore());
        stats.finishRound(0.9);
        Assert.assertEquals(90, stats.getMaxRoundScore());
        stats.finishRound(0.9);
        Assert.assertEquals(90, stats.getMaxRoundScore());
        stats.finishRound(0.91);
        Assert.assertEquals(91, stats.getMaxRoundScore());
    }

    @Test
    public void testRoundsPlayed() {
        Assert.assertEquals(0, stats.getRoundsPlayed());
        stats.finishRound(0.8);
        Assert.assertEquals(1, stats.getRoundsPlayed());
        stats.finishRound(0.44);
        Assert.assertEquals(2, stats.getRoundsPlayed());
        stats.finishRound(0.92);
        stats.finishRound(0.77);
        stats.finishRound(0.47);
        Assert.assertEquals(5, stats.getRoundsPlayed());
    }

    @Test
    public void testHighestStreak() {
        Assert.assertEquals(0, stats.getHighestStreak());
        stats.finishRound(0.8);
        Assert.assertEquals(1, stats.getHighestStreak());
        stats.finishRound(0.2);
        Assert.assertEquals(2, stats.getHighestStreak());
        stats.getStreak().setZero();
        stats.finishRound(0.44);
        Assert.assertEquals(2, stats.getHighestStreak());
        stats.finishRound(0.9);
        Assert.assertEquals(2, stats.getHighestStreak());
        stats.finishRound(0.82);
        Assert.assertEquals(3, stats.getHighestStreak());
        stats.finishRound(0.81);
        Assert.assertEquals(4, stats.getHighestStreak());
    }

    @Test
    public void testDailiesCompleted() {
        Assert.assertEquals(0, stats.getDailiesCompleted());
        Assert.assertFalse(stats.getDaily().isCompleted());
        stats.finishRound(0.84);
        stats.finishRound(0.2);
        stats.finishRound(0.75);
        Assert.assertEquals(1, stats.getDailiesCompleted());
        Assert.assertTrue(stats.getDaily().isCompleted());
        stats.finishRound(0.2);
        stats.finishRound(0.75);
        Assert.assertEquals(1, stats.getDailiesCompleted());
        Assert.assertTrue(stats.getDaily().isCompleted());
    }

    /**
     * Dailies are chosen randomly, difficult to test.
     */
    @Test
    public void testDailies() {
        Assert.assertNotNull(stats.getDaily());
        Assert.assertEquals(stats.getDaily().getDate(), LocalDate.now());
    }

    @Test
    public void testStreaks() {
        Assert.assertNotNull(stats.getStreak());
        Assert.assertEquals(0, stats.getStreak().getCounter());
        stats.finishRound(0.2);
        Assert.assertEquals(1, stats.getStreak().getCounter());
        stats.finishRound(0.9);
        Assert.assertEquals(2, stats.getStreak().getCounter());
        stats.getStreak().setZero();
        Assert.assertEquals(0, stats.getStreak().getCounter());
        stats.finishRound(0.33);
        Assert.assertEquals(1, stats.getStreak().getCounter());
    }

    @Test
    public void testAchievements() {
        Assert.assertNotNull(stats.getAchievements());
        Assert.assertFalse(stats.getAchievements().isEmpty());
        Assert.assertEquals(21, stats.getAchievements().size());
    }

    @Test
    public void testSkipRound() {
        stats.finishRound(0.3);
        Assert.assertEquals(1, stats.getStreak().getCounter());
        stats.skipRound();
        Assert.assertEquals(0, stats.getStreak().getCounter());
    }

    @Test
    public void testScore() {
        Assert.assertEquals(0, stats.getScore());
        stats.finishRound(0.2);
        Assert.assertEquals(10, stats.getScore());
        stats.finishRound(0.6);
        Assert.assertEquals(70, stats.getScore());
    }
}
