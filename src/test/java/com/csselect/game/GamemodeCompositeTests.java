package com.csselect.game;

import com.csselect.inject.Injector;
import com.csselect.inject.TestClass;
import com.csselect.user.Player;
import com.csselect.database.DatabaseAdapter;

import org.junit.Assert;
import org.junit.Test;

public class GamemodeCompositeTests extends TestClass {

    private GamemodeComposite gamemodeComposite;

    @Override
    public void setUp() {
        this.gamemodeComposite = new GamemodeComposite();
    }

    @Override
    public void reset() {

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
    public void createRoundOneGamemode() {
        DatabaseAdapter adapter = Injector.getInstance().getDatabaseAdapter();
        Player player = adapter.createPlayer("email", "hash", "salt", "username");

        BinarySelect testBinary = new BinarySelect();

        this.gamemodeComposite.add(testBinary);

        Assert.assertNotNull(this.gamemodeComposite.createRound(player));

    }

    @Test
    public void createRoundTwoGamemodes() {
        DatabaseAdapter adapter = Injector.getInstance().getDatabaseAdapter();
        Player player = adapter.createPlayer("email", "hash", "salt", "username");

        BinarySelect testBinary = new BinarySelect();
        BinarySelect testBinaryB = new BinarySelect();

        this.gamemodeComposite.add(testBinary);
        this.gamemodeComposite.add(testBinaryB);

        Assert.assertNotNull(this.gamemodeComposite.createRound(player));

    }

}
