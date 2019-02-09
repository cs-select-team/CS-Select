package com.csselect.gamification;

import com.csselect.inject.Injector;
import com.csselect.inject.TestClass;
import com.csselect.database.mock.MockDatabaseAdapter;
import com.csselect.user.Player;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SortScoreLastWeekTests extends TestClass {

    private List<Player> playersList;
    private Map<Player, Integer> playersMap;
    private LeaderboardSortingStrategy lastWeek;
    private MockDatabaseAdapter mockDatabaseAdapter;

    @Override
    public void setUp() {
        playersList = new LinkedList<>();
        lastWeek = new SortScoreLastWeek();
        mockDatabaseAdapter = (MockDatabaseAdapter) Injector.getInstance().getDatabaseAdapter();
    }

    @Override
    public void reset() {

    }

    @Test
    public void testEmptyList() {
        playersMap = lastWeek.sort(playersList);
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

        player.getStats().finishRound(0.0);
        player1.getStats().finishRound(0.6);

        playersMap = lastWeek.sort(playersList);
        Assert.assertNotNull(playersMap);
        Assert.assertEquals(3, playersMap.size());
    }
}
