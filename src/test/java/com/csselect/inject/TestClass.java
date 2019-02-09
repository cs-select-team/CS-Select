package com.csselect.inject;

import com.csselect.database.mock.MockPlayerAdapter;
import org.junit.After;
import org.junit.Before;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

public abstract class TestClass {

    @Before
    public void enableTestmode() {
        Injector.useTestMode();
        setUp();
    }

    /**
     * Method runs before every unit test
     * Annotated with @Before
     */
    public abstract void setUp();

    @After
    public void resetInjector() throws Exception {
        reset();
        resetStaticFinal(MockPlayerAdapter.class.getDeclaredField("PLAYERSTATS_ADAPTERS"), new HashMap<>());

        Injector.resetInjector();
    }

    /**
     * This method is run after every unit test
     * Annotated with @After
     */
    public abstract void reset();

    private void resetStaticFinal(Field field, Object newValue) throws Exception {
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }
}
