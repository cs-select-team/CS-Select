package com.csselect.game;

import com.csselect.inject.TestClass;
import org.junit.Assert;
import org.junit.Test;

public class FeatureTests extends TestClass {
    @Override
    public void setUp() {

    }

    @Override
    public void reset() {

    }

    @Test
    public void descriptionTest() {
        Feature feature = new Feature(1, "ab");
        feature.setEnglishDescription("desc");
        feature.setGermanDescription("beschr");
        Assert.assertEquals(feature.getDescription("en"), "desc");
        Assert.assertEquals(feature.getDescription("de"), "beschr");
        Assert.assertEquals(feature.getDescription("es"), "desc");

    }

    @Test
    public void nameTest() {
        Feature feature = new Feature(1, "ab");
        feature.setEnglishName("name");
        feature.setGermanName("Merkmal");
        Assert.assertEquals(feature.getName("en"), "name");
        Assert.assertEquals(feature.getName("de"), "Merkmal");
        Assert.assertEquals(feature.getName("es"), "name");

    }

}
