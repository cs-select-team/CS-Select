package com.csselect.gamification;

import com.csselect.user.Player;

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
        // Singleton
    }

    public static Leaderboard getInstance() {
        return instance;
    }

    /**
     * Sets the strategy that is supposed to be used to sort the list of players.
     * @param strategy The strategy to use.
     */
    public void setSortingStrategy(LeaderboardSortingStrategy strategy) {

    }

    /**
     * Gets the sorted list of players.
     * @return The sorted list of players.
     */
    public List<Player> getPlayers() {
        return players;
    }

}
