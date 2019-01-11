package com.csselect.configuration;

import java.io.File;

/**
 * Mock-Implementation of the {@link Configuration} interface
 */
public class MockConfiguration implements Configuration {

    @Override
    public String getOrganiserPassword() {
        return "sicherespasswort";
    }

    @Override
    public String getMLServerURL() {
        return "127.0.0.1";
    }

    @Override
    public String getDatabaseType() {
        return "mysql";
    }

    @Override
    public String getDatabaseHostname() {
        return "127.0.0.1";
    }

    @Override
    public int getDatabaseport() {
        return 3306;
    }

    @Override
    public String getDatabaseUsername() {
        return "admin";
    }

    @Override
    public String getDatabasePassword() {
        return "4dm1n";
    }

    @Override
    public String getHomeDirectory() {
        return System.getProperty("user.dir") + File.separator + "target" + File.separator + "CSSelect";
    }
}
