package com.csselect.database;

import com.csselect.game.Game;
import com.csselect.game.gamecreation.patterns.Pattern;

import java.util.Collection;

/**
 * Interface abstracting a {@link com.csselect.user.Organiser} from its database representation
 * Adapters are matched to their organisers unanimously by their IDs
 */
public interface OrganiserAdapter extends UserAdapter {

    /**
     * Gets a {@link Collection} of all {@link Pattern}s the organiser has saved
     * @return patterns
     */
    Collection<Pattern> getPatterns();

    /**
     * Gets a {@link Collection} of all active {@link Game}s the {@link com.csselect.user.Organiser} owns
     * @return active games
     */
    Collection<Game> getActiveGames();

    /**
     * Gets a {@link Collection} of all terminated {@link Game}s the {@link com.csselect.user.Organiser} owns
     * @return terminated games
     */
    Collection<Game> getTerminatedGames();

    /**
     * Adds a {@link Pattern} to {@link com.csselect.user.Organiser}s saved Patterns
     * @param pattern pattern to save
     */
    void addPattern(Pattern pattern);
}
