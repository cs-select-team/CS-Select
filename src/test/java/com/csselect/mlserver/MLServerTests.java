package com.csselect.mlserver;

import com.csselect.inject.Injector;
import com.csselect.inject.TestClass;
import com.csselect.configuration.Configuration;
import com.csselect.game.Feature;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

public class MLServerTests extends TestClass {

    private MLServer mlServer;

    @Override
    public void setUp() {
        Configuration config = Injector.getInstance().getConfiguration();
        mlServer = new RESTMLServer(config);
    }

    @Override
    public void reset() {

    }

    @Test
    public void testVersion() throws IOException {
        Assert.assertEquals("0.1.5", mlServer.getVersion());
    }

    @Test
    public void testGetFeatures() throws IOException {
        mlServer.getFeatures("populationGender");
    }

    @Test
    public void testGetScore() throws IOException {
        Collection<Feature> features = new LinkedList<>();
        features.add(new Feature(1, "1"));
        features.add(new Feature(1, "2"));
        Assert.assertEquals(0.5473, mlServer.getScore("populationGender", features), Double.MIN_VALUE);
    }

    @Test
    public void validDatasetTest() {
        Assert.assertTrue(mlServer.isValidDataset("populationGender"));
        Assert.assertFalse(mlServer.isValidDataset("test"));
    }

    @Test
    public void testIsOnline() {
        Assert.assertTrue(mlServer.isOnline());
    }
}
