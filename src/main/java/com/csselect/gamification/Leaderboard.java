package com.csselect.gamification;

import com.csselect.inject.Injector;
import com.csselect.user.Player;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Represents a leaderboard.
 * This is an enum in order to follow the singleton design pattern.
 */
public enum Leaderboard {

    /**
     * Instance of the Leaderboard.
     */
    INSTANCE;

    private LeaderboardSortingStrategy strategy;

    /**
     * Creates a new {@link Leaderboard} and sets the default sorting strategy SortScoreLastWeek.
     */
    Leaderboard() {
        setSortingStrategy(new SortScoreLastWeek());
    }


    /**
     * Gets the current sorting strategy.
     * @return The current sorting strategy.
     */
    LeaderboardSortingStrategy getStrategy() {
        return strategy;
    }

    /**
     * Sets the strategy that is supposed to be used to sort the list of players.
     * @param strategy The strategy to use.
     */
    void setSortingStrategy(LeaderboardSortingStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Gets the sorted Map of players and their points cut to the first five entries.
     * @return The sorted Map of players and their points.
     */
    public synchronized Map<Player, Integer> getPlayers() {
        Map<Player, Integer> sortedMap = strategy.sort(getPlayersFromDatabase());
        return cutToFive(sortedMap);
    }

    /**
     * Gets the current list of all players from the database.
     * @return The list of players.
     */
    private List<Player> getPlayersFromDatabase() {
        return new LinkedList<>(Injector.getInstance().getDatabaseAdapter().getPlayers());
    }

    /**
     * Cuts the map to the first five players (or less if less players exist).
     * @param sortedMap The sorted map that is to be cut.
     * @return A new sorted LinkedHashMap containing five entries.
     */
    private Map<Player, Integer> cutToFive(Map<Player, Integer> sortedMap) {
        Iterator<Map.Entry<Player, Integer>> iterator = sortedMap.entrySet().iterator();
        Map<Player, Integer> mapFive = new LinkedHashMap<>();
        int i = 0;

        while (iterator.hasNext() && i < 5) {
            Map.Entry<Player, Integer> entry = iterator.next();
            mapFive.put(entry.getKey(), entry.getValue());
            i++;
        }
        return mapFive;
    }

}
