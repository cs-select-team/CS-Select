package com.csselect.database.mock;

import com.csselect.inject.TestClass;
import com.csselect.database.UserAdapter;
import org.junit.Assert;
import org.junit.Test;

public class MockUserAdapterTest extends TestClass {

    private static final String TEST_EMAIL = "test@test.de";
    private static final String TEST_HASH = "hash";
    private static final String TEST_SALT = "salt";
    private static final String TEST_LANGCODE = "haskell";

    private UserAdapter adapter;

    @Override
    public void setUp() {
        adapter = new MockPlayerAdapter(0);
    }

    @Override
    public void reset() {

    }

    @Test
    public void testID() {
        Assert.assertEquals(0, adapter.getID());
    }

    @Test
    public void testEmail() {
        adapter.setEmail(TEST_EMAIL);
        Assert.assertEquals(TEST_EMAIL, adapter.getEmail());
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