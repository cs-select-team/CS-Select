package com.csselect;

import com.google.inject.Guice;

/**
 * Class used for retrieving the guice injector
 */
public final class Injector {

    private static com.google.inject.Injector injector = null;
    private static boolean testMode;

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
     * This method resets the injector for the next test case, only works if testmode == true
     */
    static void resetInjector() {
        if (testMode) {
            injector = null;
        }
    }
}
