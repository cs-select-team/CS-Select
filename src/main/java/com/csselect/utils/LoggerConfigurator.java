package com.csselect.utils;

import com.csselect.inject.Injector;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.writers.FileWriter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

/**
 * Class used for configuring our logger on startup
 */
public final class LoggerConfigurator implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Configurator.defaultConfig().addWriter(new FileWriter(Injector.getInstance().getConfiguration()
        .getHomeDirectory() + File.separator + "log.txt")).level(Level.INFO).activate();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
