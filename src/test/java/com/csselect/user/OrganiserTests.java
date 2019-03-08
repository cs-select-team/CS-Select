package com.csselect.user;

import com.csselect.inject.Injector;
import com.csselect.inject.TestClass;
import com.csselect.game.Gamemode;
import com.csselect.game.Termination;
import com.csselect.user.management.OrganiserManagement;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

public class OrganiserTests extends TestClass {


    private static final String EMAIL = "skywalker1@csselect.com";

    private Organiser organiser;

    @Override
    public void setUp() {
        OrganiserManagement om = new OrganiserManagement();
        organiser = om.register(EMAIL, Injector.getInstance().getConfiguration().getOrganiserPassword());
    }

    @Override
    public void reset() {
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
                "time:200011121");
        Termination termination = organiser.getGameOptions().getTermination();
        Assert.assertEquals(termination.getName(), "TimeTermination");
    }

    @Test
    public void testSetTerminationRounds() {
        organiser.setGameOption("termination", "rounds:100");
        Termination termination = organiser.getGameOptions().getTermination();
        Assert.assertEquals(termination.getName(), "NumberOfRoundsTermination");
    }

    @Test
    public void testSetGamemodeMatrix() {
        organiser.setGameOption("gamemode", "matrixSelect,20,2,10");
        Gamemode mode = organiser.getGameOptions().getGamemode();
        Assert.assertEquals(mode.getName(), "MatrixSelect");
    }

    @Test
    public void testSetGamemodeBinary() {
        organiser.setGameOption("gamemode", "binarySelect");
        Gamemode mode = organiser.getGameOptions().getGamemode();
        Assert.assertEquals(mode.getName(), "BinarySelect");
    }
}
