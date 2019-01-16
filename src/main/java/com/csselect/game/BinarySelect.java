package com.csselect.game;

import com.csselect.user.Player;

/**
 * The BinarySelect class represents a concrete game mode {@link Gamemode} in which the player {@link Player} five times
 * chooses one of two features {@link Feature}
 */
public class BinarySelect extends Gamemode {


    /**
     * Constructor for a binary select object
     */
    public BinarySelect() {

    }

    @Override
    public StandardRound createRound(Player player, int number) {
        return null;
    }
}
