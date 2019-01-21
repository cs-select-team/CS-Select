package com.csselect;

import com.google.inject.Guice;

/**
 * Class used for retrieving the guice injector
 */
public final class Injector {

    private static com.google.inject.Injector injector = null;
    private static boolean testMode;
    private static boolean mysqlTestMode;

    private Injector() {
        //Utility classes shouldn't be instantiated
    }

    /**
     * Returns the guice {@link com.google.inject.Injector} to receive instances from
     * @return the injector
     */
    public static com.google.inject.Injector getInjector() {
        if (injector == null) {
            if (testMode) {
                injector = Guice.createInjector(new CSSelectTestModule());
            } else if (mysqlTestMode) {
                injector = Guice.createInjector(new CSSelectMysqlTestModule());
            } else {
                injector = Guice.createInjector(new CSSelectModule());
            }
        }
        return injector;
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
     * This method resets the injector for the next test case, only works if testmode == true
     */
    static void resetInjector() {
        if (testMode) {
            injector = null;
            testMode = false;
            mysqlTestMode = false;
        }
    }
}
