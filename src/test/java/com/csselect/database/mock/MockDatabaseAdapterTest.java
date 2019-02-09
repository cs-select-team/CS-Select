package com.csselect.database.mock;

import com.csselect.inject.Injector;
import com.csselect.inject.TestClass;
import com.csselect.database.OrganiserAdapter;
import com.csselect.database.PlayerAdapter;
import com.csselect.user.Organiser;
import com.csselect.user.Player;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

public class MockDatabaseAdapterTest extends TestClass {

    private MockDatabaseAdapter mockDatabaseAdapter;

    private static final String TEST_EMAIL = "test@test.de";
    private static final String TEST_EMAIL2 = "test2@test.de";
    private static final String TEST_HASH = "hash";
    private static final String TEST_SALT = "salt";
    private static final String TEST_USERNAME = "tester";
    private static final String TEST_USERNAME2 = "tester2";

    @Override
    public void setUp() {
        mockDatabaseAdapter = (MockDatabaseAdapter) Injector.getInstance().getDatabaseAdapter();
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
        PlayerAdapter adapter = mockDatabaseAdapter.getPlayerAdapter(0);
        Assert.assertNotNull(adapter);
        Assert.assertEquals(0, adapter.getID());
        Assert.assertEquals(TEST_USERNAME, adapter.getUsername());
        Assert.assertEquals(TEST_EMAIL, adapter.getEmail());
        Assert.assertEquals(TEST_HASH, adapter.getPasswordHash());
        Assert.assertEquals(TEST_SALT, adapter.getPasswordSalt());
    }

    @Test
    public void testOrganiserCreation() {
        Assert.assertNotNull(mockDatabaseAdapter);
        Organiser organiser = mockDatabaseAdapter.createOrganiser(TEST_EMAIL, TEST_HASH, TEST_SALT);
        Assert.assertNotNull(organiser);
        organiser = mockDatabaseAdapter.getOrganiser(TEST_EMAIL);
        Assert.assertNotNull(organiser);
        Assert.assertEquals(0, organiser.getId());
        OrganiserAdapter adapter = mockDatabaseAdapter.getOrganiserAdapter(0);
        Assert.assertNotNull(adapter);
        Assert.assertEquals(0, adapter.getID());
        Assert.assertEquals(TEST_EMAIL, adapter.getEmail());
        Assert.assertEquals(TEST_HASH, adapter.getPasswordHash());
        Assert.assertEquals(TEST_SALT, adapter.getPasswordSalt());
    }

    @Test
    public void testMultiplePlayerCreation() {
        Assert.assertNotNull(mockDatabaseAdapter);
        Player player1 = mockDatabaseAdapter.createPlayer(TEST_EMAIL, TEST_HASH, TEST_SALT, TEST_USERNAME);
        Player player2 = mockDatabaseAdapter.createPlayer(TEST_EMAIL2, TEST_HASH, TEST_SALT, TEST_USERNAME2);
        Assert.assertNotNull(player1);
        Assert.assertNotNull(player2);
        Assert.assertEquals(player1, mockDatabaseAdapter.getPlayer(TEST_EMAIL));
        Assert.assertEquals(player2, mockDatabaseAdapter.getPlayer(TEST_EMAIL2));
    }

    @Test
    public void testGetPlayers() {
        Player player1 = mockDatabaseAdapter.createPlayer(TEST_EMAIL, TEST_HASH, TEST_SALT, TEST_USERNAME);
        Player player2 = mockDatabaseAdapter.createPlayer(TEST_EMAIL2, TEST_HASH, TEST_SALT, TEST_USERNAME2);
        Collection<Player> players = mockDatabaseAdapter.getPlayers();
        Assert.assertTrue(players.contains(player1));
        Assert.assertTrue(players.contains(player2));
    }

    @Test
    public void testGetSaltsAndHashs() {
        Player player = mockDatabaseAdapter.createPlayer(TEST_EMAIL, TEST_HASH, TEST_SALT, TEST_USERNAME);
        Organiser organiser = mockDatabaseAdapter.createOrganiser(TEST_EMAIL2, TEST_HASH, TEST_SALT);
        Assert.assertEquals(TEST_HASH, mockDatabaseAdapter.getPlayerHash(player.getId()));
        Assert.assertEquals(TEST_SALT, mockDatabaseAdapter.getPlayerSalt(player.getId()));
        Assert.assertEquals(TEST_HASH, mockDatabaseAdapter.getOrganiserHash(organiser.getId()));
        Assert.assertEquals(TEST_SALT, mockDatabaseAdapter.getOrganiserSalt(organiser.getId()));
    }

}