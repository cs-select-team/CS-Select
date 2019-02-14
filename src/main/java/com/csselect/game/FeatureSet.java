package com.csselect.game;

import java.util.HashSet;
import java.util.Set;

/**
 * The FeatureSet class represents a set of features {@link Feature} and has a unique String identifier
 */
public class FeatureSet {

    private final Set<Feature> features;
    private final String identifier;

    /**
     * Constructor of a feature set object
     * @param identifier the unique String identifier
     */
    public FeatureSet(String identifier) {
        this.features = new HashSet<>();
        this.identifier = identifier;
    }

    /**
     * Getter for the unique identifier
     * @return the String identifier
     */
    public String getIdentifier() {
        return this.identifier;
    }

    /**
     * Getter for the features {@link Feature} belonging to the feature set
     * @return the set of features {@link Feature}
     */
    public Set<Feature> getFeatures() {
        return this.features;
    }

    /**
     * Adds a feature {@link Feature} to the feature set, checks if feature is null or already in the FeatureSet
     * @param feature the feature {@link  Feature} to be added
     */
    public void addFeature(Feature feature) {
        if (feature == null) {
            return;
        }

        for (Feature feat : this.features) {
            if (feat.getID() == feature.getID()) {
                return;
            }
        }

        this.features.add(feature);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof FeatureSet)) {
            return false;
        } else {
            FeatureSet featureSet = (FeatureSet) o;
            return this.identifier.equals(featureSet.identifier);
        }
    }

    @Override
    public final int hashCode() {
        return identifier.hashCode();
    }
}
