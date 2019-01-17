package com.csselect.mlserver;

import com.csselect.CSSelectTestModule;
import com.csselect.configuration.Configuration;
import com.csselect.game.Feature;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

public class MLServerTests {

    private MLServer mlServer;

    @Before
    public void setUp() {
        Injector injector = CSSelectTestModule.getInjector();
        Configuration config = injector.getInstance(Configuration.class);
        mlServer = new RESTMLServer(config);
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
}
