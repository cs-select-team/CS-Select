package com.csselect.parser;

import com.csselect.TestClass;
import com.csselect.game.NumberOfRoundsTermination;
import com.csselect.game.TimeTermination;
import org.junit.Assert;
import org.junit.Test;

public class TerminationParserTest extends TestClass {
    String input[];

    @Override
    public void setUp() {
        input = new String[2];
    }

    @Override
    public void reset() {
        input = new String[2];
    }

    @Test
    public void testTimeTerminationParsing() {
        input[0] = "time";
        input[1] = "2019-01-30T10:15:30";
        TimeTermination termination = (TimeTermination) TerminationParser.getTermination(input);
        Assert.assertNotNull(termination);
    }

    @Test
    public void testRoundTerminationParsing() {
        input[0] = "rounds";
        input[1] = "100";
        NumberOfRoundsTermination termination = (NumberOfRoundsTermination) TerminationParser.getTermination(input);
        Assert.assertNotNull(termination);
    }
}
