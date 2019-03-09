package com.csselect.configuration;

import com.csselect.inject.Injector;

import java.io.File;

/**
 * Mock-Implementation of the {@link Configuration} interface
 */
public final class MockConfiguration implements Configuration {


    /**
     * Creates a new {@link MockConfiguration}. Only to be used by the {@link Injector}
     */
    public MockConfiguration() {

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
    public String getCSSelectURL() {
        return "http://localhost:8080/CS-Select";
    }

    @Override
    public String getTimezone() {
        return "CET";
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

    @Override
    public String getEmailHostname() {
        return "smtp.gmail.com";
    }

    @Override
    public int getEmailPort() {
        return 465;
    }

    @Override
    public String getEmailAddress() {
        return "bettercsselect@gmail.com";
    }

    @Override
    public String getEmailPassword() {
        return "PSEWs2018/19";
    }

    @Override
    public String getEmailUsername() {
        return this.getEmailAddress();
    }
}
