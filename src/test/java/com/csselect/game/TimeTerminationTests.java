package com.csselect.game;

import com.csselect.inject.TestClass;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class TimeTerminationTests extends TestClass {


    @Override
    public void setUp() {
    }

    @Override
    public void reset() {

    }

    @Test
    public void reachedTime() {
        TimeTermination timeTermination = new TimeTermination(LocalDateTime.now().minusSeconds(100));
        timeTermination.setGame(new Game(1));
        Assert.assertTrue(timeTermination.checkTermination());
    }

    @Test
    public void notReachedTime() {
        TimeTermination timeTermination = new TimeTermination(LocalDateTime.now().plusSeconds(100));
        timeTermination.setGame(new Game(1));
        Assert.assertFalse(timeTermination.checkTermination());
    }

    @Test
    public void exactTime() {
        TimeTermination timeTermination = new TimeTermination(LocalDateTime.now());
        timeTermination.setGame(new Game(1));
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(timeTermination.checkTermination());
    }


}
