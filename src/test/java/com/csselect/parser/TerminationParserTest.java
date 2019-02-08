package com.csselect.parser;

import com.csselect.TestClass;
import com.csselect.game.NumberOfRoundsTermination;
import com.csselect.game.Termination;
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
        input[0] = "TimeTermination";
        input[1] = "2019-01-30T10:15:30";
        Termination termination = TerminationParser.getTermination(input);
        Assert.assertEquals("TimeTermination", termination.getName());
    }

    @Test
    public void testRoundTerminationParsing() {
        input[0] = "NumberOfRoundsTermination";
        input[1] = "100";
        Termination termination = TerminationParser.getTermination(input);
        Assert.assertEquals("NumberOfRoundsTermination", termination.getName());
    }
}
