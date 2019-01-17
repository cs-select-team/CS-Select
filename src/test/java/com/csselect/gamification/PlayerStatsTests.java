package com.csselect.gamification;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDate;

public class PlayerStatsTests {

    private PlayerStats stats;

    @Before
    public void initialise() {
        stats = new PlayerStats();
    }

    @Test
    public void loadingTest() {
        Assert.assertNotNull(stats);
    }

    @Ignore("Not ready yet.")
    @Test
    public void testFinishRoundWithoutGamification() {
        Assert.assertEquals(stats.finishRound(0.2), 10);
        Assert.assertEquals(stats.finishRound(0.44), 22);
        // Assert.assertEquals(stats.finishRound(0.50), 25);
        // Assert.assertEquals(stats.finishRound(0.51), 51);
        // Assert.assertEquals(stats.finishRound(0.8), 80);
        // Assert.assertEquals(stats.finishRound(0.99), 99);
        // Assert.assertEquals(stats.finishRound(1), 100);
        // Assert.assertEquals(stats.finishRound(1.01), 0);
        // Assert.assertEquals(stats.finishRound(0), 0);
        // Assert.assertEquals(stats.finishRound(-0.4), 0);
    }

    @Ignore("Outdated, because of dailies.")
    @Test
    public void testFinishRoundWithStreak() {
        Assert.assertEquals(stats.finishRound(0.8), 80);
        Assert.assertEquals(stats.finishRound(0.6), 60);
        Assert.assertEquals(stats.finishRound(0.8), 120);
        Assert.assertEquals(stats.finishRound(0.75), 112);
        Assert.assertEquals(stats.finishRound(0.9), 180);
        Assert.assertEquals(stats.finishRound(0.66), 132);
        stats.getStreak().setZero();
        Assert.assertEquals(stats.finishRound(0.66), 66);
    }

    @Test
    public void testLastScore() {
        Assert.assertEquals(stats.getLastScore(), 0);
        stats.finishRound(0.8);
        Assert.assertEquals(stats.getLastScore(), 80);
        stats.finishRound(0.44);
        Assert.assertEquals(stats.getLastScore(), 22);
        stats.finishRound(0.9);
        Assert.assertEquals(stats.getLastScore(), 90);
    }

    @Test
    public void testMaxRoundScore() {
        Assert.assertEquals(stats.getMaxRoundScore(), 0);
        stats.finishRound(0.8);
        Assert.assertEquals(stats.getMaxRoundScore(), 80);
        stats.finishRound(0.44);
        Assert.assertEquals(stats.getMaxRoundScore(), 80);
        stats.finishRound(0.9);
        Assert.assertEquals(stats.getMaxRoundScore(), 90);
        stats.finishRound(0.9);
        Assert.assertEquals(stats.getMaxRoundScore(), 90);
    }

    @Test
    public void testRoundsPlayed() {
        Assert.assertEquals(stats.getRoundsPlayed(), 0);
        stats.finishRound(0.8);
        Assert.assertEquals(stats.getRoundsPlayed(), 1);
        stats.finishRound(0.44);
        Assert.assertEquals(stats.getRoundsPlayed(), 2);
        stats.finishRound(0.92);
        stats.finishRound(0.77);
        stats.finishRound(0.47);
        Assert.assertEquals(stats.getRoundsPlayed(), 5);
    }

    @Test
    public void testHighestStreak() {
        Assert.assertEquals(stats.getHighestStreak(), 0);
        stats.finishRound(0.8);
        Assert.assertEquals(stats.getHighestStreak(), 1);
        stats.finishRound(0.2);
        Assert.assertEquals(stats.getHighestStreak(), 2);
        stats.getStreak().setZero();
        stats.finishRound(0.44);
        Assert.assertEquals(stats.getHighestStreak(), 2);
        stats.finishRound(0.9);
        Assert.assertEquals(stats.getHighestStreak(), 2);
        stats.finishRound(0.82);
        Assert.assertEquals(stats.getHighestStreak(), 3);
        stats.finishRound(0.81);
        Assert.assertEquals(stats.getHighestStreak(), 4);
    }

    @Test
    public void testDailiesCompleted() {
        Assert.assertEquals(stats.getDailiesCompleted(), 0);
        Assert.assertFalse(stats.getDaily().isCompleted());
        stats.finishRound(0.84);
        stats.finishRound(0.2);
        stats.finishRound(0.75);
        Assert.assertEquals(stats.getDailiesCompleted(), 1);
        Assert.assertTrue(stats.getDaily().isCompleted());
        stats.finishRound(0.2);
        stats.finishRound(0.75);
        Assert.assertEquals(stats.getDailiesCompleted(), 1);
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
        Assert.assertEquals(stats.getStreak().getCounter(), 0);
        stats.finishRound(0.2);
        Assert.assertEquals(stats.getStreak().getCounter(), 1);
        stats.finishRound(0.9);
        Assert.assertEquals(stats.getStreak().getCounter(), 2);
        stats.getStreak().setZero();
        Assert.assertEquals(stats.getStreak().getCounter(), 0);
        stats.finishRound(0.33);
        Assert.assertEquals(stats.getStreak().getCounter(), 1);
    }
}
