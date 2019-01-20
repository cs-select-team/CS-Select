package com.csselect.gamification;

import com.csselect.TestClass;
import org.junit.Assert;
import org.junit.Test;

public class LeaderboardTests extends TestClass {

    private Leaderboard leaderboard;

    @Override
    public void setUp() {
        leaderboard = Leaderboard.getInstance();
    }

    @Override
    public void reset() {

    }

    @Test
    public void loadingTest() {
        Assert.assertNotNull(leaderboard);
    }

    @Test
    public void testEmptyLeaderboard() {
        Assert.assertTrue(leaderboard.getPlayers().isEmpty());
    }
}
