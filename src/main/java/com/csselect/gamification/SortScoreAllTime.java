package com.csselect.gamification;

import com.csselect.user.Player;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Represent a sorting strategy. It sorts the players according to their points they reached during
 * last week.
 */
public class SortScoreAllTime extends LeaderboardSortingStrategy {

    @Override
    protected void sort(List<Player> players) {
        Collections.sort(players, Comparator.comparing(p -> p.getStats().getScore()));
    }
}

