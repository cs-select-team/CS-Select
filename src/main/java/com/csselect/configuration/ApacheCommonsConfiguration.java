package com.csselect.configuration;

import com.csselect.inject.Injector;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.pmw.tinylog.Logger;

import java.io.File;

/**
 * Apache Commons Configuration based implementation of the {@link Configuration} interface
 */
public final class ApacheCommonsConfiguration implements Configuration {

    private static final String DEFAULT_CONFIG_PATH = System.getProperty("catalina.home") + File.separator + "conf"
            + File.separator + "Catalina" + File.separator + "cs_select" + File.separator + "config.properties";

    private FileBasedConfiguration configuration;

    /**
     * Creates a new {@link ApacheCommonsConfiguration}. Only to be used by the {@link Injector}
     */
    public ApacheCommonsConfiguration() {
        this(DEFAULT_CONFIG_PATH);
    }

    /**
     * Constructor used to allow instantiation in test cases without dependency injection
     * @param configPath path to test config file
     */
    ApacheCommonsConfiguration(String configPath) {
        File configFile = new File(configPath);
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder
                = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(params.fileBased().setFile(configFile));
        try {
            this.configuration = builder.getConfiguration();
        } catch (org.apache.commons.configuration2.ex.ConfigurationException e) {
            Logger.error("Reading of configuration-file failed!");
            Logger.error(e);
            throw new ConfigurationException();
        }
    }

    @Override
    public String getOrganiserPassword() {
        return configuration.getString("organiserpassword");
    }

    @Override
    public String getMLServerURL() {
        return configuration.getString("mlserverurl");
    }

    @Override
    public String getCSSelectURL() {
        return configuration.getString("csselecturl");
    }

    @Override
    public String getTimezone() {
        return configuration.getString("timezone");
    }

    @Override
    public String getDatabaseHostname() {
        return configuration.getString("database.hostname");
    }

    @Override
    public int getDatabasePort() {
        return configuration.getInt("database.port");
    }

    @Override
    public String getDatabaseUsername() {
        return configuration.getString("database.username");
    }

    @Override
    public String getDatabasePassword() {
        return configuration.getString("database.password");
    }

    @Override
    public String getHomeDirectory() {
        return configuration.getString("homedirectory");
    }

    @Override
    public String getEmailHostname() {
        return configuration.getString("email.hostname");
    }

    @Override
    public int getEmailPort() {
        return configuration.getInt("email.port");
    }

    @Override
    public String getEmailAddress() {
        return configuration.getString("email.address");
    }

    @Override
    public String getEmailPassword() {
        return configuration.getString("email.password");
    }

    @Override
    public String getEmailUsername() {
        return configuration.getString("email.username");
    }
}
