package com.csselect.database.mysql;

import com.csselect.Injector;
import com.csselect.MysqlTestClass;
import com.csselect.database.DatabaseAdapter;
import com.csselect.database.PlayerStatsAdapter;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

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
        mysqlDatabaseAdapter = (MysqlDatabaseAdapter) Injector.getInjector().getInstance(DatabaseAdapter.class);
        mysqlDatabaseAdapter.createPlayer(TEST_EMAIL, TEST_HASH, TEST_SALT, TEST_USERNAME);
        try {
            adapter = new MysqlPlayerStatsAdapter(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reset() {
        try {
            mysqlDatabaseAdapter.executeMysqlUpdate("DROP DATABASE CS_SELECT;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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