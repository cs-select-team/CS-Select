package com.csselect.game;

import com.csselect.inject.TestClass;
import org.junit.Assert;
import org.junit.Test;

public class GamemodeTests extends TestClass {

    @Override
    public void setUp() {

    }

    @Override
    public void reset() {

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
}
