package com.csselect.database.mysql;

import com.csselect.inject.Injector;
import com.csselect.inject.MysqlTestClass;
import com.csselect.database.UserAdapter;
import org.junit.Assert;
import org.junit.Test;

public class MysqlUserAdapterTest extends MysqlTestClass {

    private static final String TEST_EMAIL = "test@test.de";
    private static final String TEST_EMAIL_2 = "test2@test.de";
    private static final String TEST_HASH = "hash";
    private static final String TEST_SALT = "salt";
    private static final String TEST_LANGCODE = "haskell";
    private static final String TEST_USERNAME = "tester";

    private UserAdapter adapter;
    private MysqlDatabaseAdapter mysqlDatabaseAdapter;

    @Override
    public void setUp() {
        mysqlDatabaseAdapter = (MysqlDatabaseAdapter) Injector.getInstance().getDatabaseAdapter();
        mysqlDatabaseAdapter.createPlayer(TEST_EMAIL, TEST_HASH, TEST_SALT, TEST_USERNAME);
        mysqlDatabaseAdapter.createOrganiser(TEST_EMAIL, TEST_HASH, TEST_SALT);
        adapter = mysqlDatabaseAdapter.getPlayerAdapter(1);
    }

    @Override
    public void reset() {
        mysqlDatabaseAdapter.executeMysqlUpdate("DROP DATABASE CS_SELECT;");
        mysqlDatabaseAdapter.executeMysqlUpdate("DROP DATABASE PSE;", "PSE");
    }

    @Test
    public void testID() {
        Assert.assertEquals(1, adapter.getID());
    }

    @Test
    public void testEmail() {
        adapter.setEmail(TEST_EMAIL_2);
        Assert.assertEquals(TEST_EMAIL_2, adapter.getEmail());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDuplicateEmail() {
        adapter.setEmail(TEST_EMAIL);
    }

    @Test
    public void testHashAndSalt() {
        adapter.setPassword(TEST_HASH, TEST_SALT);
        Assert.assertEquals(TEST_HASH, adapter.getPasswordHash());
        Assert.assertEquals(TEST_SALT, adapter.getPasswordSalt());
    }

    @Test
    public void testLanguage() {
        adapter.setLanguage(TEST_LANGCODE);
        Assert.assertEquals(TEST_LANGCODE, adapter.getLanguage());
    }
}