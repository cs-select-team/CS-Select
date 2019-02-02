package com.csselect.gamification;

import com.csselect.TestClass;
import com.csselect.database.mock.MockPlayerStatsAdapter;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class AchievementTests extends TestClass {

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
        Assert.assertNotNull(stats.getAchievements());
        Assert.assertFalse(stats.getAchievements().isEmpty());
        Assert.assertEquals(21, stats.getAchievements().size());
    }

    @Test
    public void testPlayRounds() {
        List<Achievement> ach = stats.getAchievements();
        Assert.assertEquals(AchievementState.SHOWN, ach.get(0).getState());
        Assert.assertEquals(AchievementState.CONCEALED, ach.get(1).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, ach.get(2).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, ach.get(3).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, ach.get(4).getState());

        stats.finishRound(0.4);

        ach = stats.getAchievements();
        Assert.assertEquals(AchievementState.FINISHED, ach.get(0).getState());
        Assert.assertEquals(AchievementState.SHOWN, ach.get(1).getState());
        Assert.assertEquals(AchievementState.CONCEALED, ach.get(2).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, ach.get(3).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, ach.get(4).getState());

        for (int i = 0; i < 40; i++) {
            stats.finishRound(0.3);
        }

        ach = stats.getAchievements();
        Assert.assertEquals(AchievementState.FINISHED, ach.get(0).getState());
        Assert.assertEquals(AchievementState.FINISHED, ach.get(1).getState());
        Assert.assertEquals(AchievementState.FINISHED, ach.get(2).getState());
        Assert.assertEquals(AchievementState.SHOWN, ach.get(3).getState());
        Assert.assertEquals(AchievementState.CONCEALED, ach.get(4).getState());

        stats.finishRound(0.1);
        ach = stats.getAchievements();
        Assert.assertEquals(AchievementState.FINISHED, ach.get(0).getState());
        Assert.assertEquals(AchievementState.FINISHED, ach.get(1).getState());
        Assert.assertEquals(AchievementState.FINISHED, ach.get(2).getState());
        Assert.assertEquals(AchievementState.FINISHED, ach.get(3).getState());
        Assert.assertEquals(AchievementState.SHOWN, ach.get(4).getState());
    }

    @Test
    public void testGetStreak() {
        List<Achievement> ach = stats.getAchievements();
        Assert.assertEquals(AchievementState.SHOWN, ach.get(5).getState());
        Assert.assertEquals(AchievementState.CONCEALED, ach.get(6).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, ach.get(7).getState());

        stats.finishRound(0.4);
        stats.finishRound(0.66);

        ach = stats.getAchievements();
        Assert.assertEquals(AchievementState.FINISHED, ach.get(5).getState());
        Assert.assertEquals(AchievementState.SHOWN, ach.get(6).getState());
        Assert.assertEquals(AchievementState.CONCEALED, ach.get(7).getState());

        for (int i = 0; i < 7; i++) {
            stats.finishRound(0.3);
        }

        ach = stats.getAchievements();
        Assert.assertEquals(AchievementState.FINISHED, ach.get(5).getState());
        Assert.assertEquals(AchievementState.FINISHED, ach.get(6).getState());
        Assert.assertEquals(AchievementState.SHOWN, ach.get(7).getState());

        stats.finishRound(0.1);
        ach = stats.getAchievements();
        Assert.assertEquals(AchievementState.FINISHED, ach.get(5).getState());
        Assert.assertEquals(AchievementState.FINISHED, ach.get(6).getState());
        Assert.assertEquals(AchievementState.FINISHED, ach.get(7).getState());
    }

    @Test
    public void testCompleteDaily() {
        List<Achievement> ach = stats.getAchievements();
        Assert.assertEquals(AchievementState.SHOWN, ach.get(8).getState());
        Assert.assertEquals(AchievementState.CONCEALED, ach.get(9).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, ach.get(10).getState());

        for (int i = 0; i < 3; i++) {
            stats.finishRound(0.99);
        }

        ach = stats.getAchievements();
        Assert.assertEquals(AchievementState.FINISHED, ach.get(8).getState());
        Assert.assertEquals(AchievementState.SHOWN, ach.get(9).getState());
        Assert.assertEquals(AchievementState.CONCEALED, ach.get(10).getState());
    }

    @Test
    public void testTotalScore() {
        List<Achievement> ach = stats.getAchievements();
        Assert.assertEquals(AchievementState.SHOWN, ach.get(11).getState());
        Assert.assertEquals(AchievementState.CONCEALED, ach.get(12).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, ach.get(13).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, ach.get(14).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, ach.get(15).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, ach.get(15).getState());

        stats.finishRound(1);

        ach = stats.getAchievements();
        Assert.assertEquals(AchievementState.FINISHED, ach.get(11).getState());
        Assert.assertEquals(AchievementState.SHOWN, ach.get(12).getState());
        Assert.assertEquals(AchievementState.CONCEALED, ach.get(13).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, ach.get(14).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, ach.get(15).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, ach.get(15).getState());

        for (int i = 0; i < 12; i++) {
            stats.finishRound(1);
        }

        ach = stats.getAchievements();
        Assert.assertEquals(AchievementState.FINISHED, ach.get(11).getState());
        Assert.assertEquals(AchievementState.FINISHED, ach.get(12).getState());
        Assert.assertEquals(AchievementState.FINISHED, ach.get(13).getState());
        Assert.assertEquals(AchievementState.FINISHED, ach.get(14).getState());
        Assert.assertEquals(AchievementState.FINISHED, ach.get(15).getState());
        Assert.assertEquals(AchievementState.SHOWN, ach.get(16).getState());

        for (int i = 0; i < 20; i++) {
            stats.finishRound(1);
        }

        ach = stats.getAchievements();
        Assert.assertEquals(AchievementState.FINISHED, ach.get(11).getState());
        Assert.assertEquals(AchievementState.FINISHED, ach.get(12).getState());
        Assert.assertEquals(AchievementState.FINISHED, ach.get(13).getState());
        Assert.assertEquals(AchievementState.FINISHED, ach.get(14).getState());
        Assert.assertEquals(AchievementState.FINISHED, ach.get(15).getState());
        Assert.assertEquals(AchievementState.FINISHED, ach.get(16).getState());
    }

    @Test
    public void testRoundScore() {
        List<Achievement> ach = stats.getAchievements();
        Assert.assertEquals(AchievementState.SHOWN, ach.get(17).getState());
        Assert.assertEquals(AchievementState.CONCEALED, ach.get(18).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, ach.get(19).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, ach.get(20).getState());

        stats.finishRound(0.4);
        stats.finishRound(0.57);

        ach = stats.getAchievements();
        Assert.assertEquals(AchievementState.SHOWN, ach.get(17).getState());
        Assert.assertEquals(AchievementState.CONCEALED, ach.get(18).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, ach.get(19).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, ach.get(20).getState());

        stats.finishRound(0.74);

        ach = stats.getAchievements();
        Assert.assertEquals(AchievementState.FINISHED, ach.get(17).getState());
        Assert.assertEquals(AchievementState.FINISHED, ach.get(18).getState());
        Assert.assertEquals(AchievementState.SHOWN, ach.get(19).getState());
        Assert.assertEquals(AchievementState.CONCEALED, ach.get(20).getState());

        stats.finishRound(0.9);

        ach = stats.getAchievements();
        Assert.assertEquals(AchievementState.FINISHED, ach.get(17).getState());
        Assert.assertEquals(AchievementState.FINISHED, ach.get(18).getState());
        Assert.assertEquals(AchievementState.FINISHED, ach.get(19).getState());
        Assert.assertEquals(AchievementState.FINISHED, ach.get(20).getState());
    }
}
