package com.csselect.database;

import com.csselect.game.Game;
import com.csselect.gamification.PlayerStats;

import java.util.Collection;

/**
 * Interface abstracting a {@link com.csselect.user.Player} from its database representation
 * Adapters are matched to their players unanimously by their IDs
 */
public interface PlayerAdapter extends UserAdapter {

    /**
     * Gets the {@link com.csselect.user.Player}s username
     * @return username
     */
    String getUsername();

    /**
     * Gets the {@link com.csselect.user.Player}s associated {@link PlayerStats}
     * @return PlayerStats
     */
    PlayerStats getPlayerStats();
}
