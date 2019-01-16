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
    public static final int numberOfSelections = 5;

    /**
     * The number of features shown per selection in the BinarySelect game mode
     */
    public static final int featuresPerSelection = 2;

    /**
     * The minimum number of features to be selected in the BinarySelect game mode
     */
    public static final int minSelect = 1;

    /**
     * The maximum number of features to be selected in the BinarySelect game mode
     */
    public static final int maxSelect = 1;

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
