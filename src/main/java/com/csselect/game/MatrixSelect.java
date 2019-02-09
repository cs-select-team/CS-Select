package com.csselect.game;

import com.csselect.user.Player;

/**
 * The MatrixSelect class represents a concrete game mode {@link Gamemode} in which the player {@link Player} has to
 * choose features {@link Feature} from a matrix. There is a minimum and maximum amount of features {@link Feature} he
 * is allowed to select.
 */
public class MatrixSelect extends Gamemode {

    /**
     * Type-String used in parsing from/to String
     */
    public static final String TYPE = "matrixSelect";

    /**
     * The number of selections of features in the MatrixSelect game mode
     */
    private static final int NUMBER_OF_SELECTIONS = 1;

    private final int numberOfFeatures;
    private final int minSelect;
    private final int maxSelect;

    /**
     * Constructor for a matrix select object
     * @param numberOfFeatures the number of features {@link Feature} that are shown to the player {@link Player}
     * @param minSelect the minimum amount of features {@link Feature} the player {@link Player} has to select
     * @param maxSelect the maximum amount of features {@link Feature} the player {@link Player} has to select
     */
    public MatrixSelect(int numberOfFeatures, int minSelect, int maxSelect) {
        this.numberOfFeatures = numberOfFeatures;
        this.minSelect = minSelect;
        this.maxSelect = maxSelect;
    }

    @Override
    public StandardRound createRound(Player player) {
        if (player == null) {
            return null;
        }

        return new StandardRound(player, NUMBER_OF_SELECTIONS, this.numberOfFeatures, this.minSelect, this.maxSelect);
    }
  
    @Override
    public String toString() {
        return TYPE + "," + numberOfFeatures + "," + minSelect + "," + maxSelect;
    }
}