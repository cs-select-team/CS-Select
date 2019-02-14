package com.csselect.game;

import com.csselect.inject.TestClass;
import org.junit.Assert;
import org.junit.Test;

import java.awt.image.BufferedImage;

public class FeatureTests extends TestClass {
    
    private static final String INTERNAL_NAME = "ab";
    @Override
    public void setUp() {

    }

    @Override
    public void reset() {

    }

    @Test
    public void descriptionTest() {
        Feature feature = new Feature(1, INTERNAL_NAME);
        feature.setEnglishDescription("desc");
        feature.setGermanDescription("beschr");
        Assert.assertEquals(feature.getDescription("en"), "desc");
        Assert.assertEquals(feature.getDescription("de"), "beschr");
        Assert.assertEquals(feature.getDescription("es"), "desc");

    }

    @Test
    public void nameTest() {
        Feature feature = new Feature(1, INTERNAL_NAME);
        feature.setEnglishName("name");
        feature.setGermanName("Merkmal");
        Assert.assertEquals(feature.getName("en"), "name");
        Assert.assertEquals(feature.getName("de"), "Merkmal");
        Assert.assertEquals(feature.getName("es"), "name");
    }

    @Test
    public void testDefaultMissing() {
        Feature feature = new Feature(1, INTERNAL_NAME);
        Assert.assertEquals(feature.getName("en"), "");
        Assert.assertEquals(feature.getName("de"), "");
        Assert.assertEquals(feature.getName("es"), "");
    }
    
    @Test
    public void testInternalName() {
        Feature feature = new Feature(1, INTERNAL_NAME);
        Assert.assertEquals(INTERNAL_NAME, feature.getInternalName());
    }

    @Test
    public void testCompareTo() {
        Feature feature1 = new Feature(1, INTERNAL_NAME);
        Feature feature2 = new Feature(2, INTERNAL_NAME);
        Assert.assertTrue(feature1.compareTo(feature2) < 0);
        Assert.assertTrue(feature2.compareTo(feature1) > 0);
        //noinspection EqualsWithItself
        Assert.assertEquals(0, feature1.compareTo(feature1));
    }

    @Test
    public void testGraphs() {
        Feature feature = new Feature(1, INTERNAL_NAME);
        BufferedImage img1 = new BufferedImage(1,1, BufferedImage.TYPE_INT_ARGB);
        BufferedImage img2 = new BufferedImage(1,1, BufferedImage.TYPE_INT_ARGB);
        feature.setClassGraph(img1);
        feature.setTotalGraph(img2);
        Assert.assertEquals(img1, feature.getClassGraph());
        Assert.assertEquals(img2, feature.getTotalGraph());
    }

}
