package com.csselect.game;

import com.csselect.inject.TestClass;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class TerminationCompositeTests extends TestClass {

    private TerminationComposite terminationComposite;

    @Override
    public void setUp() {
        this.terminationComposite = new TerminationComposite();
    }

    @Override
    public void reset() {

    }

    @Test
    public void terminatingOneTermination() {
        this.terminationComposite.add(new TimeTermination(LocalDateTime.now().minusSeconds(100)));
        this.terminationComposite.setGame(new Game(1));
        Assert.assertTrue(this.terminationComposite.checkTermination());
    }

    @Test
    public void terminatingTwoTerminations() {
        this.terminationComposite.add(new TimeTermination(LocalDateTime.now().minusSeconds(100)));
        this.terminationComposite.add(new NumberOfRoundsTermination(-1));
        this.terminationComposite.setGame(new Game(1));
        Assert.assertTrue(this.terminationComposite.checkTermination());
    }

    @Test
    public void terminatingOneOfTwoTerminations() {
        this.terminationComposite.add(new TimeTermination(LocalDateTime.now().minusSeconds(100)));
        this.terminationComposite.add(new NumberOfRoundsTermination(1));
        this.terminationComposite.setGame(new Game(1));
        Assert.assertTrue(this.terminationComposite.checkTermination());
    }

    @Test
    public void notTerminating() {
        this.terminationComposite.add(new TimeTermination(LocalDateTime.now().plusSeconds(100)));
        this.terminationComposite.add(new OrganiserTermination());
        this.terminationComposite.setGame(new Game(1));
        Assert.assertTrue(!this.terminationComposite.checkTermination());
    }

    @Test
    public void nullTermination() {
        this.terminationComposite.add(null);
        Assert.assertEquals(this.terminationComposite.getTerminations().size(), 0);
    }

    @Test
    public void setGame() {
        this.terminationComposite.add(new TimeTermination(LocalDateTime.now().plusSeconds(100)));
        Termination termination = new OrganiserTermination();
        termination.setGame(new Game(3));
        this.terminationComposite.add(termination);
        Game game = new Game(1);
        this.terminationComposite.setGame(game);
        for (Termination term : this.terminationComposite.getTerminations()) {
            Assert.assertEquals(term.game, game);
        }
    }
}
