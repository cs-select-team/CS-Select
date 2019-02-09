package com.csselect.inject;

import com.csselect.configuration.Configuration;
import com.csselect.database.DatabaseAdapter;
import com.csselect.mlserver.MLServer;

/**
 * A Module handles the bindings of implementations to their interfaces for our {@link Injector}
 */
abstract class Module {

    private final DatabaseAdapter databaseAdapter;
    private final MLServer mlServer;
    private final Configuration configuration;

    /**
     * Creates a new Module with the given bindings
     * @param databaseAdapter databaseAdapter instance to be used
     * @param mlServer mlServer instance to be used
     * @param configuration configuration instance to be used
     */
    Module(DatabaseAdapter databaseAdapter, MLServer mlServer, Configuration configuration) {
        this.databaseAdapter = databaseAdapter;
        this.mlServer = mlServer;
        this.configuration = configuration;
    }

    /**
     * Gets the {@link Module}s {@link DatabaseAdapter}
     * @return databaseAdapter
     */
    final DatabaseAdapter getDatabaseAdapter() {
        return databaseAdapter;
    }

    /**
     * Gets the {@link Module}s {@link MLServer}
     * @return mlServer
     */
    final MLServer getMLServer() {
        return mlServer;
    }

    /**
     * Gets the {@link Module}s {@link Configuration}
     * @return configuration
     */
    final Configuration getConfiguration() {
        return configuration;
    }
}
