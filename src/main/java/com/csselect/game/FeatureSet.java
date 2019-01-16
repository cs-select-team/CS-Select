package com.csselect.game;

import java.util.Collection;

/**
 * The FeatureSet class represents a set of features {@link Feature} and has a unique String identifier
 */
public class FeatureSet {

    private final String identifier;

    /**
     * Constructor of a feature set object
     * @param identifier the unique String identifier
     */
    public FeatureSet(String identifier) {

        this.identifier = identifier;
    }

    /**
     * Getter for the unique identifier
     * @return the String identifier
     */
    public String getIdentifier() {
        return "";
    }

    /**
     * Getter for the features {@link Feature} belonging to the feature set
     * @return the collection of features {@link Feature}
     */
    public Collection<Feature> getFeatures() {
        return null;
    }

    /**
     * Adds a feature {@link Feature} to the feature set
     * @param feature the feature {@link  Feature} to be added
     */
    public void addFeature(Feature feature) {

    }
}
