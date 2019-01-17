package com.csselect.game;

import com.csselect.Injector;
import com.csselect.user.Player;
import com.csselect.database.DatabaseAdapter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GamemodeCompositeTests {

    private GamemodeComposite gamemodeComposite;

    @Before
    public void initialise() {
        this.gamemodeComposite = new GamemodeComposite();
    }

    @Test
    public void addGamemodes() {
        MatrixSelect testMatrix = new MatrixSelect(5, 3, 4);
        BinarySelect testBinary = new BinarySelect();

        this.gamemodeComposite.add(testMatrix);
        this.gamemodeComposite.add(testBinary);

        Assert.assertTrue(this.gamemodeComposite.getGamemodes().contains(testMatrix) && this.gamemodeComposite.getGamemodes().contains(testBinary));
    }

    @Test
    public void addDuplicateGamemode() {
        BinarySelect testBinary = new BinarySelect();

        this.gamemodeComposite.add(testBinary);
        this.gamemodeComposite.add(testBinary);

        Assert.assertEquals(1, this.gamemodeComposite.getGamemodes().size());

    }

    @Test
    public void deleteGamemode() {
        BinarySelect testBinary = new BinarySelect();

        this.gamemodeComposite.add(testBinary);
        this.gamemodeComposite.delete(testBinary);

        Assert.assertEquals(0, this.gamemodeComposite.getGamemodes().size());
    }

    @Test
    public void createRoundOneGamemode() {
        Injector.useTestMode();
        DatabaseAdapter adapter = Injector.getInjector().getInstance(DatabaseAdapter.class);
        Player player = adapter.createPlayer("email", "hash", "salt", "username");

        BinarySelect testBinary = new BinarySelect();

        this.gamemodeComposite.add(testBinary);

        Assert.assertNotNull(this.gamemodeComposite.createRound(player, 0));

    }

    @Test
    public void createRoundTwoGamemodes() {
        Injector.useTestMode();
        DatabaseAdapter adapter = Injector.getInjector().getInstance(DatabaseAdapter.class);
        Player player = adapter.createPlayer("email", "hash", "salt", "username");

        BinarySelect testBinary = new BinarySelect();
        BinarySelect testBinaryB = new BinarySelect();

        this.gamemodeComposite.add(testBinary);
        this.gamemodeComposite.add(testBinaryB);

        Assert.assertNotNull(this.gamemodeComposite.createRound(player, 0));

    }

}
