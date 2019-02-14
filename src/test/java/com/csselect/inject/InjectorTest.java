package com.csselect.inject;

import com.csselect.configuration.ApacheCommonsConfiguration;
import com.csselect.configuration.Configuration;
import com.csselect.database.DatabaseAdapter;
import com.csselect.database.mysql.MysqlDatabaseAdapter;
import com.csselect.mlserver.MLServer;
import com.csselect.mlserver.RESTMLServer;
import org.junit.Assert;
import org.junit.Test;

public class InjectorTest {

    @Test
    public void testProductionInjector() {
        DatabaseAdapter adapter = Injector.getInstance().getDatabaseAdapter();
        Configuration configuration = Injector.getInstance().getConfiguration();
        MLServer mlServer = Injector.getInstance().getMLServer();
        Assert.assertTrue(adapter instanceof MysqlDatabaseAdapter);
        Assert.assertTrue(configuration instanceof ApacheCommonsConfiguration);
        Assert.assertTrue(mlServer instanceof RESTMLServer);
    }
}
