package com.csselect.inject;

import com.csselect.configuration.ApacheCommonsConfiguration;
import com.csselect.configuration.Configuration;
import com.csselect.configuration.ConfigurationException;
import com.csselect.configuration.MockConfiguration;
import com.csselect.database.DatabaseAdapter;
import com.csselect.database.DatabaseException;
import com.csselect.mlserver.MLServer;

/**
 * Class used for retrieving the Injector which is responsible for delivering the
 * correct implementations for our singletons
 */
public final class Injector {

    private static Injector injector = null;
    private static boolean testMode;
    private static boolean mysqlTestMode;

    private final Module module;

    private Injector(Module module) {
        this.module = module;
    }

    /**
     * Returns the Injector to receive instances from
     * @return the injector
     */
    public static Injector getInstance() {
        if (injector == null) {
            synchronized (Injector.class) {
                if (testMode) {
                    injector = new Injector(new CSSelectTestModule());
                } else if (mysqlTestMode) {
                    injector = new Injector(new CSSelectMysqlTestModule(new MockConfiguration()));
                } else {
                    Configuration configuration;
                    Module module;
                    try {
                        configuration = new ApacheCommonsConfiguration();
                    } catch (ConfigurationException | NoClassDefFoundError e) {
                        injector = null;
                        throw new ConfigurationException();
                    }
                    try {
                        module = new CSSelectModule(configuration);
                    } catch (DatabaseException | NoClassDefFoundError e) {
                        injector = null;
                        throw new DatabaseException();
                    }
                    injector = new Injector(module);
                }
            }
        }
        return injector;
    }

    /**
     * Gets the used {@link DatabaseAdapter} as specified in the active {@link Module}
     * @return databaseAdapter
     */
    public DatabaseAdapter getDatabaseAdapter() {
        return module.getDatabaseAdapter();
    }

    /**
     * Gets the used {@link MLServer} as specified in the active {@link Module}
     * @return databaseAdapter
     */
    public MLServer getMLServer() {
        return module.getMLServer();
    }

    /**
     * Gets the used {@link Configuration} as specified in the active {@link Module}
     * @return databaseAdapter
     */
    public Configuration getConfiguration() {
        return module.getConfiguration();
    }

    /**
     * This method tells the injector to only produce mock implementations instead of the real implementations
     */
    static void useTestMode() {
        testMode = true;
    }

    /**
     * This method tells the injector to use the Module for testing the Mysql-database-implementation
     */
    static void useMysqlTestMode() {
        mysqlTestMode = true;
    }

    /**
     * This method resets the injector for the next test case, only works if testmode or mysqltestmode == true
     */
    static void resetInjector() {
        if (testMode || mysqlTestMode) {
            injector = null;
            testMode = false;
            mysqlTestMode = false;
        }
    }
}
