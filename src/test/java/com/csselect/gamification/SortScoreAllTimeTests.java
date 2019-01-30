package com.csselect.gamification;

import com.csselect.Injector;
import com.csselect.TestClass;
import com.csselect.database.DatabaseAdapter;
import com.csselect.database.mock.MockDatabaseAdapter;
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
        mockDatabaseAdapter = (MockDatabaseAdapter) Injector.getInjector().getInstance(DatabaseAdapter.class);
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
        Assert.assertEquals(Integer.valueOf(10), playersMap.get(player));
        Assert.assertEquals(Integer.valueOf(60), playersMap.get(player1));
        Assert.assertEquals(Integer.valueOf(0), playersMap.get(player2));
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
        Object[] points = playersMap.values().toArray();
        Object[] players = playersMap.keySet().toArray();

        Assert.assertEquals(60, points[0]);
        Assert.assertEquals(20, points[1]);
        Assert.assertEquals(10, points[2]);

        Assert.assertEquals(player1, players[0]);
        Assert.assertEquals(player2, players[1]);
        Assert.assertEquals(player, players[2]);

        player.getStats().finishRound(0.4);
        player2.getStats().finishRound(0.0);
        player1.getStats().finishRound(0.59);
        playersMap = allTime.sort(playersList);
        points = playersMap.values().toArray();
        players = playersMap.keySet().toArray();

        Assert.assertEquals(119, points[0]);
        Assert.assertEquals(30, points[1]);
        Assert.assertEquals(20, points[2]);

        Assert.assertEquals(player1, players[0]);
        Assert.assertEquals(player, players[1]);
        Assert.assertEquals(player2, players[2]);
    }
}
