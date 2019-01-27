package com.csselect;

import com.csselect.configuration.Configuration;
import com.csselect.database.mysql.MysqlDatabaseAdapter;
import com.csselect.mlserver.RESTMLServer;
/**
 * This class manages which implementations should be injected by guice
 */

public class CSSelectModule extends Module {

    CSSelectModule(Configuration configuration) {
        super(new MysqlDatabaseAdapter(configuration), new RESTMLServer(configuration), configuration);
    }
}
