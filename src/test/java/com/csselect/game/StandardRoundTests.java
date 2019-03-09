package com.csselect.game;


import com.csselect.inject.Injector;
import com.csselect.inject.TestClass;
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
        DatabaseAdapter adapter = Injector.getInstance().getDatabaseAdapter();
        Player player = adapter.getPlayer("email");
        if (player == null) {
            player = adapter.createPlayer("email", "hash", "salt", "username");
        }
        round = new StandardRound(player, 5, 3, 2, 2);

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
        round  = null;
    }


    @Test
    public void selectTooManyFeatures() {
        int[] feat = new int[]{1, 2, 3, 1, 1, 2, 1, 2, 3, 1, 2};
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

    @Test
    public void getters() {
        Assert.assertEquals(round.getNumberOfSelections(), 5);
        Assert.assertEquals(round.getFeaturesPerSelection(), 3);
        Assert.assertEquals(round.getMinSelect(), 2);
        Assert.assertEquals(round.getMaxSelect(), 2);
    }


}
