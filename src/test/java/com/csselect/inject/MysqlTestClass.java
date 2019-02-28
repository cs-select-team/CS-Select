package com.csselect.inject;

import org.junit.After;
import org.junit.Before;

public abstract class MysqlTestClass {

    @Before
    public void enableTestmode() {
        Injector.useMysqlTestMode();
        Injector.getInstance().getDatabaseAdapter().checkDuplicateDatabase("TEST"); //We need to call a query so direct construction
        setUp();                                                                                 //of adapters works correctly
    }

    /**
     * Method runs before every unit test
     * Annotated with @Before
     */
    public abstract void setUp();

    @After
    public void resetInjector() {
        reset();
        Injector.resetInjector();
    }

    /**
     * This method is run after every unit test
     * Annotated with @After
     */
    public abstract void reset();
}
