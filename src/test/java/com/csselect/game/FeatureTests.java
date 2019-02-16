package com.csselect.game;

import com.csselect.inject.TestClass;
import com.csselect.utils.Languages;
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
        feature.setDescription(Languages.ENGLISH, "desc");
        feature.setDescription(Languages.GERMAN, "beschr");
        Assert.assertEquals(feature.getDescription(Languages.ENGLISH), "desc");
        Assert.assertEquals(feature.getDescription(Languages.GERMAN), "beschr");
        Assert.assertEquals(feature.getDescription(Languages.ENGLISH), "desc");

    }

    @Test
    public void nameTest() {
        Feature feature = new Feature(1, INTERNAL_NAME);
        feature.setName(Languages.ENGLISH, "name");
        feature.setName(Languages.GERMAN, "Merkmal");
        Assert.assertEquals(feature.getName(Languages.ENGLISH), "name");
        Assert.assertEquals(feature.getName(Languages.GERMAN), "Merkmal");
        Assert.assertEquals(feature.getName(Languages.ENGLISH), "name");
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

    @SuppressWarnings("SimplifiableJUnitAssertion") // The last assertion can't be simplified because the method doesn't
    @Test                                           // exist but IntelliJ will somehow still try to do so
    public void featureSetEqualsTest() {
        FeatureSet featureSet1 = new FeatureSet(INTERNAL_NAME);
        FeatureSet featureSet2 = new FeatureSet(INTERNAL_NAME);
        Object o = new Object();
        Assert.assertEquals(featureSet1, featureSet1);
        Assert.assertEquals(featureSet1, featureSet2);
        Assert.assertEquals(featureSet2, featureSet1);
        Assert.assertFalse(featureSet1.equals(o));
    }

    @Test
    public void featureSetHashCodeTest() {
        FeatureSet featureSet1 = new FeatureSet(INTERNAL_NAME);
        FeatureSet featureSet2 = new FeatureSet(INTERNAL_NAME);
        Assert.assertEquals(featureSet1.hashCode(), featureSet2.hashCode());
    }

}
