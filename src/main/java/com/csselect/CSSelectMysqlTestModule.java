package com.csselect;


import com.csselect.configuration.Configuration;
import com.csselect.database.mysql.MysqlDatabaseAdapter;
import com.csselect.mlserver.MockMLServer;
/**
 * This class manages which implementations should be injected by guice while testing
 */
public class CSSelectMysqlTestModule extends Module {

    CSSelectMysqlTestModule(Configuration configuration) {
        super(new MysqlDatabaseAdapter(configuration), new MockMLServer(), configuration);
    }
}
