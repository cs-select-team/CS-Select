package com.csselect.inject;

import org.junit.After;
import org.junit.Before;

public abstract class MysqlTestClass {

    @Before
    public void enableTestmode() {
        Injector.useMysqlTestMode();
        setUp();
    }

    /**
     * Method runs before every unit test
     * Annotated with @Before
     */
    public abstract void setUp();

    @After
    public void resetInjector() {
        Injector.resetInjector();
        reset();
    }

    /**
     * This method is run after every unit test
     * Annotated with @After
     */
    public abstract void reset();
}
