package com.csselect.database.mysql;

import com.csselect.inject.Injector;
import com.csselect.inject.MysqlTestClass;
import com.csselect.database.PlayerStatsAdapter;
import org.junit.Assert;
import org.junit.Test;

public class MysqlPlayerStatsAdapterTest extends MysqlTestClass {

    private static final String TEST_EMAIL = "test@test.de";
    private static final String TEST_HASH = "hash";
    private static final String TEST_SALT = "salt";
    private static final String TEST_LANGCODE = "haskell";
    private static final String TEST_USERNAME = "tester";

    private PlayerStatsAdapter adapter;
    private MysqlDatabaseAdapter mysqlDatabaseAdapter;

    @Override
    public void setUp() {
        mysqlDatabaseAdapter = (MysqlDatabaseAdapter) Injector.getInstance().getDatabaseAdapter();
        mysqlDatabaseAdapter.createPlayer(TEST_EMAIL, TEST_HASH, TEST_SALT, TEST_USERNAME);
        adapter = new MysqlPlayerStatsAdapter(1);
    }

    @Override
    public void reset() {
        mysqlDatabaseAdapter.executeMysqlUpdate("DROP DATABASE CS_SELECT;");
    }

    @Test
    public void testScore() {
        adapter.addScore(100);
        Assert.assertEquals(100, adapter.getScore());
    }

    @Test
    public void testRoundsPlayed() {
        adapter.playRound();
        Assert.assertEquals(1, adapter.getRoundsPlayed());
    }

    @Test
    public void testDailiesCompleted() {
        adapter.completeDaily();
        Assert.assertEquals(1, adapter.getDailiesCompleted());
    }

    @Test
    public void testMaxRoundScore() {
        adapter.setMaxRoundScore(100);
        Assert.assertEquals(100, adapter.getMaxRoundScore());
    }

    @Test
    public void testLastScore() {
        adapter.setLastScore(100);
        Assert.assertEquals(100, adapter.getLastScore());
    }

    @Test
    public void testHighestStreak() {
        adapter.setHighestStreak(100);
        Assert.assertEquals(100, adapter.getHighestStreak());
    }
}