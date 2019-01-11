package com.csselect.mlserver;

import com.csselect.game.Feature;
import com.csselect.game.FeatureSet;

import java.util.Collection;

/**
 * Abstraction of the Machine-Learning-Server being used to evaluate feature selections
 * Realized as singleton to ensure only one instance can exist at all time
 */
public interface MLServer {

    /**
     * Gets the ML-Servers version
     * @return version
     */
    String getVersion();

    /**
     * Gets the {@link FeatureSet} specified by the given dataset name
     * @return featureSet
     */
    FeatureSet getFeatures();

    /**
     * Gets the score computed for the given {@link Feature}-Selection from the given dataset.
     * Score is always a double value in range [0-1]
     * While technically values below ~0.5 are possible, it can safely be expected that the score will almost always be
     * greater than 0.5
     * @param dataset dataset to which the feature selection belongs
     * @param selectedFeatures feature selection to be evaluated
     * @return score out of [0-1]
     */
    double getScore(String dataset, Collection<Feature> selectedFeatures);
}
