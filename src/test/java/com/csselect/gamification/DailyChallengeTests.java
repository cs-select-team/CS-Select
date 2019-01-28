package com.csselect.gamification;

import com.csselect.TestClass;
import com.csselect.database.mock.MockPlayerStatsAdapter;
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
        Assert.assertEquals("Spiele drei Runden am Stück.", daily.getDescription("de"));
        Assert.assertEquals("Play three rounds in a row.", daily.getDescription("en"));
        Assert.assertEquals(50, daily.getReward());
        Assert.assertEquals(LocalDate.now(), daily.getDate());
        Assert.assertFalse(daily.checkFinished(stats));
        Assert.assertFalse(daily.isCompleted());

        stats.finishRound(0.2);
        Assert.assertFalse(daily.checkFinished(stats));
        Assert.assertFalse(daily.isCompleted());

        stats.finishRound(0.4);
        Assert.assertFalse(daily.checkFinished(stats));
        Assert.assertFalse(daily.isCompleted());

        stats.finishRound(0.4);
        Assert.assertTrue(daily.checkFinished(stats));
        Assert.assertTrue(daily.isCompleted());
    }

    @Test
    public void testDailyPlayThreeRounds() {
        DailyChallenge daily = new DailyPlayThreeRounds();
        Assert.assertNotNull(daily);
        Assert.assertEquals("Spiele heute drei Runden.", daily.getDescription("de"));
        Assert.assertEquals("Play three rounds today.", daily.getDescription("en"));
        Assert.assertEquals(50, daily.getReward());
        Assert.assertEquals(LocalDate.now(), daily.getDate());

        Assert.assertFalse(daily.isCompleted());

        stats.finishRound(0.2);
        Assert.assertFalse(daily.checkFinished(stats));
        Assert.assertFalse(daily.isCompleted());

        stats.finishRound(0.4);
        Assert.assertFalse(daily.checkFinished(stats));
        Assert.assertFalse(daily.isCompleted());

        stats.finishRound(0.4);
        Assert.assertTrue(daily.checkFinished(stats));
        Assert.assertTrue(daily.isCompleted());
    }

    @Test
    public void testDailyReachRoundScoreEighty() {
        DailyChallenge daily = new DailyReachRoundScoreEighty();
        Assert.assertNotNull(daily);
        Assert.assertEquals("Erreiche 80 Punkte nach einer einzelnen Runde.", daily.getDescription("de"));
        Assert.assertEquals("Reach a score of 80 after a single round.", daily.getDescription("en"));
        Assert.assertEquals(50, daily.getReward());
        Assert.assertEquals(LocalDate.now(), daily.getDate());
        Assert.assertFalse(daily.checkFinished(stats));
        Assert.assertFalse(daily.isCompleted());

        stats.finishRound(0.2);
        Assert.assertFalse(daily.checkFinished(stats));
        Assert.assertFalse(daily.isCompleted());

        stats.finishRound(0.4);
        Assert.assertFalse(daily.checkFinished(stats));
        Assert.assertFalse(daily.isCompleted());

        stats.finishRound(0.8);
        Assert.assertTrue(daily.checkFinished(stats));
        Assert.assertTrue(daily.isCompleted());
    }

    @Test
    public void testDailyReachScoreHundredFifty() {
        DailyChallenge daily = new DailyReachScoreHundredFifty();
        Assert.assertNotNull(daily);
        Assert.assertEquals("Erreiche heute insgesamt 150 Punkte.", daily.getDescription("de"));
        Assert.assertEquals("Achieve a total score of 150 today.", daily.getDescription("en"));
        Assert.assertEquals(50, daily.getReward());
        Assert.assertEquals(LocalDate.now(), daily.getDate());
        Assert.assertFalse(daily.checkFinished(stats));
        Assert.assertFalse(daily.isCompleted());

        stats.finishRound(0.6);
        Assert.assertFalse(daily.checkFinished(stats));
        Assert.assertFalse(daily.isCompleted());

        stats.finishRound(0.4);
        Assert.assertFalse(daily.checkFinished(stats));
        Assert.assertFalse(daily.isCompleted());

        stats.finishRound(0.9);
        Assert.assertTrue(daily.checkFinished(stats));
        Assert.assertTrue(daily.isCompleted());
    }
}
