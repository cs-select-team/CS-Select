package com.csselect;

import com.csselect.database.mock.MockPlayerAdapter;
import org.junit.After;
import org.junit.Before;

public abstract class TestClass {

    @Before
    public void enableTestmode() {
        com.csselect.Injector.useTestMode();
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
        MockPlayerAdapter.reset();
    }

    /**
     * This method is run after every unit test
     * Annotated with @After
     */
    public abstract void reset();
}
