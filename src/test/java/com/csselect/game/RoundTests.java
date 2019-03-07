package com.csselect.game;

import com.csselect.inject.Injector;
import com.csselect.inject.TestClass;
import com.csselect.database.DatabaseAdapter;
import com.csselect.user.Player;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RoundTests extends TestClass {

    private Round round;

    @Override @Before
    public void setUp() {
        DatabaseAdapter adapter = Injector.getInstance().getDatabaseAdapter();
        Player player = adapter.getPlayer("email");
        if (player == null) {
            player = adapter.createPlayer("email", "hash", "salt", "username");
        }
        round = new StandardRound(player, 5, 2, 1, 1);

        Game game = new Game(1);

        Termination term = new NumberOfRoundsTermination(3);
        game.setTermination(term);

        FeatureSet features = new FeatureSet("abc");
        for (int i = 0; i < 5; i++) {
            features.addFeature(new Feature(i, "a"));
        }
        game.setFeatureSet(features);
        round.setGame(game);
        round.start();
    }

    @Override @After
    public void reset() {
        round = null;
    }


    @Test
    public void skip() {
        round.skip(new int[]{1, 2});
        Assert.assertEquals(round.getUselessFeatures().size(), 2);
    }

    @Test
    public void selectTwoFeatures() {
        round.selectFeatures(new int[]{3, 0, 1, 2, 4}, new int[]{1});
        Assert.assertEquals(round.getUselessFeatures().size(), 1);
        Assert.assertTrue(round.getPoints() > 0);
        Assert.assertTrue(round.getQuality() > 0);
        Assert.assertEquals(round.getChosenFeatures().size(), 5);
    }

    @Test
    public void featureSelectedAndUseless() {
        int[] feature = new int[]{1, 2, 1, 1, 2};
        round.selectFeatures(feature, feature);
        Assert.assertEquals(round.getUselessFeatures().size(), 5);
        Assert.assertTrue(round.getPoints() > 0);
        Assert.assertTrue(round.getQuality() > 0);
        Assert.assertEquals(round.getChosenFeatures().size(), 5);
    }

    @Test
    public void player() {
        Assert.assertNotNull(round.getPlayer());
        Assert.assertNotNull(round.getPlayer().getStats());
    }

    @Test
    public void start() {
        Assert.assertEquals(round.start().size(), 10);
        Assert.assertEquals(round, round.getPlayer().getActiveRound());
    }

    @Test
    public void nullFeatures() {
        round.selectFeatures(null, null);
    }

    @Test
    public void zeroFeatures() {
        round.selectFeatures(new int[0], new int[0]);
        Assert.assertEquals(round.getChosenFeatures().size(), 0);
    }
}
