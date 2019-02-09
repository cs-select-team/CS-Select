package com.csselect.database.mock;

import com.csselect.inject.Injector;
import com.csselect.inject.TestClass;
import com.csselect.database.GameAdapter;
import com.csselect.user.Player;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;

public class MockGameAdapterTest extends TestClass {

    private static final String TEST_TITLE = "PSE - Projekt Studenten exmatrikulieren";
    private static final String TEST_DESCRIPTION = "This game's aim is to find out which of students traits correlate with failure in the PSE";
    private static final String TEST_DATABASE_NAME = "PSE";
    private static final String TEST_EMAIL = "test@test.de";
    private static final String TEST_EMAIL2 = "test2@test.de";

    private GameAdapter adapter;
    private MockDatabaseAdapter mockDatabaseAdapter;

    @Override
    public void setUp() {
        adapter = new MockGameAdapter(0);
        mockDatabaseAdapter = (MockDatabaseAdapter) Injector.getInstance().getDatabaseAdapter();
    }

    @Override
    public void reset() {

    }

    @Test
    public void testGetID() {
        Assert.assertEquals(0, adapter.getID());
    }

    @Test
    public void testTitle() {
        adapter.setTitle(TEST_TITLE);
        Assert.assertEquals(TEST_TITLE, adapter.getTitle());
    }

    @Test
    public void testDescription() {
        adapter.setDescription(TEST_DESCRIPTION);
        Assert.assertEquals(TEST_DESCRIPTION, adapter.getDescription());
    }

    @Test
    public void testDatabaseName() {
        adapter.setDatabase(TEST_DATABASE_NAME);
        Assert.assertEquals(TEST_DATABASE_NAME, adapter.getDatabaseName());
    }

    @Test
    public void testFinished() {
        Assert.assertFalse(adapter.isFinished());
        adapter.setFinished();
        Assert.assertTrue(adapter.isFinished());
    }

    @Test
    public void testPlayerInvitation() {
        Collection<String> emails = new HashSet<>();
        emails.add(TEST_EMAIL);
        emails.add(TEST_EMAIL2);
        adapter.addInvitedPlayers(emails);
        Assert.assertEquals(emails, adapter.getInvitedPlayers());
    }

    @Test
    public void testPlayingPlayerAdding() {
        Collection<String> emails = new HashSet<>();
        Player player1 = mockDatabaseAdapter.createPlayer(TEST_EMAIL, TEST_EMAIL, TEST_EMAIL, TEST_EMAIL);
        Player player2 = mockDatabaseAdapter.createPlayer(TEST_EMAIL2, TEST_EMAIL, TEST_EMAIL, TEST_EMAIL2);
        emails.add(TEST_EMAIL);
        emails.add(TEST_EMAIL2);
        adapter.addPlayingPlayers(emails);
        Collection<Player> players = adapter.getPlayingPlayers();
        Assert.assertTrue(players.contains(player1));
        Assert.assertTrue(players.contains(player2));
    }

    @Test
    public void testInvitedPlayerRemoval() {
        Collection<String> emails = new HashSet<>();
        Collection<String> toRemove = new HashSet<>();
        emails.add(TEST_EMAIL);
        emails.add(TEST_EMAIL2);
        toRemove.add(TEST_EMAIL2);
        adapter.addInvitedPlayers(emails);
        adapter.removeInvitedPlayers(toRemove);
        Assert.assertTrue(adapter.getInvitedPlayers().contains(TEST_EMAIL));
        Assert.assertFalse(adapter.getInvitedPlayers().contains(TEST_EMAIL2));
    }
}