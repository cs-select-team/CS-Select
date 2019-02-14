package com.csselect.parser;

import com.csselect.game.OrganiserTermination;
import com.csselect.game.Termination;
import com.csselect.game.TerminationComposite;
import com.csselect.inject.TestClass;
import com.csselect.game.NumberOfRoundsTermination;
import com.csselect.game.TimeTermination;
import org.junit.Assert;
import org.junit.Test;

public class TerminationParserTest extends TestClass {

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
        Termination termination = TerminationParser.parseTermination(TIME);
        Assert.assertNotNull(termination);
        Assert.assertTrue(termination instanceof TimeTermination);
    }

    @Test
    public void testRoundTerminationParsing() {
        Termination termination = TerminationParser.parseTermination(ROUNDS);
        Assert.assertNotNull(termination);
        Assert.assertTrue(termination instanceof NumberOfRoundsTermination);
    }

    @Test
    public void testCompositeTerminationParsing() {
        Termination termination = TerminationParser.parseTermination(COMPOSITE);
        Assert.assertNotNull(termination);
        Assert.assertTrue(termination instanceof TerminationComposite);
        TerminationComposite composite = (TerminationComposite) termination;
        Assert.assertEquals(2, composite.getTerminations().size());
    }

    @Test
    public void testInvalidTerminationParsing() {
        Termination termination = TerminationParser.parseTermination(INVALID);
        Assert.assertNotNull(termination);
        Assert.assertTrue(termination instanceof OrganiserTermination);
    }
}
