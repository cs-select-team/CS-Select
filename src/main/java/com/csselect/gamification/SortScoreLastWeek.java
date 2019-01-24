package com.csselect.gamification;

import com.csselect.game.Round;
import com.csselect.user.Player;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represent a sorting strategy. It sorts the players according to their points they reached all time.
 */
public class SortScoreLastWeek extends LeaderboardSortingStrategy {

    @Override
    protected Map<Player, Integer> sort(List<Player> players) {
        Map<Player, List<Round>> roundsLastWeek = new LinkedHashMap<>();
        Map<Player, Integer> scoreLastWeek = new LinkedHashMap<>();

        players.stream().forEach(p -> roundsLastWeek.put(p, p.getRounds().stream()
                .filter(r -> r.getTime().toLocalDate().isAfter(LocalDate.now().minusDays(7))).collect(Collectors.toList())));


        for (Map.Entry<Player, List<Round>> entry : roundsLastWeek.entrySet()) {
            List<Integer> allPoints = new LinkedList<>();
            entry.getValue().stream().forEach(r -> allPoints.add(r.getPoints()));
            int sum = allPoints.stream().mapToInt(Integer::intValue).sum();
            scoreLastWeek.put(entry.getKey(), sum);
        }

        Map<Player, Integer> sortedMap = scoreLastWeek.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e1));

        // List<Player> sortedList = new LinkedList<>(sortedMap.keySet());
        return sortedMap;
    }
}
