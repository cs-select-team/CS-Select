package com.csselect.database.mock;

import com.csselect.Injector;
import com.csselect.TestClass;
import com.csselect.database.DatabaseAdapter;
import com.csselect.user.Organiser;
import com.csselect.user.Player;
import org.junit.Assert;
import org.junit.Test;

public class MockDatabaseAdapterTest extends TestClass {

    private MockDatabaseAdapter mockDatabaseAdapter;

    private static final String TEST_EMAIL = "test@test.de";
    private static final String TEST_HASH = "hash";
    private static final String TEST_SALT = "salt";
    private static final String TEST_USERNAME = "tester";

    @Override
    public void setUp() {
        mockDatabaseAdapter = (MockDatabaseAdapter) Injector.getInjector().getInstance(DatabaseAdapter.class);
    }

    @Override
    public void reset() {

    }

    @Test
    public void testPlayerCreation() {
        Assert.assertNotNull(mockDatabaseAdapter);
        Player player = mockDatabaseAdapter.createPlayer(TEST_EMAIL, TEST_HASH, TEST_SALT, TEST_USERNAME);
        Assert.assertNotNull(player);
        player = mockDatabaseAdapter.getPlayer(TEST_EMAIL);
        Assert.assertNotNull(player);
        Assert.assertEquals(0, player.getId());
    }

    @Test
    public void testOrganiserCreation() {
        Assert.assertNotNull(mockDatabaseAdapter);
        Organiser organiser = mockDatabaseAdapter.createOrganiser(TEST_EMAIL, TEST_HASH, TEST_SALT);
        Assert.assertNotNull(organiser);
        organiser = mockDatabaseAdapter.getOrganiser(TEST_EMAIL);
        Assert.assertNotNull(organiser);
        Assert.assertEquals(0, organiser.getId());
    }

}