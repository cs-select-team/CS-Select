package com.csselect.game;

import com.csselect.user.Player;

import java.util.List;
import java.util.ArrayList;

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

    private static final int PROVIDE_FEATURES_MAX_TRIES = 3;
    /**
     * Constructor for a standard round object
     * @param player the player {@link Player} who plays the round
     * @param numberOfSelections the number of selections per round
     * @param featuresPerSelection the number of features {@link Feature} per selection
     * @param minSelect the minimum number of features {@link Feature} to be selected per selection
     * @param maxSelect the maximum number of features {@link Feature} to be selected per selection
     */
    StandardRound(Player player, int numberOfSelections, int featuresPerSelection, int minSelect, int maxSelect) {
        super(player);
        this.numberOfSelections = numberOfSelections;
        this.featuresPerSelection = featuresPerSelection;
        this.minSelect = minSelect;
        this.maxSelect = maxSelect;
    }

    /**
     * Getter for the number of selections per round
     * @return the number of selections
     */
    int getNumberOfSelections() {
        return this.numberOfSelections;
    }

    /**
     * Getter for the number of features {@link Feature} per selection
     * @return the number of features {@link Feature} per selection
     */
    int getFeaturesPerSelection() {
        return this.featuresPerSelection;
    }

    /**
     * Getter for the minimum number of features {@link Feature} to be selected per selection
     * @return the minimum number of features {@link Feature} to be selected per selection
     */
    int getMinSelect() {
        return this.minSelect;
    }

    /**
     * Getter for the maximum number of features {@link Feature} to be selected per selection
     * @return the maximum number of features {@link Feature} to be selected per selection
     */
    int getMaxSelect() {
        return this.maxSelect;
    }

    @Override
    public List<Feature> provideFeatures() {
        List<Feature> providedFeatures = new ArrayList<>();
        int k = 0;
        do {
            for (int i = 0; i < this.numberOfSelections; i++) {
                List<Feature> featureListThisSelection = new ArrayList<>(this.features);
                for (int j = 0; j < this.featuresPerSelection; j++) {


                    int randomFeature = (int) (Math.random() * featureListThisSelection.size());

                    Feature feature = featureListThisSelection.get(randomFeature);
                    providedFeatures.add(feature);
                    featureListThisSelection.remove(feature);
                }
            }
            k++;
        } while (this.game.checkDuplicateFeatureProvision(providedFeatures) && k < PROVIDE_FEATURES_MAX_TRIES);
        return providedFeatures;
    }

    @Override
    public int selectFeatures(int[] selectedFeatures, int[] uselessFeatures) {
        if (uselessFeatures == null || selectedFeatures == null) {
            return -1;
        }

        if ((this.maxSelect * this.numberOfSelections) < selectedFeatures.length) {
            return -1;
        }

        return super.selectFeatures(selectedFeatures, uselessFeatures);
    }
}
