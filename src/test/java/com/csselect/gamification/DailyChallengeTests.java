package com.csselect.gamification;

import com.csselect.TestClass;
import com.csselect.database.PlayerStatsAdapter;
import com.csselect.database.mock.MockPlayerStatsAdapter;
import org.junit.Assert;
import org.junit.Test;

public class DailyChallengeTests extends TestClass {

    private PlayerStats stats;
    private PlayerStatsAdapter playerStatsAdapter;

    @Override
    public void setUp() {
        this.playerStatsAdapter = new MockPlayerStatsAdapter();
        this.stats = new PlayerStats(playerStatsAdapter);
    }

    @Override
    public void reset() {

    }

    @Test
    public void testDailyGetStreakThree() {
        DailyChallenge daily = new DailyGetStreakThree();
        Assert.assertNotNull(daily);
        Assert.assertEquals("Spiele drei Runden am Stück.", daily.getGermanName());
        Assert.assertEquals("Play three rounds in a row.", daily.getEnglishName());
        Assert.assertEquals(50, daily.getReward());
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
        Assert.assertEquals("Spiele heute drei Runden.", daily.getGermanName());
        Assert.assertEquals("Play three rounds today.", daily.getEnglishName());
        Assert.assertEquals(50, daily.getReward());

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
        Assert.assertEquals("Erreiche 80 Punkte nach einer einzelnen Runde.", daily.getGermanName());
        Assert.assertEquals("Reach a score of 80 after a single round.", daily.getEnglishName());
        Assert.assertEquals(50, daily.getReward());
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
        Assert.assertEquals("Erreiche heute insgesamt 150 Punkte.", daily.getGermanName());
        Assert.assertEquals("Achieve a total score of 150 today.", daily.getEnglishName());
        Assert.assertEquals(50, daily.getReward());
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
