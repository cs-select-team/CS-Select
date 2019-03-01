package com.csselect.mlserver;

import com.csselect.game.Feature;
import com.csselect.game.FeatureSet;

import java.util.Collection;
import java.util.Random;

/**
 * Mock-Implementation of the {@link com.csselect.mlserver.MLServer} Interface
 */
public final class MockMLServer implements MLServer {

    private final Random random;

    /**
     * Creates a new {@link MockMLServer}
     */
    public MockMLServer() {
        this.random = new Random();
    }

    @Override
    public String getVersion() {
        return "0.1.5";
    }

    //TODO Populate FeatureSet with Features when the needed classes are available
    @Override
    public FeatureSet getFeatures(String dataset) {
        return new FeatureSet(dataset);
    }

    @Override
    public double getScore(String dataset, Collection<Feature> selectedFeatures) {
        return random.nextDouble();
    }

    @Override
    public boolean isValidDataset(String dataset) {
        return dataset.equalsIgnoreCase("populationGender");
    }

    @Override
    public boolean isOnline() {
        return true;
    }
}
