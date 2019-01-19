package com.csselect.database;

import com.csselect.game.Game;
import com.csselect.game.Round;
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

    /**
     * Gets a {@link Collection} of all {@link Game}s the {@link com.csselect.user.Player} is invited to
     * @return invited games
     */
    Collection<Game> getInvitedGames();

    /**
     * Gets a {@link Collection} of all {@link Round}s a {@link com.csselect.user.Player} has played
     * @return players rounds
     */
    Collection<Round> getRounds();
}
