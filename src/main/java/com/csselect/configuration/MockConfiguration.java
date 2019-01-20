package com.csselect.configuration;

import com.google.inject.Inject;

import java.io.File;

/**
 * Mock-Implementation of the {@link Configuration} interface
 */
public final class MockConfiguration implements Configuration {

    @Inject
    private MockConfiguration() {

    }

    @Override
    public String getOrganiserPassword() {
        return "sicherespasswort";
    }

    @Override
    public String getMLServerURL() {
        return "127.0.0.1:8000";
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
    public int getDatabasePort() {
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
