package com.csselect.game;

import com.csselect.user.Player;

import java.util.Collection;

/**
 * The StandardRound class represents all concrete rounds {@link Round} that are only different in the number
 * of selections, the number of features {@link Feature} shown in a selection and the number of features {@link Feature}
 * that are to be selected.
 */
public class StandardRound extends Round {

    /**
     * Constructor for a standard round object
     * @param player the player {@link Player} who plays the round
     * @param numberOfRound the number of the round
     * @param numberOfSelections the number of selections per round
     * @param featuresPerSelection the number of features {@link Feature} per selection
     * @param minSelect the minimum number of features {@link Feature} to be selected per selection
     * @param maxSelect the maximum number of features {@link Feature} to be selected per selection
     */
    public StandardRound(Player player, int numberOfRound, int numberOfSelections, int featuresPerSelection, int minSelect, int maxSelect) {

    }

    /**
     * Getter for the number of selections per round
     * @return the number of selections
     */
    public int getNumberOfSelections() {
        return 0;
    }

    /**
     * Getter for the number of features {@link Feature} per selection
     * @return the number of features {@link Feature} per selection
     */
    public int getFeaturesPerSelection() {
        return 0;
    }

    /**
     * Getter for the minimum number of features {@link Feature} to be selected per selection
     * @return the minimum number of features {@link Feature} to be selected per selection
     */
    public int getMinSelect() {
        return 0;
    }

    /**
     * Getter for the maximum number of features {@link Feature} to be selected per selection
     * @return the maximum number of features {@link Feature} to be selected per selection
     */
    public int getMaxSelect() {
        return 0;
    }

    @Override
    public Collection<Feature> provideFeatures() {
        return null;
    }
}
