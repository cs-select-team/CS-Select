package com.csselect.game;

import com.csselect.user.Player;

/**
 * The Gamemode class represents a game mode. It is abstract and generalizes a method to create a concrete
 * round {@link Round} of this game mode by using the fabric method pattern.
 */
public abstract class Gamemode {


    /**
     * Creates a round {@link Round} object of this game mode
     * @param player the player {@link Player} who plays the round {@link Round}
     * @return returns the round {@link Round} object
     */
    public abstract Round createRound(Player player);

    /**
     * Getter for the name of the gamemode
     * @return returns the name of the class of the gamemode
     */
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public abstract String toString();
}
