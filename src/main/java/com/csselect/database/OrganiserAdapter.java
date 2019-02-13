package com.csselect.database;

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
     * Adds a {@link Pattern} to {@link com.csselect.user.Organiser}s saved Patterns
     * @param pattern pattern to save
     */
    void addPattern(Pattern pattern);

    /**
     * Checks whether the given {@link com.csselect.game.Game} title is already used by the organiser
     * @param title title of the game
     * @return true if title is in use, false otherwise
     */
    boolean gameTitleInUse(String title);
}
