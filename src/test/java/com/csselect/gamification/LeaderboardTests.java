package com.csselect.gamification;

import com.csselect.TestClass;
import com.csselect.database.mock.MockDatabaseAdapter;
import org.junit.Assert;
import org.junit.Test;

public class LeaderboardTests extends TestClass {

    private MockDatabaseAdapter mockDatabaseAdapter;
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

    @Test
    public void testSortingStrategy() {
        Assert.assertNotNull(leaderboard.getStrategy());
        LeaderboardSortingStrategy sortingStrategy = new SortScoreAllTime();
        leaderboard.setSortingStrategy(sortingStrategy);
        Assert.assertEquals(leaderboard.getStrategy(), sortingStrategy);
    }

    /*
    @Test
    public void testOneLeaderboardNoScore() {
        mockDatabaseAdapter = (MockDatabaseAdapter) Injector.getInstance().getDatabaseAdapter();
        Player player = mockDatabaseAdapter.createPlayer("email", "hash", "salt", "username");
        Player player2 = mockDatabaseAdapter.createPlayer("email2", "hash2", "salt2", "username2");
        Assert.assertEquals(2, leaderboard.getPlayers().size());
    }
    */



}
