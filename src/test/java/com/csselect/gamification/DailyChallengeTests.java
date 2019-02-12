package com.csselect.gamification;

import com.csselect.database.mock.MockPlayerStatsAdapter;
import com.csselect.inject.TestClass;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class DailyChallengeTests extends TestClass {

    private PlayerStats stats;

    @Override
    public void setUp() {
        this.stats = new PlayerStats(new MockPlayerStatsAdapter());
    }

    @Override
    public void reset() {

    }

    @Test
    public void testDailyGetStreakThree() {
        DailyChallenge daily = new DailyGetStreakThree();
        Assert.assertNotNull(daily);
        Assert.assertEquals(50, daily.getReward());
        Assert.assertEquals(LocalDate.now(), daily.getDate());

        // Streak: 0
        Assert.assertFalse(daily.isCompleted());

        // Streak: 1
        stats.finishRound(0.2);
        Assert.assertFalse(daily.checkFinished(stats));
        Assert.assertFalse(daily.isCompleted());

        // Streak: 2
        stats.finishRound(0.4);
        Assert.assertFalse(daily.checkFinished(stats));
        Assert.assertFalse(daily.isCompleted());

        // Streak: 3
        stats.finishRound(0.4);
        Assert.assertTrue(daily.checkFinished(stats));
        Assert.assertTrue(daily.isCompleted());
    }

    @Test
    public void testDailyPlayThreeRounds() {
        DailyChallenge daily = new DailyPlayThreeRounds();
        Assert.assertNotNull(daily);
        Assert.assertEquals(50, daily.getReward());
        Assert.assertEquals(LocalDate.now(), daily.getDate());

        // Rounds played: 0
        Assert.assertFalse(daily.isCompleted());

        // Rounds played: 1
        stats.finishRound(0.2);
        Assert.assertFalse(daily.checkFinished(stats));
        Assert.assertFalse(daily.isCompleted());

        // Rounds played: 2
        stats.finishRound(0.4);
        Assert.assertFalse(daily.checkFinished(stats));
        Assert.assertFalse(daily.isCompleted());

        // Rounds played: 3
        stats.finishRound(0.4);
        Assert.assertTrue(daily.checkFinished(stats));
        Assert.assertTrue(daily.isCompleted());
    }

    @Test
    public void testDailyReachRoundScoreEighty() {
        DailyChallenge daily = new DailyReachRoundScoreEighty();
        Assert.assertNotNull(daily);
        Assert.assertEquals(50, daily.getReward());
        Assert.assertEquals(LocalDate.now(), daily.getDate());

        // Round score: 0
        Assert.assertFalse(daily.isCompleted());

        // Round score: 10
        stats.finishRound(0.2);
        Assert.assertFalse(daily.checkFinished(stats));
        Assert.assertFalse(daily.isCompleted());

        // Round score: 20
        stats.finishRound(0.4);
        Assert.assertFalse(daily.checkFinished(stats));
        Assert.assertFalse(daily.isCompleted());

        // Round score: 80
        stats.finishRound(0.8);
        Assert.assertTrue(daily.checkFinished(stats));
        Assert.assertTrue(daily.isCompleted());
    }

    @Test
    public void testDailyReachScoreHundredFifty() {
        DailyChallenge daily = new DailyReachScoreHundredFifty();
        Assert.assertNotNull(daily);
        Assert.assertEquals(50, daily.getReward());
        Assert.assertEquals(LocalDate.now(), daily.getDate());

        // Total daily score: 0
        Assert.assertFalse(daily.isCompleted());

        // Total daily score: 60
        stats.finishRound(0.6);
        Assert.assertFalse(daily.checkFinished(stats));
        Assert.assertFalse(daily.isCompleted());

        // Total daily score: 80
        stats.finishRound(0.4);
        Assert.assertFalse(daily.checkFinished(stats));
        Assert.assertFalse(daily.isCompleted());

        // Total daily score: > 150
        stats.finishRound(0.9);
        Assert.assertTrue(daily.checkFinished(stats));
        Assert.assertTrue(daily.isCompleted());
    }
}
