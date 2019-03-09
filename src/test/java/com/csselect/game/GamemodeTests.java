package com.csselect.game;

import com.csselect.inject.TestClass;
import org.junit.Assert;
import org.junit.Test;

public class GamemodeTests extends TestClass {


    private static final String MATRIX = "matrixSelect,10,2,6";
    private static final String BINARY = "binarySelect";
    private static final String INVALID = "testtest";

    @Override
    public void setUp() {

    }

    @Override
    public void reset() {

    }

    @Test //there for debugging
    public void testMatrixParsing() {
        Gamemode gamemode = Gamemode.parseGamemode(MATRIX);
        Assert.assertNotNull(gamemode);
        Assert.assertTrue(gamemode instanceof  MatrixSelect);
    }

    @Test
    public void testBinaryParsing() {
        Gamemode gamemode = Gamemode.parseGamemode(BINARY);
        Assert.assertNotNull(gamemode);
        Assert.assertTrue(gamemode instanceof BinarySelect);
    }

    @Test
    public void testInvalidGamemodeParsing() {
        Gamemode gamemode = Gamemode.parseGamemode(INVALID);
    }



    @Test
    public void binarySelect() {
        Gamemode bin = new BinarySelect();
        Assert.assertEquals(bin.getName(), "BinarySelect");
    }

    @Test
    public void matrixSelect() {
        Gamemode matr = new MatrixSelect(2, 3, 4);
        Assert.assertEquals(matr.getName(), "MatrixSelect");
    }

    @Test
    public void nullPlayer() {
        Gamemode bin = new BinarySelect();
        Assert.assertNull(bin.createRound(null));
    }
}
