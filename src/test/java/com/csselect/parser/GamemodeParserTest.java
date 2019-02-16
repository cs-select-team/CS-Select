package com.csselect.parser;

import com.csselect.game.Gamemode;
import com.csselect.inject.TestClass;
import com.csselect.game.BinarySelect;
import com.csselect.game.MatrixSelect;
import org.junit.Assert;
import org.junit.Test;

public class GamemodeParserTest extends TestClass {

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
        Gamemode gamemode = GamemodeParser.parseGamemode(MATRIX);
        Assert.assertNotNull(gamemode);
        Assert.assertTrue(gamemode instanceof  MatrixSelect);
    }

    @Test
    public void testBinaryParsing() {
        Gamemode gamemode = GamemodeParser.parseGamemode(BINARY);
        Assert.assertNotNull(gamemode);
        Assert.assertTrue(gamemode instanceof BinarySelect);
    }

    @Test
    public void testInvalidGamemodeParsing() {
        Gamemode gamemode = GamemodeParser.parseGamemode(INVALID);
    }
}

