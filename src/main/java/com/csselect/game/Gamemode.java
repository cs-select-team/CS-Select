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
     * @param number the number rounds {@link Round} that have already been started in a game {@link Game}
     * @return returns the round {@link Round} object
     */
    public abstract Round createRound(Player player, int number);
}
