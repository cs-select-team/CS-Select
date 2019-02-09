package com.csselect.inject;

import com.csselect.configuration.Configuration;
import com.csselect.database.mysql.MysqlDatabaseAdapter;
import com.csselect.mlserver.RESTMLServer;
/**
 * This class manages which implementations should be injected by our {@link Injector}
 */

class CSSelectModule extends Module {

    /**
     * Creates a new {@link CSSelectModule}
     * @param configuration configuration instance to be used
     */
    CSSelectModule(Configuration configuration) {
        super(new MysqlDatabaseAdapter(configuration), new RESTMLServer(configuration), configuration);
    }
}
