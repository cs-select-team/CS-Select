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
     * Gets the timezone used for connecting to the database server
     * @return timezone
     */
    String getTimezone();

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

    /**
     * Gets the hostname of the email-server to use for sending emails
     * @return hostname of the email-server
     */
    String getEmailHostname();

    /**
     * Gets the port of the email-server to use for sending emails
     * @return port of the email-server
     */
    int getEmailPort();

    /**
     * Gets the email-address to be used for sending emails
     * @return email-address
     */
    String getEmailAddress();

    /**
     * Gets the password for the email account to use for sending emails
     * @return password of the email account
     */
    String getEmailPassword();
}
