package com.csselect.database.mysql;

import com.csselect.gamification.PlayerStats;
import com.csselect.inject.Injector;
import com.csselect.inject.MysqlTestClass;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

public class MysqlPlayerAdapterTest extends MysqlTestClass {

    private MysqlDatabaseAdapter mysqlDatabaseAdapter;
    private MysqlPlayerAdapter playerAdapter;

    private static final String TEST_EMAIL = "test@test.de";
    private static final String TEST_EMAIL2 = "test2@test.de";
    private static final String TEST_HASH = "hash";
    private static final String TEST_SALT = "salt";
    private static final String TEST_USERNAME = "tester";
    private static final String TEST_USERNAME2 = "tester2";

    @Override
    public void setUp() {
        mysqlDatabaseAdapter = (MysqlDatabaseAdapter) Injector.getInstance().getDatabaseAdapter();
        try {
            playerAdapter = new MysqlPlayerAdapter(TEST_USERNAME, TEST_EMAIL, TEST_HASH, TEST_SALT);
        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testUsername() {
        playerAdapter.setUsername(TEST_USERNAME);
        String username = playerAdapter.getUsername();
        Assert.assertNotNull(username);
        Assert.assertEquals(TEST_USERNAME, username);
    }

    @Override
    public void reset() {
        mysqlDatabaseAdapter.executeMysqlUpdate("DROP DATABASE CS_SELECT;");
        mysqlDatabaseAdapter.executeMysqlUpdate("DROP DATABASE PSE;", "PSE");
    }

    @Test
    public void getPlayerStatsTest() {
        PlayerStats p1 = playerAdapter.getPlayerStats();
        PlayerStats p2 = playerAdapter.getPlayerStats();
        Assert.assertSame(p1, p2);
    }

    @Test
    public void getInvitedGamesEmptyTest() {
        Assert.assertTrue(playerAdapter.getInvitedGames().isEmpty());
    }

    @Test
    public void getRoundsEmptyTest() {
        Assert.assertTrue(playerAdapter.getRounds().isEmpty());
    }

    @Test
    public void getActiveGamesEmptyTest() {
        Assert.assertTrue(playerAdapter.getActiveGames().isEmpty());
    }

    @Test
    public void getTerminatedGamesEmptyTest() {
        Assert.assertTrue(playerAdapter.getTerminatedGames().isEmpty());
    }

    @SuppressWarnings("SimplifiableJUnitAssertion") //Assert.assertNotEquals method is suggested but doesn't exist
    @Test
    public void equalsTest() throws SQLException {
        MysqlPlayerAdapter playerAdapter2 = new MysqlPlayerAdapter(TEST_USERNAME2, TEST_EMAIL2, TEST_HASH, TEST_SALT);
        Assert.assertEquals(playerAdapter, playerAdapter);
        Assert.assertFalse(playerAdapter.equals(playerAdapter2));
        Assert.assertFalse(playerAdapter2.equals(playerAdapter));
    }
}