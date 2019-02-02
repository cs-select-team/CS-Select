package com.csselect.gamification;

import com.csselect.user.Player;

import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

/**
 * This abstract class contains one abstract method that has to be implemented in subclasses
 * that all represent a different strategy for sorting.
 */
public abstract class LeaderboardSortingStrategy {

    /**
     * Sorts the list of players. Every subclass has to implement this method in order to
     * sort the list in the given way.
     * @param players The list of players to sort.
     * @return The sorted Map of players with their corresponding points.
     */
    protected abstract Map<Player, Integer> sort(List<Player> players);

    /**
     * A method to use in subclasses to sort a map by their score (value of the map).
     * @param map The map to be sorted. They key has to be a player object and the value the according score.
     * @return The corresponding sorted map.
     */
    Map<Player, Integer> sortMap(Map<Player, Integer> map) {
        return map.entrySet().stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(Map.Entry::getValue)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

}
