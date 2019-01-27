package com.csselect;

import com.csselect.configuration.Configuration;
import com.csselect.database.DatabaseAdapter;
import com.csselect.mlserver.MLServer;

abstract class Module {

    private final DatabaseAdapter databaseAdapter;
    private final MLServer mlServer;
    private final Configuration configuration;

    Module(DatabaseAdapter databaseAdapter, MLServer mlServer, Configuration configuration) {
        this.databaseAdapter = databaseAdapter;
        this.mlServer = mlServer;
        this.configuration = configuration;
    }

    final DatabaseAdapter getDatabaseAdapter() {
        return databaseAdapter;
    }
    final MLServer getMLServer() {
        return mlServer;
    }
    final Configuration getConfiguration() {
        return configuration;
    }
}
