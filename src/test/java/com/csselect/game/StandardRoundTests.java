package com.csselect.game;


import com.csselect.Injector;
import com.csselect.TestClass;
import com.csselect.database.DatabaseAdapter;
import com.csselect.user.Player;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class StandardRoundTests extends TestClass {

    private StandardRound round;

    @Override @Before
    public void setUp() {
        DatabaseAdapter adapter = Injector.getInjector().getInstance(DatabaseAdapter.class);
        Player player = adapter.createPlayer("email", "hash", "salt", "username");
        round = new StandardRound(player, 5, 3, 2, 2);

        Game game = new Game(1);

        FeatureSet features = new FeatureSet("abc");
        for (int i = 0; i < 5; i++) {
            features.addFeature(new Feature(i, "a"));
        }
        game.setFeatureSet(features);
        // game.setMlserver(new MockMLServer());
        round.setGame(game);
    }

    @Override @After
    public void reset() {
        round  = null;
    }

    @Test
    public void selectNotEnoughFeatures() {
        int[] feat = new int[]{1};
        Assert.assertEquals(round.selectFeatures(feat, feat), -1);
    }

    @Test
    public void selectTooManyFeatures() {
        int[] feat = new int[]{1, 2, 3};
        Assert.assertEquals(round.selectFeatures(feat, feat), -1);
    }

    @Test
    public void provideFeatures() {
        List<Feature> features = round.provideFeatures();
        Assert.assertEquals(15, features.size());


        for (int i = 0; i < 5; i++) {
            Assert.assertTrue(features.get(3 * i).getID() != features.get((3 * i) + 1).getID());
            Assert.assertTrue(features.get(3 * i).getID() != features.get((3 * i) + 2).getID());
            Assert.assertTrue(features.get((3 * i) + 2).getID() != features.get((3 * i) + 1).getID());

        }
    }


}
