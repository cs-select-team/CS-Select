package com.csselect.game;

import com.csselect.user.Player;

/**
 * The BinarySelect class represents a concrete game mode {@link Gamemode} in which the player {@link Player} five times
 * chooses one of two features {@link Feature}
 */
public class BinarySelect extends Gamemode {

    /**
     * Type-String used in parsing from/to String
     */
    public static final String TYPE = "binarySelect";

    /**
     * The number of selections of features in the BinarySelect game mode
     */
    private static final int NUMBER_OF_SELECTIONS = 5;

    /**
     * The number of features shown per selection in the BinarySelect game mode
     */
    private static final int FEATURES_PER_SELECTION = 2;

    /**
     * The minimum number of features to be selected in the BinarySelect game mode
     */
    private static final int MIN_SELECT = 1;

    /**
     * The maximum number of features to be selected in the BinarySelect game mode
     */
    private static final int MAX_SELECT = 1;

    @Override
    public StandardRound createRound(Player player) {
        if (player == null) {
            return null;
        }

        return new StandardRound(player, NUMBER_OF_SELECTIONS, FEATURES_PER_SELECTION, MIN_SELECT, MAX_SELECT);
    }

    @Override
    public String toString() {
        return TYPE;
    }
}
