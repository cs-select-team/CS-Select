package com.csselect.user;

import com.csselect.Injector;
import com.csselect.TestClass;
import com.csselect.configuration.Configuration;
import com.csselect.user.management.OrganiserManagement;
import org.junit.Test;

public class OrganiserTests extends TestClass {
    private Organiser organiser;
    private static boolean registered = false;

    @Override
    public void setUp() {
        if (!registered) {
            String globalPassword = Injector.getInstance().getConfiguration().getOrganiserPassword();
            OrganiserManagement om = new OrganiserManagement();
            String[] args = new String[3];
            args[0] = "voldi@csselect.com";
            args[1] = "#ElDeRsTaB!#";
            args[2] = globalPassword;

            organiser = om.register(args);
            registered = true;
        } else {
            OrganiserManagement om = new OrganiserManagement();
            organiser = om.login("voldi@csselect.com", "#ElDeRsTaB!#");
        }
    }

    @Override
    public void reset() {
        organiser.logout();
        this.organiser = null;
    }

    @Test
    public void testSetTitle() {
        organiser.setGameOption("title", "TITLE");
    }

    @Test
    public void testSetDescription() {
        organiser.setGameOption("description", "TEST");
    }

    @Test
    public void testSetDatabaseAddress() {
        organiser.setGameOption("title", "TEST");
    }

    @Test
    public void testAddPlayers() {
        organiser.setGameOption("addPlayers", "1@gmail.com,2@yahoo.de,3@gmx.de,4@web.de");
    }

    @Test
    public void testSetTerminationTime() {
        organiser.setGameOption("termination",
                "time&2011-12-03T10:15:30");
    }

    @Test
    public void testSetTerminationRounds() {
        organiser.setGameOption("termination",
                "rounds&100");
    }

    @Test
    public void testSetGamemodeMatrix() {
        organiser.setGameOption("gamemode", "matrix&num=20,min=2,max=10");
    }

    @Test
    public void testSetGamemodeBinary() {
        organiser.setGameOption("gamemode", "binary&none");
    }
}
