package com.csselect.database.mysql;

import com.csselect.Injector;
import com.csselect.MysqlTestClass;
import com.csselect.database.DatabaseAdapter;
import com.csselect.database.GameAdapter;
import com.csselect.user.Player;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

public class MysqlGameAdapterTest extends MysqlTestClass {

    private static final String TEST_TITLE = "PSE - Projekt Studenten exmatrikulieren";
    private static final String TEST_DESCRIPTION = "This games aim is to find out which of students traits correlate with failure in the PSE";
    private static final String TEST_DATABASE_NAME = "PSE";
    private static final String TEST_EMAIL = "test@test.de";
    private static final String TEST_EMAIL2 = "test2@test.de";

    private GameAdapter adapter;
    private MysqlDatabaseAdapter mysqlDatabaseAdapter;

    @Override
    public void setUp() {
        mysqlDatabaseAdapter = (MysqlDatabaseAdapter) Injector.getInstance().getDatabaseAdapter();
        try {
            adapter = new MysqlGameAdapter();
            adapter.setDatabase("PSE");
        } catch (SQLException e) {
            System.err.println("Setup-Error occurred!");
            e.printStackTrace();
        }
    }

    @Override
    public void reset() {
        try {
            mysqlDatabaseAdapter.executeMysqlUpdate("DROP DATABASE CS_SELECT;");
            mysqlDatabaseAdapter.executeMysqlUpdate("DROP DATABASE PSE;", "PSE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetID() {
        Assert.assertEquals(1, adapter.getID());
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
        Player player1 = mysqlDatabaseAdapter.createPlayer(TEST_EMAIL, TEST_EMAIL, TEST_EMAIL, TEST_EMAIL);
        Player player2 = mysqlDatabaseAdapter.createPlayer(TEST_EMAIL2, TEST_EMAIL, TEST_EMAIL, TEST_EMAIL2);
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