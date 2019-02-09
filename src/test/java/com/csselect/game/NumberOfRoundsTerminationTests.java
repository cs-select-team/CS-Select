package com.csselect.game;

import com.csselect.inject.TestClass;
import org.junit.Assert;
import org.junit.Test;

public class NumberOfRoundsTerminationTests extends TestClass{

    @Override
    public void setUp() {

    }

    @Override
    public void reset() {

    }

    @Test
    public void numberReached() {
        NumberOfRoundsTermination numberTerm = new NumberOfRoundsTermination(-1);
        numberTerm.setGame(new Game(1));
        Assert.assertTrue(numberTerm.checkTermination());
    }

    @Test
    public void numberNotReached() {
        NumberOfRoundsTermination numberTerm = new NumberOfRoundsTermination(1);
        numberTerm.setGame(new Game(1));
        Assert.assertTrue(!numberTerm.checkTermination());
    }

    @Test
    public void exactNumber() {
        NumberOfRoundsTermination numberTerm = new NumberOfRoundsTermination(0);
        numberTerm.setGame(new Game(1));
        Assert.assertTrue(numberTerm.checkTermination());
    }

}
