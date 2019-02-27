package com.csselect.servletlisteners;

import com.csselect.database.DatabaseAdapter;
import com.csselect.database.DatabaseException;
import com.csselect.inject.Injector;
import org.pmw.tinylog.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public final class DatabaseAvailableChecker implements ServletContextListener {

    private static final String DATABASE_NOT_AVAILABLE_DURING_STARTUP = "We couldn't establish a database connection "
            + "during startup of CS:Select. As CS:Select can't start correctly without a database connection, startup "
            + "was aborted. Please make sure that your database server is available and restart CS:Select.";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            DatabaseAdapter adapter = Injector.getInstance().getDatabaseAdapter();
        } catch (DatabaseException e) {
            Logger.error(DATABASE_NOT_AVAILABLE_DURING_STARTUP);
            throw e;
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
