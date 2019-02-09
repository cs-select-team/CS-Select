package com.csselect.inject;

import com.csselect.configuration.MockConfiguration;
import com.csselect.database.mock.MockDatabaseAdapter;
import com.csselect.mlserver.MockMLServer;

/**
 * This class manages which implementations should be injected by our {@link Injector} while testing
 */
class CSSelectTestModule extends Module {

    /**
     * Creates a new {@link CSSelectTestModule}
     */
    CSSelectTestModule() {
        super(new MockDatabaseAdapter(), new MockMLServer(), new MockConfiguration());
    }
}
