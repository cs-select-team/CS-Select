package com.csselect.game;

import com.csselect.Injector;
import com.csselect.TestClass;
import com.csselect.database.DatabaseAdapter;
import com.csselect.mlserver.MLServer;
import com.csselect.user.Player;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

public class RoundTests extends TestClass {

    private Round round;

    @Override @Before
    public void setUp() {
        DatabaseAdapter adapter = Injector.getInstance().getDatabaseAdapter();
        Player player = adapter.getPlayer("email");
        if (player == null) {
            player = adapter.createPlayer("email", "hash", "salt", "username");
        }
        round = new StandardRound(player, 5, 3, 0, 2);

        Game game = new Game(1);

        Termination term = new NumberOfRoundsTermination(3);
        game.setTermination(term);

        FeatureSet features = new FeatureSet("abc");
        for (int i = 0; i < 5; i++) {
            features.addFeature(new Feature(i, "a"));
        }
        game.setFeatureSet(features);
        round.setGame(game);
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
        round.selectFeatures(new int[]{3, 0}, new int[]{1});
        Assert.assertEquals(round.getUselessFeatures().size(), 1);
        Assert.assertTrue(round.getPoints() > 0);
        Assert.assertTrue(round.getQuality() > 0);
        Assert.assertEquals(round.getChosenFeatures().size(), 2);
    }

    @Test
    public void featureSelectedAndUseless() {
        int[] feature = new int[]{1, 2};
        round.selectFeatures(feature, feature);
        Assert.assertEquals(round.getUselessFeatures().size(), 2);
        Assert.assertTrue(round.getPoints() > 0);
        Assert.assertTrue(round.getQuality() > 0);
        Assert.assertEquals(round.getChosenFeatures().size(), 2);
    }

    @Test
    public void player() {
        Assert.assertNotNull(round.getPlayer());
        Assert.assertNotNull(round.getPlayer().getStats());
    }

    @Test
    public void start() {
        Assert.assertEquals(round.start().size(), 15);
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
