package com.csselect.database.mysql;

import com.csselect.Injector;
import com.csselect.MysqlTestClass;
import com.csselect.configuration.Configuration;
import com.csselect.database.OrganiserAdapter;
import com.csselect.database.PlayerAdapter;
import com.csselect.user.Organiser;
import com.csselect.user.Player;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class MysqlDatabaseAdapterTest extends MysqlTestClass {

    private MysqlDatabaseAdapter mysqlDatabaseAdapter;

    private static final String TEST_EMAIL = "test@test.de";
    private static final String TEST_EMAIL2 = "test2@test.de";
    private static final String TEST_HASH = "hash";
    private static final String TEST_SALT = "salt";
    private static final String TEST_USERNAME = "tester";
    private static final String TEST_USERNAME2 = "tester2";

    @Override
    public void setUp() {
        mysqlDatabaseAdapter = new MysqlDatabaseAdapter(Injector.getInjector().getInstance(Configuration.class));
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
    public void testTimezone() throws SQLException {
        ResultSet resultSet = mysqlDatabaseAdapter.executeMysqlQuery("SELECT @@global.time_zone, @@session.time_zone;");
        validateTimezone(resultSet);
    }

    @Test
    public void testTimezoneOtherDatabase() throws SQLException {
        ResultSet resultSet = mysqlDatabaseAdapter.executeMysqlQuery("SELECT @@global.time_zone, @@session.time_zone;", "ORGANISERS");
        validateTimezone(resultSet);
    }

    @Test
    public void testTableCreation() throws SQLException {
        Assert.assertTrue(mysqlDatabaseAdapter.executeMysqlQuery("SHOW TABLES LIKE 'organisers'").next());
        Assert.assertTrue(mysqlDatabaseAdapter.executeMysqlQuery("SHOW TABLES LIKE 'players'").next());
        Assert.assertTrue(mysqlDatabaseAdapter.executeMysqlQuery("SHOW TABLES LIKE 'games'").next());
        Assert.assertTrue(mysqlDatabaseAdapter.executeMysqlQuery("SHOW TABLES LIKE 'patterns'").next());
        Assert.assertTrue(mysqlDatabaseAdapter.executeMysqlQuery("SHOW TABLES LIKE 'playerstats'").next());
    }

    private void validateTimezone(ResultSet resultSet) throws SQLException {
        resultSet.next();
        String globalTimeZone = resultSet.getString(1);
        String sessionTimeZone = resultSet.getString(2);
        Assert.assertThat(globalTimeZone, Matchers.either(Matchers.is("CET")).or(Matchers.is("SYSTEM")));
        Assert.assertThat(sessionTimeZone, Matchers.either(Matchers.is("CET")).or(Matchers.is("SYSTEM")));
    }

    @Test
    public void testPlayerCreation() {
        Assert.assertNotNull(mysqlDatabaseAdapter);
        Player player = mysqlDatabaseAdapter.createPlayer(TEST_EMAIL, TEST_HASH, TEST_SALT, TEST_USERNAME);
        Assert.assertNotNull(player);
        player = mysqlDatabaseAdapter.getPlayer(TEST_EMAIL);
        Assert.assertNotNull(player);
        Assert.assertEquals(1, player.getId());
        PlayerAdapter adapter = mysqlDatabaseAdapter.getPlayerAdapter(1);
        Assert.assertNotNull(adapter);
        Assert.assertEquals(1, adapter.getID());
        Assert.assertEquals(TEST_USERNAME, adapter.getUsername());
        Assert.assertEquals(TEST_EMAIL, adapter.getEmail());
        Assert.assertEquals(TEST_HASH, adapter.getPasswordHash());
        Assert.assertEquals(TEST_SALT, adapter.getPasswordSalt());
    }

    @Test
    public void testOrganiserCreation() {
        Assert.assertNotNull(mysqlDatabaseAdapter);
        Organiser organiser = mysqlDatabaseAdapter.createOrganiser(TEST_EMAIL, TEST_HASH, TEST_SALT);
        Assert.assertNotNull(organiser);
        organiser = mysqlDatabaseAdapter.getOrganiser(TEST_EMAIL);
        Assert.assertNotNull(organiser);
        Assert.assertEquals(1, organiser.getId());
        OrganiserAdapter adapter = mysqlDatabaseAdapter.getOrganiserAdapter(1);
        Assert.assertNotNull(adapter);
        Assert.assertEquals(1, adapter.getID());
        Assert.assertEquals(TEST_EMAIL, adapter.getEmail());
        Assert.assertEquals(TEST_HASH, adapter.getPasswordHash());
        Assert.assertEquals(TEST_SALT, adapter.getPasswordSalt());
    }

    @Test
    public void testMultiplePlayerCreation() {
        Assert.assertNotNull(mysqlDatabaseAdapter);
        Player player1 = mysqlDatabaseAdapter.createPlayer(TEST_EMAIL, TEST_HASH, TEST_SALT, TEST_USERNAME);
        Player player2 = mysqlDatabaseAdapter.createPlayer(TEST_EMAIL2, TEST_HASH, TEST_SALT, TEST_USERNAME2);
        Assert.assertNotNull(player1);
        Assert.assertNotNull(player2);
        Assert.assertNotNull(mysqlDatabaseAdapter.getPlayer(TEST_EMAIL));
        Assert.assertNotNull(mysqlDatabaseAdapter.getPlayer(TEST_EMAIL2));
        Assert.assertEquals(player1, mysqlDatabaseAdapter.getPlayer(TEST_EMAIL));
        Assert.assertEquals(player2, mysqlDatabaseAdapter.getPlayer(TEST_EMAIL2));
    }

    @Test
    public void testGetPlayers() {
        Player player1 = mysqlDatabaseAdapter.createPlayer(TEST_EMAIL, TEST_HASH, TEST_SALT, TEST_USERNAME);
        Player player2 = mysqlDatabaseAdapter.createPlayer(TEST_EMAIL2, TEST_HASH, TEST_SALT, TEST_USERNAME2);
        Collection<Player> players = mysqlDatabaseAdapter.getPlayers();
        Assert.assertTrue(players.contains(player1));
        Assert.assertTrue(players.contains(player2));
    }

    @Test
    public void testGetSaltsAndHashs() {
        Player player = mysqlDatabaseAdapter.createPlayer(TEST_EMAIL, TEST_HASH, TEST_SALT, TEST_USERNAME);
        Organiser organiser = mysqlDatabaseAdapter.createOrganiser(TEST_EMAIL2, TEST_HASH, TEST_SALT);
        Assert.assertEquals(TEST_HASH, mysqlDatabaseAdapter.getPlayerHash(player.getId()));
        Assert.assertEquals(TEST_SALT, mysqlDatabaseAdapter.getPlayerSalt(player.getId()));
        Assert.assertEquals(TEST_HASH, mysqlDatabaseAdapter.getOrganiserHash(organiser.getId()));
        Assert.assertEquals(TEST_SALT, mysqlDatabaseAdapter.getOrganiserSalt(organiser.getId()));
    }
}