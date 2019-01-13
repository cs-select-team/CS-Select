package com.csselect.mlserver;

import com.csselect.CSSelectTestModule;
import com.csselect.configuration.Configuration;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MLServerTests {

    private MLServer mlServer;

    @Before
    public void setUp() {
        Injector injector = Guice.createInjector(new CSSelectTestModule());
        Configuration config = injector.getInstance(Configuration.class);
        mlServer = new RESTMLServer(config);
    }

    @Test
    public void testVersion() {
        Assert.assertEquals("0.1.5", mlServer.getVersion());
    }
}
