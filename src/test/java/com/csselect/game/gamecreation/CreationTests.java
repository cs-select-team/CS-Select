package com.csselect.game.gamecreation;

import com.csselect.game.BinarySelect;
import com.csselect.game.Game;
import com.csselect.game.NumberOfRoundsTermination;
import com.csselect.game.gamecreation.patterns.Pattern;
import com.csselect.inject.TestClass;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CreationTests extends TestClass {
    private GameCreator builder;

    private static final String TITLE_KEY = "title";
    private static final String DESCRIPTION_KEY = "description";
    private static final String DATABASE_KEY = "addressOrganiserDatabase";
    private static final String FEATURESET_KEY = "featureSet";
    private static final String GAMEMODE_KEY = "gamemode";
    private static final String TERMINATION_KEY = "termination";
    private static final String ADD_PLAYERS_KEY = "addPlayers";

    private static final String EMAIL1 = "test@test.de";
    private static final String EMAIL2 = "test2@test.de";
    private static final String EMAIL_STRING = EMAIL1 + "," + EMAIL2;

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
        Assert.assertEquals("TEST", builder.getGameOptions().getDescription());
    }

    @Test
    public void testSetDataset() {
        builder.setOption(FEATURESET_KEY, "TEST");
        Assert.assertEquals("TEST", builder.getGameOptions().getDataset());
    }

    @Test
    public void testSetTermination() {
        builder.setOption(TERMINATION_KEY, NumberOfRoundsTermination.TYPE + ":50");
        Assert.assertNotNull(builder.getGameOptions().getTermination());
        Assert.assertTrue(builder.getGameOptions().getTermination() instanceof NumberOfRoundsTermination);
    }

    @Test
    public void testSetResultDatabaseName() {
        builder.setOption(DATABASE_KEY, "database");
        Assert.assertEquals("database", builder.getGameOptions().getResultDatabaseName());
    }

    @Test
    public void testSetGamemodeBinary() {
        builder.setOption(GAMEMODE_KEY, BinarySelect.TYPE + ":50");
        Assert.assertNotNull(builder.getGameOptions().getGamemode());
        Assert.assertTrue(builder.getGameOptions().getGamemode() instanceof BinarySelect);
    }

    @Test
    public void testAddPlayers() {
        builder.setOption(ADD_PLAYERS_KEY, "3@yahoo.de,1@gmail.com,2@gmx.de");
        Assert.assertTrue(builder.getGameOptions().getInvitedEmails().contains("3@yahoo.de"));
        Assert.assertTrue(builder.getGameOptions().getInvitedEmails().contains("1@gmail.com"));
        Assert.assertTrue(builder.getGameOptions().getInvitedEmails().contains("2@gmx.de"));
    }

    @Test
    public void testMakePattern() {
        fullySetOptions();
        Pattern pattern = builder.makePattern("test");
        Assert.assertNotNull(pattern);
        Assert.assertEquals("test", pattern.getTitle());
        builder.loadPattern(pattern);
        Assert.assertTrue(builder.getGameOptions().isComplete());
    }

    @Test
    public void testRemoveEmails() {
        builder.getGameOptions().addInvitedEmails(getEmails());
        Assert.assertTrue(builder.getGameOptions().getInvitedEmails().contains(TITLE_KEY));
        Assert.assertTrue(builder.getGameOptions().getInvitedEmails().contains(DESCRIPTION_KEY));
        builder.getGameOptions().removeInvitedEmails(getEmails());
        Assert.assertTrue(builder.getGameOptions().getInvitedEmails().isEmpty());
    }

    @Test
    public void testResetEmails() {
        builder.getGameOptions().addInvitedEmails(getEmails());
        Assert.assertTrue(builder.getGameOptions().getInvitedEmails().contains(TITLE_KEY));
        Assert.assertTrue(builder.getGameOptions().getInvitedEmails().contains(DESCRIPTION_KEY));
        builder.getGameOptions().resetEmails();
        Assert.assertTrue(builder.getGameOptions().getInvitedEmails().isEmpty());
    }

    @Test
    public void isIncomplete() {
        Assert.assertFalse(builder.getGameOptions().isComplete());
    }

    @Test
    public void isComplete() {
        fullySetOptions();
        Assert.assertTrue(builder.getGameOptions().isComplete());
    }

    @Test
    public void testAddNoPlayers() {
        builder.setOption(ADD_PLAYERS_KEY, "");
        Assert.assertTrue(builder.getGameOptions().getInvitedEmails().isEmpty());
    }

    @Test
    public void testDoCreate() {
        fullySetOptions();
        Game game = builder.doCreate();
        Assert.assertNotNull(game);
        Assert.assertEquals(builder.getGameOptions().getTitle(), game.getTitle());
        Assert.assertEquals(builder.getGameOptions().getDescription(), game.getDescription());
        Assert.assertEquals(builder.getGameOptions().getResultDatabaseName(), game.getNameOrganiserDatabase());
        Assert.assertEquals(builder.getGameOptions().getGamemode(), game.getGamemode());
        Assert.assertEquals(builder.getGameOptions().getTermination(), game.getTermination());
        Assert.assertTrue(game.getInvitedPlayers().contains(EMAIL1));
        Assert.assertTrue(game.getInvitedPlayers().contains(EMAIL2));
        Assert.assertTrue(builder.getGameOptions().getInvitedEmails().isEmpty());
    }

    @Test
    public void testIncompleteDoCreate() {
        Game game = builder.doCreate();
        Assert.assertNull(game);
    }

    private void fullySetOptions() {
        builder.setOption(TITLE_KEY, "title");
        builder.setOption(DESCRIPTION_KEY, "description");
        builder.setOption(DATABASE_KEY, "database");
        builder.setOption(FEATURESET_KEY, "populationGender");
        builder.setOption(GAMEMODE_KEY, BinarySelect.TYPE + ":50");
        builder.setOption(TERMINATION_KEY, NumberOfRoundsTermination.TYPE + ":50");
        builder.setOption(ADD_PLAYERS_KEY, EMAIL_STRING);
    }

    private Collection<String> getEmails() {
        Set<String> emails = new HashSet<>();
        emails.add(TITLE_KEY);
        emails.add(DESCRIPTION_KEY);
        return emails;
    }
}
