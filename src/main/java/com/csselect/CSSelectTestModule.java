package com.csselect;

import com.csselect.configuration.MockConfiguration;
import com.csselect.database.mock.MockDatabaseAdapter;
import com.csselect.mlserver.MockMLServer;

/**
 * This class manages which implementations should be injected by guice while testing
 */
public class CSSelectTestModule extends Module {

    CSSelectTestModule() {
        super(new MockDatabaseAdapter(), new MockMLServer(), new MockConfiguration());
    }
}
