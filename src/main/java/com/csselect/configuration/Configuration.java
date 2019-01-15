package com.csselect.configuration;

/**
 * Abstraction of the configuration implementation used
 * Allows access to configuration values and is realized as singleton
 */
public interface Configuration {

    /**
     * Gets the password organisers have to use to be able to register
     * @return organiser password
     */
    String getOrganiserPassword();

    /**
     * Gets the URL of the Machine-Learning-Server used
     * @return ML-Server URL
     */
    String getMLServerURL();

    /**
     * Gets the type of database used
     * @return database type
     */
    String getDatabaseType();

    /**
     * Gets the hostname under which the database-server is located
     * @return hostname of the database-server
     */
    String getDatabaseHostname();

    /**
     * Gets the port of the database-server
     * @return port of the database-server
     */
    int getDatabasePort();

    /**
     * Gets the username of the database user to be used
     * @return username
     */
    String getDatabaseUsername();

    /**
     * Gets the password of the database user to be used
     * @return password
     */
    String getDatabasePassword();

    /**
     * Gets the path to the home directory
     * @return home directory path
     */
    String getHomeDirectory();
}
