package com.csselect.parser;

import com.csselect.TestClass;
import com.csselect.game.BinarySelect;
import com.csselect.game.MatrixSelect;
import org.junit.Assert;
import org.junit.Test;

public class GamemodeParserTest extends TestClass {
    private String[] input;

    @Override
    public void setUp() {
        input = new String[2];
    }

    @Override
    public void reset() {
        input = new String[2];
    }

    @Test //there for debugging
    public void testMatrixParsing() {
        input[0] = "matrix";
        input[1] = "num=10, min=2, max=6";
        MatrixSelect gamemode = (MatrixSelect) GamemodeParser.getGamemode(input);
        Assert.assertNotNull(gamemode);
    }

    @Test
    public void testBinaryParsing() {
        input[0] = "binary";
        BinarySelect gamemode = (BinarySelect) GamemodeParser.getGamemode(input);
        Assert.assertNotNull(gamemode);
    }
}
