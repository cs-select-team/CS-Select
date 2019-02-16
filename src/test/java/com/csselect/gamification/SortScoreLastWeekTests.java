package com.csselect.gamification;


import com.csselect.game.BinarySelect;
import com.csselect.game.Gamemode;
import com.csselect.game.Round;
import com.csselect.inject.TestClass;
import com.csselect.user.Player;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

/**
 * This test class uses mocked players since the sorting is based on the rounds
 * a player has played and we want to avoid overly complex object creation.
 */
public class SortScoreLastWeekTests extends TestClass {

    @Mock private Player mockedPlayer;
    @Mock private Player mockedPlayer1;
    @Mock private Player mockedPlayer2;
    private List<Player> playersList;
    private Map<Player, Integer> playersMap;
    private LeaderboardSortingStrategy lastWeek;

    @Override
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        playersList = new LinkedList<>();
        lastWeek = new SortScoreLastWeek();
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
        playersList.add(mockedPlayer);
        playersList.add(mockedPlayer1);
        playersList.add(mockedPlayer2);
        // No rounds have been played.

        playersMap = lastWeek.sort(playersList);
        Assert.assertNotNull(playersMap);
        Assert.assertEquals(3, playersMap.size());
        Assert.assertEquals(Integer.valueOf(0), playersMap.get(mockedPlayer));
        Assert.assertEquals(Integer.valueOf(0), playersMap.get(mockedPlayer1));
        Assert.assertEquals(Integer.valueOf(0), playersMap.get(mockedPlayer2));
    }

    @Test
    public void testCorrectOrder() {
        Gamemode gamemode = new BinarySelect();

        // Creating rounds for different players.
        Round roundOne = gamemode.createRound(mockedPlayer);
        roundOne.setTime(LocalDateTime.now());
        roundOne.setPoints(34);
        Round roundTwo = gamemode.createRound(mockedPlayer);
        roundTwo.setTime(LocalDateTime.now().minusDays(3));
        roundTwo.setPoints(74);

        Round roundThree = gamemode.createRound(mockedPlayer1);
        roundThree.setTime(LocalDateTime.now());
        roundThree.setPoints(99);
        Round roundFour = gamemode.createRound(mockedPlayer1);
        roundFour.setTime(LocalDateTime.now().minusDays(5));
        roundFour.setPoints(57);

        Round roundFive = gamemode.createRound(mockedPlayer2);
        roundFive.setTime(LocalDateTime.now());
        // We want to check whether only this round counts, as the other one is too old.
        int pointsRoundFive = 34;
        roundFive.setPoints(pointsRoundFive);
        Round roundSix = gamemode.createRound(mockedPlayer2);
        // This round is older than a week.
        roundSix.setTime(LocalDateTime.now().minusWeeks(3));
        roundSix.setPoints(93);

        Collection<Round> roundsPlayer = new HashSet<>();
        roundsPlayer.add(roundOne);
        roundsPlayer.add(roundTwo);

        Collection<Round> roundsPlayer1 = new HashSet<>();
        roundsPlayer1.add(roundThree);
        roundsPlayer1.add(roundFour);

        Collection<Round> roundsPlayer2 = new HashSet<>();
        roundsPlayer2.add(roundFive);
        roundsPlayer2.add(roundSix);

        // Stubbing the behaviour of getRounds.
        when(mockedPlayer.getRounds()).thenReturn(roundsPlayer);
        when(mockedPlayer1.getRounds()).thenReturn(roundsPlayer1);
        when(mockedPlayer2.getRounds()).thenReturn(roundsPlayer2);

        playersList.add(mockedPlayer);
        playersList.add(mockedPlayer1);
        playersList.add(mockedPlayer2);

        playersMap = lastWeek.sort(playersList);

        // Checking correct order.
        Integer[] points = playersMap.values().toArray(new Integer[0]);
        Assert.assertTrue(points[0] >= points[1]);
        Assert.assertTrue(points[1] >= points[2]);

        // Checking if player2 only has the points of roundFive.
        Assert.assertEquals(Integer.valueOf(pointsRoundFive), playersMap.get(mockedPlayer2));

        // Adding one round for player and player1.
        Round roundSeven = gamemode.createRound(mockedPlayer);
        roundSeven.setTime(LocalDateTime.now());
        roundSeven.setPoints(99);

        Round roundEight = gamemode.createRound(mockedPlayer1);
        roundEight.setTime(LocalDateTime.now().minusDays(5));
        roundEight.setPoints(11);

        roundsPlayer.add(roundSeven);
        roundsPlayer1.add(roundEight);

        playersMap = lastWeek.sort(playersList);

        // Checking correct order.
        points = playersMap.values().toArray(new Integer[0]);
        Assert.assertTrue(points[0] >= points[1]);
        Assert.assertTrue(points[1] >= points[2]);

        // Checking if player2 only has the points of roundFive.
        Assert.assertEquals(Integer.valueOf(pointsRoundFive), playersMap.get(mockedPlayer2));
    }
}
