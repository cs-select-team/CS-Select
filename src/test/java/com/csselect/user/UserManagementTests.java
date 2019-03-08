package com.csselect.user;

import com.csselect.inject.Injector;
import com.csselect.inject.TestClass;
import com.csselect.user.management.OrganiserManagement;
import com.csselect.user.management.PlayerManagement;
import org.junit.Assert;
import org.junit.Test;

public class UserManagementTests extends TestClass {
    private PlayerManagement pm;
    private Player darth;
    private Player luke;
    private OrganiserManagement om;
    private Organiser harry;
    private Organiser voldemort;
    private String GLOBAL_PASSWORD;
    private static final String EMAIL_1 = "skywalker1@csselect.com";
    private static final String USERNAME_1 = "Luke1";
    private static final String EMAIL_2 = "vader1@csselect.com";
    private static final String USERNAME_2 = "Darth1";

    @Override
    public void setUp() {
        GLOBAL_PASSWORD = Injector.getInstance().getConfiguration().getOrganiserPassword();
        pm = new PlayerManagement();
        om = new OrganiserManagement();
    }

    @Override
    public void reset() {
        darth = null;
        luke = null;
        harry = null;
        voldemort = null;
        om = null;
        pm = null;
    }

    @Test
    public void testValidRegistrationPlayer() {
        luke = pm.register(EMAIL_1, USERNAME_1);
        Assert.assertNotNull(luke);
        Assert.assertTrue(luke.getGames().isEmpty());
        Assert.assertNotNull(luke.getStats());
        Assert.assertNull(luke.getActiveRound());
        Assert.assertTrue(luke.getRounds().isEmpty());
    }

    @Test
    public void testTwoValidRegistrationsPlayer() {
        darth = pm.register(EMAIL_1, USERNAME_1);
        Assert.assertNotNull(darth);
        luke = pm.register(EMAIL_2, USERNAME_2);
        Assert.assertNotNull(luke);
        Assert.assertNotSame(darth, luke);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDuplicateEmailRegistrationPlayer() {
        darth = pm.register(EMAIL_1, USERNAME_1);
        luke = pm.register(EMAIL_1, USERNAME_2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDuplicateUsernameRegistrationPlayer() {
        darth = pm.register(EMAIL_1, USERNAME_1);
        luke = pm.register(EMAIL_2, USERNAME_1);
    }

    @Test
    public void testValidRegistrationOrganiser() {
        voldemort = om.register(EMAIL_1, GLOBAL_PASSWORD);
        Assert.assertNotNull(voldemort);
        Assert.assertNotNull(voldemort.getActiveGames());
        Assert.assertTrue(voldemort.getActiveGames().isEmpty());
        Assert.assertNotNull(voldemort.getTerminatedGames());
        Assert.assertTrue(voldemort.getTerminatedGames().isEmpty());
        Assert.assertNotNull(voldemort.getPatterns());
        Assert.assertTrue(voldemort.getPatterns().isEmpty());
    }

    @Test
    public void testTwoValidRegistrationsOrganiser() {
        voldemort = om.register(EMAIL_1, GLOBAL_PASSWORD);
        Assert.assertNotNull(voldemort);
        harry = om.register(EMAIL_2, GLOBAL_PASSWORD);
        Assert.assertNotNull(harry);
        Assert.assertNotSame(harry, voldemort);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDuplicateEmailRegistrationOrganiser() {
        voldemort = om.register(EMAIL_1, GLOBAL_PASSWORD);
        Assert.assertNotNull(voldemort);
        harry = om.register(EMAIL_1, GLOBAL_PASSWORD);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalOrganiserPasswordRegistration() {
        voldemort = om.register(EMAIL_1, "");
    }
}
