package com.csselect;


import com.csselect.configuration.Configuration;
import com.csselect.configuration.MockConfiguration;
import com.csselect.database.DatabaseAdapter;
import com.csselect.database.mysql.MysqlDatabaseAdapter;
import com.csselect.mlserver.MLServer;
import com.csselect.mlserver.MockMLServer;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

/**
 * This class manages which implementations should be injected by guice while testing
 */
public class CSSelectMysqlTestModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Configuration.class).to(MockConfiguration.class).in(Scopes.SINGLETON);
        bind(MLServer.class).to(MockMLServer.class).in(Scopes.SINGLETON);
        bind(DatabaseAdapter.class).to(MysqlDatabaseAdapter.class).in(Scopes.SINGLETON);
    }
}
