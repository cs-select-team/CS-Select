package com.csselect;

import com.csselect.configuration.Configuration;
import com.csselect.configuration.MockConfiguration;
import com.csselect.mlserver.MockMLServer;
import com.csselect.mlserver.MLServer;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class CSSelectTestModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Configuration.class).to(MockConfiguration.class).in(Scopes.SINGLETON);
        bind(MLServer.class).to(MockMLServer.class).in(Scopes.SINGLETON);
    }
}
