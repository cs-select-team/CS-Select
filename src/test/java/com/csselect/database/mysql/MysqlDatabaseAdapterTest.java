package com.csselect.database.mysql;

import com.csselect.Injector;
import com.csselect.TestClass;
import com.csselect.configuration.Configuration;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlDatabaseAdapterTest extends TestClass {

    private MysqlDatabaseAdapter mysqlDatabaseAdapter;

    @Override
    public void setUp() {
        mysqlDatabaseAdapter = new MysqlDatabaseAdapter(Injector.getInjector().getInstance(Configuration.class));
    }

    @Override
    public void reset() {
        try {
            mysqlDatabaseAdapter.executeMysqlUpdate("DROP DATABASE CS_SELECT");
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
    }

    private void validateTimezone(ResultSet resultSet) throws SQLException {
        resultSet.next();
        String globalTimeZone = resultSet.getString(1);
        String sessionTimeZone = resultSet.getString(2);
        Assert.assertThat(globalTimeZone, Matchers.either(Matchers.is("CET")).or(Matchers.is("SYSTEM")));
        Assert.assertThat(sessionTimeZone, Matchers.either(Matchers.is("CET")).or(Matchers.is("SYSTEM")));
    }
}