package com.csselect.inject;


import com.csselect.configuration.Configuration;
import com.csselect.database.mysql.MysqlDatabaseAdapter;
import com.csselect.mlserver.MockMLServer;
/**
 * This class manages which implementations should be injected by our {@link Injector} while testing
 */
class CSSelectMysqlTestModule extends Module {

    /**
     * Creates a new {@link CSSelectMysqlTestModule}
     * @param configuration configuration instance to be used
     */
    CSSelectMysqlTestModule(Configuration configuration) {
        super(new MysqlDatabaseAdapter(configuration), new MockMLServer(), configuration);
    }
}
