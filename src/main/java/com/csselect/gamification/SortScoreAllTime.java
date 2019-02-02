package com.csselect.gamification;

import com.csselect.user.Player;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Represent a sorting strategy. It sorts the players according to their points they
 * reached all time.
 */
public class SortScoreAllTime extends LeaderboardSortingStrategy {

    @Override
    protected Map<Player, Integer> sort(List<Player> players) {

        Map<Player, Integer> scoreAllTime = new HashMap<>();

        for (Player player : players) {
            scoreAllTime.put(player, player.getStats().getScore());
        }

        return sortMap(scoreAllTime);
    }
}

