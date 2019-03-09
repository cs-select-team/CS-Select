package com.csselect.game;

import com.csselect.inject.TestClass;
import org.junit.Assert;
import org.junit.Test;

public class TerminationTest extends TestClass {
    private static final String TIME = "time:1549639855";
    private static final String ROUNDS = "rounds:10";
    private static final String COMPOSITE = TIME + "," + ROUNDS;
    private static final String INVALID = "testtest";

    @Override
    public void setUp() {

    }

    @Override
    public void reset() {

    }

    @Test
    public void testTimeTerminationParsing() {
        Termination termination = Termination.parseTermination(TIME);
        Assert.assertNotNull(termination);
        Assert.assertTrue(termination instanceof TimeTermination);
    }

    @Test
    public void testRoundTerminationParsing() {
        Termination termination = Termination.parseTermination(ROUNDS);
        Assert.assertNotNull(termination);
        Assert.assertTrue(termination instanceof NumberOfRoundsTermination);
    }

    @Test
    public void testCompositeTerminationParsing() {
        Termination termination = Termination.parseTermination(COMPOSITE);
        Assert.assertNotNull(termination);
        Assert.assertTrue(termination instanceof TerminationComposite);
        TerminationComposite composite = (TerminationComposite) termination;
        Assert.assertEquals(2, composite.getTerminations().size());
    }

    @Test
    public void testInvalidTerminationParsing() {
        Termination termination = Termination.parseTermination(INVALID);
        Assert.assertNotNull(termination);
        Assert.assertTrue(termination instanceof OrganiserTermination);
    }
}

