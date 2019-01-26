package com.csselect.gamification;

import com.csselect.user.Player;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represent a sorting strategy. It sorts the players according to their points they
 * reached all time.
 */
public class SortScoreAllTime extends LeaderboardSortingStrategy {

    @Override
    protected Map<Player, Integer> sort(List<Player> players) {

        Map<Player, Integer> scoreAllTime = new TreeMap<>();

        for (Player player : players) {
            scoreAllTime.put(player, player.getStats().getScore());
        }

        Map<Player, Integer> sortedMap = scoreAllTime.entrySet().stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(Map.Entry::getValue)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return sortedMap;
    }
}

