package com.csselect.gamification;

import com.csselect.inject.Injector;
import com.csselect.database.DatabaseAdapter;
import com.csselect.user.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Represents a leaderboard.
 * This is an enum in order to follow the singleton design pattern.
 */
public enum Leaderboard {

    INSTANCE;

    private final DatabaseAdapter databaseAdapter;
    private LeaderboardSortingStrategy strategy;

    Leaderboard() {
        databaseAdapter =  Injector.getInstance().getDatabaseAdapter();
        setSortingStrategy(new SortScoreLastWeek());
    }


    /**
     * Gets the current sorting strategy.
     * @return The current sorting strategy.
     */
    public synchronized LeaderboardSortingStrategy getStrategy() {
        return strategy;
    }

    /**
     * Sets the strategy that is supposed to be used to sort the list of players.
     * @param strategy The strategy to use.
     */
    public synchronized void setSortingStrategy(LeaderboardSortingStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Gets the sorted list of players.
     * @return The sorted list of players.
     */
    public Map<Player, Integer> getPlayers() {
        return strategy.sort(getPlayersFromDatabase());
    }

    /**
     * Gets the current list of all players from the database.
     * @return The list of players.
     */
    private List<Player> getPlayersFromDatabase() {
        return new LinkedList<>(databaseAdapter.getPlayers());
    }

}
