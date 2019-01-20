package com.csselect.gamification;

import com.csselect.user.Player;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represent a sorting strategy. It sorts the players according to their points they reached during
 * last week.
 */
public class SortScoreAllTime extends LeaderboardSortingStrategy {

    @Override
    protected Map<Player, Integer> sort(List<Player> players) {
        // Collections.sort(players, Comparator.comparing(p -> p.getStats().getScore()));

        Map<Player, Integer> sortAllTime = new LinkedHashMap<>();
        players.stream().forEach(p -> sortAllTime.put(p, p.getStats().getScore()));

        Map<Player, Integer> sortedMap = sortAllTime.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e1));

        return sortedMap;
    }
}

