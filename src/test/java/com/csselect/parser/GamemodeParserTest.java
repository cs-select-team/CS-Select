package com.csselect.parser;

import com.csselect.TestClass;
import com.csselect.game.Gamemode;
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
        input[0] = "MatrixSelect";
        input[1] = "num~10-min~2-max~6";
        Gamemode gamemode = GamemodeParser.getGamemode(input);
        Assert.assertEquals("MatrixSelect", gamemode.getName());
    }

    @Test
    public void testBinaryParsing() {
        input[0] = "BinarySelect";
        Gamemode gamemode = GamemodeParser.getGamemode(input);
        Assert.assertEquals("BinarySelect", gamemode.getName());
    }
}
