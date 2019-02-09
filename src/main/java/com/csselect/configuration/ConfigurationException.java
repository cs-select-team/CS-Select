package com.csselect.configuration;

/**
 * Exception indicating an error with the configuration
 */
public class ConfigurationException extends RuntimeException {

    private static final String MESSAGE = "Configuration not found!";

    /**
     * Creates a new {@link ConfigurationException}
     */
    public ConfigurationException() {
        super(MESSAGE);
    }
}
