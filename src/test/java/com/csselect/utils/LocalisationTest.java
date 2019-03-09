package com.csselect.utils;

import com.csselect.inject.TestClass;
import org.junit.Assert;
import org.junit.Test;

import java.util.MissingResourceException;

public class LocalisationTest extends TestClass {

    @Override
    public void setUp() {

    }

    @Override
    public void reset() {

    }

    @Test(expected = MissingResourceException.class)
    public void testNonExistingKey() {
        Localisation.get(Languages.ENGLISH, "NonExistingKey!");
    }

    @Test
    public void testExistingKey() {
        Assert.assertNotNull(Localisation.get(Languages.ENGLISH, "organiser"));
    }
}