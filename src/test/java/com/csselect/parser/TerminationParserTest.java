package com.csselect.parser;

import com.csselect.inject.TestClass;
import com.csselect.game.NumberOfRoundsTermination;
import com.csselect.game.TimeTermination;
import org.junit.Assert;
import org.junit.Test;

public class TerminationParserTest extends TestClass {
    String input;

    @Override
    public void setUp() {
        input = new String();
    }

    @Override
    public void reset() {
        input = new String();
    }

    @Test
    public void testTimeTerminationParsing() {
        input= "time:1549639855";
        TimeTermination termination = (TimeTermination) TerminationParser.parseTermination(input);
        Assert.assertNotNull(termination);
    }

    @Test
    public void testRoundTerminationParsing() {

        NumberOfRoundsTermination termination = (NumberOfRoundsTermination) TerminationParser.parseTermination(input);
        Assert.assertNotNull(termination);
    }
}
