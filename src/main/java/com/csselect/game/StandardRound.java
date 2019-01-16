package com.csselect.game;

import com.csselect.user.Player;

import java.util.Collection;

/**
 * The StandardRound class represents all concrete rounds {@link Round} that are only different in the number
 * of selections, the number of features {@link Feature} shown in a selection and the number of features {@link Feature}
 * that are to be selected.
 */
public class StandardRound extends Round {

    private final int numberOfSelections;
    private final int featuresPerSelection;
    private final int minSelect;
    private final int maxSelect;
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
        super(player, numberOfRound);
        this.numberOfSelections = numberOfSelections;
        this.featuresPerSelection = featuresPerSelection;
        this.minSelect = minSelect;
        this.maxSelect = maxSelect;
    }

    /**
     * Getter for the number of selections per round
     * @return the number of selections
     */
    public int getNumberOfSelections() {
        return this.numberOfSelections;
    }

    /**
     * Getter for the number of features {@link Feature} per selection
     * @return the number of features {@link Feature} per selection
     */
    public int getFeaturesPerSelection() {
        return this.featuresPerSelection;
    }

    /**
     * Getter for the minimum number of features {@link Feature} to be selected per selection
     * @return the minimum number of features {@link Feature} to be selected per selection
     */
    public int getMinSelect() {
        return this.minSelect;
    }

    /**
     * Getter for the maximum number of features {@link Feature} to be selected per selection
     * @return the maximum number of features {@link Feature} to be selected per selection
     */
    public int getMaxSelect() {
        return this.maxSelect;
    }

    @Override
    public Collection<Feature> provideFeatures() {
        return null;
    }
}
