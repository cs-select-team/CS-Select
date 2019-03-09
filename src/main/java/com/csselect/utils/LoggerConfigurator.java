package com.csselect.utils;

import com.csselect.inject.Injector;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.Logger;
import org.pmw.tinylog.writers.FileWriter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.IOException;

/**
 * Class used for configuring our logger on startup
 */
public final class LoggerConfigurator implements ServletContextListener {

    private static final String LOGGER_CONFIG_PATH = System.getProperty("catalina.home") + File.separator + "conf"
            + File.separator + "Catalina" + File.separator + "cs_select" + File.separator + "logger.properties";
    private static final String NO_LOGGER_CONFIG_FOUND
            = "No tinylog configuration file found, using default configuration";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        File tinyLogConfig = new File(LOGGER_CONFIG_PATH);
        try {
            Configurator.fromFile(tinyLogConfig).activate();
        } catch (IOException e) {
            Configurator.defaultConfig().addWriter(new FileWriter(Injector.getInstance().getConfiguration()
                    .getHomeDirectory() + File.separator + "log.txt")).level(Level.INFO).activate();
            Logger.info(NO_LOGGER_CONFIG_FOUND);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //We don't need to do anything here, this class is only used to setup the config
    }
}
