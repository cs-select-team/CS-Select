package com.csselect;

import org.junit.Before;

public abstract class TestClass {

    @Before
    public void enableTestmode() {
        com.csselect.Injector.useTestMode();
        setUp();
    }

    /**
     * Method run before every test case
     */
    public abstract void setUp();
}
