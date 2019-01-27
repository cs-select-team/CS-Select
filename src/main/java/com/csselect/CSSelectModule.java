package com.csselect;

import com.csselect.configuration.ApacheCommonsConfiguration;
import com.csselect.configuration.Configuration;
import com.csselect.database.DatabaseAdapter;
import com.csselect.database.mysql.MysqlDatabaseAdapter;
import com.csselect.mlserver.MLServer;
import com.csselect.mlserver.RESTMLServer;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

/**
 * This class manages which implementations should be injected by guice
 */

public class CSSelectModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Configuration.class).to(ApacheCommonsConfiguration.class).in(Scopes.SINGLETON);
        bind(MLServer.class).to(RESTMLServer.class).in(Scopes.SINGLETON);
        bind(DatabaseAdapter.class).to(MysqlDatabaseAdapter.class).in(Scopes.SINGLETON);
    }
}
