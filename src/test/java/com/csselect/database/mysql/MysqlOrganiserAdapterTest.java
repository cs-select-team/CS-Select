package com.csselect.database.mysql;

import com.csselect.game.BinarySelect;
import com.csselect.game.NumberOfRoundsTermination;
import com.csselect.game.gamecreation.patterns.GameOptions;
import com.csselect.game.gamecreation.patterns.Pattern;
import com.csselect.inject.Injector;
import com.csselect.inject.MysqlTestClass;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MysqlOrganiserAdapterTest extends MysqlTestClass {

    private MysqlDatabaseAdapter mysqlDatabaseAdapter;
    private MysqlOrganiserAdapter organiserAdapter;

    private static final String TEST_EMAIL = "test@test.de";
    private static final String TEST_EMAIL2 = "test2@test.de";
    private static final String TEST_HASH = "hash";
    private static final String TEST_SALT = "salt";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String DATASET = "populationGender";
    private static final String DATABASE_NAME = "PSE";

    @Override
    public void setUp() {
        mysqlDatabaseAdapter = (MysqlDatabaseAdapter) Injector.getInstance().getDatabaseAdapter();
        try {
            organiserAdapter = new MysqlOrganiserAdapter(TEST_EMAIL, TEST_HASH, TEST_SALT);
        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Override
    public void reset() {
        mysqlDatabaseAdapter.executeMysqlUpdate("DROP DATABASE CS_SELECT;");
        mysqlDatabaseAdapter.executeMysqlUpdate("DROP DATABASE PSE;", "PSE");
    }

    @Test
    public void getActiveGamesEmptyTest() {
        Assert.assertTrue(organiserAdapter.getActiveGames().isEmpty());
    }

    @Test
    public void getTerminatedGamesEmptyTest() {
        Assert.assertTrue(organiserAdapter.getTerminatedGames().isEmpty());
    }

    @Test
    public void testGameTitleInUse() {
        Assert.assertFalse(organiserAdapter.gameTitleInUse(TEST_EMAIL));
        mysqlDatabaseAdapter.executeMysqlUpdate("INSERT INTO "  + TableNames.GAMES + " ("
                        + ColumnNames.TITLE + ", " + ColumnNames.ORGANISER_ID + ") VALUES (?,?);",
                new StringParam(TEST_EMAIL), new IntParam(organiserAdapter.getID()));
        Assert.assertTrue(organiserAdapter.gameTitleInUse(TEST_EMAIL));
    }

    @Test
    public void patternTest() {
        GameOptions gameOptions = getCompleteGameOptions();
        Pattern pattern = new Pattern(gameOptions, TITLE);
        Assert.assertTrue(organiserAdapter.getPatterns().isEmpty());
        organiserAdapter.addPattern(pattern);
        Assert.assertTrue(organiserAdapter.getPatterns().contains(pattern));
        Assert.assertEquals(1, organiserAdapter.getPatterns().size());
        Pattern pattern2 = new Pattern(gameOptions, TITLE);
        organiserAdapter.addPattern(pattern2);
        Assert.assertEquals(1, organiserAdapter.getPatterns().size());
    }

    private Collection<String> getEmails() {
        Set<String> emails = new HashSet<>();
        emails.add(TEST_EMAIL);
        emails.add(TEST_EMAIL2);
        return emails;
    }

    private GameOptions getCompleteGameOptions() {
        GameOptions gameOptions = new GameOptions();
        gameOptions.setTitle(TITLE);
        gameOptions.setDescription(DESCRIPTION);
        gameOptions.setDataset(DATASET);
        gameOptions.setGamemode(new BinarySelect());
        gameOptions.setTermination(new NumberOfRoundsTermination(10));
        gameOptions.setResultDatabaseName(DATABASE_NAME);
        gameOptions.addInvitedEmails(getEmails());
        return gameOptions;
    }
}