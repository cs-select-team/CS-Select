package com.csselect.user;

import com.csselect.Injector;
import com.csselect.TestClass;
import com.csselect.configuration.Configuration;
import com.csselect.game.Gamemode;
import com.csselect.game.Termination;
import com.csselect.user.management.OrganiserManagement;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

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
        Assert.assertEquals(organiser.getGameOptions().getTitle(), "TITLE");
    }

    @Test
    public void testSetDescription() {
        organiser.setGameOption("description", "TEST");
        Assert.assertEquals(organiser.getGameOptions().getDescription(), "TEST");
    }

    @Test
    public void testSetFeaturesSet() {
        organiser.setGameOption("featureSet", "example");
        Assert.assertEquals(organiser.getGameOptions().getDataset(), "example");
    }

    @Test
    public void testSetDatabaseName() {
        organiser.setGameOption("addressOrganiserDatabase", "TEST");
        Assert.assertEquals(organiser.getGameOptions().getResultDatabaseName(), "TEST");
    }

    @Test
    public void testAddPlayers() {
        organiser.setGameOption("addPlayers", "1@gmail.com,2@yahoo.de,3@gmx.de,4@web.de");
        Collection<String> emails = organiser.getGameOptions().getInvitedEmails();
        Assert.assertTrue(emails.contains("1@gmail.com") && emails.contains("2@yahoo.de") &&
                emails.contains("3@gmx.de") && emails.contains("4@web.de"));
    }

    @Test
    public void testSetTerminationTime() {
        organiser.setGameOption("termination",
                "TimeTermination:2011-12-03T10:15:30");
        Termination termination = organiser.getGameOptions().getTermination();
        Assert.assertEquals(termination.getName(), "TimeTermination");
    }

    @Test
    public void testSetTerminationRounds() {
        organiser.setGameOption("termination", "NumberOfRoundsTermination:100");
        Termination termination = organiser.getGameOptions().getTermination();
        Assert.assertEquals(termination.getName(), "NumberOfRoundsTermination");
    }

    @Test
    public void testSetGamemodeMatrix() {
        organiser.setGameOption("gamemode", "MatrixSelect:num~20-min~2-max~10");
        Gamemode mode = organiser.getGameOptions().getGamemode();
        Assert.assertEquals(mode.getName(), "MatrixSelect");
    }

    @Test
    public void testSetGamemodeBinary() {
        organiser.setGameOption("gamemode", "BinarySelect:none");
        Gamemode mode = organiser.getGameOptions().getGamemode();
        Assert.assertEquals(mode.getName(), "BinarySelect");
    }
}
