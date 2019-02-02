package com.csselect.game.gamecreation;

import com.csselect.TestClass;
import org.junit.Assert;
import org.junit.Test;

public class CreationTests extends TestClass {
    private GameCreator builder;

    private static final String TITLE_KEY = "title";
    private static final String DESCRIPTION_KEY = "description";
    private static final String DATABASE_KEY = "addressOrganiserDatabase";
    private static final String FEATURESET_KEY = "featureSet";
    private static final String GAMEMODE_KEY = "gamemode";
    private static final String TERMINATION_KEY = "termination";
    private static final String ADD_PLAYERS_KEY = "addPlayers";

    @Override
    public void setUp() {
        builder = new GameCreator(null);
    }

    @Override
    public void reset() {
    }

    @Test
    public void testSetTitle() {
        builder.setOption(TITLE_KEY, "TEST");
        Assert.assertEquals("TEST", builder.getGameOptions().getTitle());
    }

    @Test
    public void testSetDescription() {
        builder.setOption(DESCRIPTION_KEY, "TEST");
        Assert.assertEquals("TEST", builder.getGameOptions().getTitle());
    }

    @Test
    public void testSetTermination() {
        builder.setOption(TERMINATION_KEY, "rounds:50");
        Assert.assertNotNull(builder.getGameOptions().getTermination());
    }

    @Test
    public void testSetGamemodeBinary() {
        builder.setOption(GAMEMODE_KEY, "binary:50");
        Assert.assertNotNull(builder.getGameOptions().getGamemode());
    }

    @Test
    public void testAddPlayers() {
        builder.setOption(ADD_PLAYERS_KEY, "3@yahoo.de:1@gmail.com:2@gmx.de");
        Assert.assertTrue(builder.getGameOptions().getInvitedEmails().contains("3@yahoo.de"));
        Assert.assertTrue(builder.getGameOptions().getInvitedEmails().contains("1@gmail.com"));
        Assert.assertTrue(builder.getGameOptions().getInvitedEmails().contains("2@gmx.de"));
    }

    @Test
    public void isIncomplete() {
        Assert.assertFalse(builder.getGameOptions().isComplete());
    }

    @Test
    public void isComplete() {
        builder.setOption(TITLE_KEY, "title");
        builder.setOption(DESCRIPTION_KEY, "description");
        builder.setOption(DATABASE_KEY, "database");
        builder.setOption(FEATURESET_KEY, "populationGender");
        builder.setOption(GAMEMODE_KEY, "binary:50");
        builder.setOption(TERMINATION_KEY, "rounds:50");
        Assert.assertTrue(builder.getGameOptions().isComplete());
    }
}
