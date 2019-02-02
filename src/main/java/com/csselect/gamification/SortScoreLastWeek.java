package com.csselect.gamification;

import com.csselect.game.Round;
import com.csselect.user.Player;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Represent a sorting strategy. It sorts the players according to their points they reached all time.
 */
public class SortScoreLastWeek extends LeaderboardSortingStrategy {

    @Override
    protected Map<Player, Integer> sort(List<Player> players) {
        Map<Player, Integer> scoreLastWeek = new HashMap<>();

        for (Player player : players) {
            int sum = 0;
            for (Round round : player.getRounds().stream()
                    .filter(r -> r.getTime().toLocalDate().isAfter(LocalDate.now().minusWeeks(1)))
                    .collect(Collectors.toList())) {
                sum += round.getPoints();
            }
            scoreLastWeek.put(player, sum);
        }

        return sortMap(scoreLastWeek);
    }
}
