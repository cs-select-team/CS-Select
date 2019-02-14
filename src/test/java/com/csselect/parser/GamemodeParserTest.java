package com.csselect.parser;

import com.csselect.inject.TestClass;
import com.csselect.game.BinarySelect;
import com.csselect.game.MatrixSelect;
import org.junit.Assert;
import org.junit.Test;

public class GamemodeParserTest extends TestClass {
    private String input;

    @Override
    public void setUp() {
        input = new String();
    }

    @Override
    public void reset() {
        input = new String();
    }

    @Test //there for debugging
    public void testMatrixParsing() {
        input = "matrixSelect,10,2,6";

        MatrixSelect gamemode = (MatrixSelect) GamemodeParser.parseGamemode(input);
        Assert.assertNotNull(gamemode);
    }

    @Test
    public void testBinaryParsing() {
        input = "binarySelect";
        BinarySelect gamemode = (BinarySelect) GamemodeParser.parseGamemode(input);
        Assert.assertNotNull(gamemode);
    }
}

