package com.csselect.configuration;

import com.csselect.inject.TestClass;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class ConfigurationTests extends TestClass {

    private Configuration config;
    private static final String TEST_PATH = System.getProperty("user.dir") + File.separator + "src"
            + File.separator + "test" + File.separator + "resources" + File.separator + "config.properties";
    private static final String INVALID_PATH = "muchEmpty/soConfig/wow";

    @Override
    public void setUp() {
        config = new ApacheCommonsConfiguration(TEST_PATH);
    }

    @Override
    public void reset() {

    }

    @Test(expected = ConfigurationException.class)
    public void invalidPathTest() {
        Configuration configuration = new ApacheCommonsConfiguration(INVALID_PATH);
    }

    @Test
    public void loadingTest() { Assert.assertNotNull(config); }

    @Test
    public void testOrganiserPassword() {
        testString("sicherespasswort", config.getOrganiserPassword());
    }

    @Test
    public void testMLServerURL() {
        testString("127.0.0.1:8000", config.getMLServerURL());
    }

    @Test
    public void testCSSelectURL() {
        testString("http://localhost:8080/CS-Select", config.getCSSelectURL());
    }

    @Test
    public void testTimezone() {
        testString("CET", config.getTimezone());
    }

    @Test
    public void testDatabaseHostname() {
        testString("127.0.0.1", config.getDatabaseHostname());
    }

    @Test
    public void testDatabasePort() {
        Assert.assertEquals(3306, config.getDatabasePort());
    }

    @Test
    public void testDatabaseUsername() {
        testString("admin", config.getDatabaseUsername());
    }
    @Test
    public void testDatabasePassword() {
        testString("4dm1n", config.getDatabasePassword());
    }

    @Test
    public void testHomeDirectory() {
        testString("/test/CSSelect", config.getHomeDirectory());
    }

    @Test
    public void testEmailHostname() {
        testString("smtp.gmail.com", config.getEmailHostname());
    }

    @Test
    public void testEmailPort() {
        Assert.assertEquals(465, config.getEmailPort());
    }

    @Test
    public void testEmailAddress() {
        testString("bettercsselect@gmail.com", config.getEmailAddress());
    }

    @Test
    public void testEmailPassword() {
        testString("PSEWs2018/19", config.getEmailPassword());
    }

    @Test
    public void testEmailUsername() {
        testString("bettercsselect@gmail.com", config.getEmailUsername());
    }

    private void testString(String expected, String actual) {
        Assert.assertEquals(expected, actual);
    }
}
