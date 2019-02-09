package com.csselect.gamification;

import com.csselect.database.DatabaseAdapter;
import com.csselect.inject.Injector;
import com.csselect.inject.TestClass;
import com.csselect.user.Player;
import org.junit.Assert;
import org.junit.Test;

public class LeaderboardTests extends TestClass {

    private DatabaseAdapter mockDatabaseAdapter;
    private Leaderboard leaderboard;

    @Override
    public void setUp() {
        leaderboard = Leaderboard.INSTANCE;
        mockDatabaseAdapter = Injector.getInstance().getDatabaseAdapter();
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
        Assert.assertEquals(sortingStrategy, leaderboard.getStrategy());

        LeaderboardSortingStrategy otherSortingStrategy = new SortScoreLastWeek();
        leaderboard.setSortingStrategy(otherSortingStrategy);
        Assert.assertEquals(otherSortingStrategy, leaderboard.getStrategy());
    }

    @Test
    public void testLeaderboardNoScore() {
        Player player = mockDatabaseAdapter.createPlayer("email", "hash", "salt", "username");
        Player player2 = mockDatabaseAdapter.createPlayer("email2", "hash2", "salt2", "username2");
        Player player3 = mockDatabaseAdapter.createPlayer("email3", "hash3", "salt3", "username3");
        Assert.assertEquals(3, leaderboard.getPlayers().size());
        Assert.assertTrue(leaderboard.getPlayers().containsKey(player));
        Assert.assertTrue(leaderboard.getPlayers().containsKey(player2));
        Assert.assertTrue(leaderboard.getPlayers().containsKey(player3));
    }
}
