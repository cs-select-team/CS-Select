package com.csselect.game;

import com.csselect.user.Player;

/**
 * The BinarySelect class represents a concrete game mode {@link Gamemode} in which the player {@link Player} five times
 * chooses one of two features {@link Feature}
 */
public class BinarySelect extends Gamemode {

    /**
     * The number of selections of features in the BinarySelect game mode
     */
    public static final int NUMBER_OF_SELECTIONS = 5;

    /**
     * The number of features shown per selection in the BinarySelect game mode
     */
    public static final int FEATURES_PER_SELECTION = 2;

    /**
     * The minimum number of features to be selected in the BinarySelect game mode
     */
    public static final int MIN_SELECT = 1;

    /**
     * The maximum number of features to be selected in the BinarySelect game mode
     */
    public static final int MAX_SELECT = 1;

    /**
     * Constructor for a binary select object
     */
    public BinarySelect() {

    }

    @Override
    public StandardRound createRound(Player player, int number) {
        if(player == null) {
            return null;
        }

        return new StandardRound(player, number, NUMBER_OF_SELECTIONS, FEATURES_PER_SELECTION, MIN_SELECT, MAX_SELECT);
    }

    @Override
    public String toString() {
        return "BinarySelect";
    }
}
