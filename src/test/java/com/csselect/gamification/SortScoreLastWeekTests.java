package com.csselect.gamification;

import com.csselect.Injector;
import com.csselect.TestClass;
import com.csselect.database.DatabaseAdapter;
import com.csselect.database.PlayerAdapter;
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
        mockDatabaseAdapter = (MockDatabaseAdapter) Injector.getInjector().getInstance(DatabaseAdapter.class);
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
        PlayerAdapter adapter = mockDatabaseAdapter.getPlayerAdapter(0);
        Player player1 = new Player(adapter);
        playersList.add(player1);

        playersMap = lastWeek.sort(playersList);
        Assert.assertNotNull(playersMap);
        Assert.assertEquals(1, playersMap.size());
        Assert.assertEquals(new Integer(0), playersMap.get(player1));
    }
}
