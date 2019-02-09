package com.csselect.gamification;

import com.csselect.inject.TestClass;
import org.junit.Assert;
import org.junit.Test;

public class StreakTests extends TestClass {

    private Streak streak;

    @Override
    public void setUp() {
        this.streak = new Streak();
    }

    @Override
    public void reset() {

    }

    @Test
    public void loadingTest() {
        Assert.assertNotNull(streak);
        Assert.assertEquals(0, streak.getCounter());
    }

    @Test
    public void testIncreaseStreak() {
        streak.increaseStreak();
        Assert.assertEquals(1, streak.getCounter());
        streak.increaseStreak();
        Assert.assertEquals(2, streak.getCounter());

        for (int i = 0; i < 5; i++) {
            streak.increaseStreak();
        }

        Assert.assertEquals(7, streak.getCounter());
    }

    @Test
    public void testResetStreak() {
        streak.setZero();
        Assert.assertEquals(0, streak.getCounter());
        streak.increaseStreak();
        streak.setZero();
        Assert.assertEquals(0, streak.getCounter());

        for (int i = 0; i < 5; i++) {
            streak.increaseStreak();
        }

        Assert.assertEquals(5, streak.getCounter());
        streak.setZero();
        Assert.assertEquals(0, streak.getCounter());
    }
}
