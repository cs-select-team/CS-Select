package com.csselect.gamification;

import com.csselect.user.Player;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a leaderboard and consists of a list of players.
 * This class is realised as a singleton.
 */
public class Leaderboard {

    private List<Player> players;
    private LeaderboardSortingStrategy strategy;
    private static Leaderboard instance;

    private Leaderboard() {
        this.players = new LinkedList<>();
        setSortingStrategy(new SortScoreAllTime());
    }

    public static Leaderboard getInstance() {
        if (Leaderboard.instance == null) {
            Leaderboard.instance = new Leaderboard();
        }
        return Leaderboard.instance;
    }

    /**
     * Sets the strategy that is supposed to be used to sort the list of players.
     * @param strategy The strategy to use.
     */
    public void setSortingStrategy(LeaderboardSortingStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Gets the sorted list of players.
     * @return The sorted list of players.
     */
    public List<Player> getPlayers() {
        players = getPlayersFromDatabase();
        strategy.sort(players);
        return players;
    }

    /**
     * Gets the current list of all players from the database.
     * @return The list of players.
     */
    private List<Player> getPlayersFromDatabase() {
        return null;
    }

}
