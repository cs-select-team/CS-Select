package com.csselect.game.gamecreation;

import com.csselect.TestClass;
import org.junit.Assert;
import org.junit.Test;

public class CreationTests extends TestClass {
    private GameCreator builder;

    @Override
    public void setUp() {
        builder = new GameCreator(null);
    }

    @Override
    public void reset() {
    }

    @Test
    public void testSetTitle() {
        builder.setOption("title", "TEST");
        Assert.assertEquals("TEST", builder.getGameOptions().getTitle());
    }

    @Test
    public void testSetTermination() {
        builder.setOption("termination", "rounds&50");
        Assert.assertNotNull(builder.getGameOptions().getTermination());
    }

    @Test
    public void testSetGamemodeBinary() {
        builder.setOption("gamemode", "binary&50");
        Assert.assertNotNull(builder.getGameOptions().getGamemode());
    }

    @Test
    public void testAddPlayers() {
        builder.setOption("addPlayers", "3@yahoo.de&1@gmail.com&2@gmx.de");
        Assert.assertTrue(builder.getGameOptions().getInvitedEmails().contains("3@yahoo.de"));
        Assert.assertTrue(builder.getGameOptions().getInvitedEmails().contains("1@gmail.com"));
        Assert.assertTrue(builder.getGameOptions().getInvitedEmails().contains("2@gmx.de"));
    }
}
