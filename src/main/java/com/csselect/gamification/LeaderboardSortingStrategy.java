package com.csselect.gamification;

import com.csselect.user.Player;

import java.util.List;
import java.util.Map;

/**
 * This abstract class contains one abstract method that has to be implemented in subclasses
 * that all represent a different strategy for sorting.
 */
public abstract class LeaderboardSortingStrategy {

    /**
     * Sorts the list of players. Every subclass has to implement this method in order to sort the list
     * in the given way.
     * @param players The list of players to sort.
     * @return The sorted Map of players with their corresponding points.
     */
    protected abstract Map<Player, Integer> sort(List<Player> players);

}
