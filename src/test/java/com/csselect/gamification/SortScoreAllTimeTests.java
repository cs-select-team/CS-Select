package com.csselect.gamification;

import com.csselect.database.mock.MockDatabaseAdapter;
import com.csselect.inject.Injector;
import com.csselect.inject.TestClass;
import com.csselect.user.Player;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SortScoreAllTimeTests extends TestClass {

    private List<Player> playersList;
    private Map<Player, Integer> playersMap;
    private LeaderboardSortingStrategy allTime;
    private MockDatabaseAdapter mockDatabaseAdapter;

    @Override
    public void setUp() {
        playersList = new LinkedList<>();
        allTime = new SortScoreAllTime();
        mockDatabaseAdapter = (MockDatabaseAdapter) Injector.getInstance().getDatabaseAdapter();
    }

    @Override
    public void reset() {

    }

    @Test
    public void testEmptyList() {
        playersMap = allTime.sort(playersList);
        Assert.assertNotNull(playersMap);
        Assert.assertTrue(playersMap.isEmpty());
    }

    @Test
    public void testNotEmptyList() {
        Player player = mockDatabaseAdapter.createPlayer("email", "hash", "salt", "username");
        Player player1 = mockDatabaseAdapter.createPlayer("email1", "hash1", "salt1", "username1");
        Player player2 = mockDatabaseAdapter.createPlayer("email2", "hash2", "salt2", "username2");
        playersList.add(player);
        playersList.add(player1);
        playersList.add(player2);

        player.getStats().finishRound(0.2);
        player1.getStats().finishRound(0.6);

        playersMap = allTime.sort(playersList);
        Assert.assertNotNull(playersMap);
        Assert.assertEquals(3, playersMap.size());
        Assert.assertEquals(Integer.valueOf(player.getStats().getScore()), playersMap.get(player));
        Assert.assertEquals(Integer.valueOf(player1.getStats().getScore()), playersMap.get(player1));
        Assert.assertEquals(Integer.valueOf(player2.getStats().getScore()), playersMap.get(player2));
    }

    @Test
    public void testCorrectOrder() {
        Player player = mockDatabaseAdapter.createPlayer("email", "hash", "salt", "username");
        Player player1 = mockDatabaseAdapter.createPlayer("email1", "hash1", "salt1", "username1");
        Player player2 = mockDatabaseAdapter.createPlayer("email2", "hash2", "salt2", "username2");
        playersList.add(player);
        playersList.add(player1);
        playersList.add(player2);

        player.getStats().finishRound(0.2);
        player2.getStats().finishRound(0.4);
        player1.getStats().finishRound(0.6);

        playersMap = allTime.sort(playersList);

        Integer[] points = playersMap.values().toArray(new Integer[0]);
        Assert.assertTrue(points[0] >= points[1]);
        Assert.assertTrue(points[1] >= points[2]);

        Assert.assertEquals(Integer.valueOf(player.getStats().getScore()), playersMap.get(player));
        Assert.assertEquals(Integer.valueOf(player1.getStats().getScore()), playersMap.get(player1));
        Assert.assertEquals(Integer.valueOf(player2.getStats().getScore()), playersMap.get(player2));

        Integer scorePlayer1 = playersMap.get(player1);

        player.getStats().finishRound(0.4);
        player2.getStats().finishRound(0.1);

        playersMap = allTime.sort(playersList);

        // Checking correct order.
        points = playersMap.values().toArray(new Integer[0]);
        Assert.assertTrue(points[0] >= points[1]);
        Assert.assertTrue(points[1] >= points[2]);

        Assert.assertEquals(Integer.valueOf(player.getStats().getScore()), playersMap.get(player));
        Assert.assertEquals(Integer.valueOf(player1.getStats().getScore()), playersMap.get(player1));
        Assert.assertEquals(Integer.valueOf(player2.getStats().getScore()), playersMap.get(player2));

        // Checks if score of player1 has been untouched.
        Assert.assertEquals(scorePlayer1, playersMap.get(player1));
    }
}
