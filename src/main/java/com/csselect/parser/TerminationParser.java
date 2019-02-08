package com.csselect.parser;

import com.csselect.game.NumberOfRoundsTermination;
import com.csselect.game.Termination;
import com.csselect.game.TerminationComposite;
import com.csselect.game.TimeTermination;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 *  This class is there to enable retrieving of {@link Termination} objects out of a String
 */
public final class TerminationParser {

    private TerminationParser() {

    }

    /**
     * Parses a database saved {@link Termination} into a termination object
     * @param termination termination string to parse
     * @return created termination
     */
    public static Termination parseTermination(String termination) {
        String[] terminationArray = termination.split(",");
        if (terminationArray.length > 1) {
            TerminationComposite term = new TerminationComposite();
            for (String t : terminationArray) {
                if (t.startsWith("time")) {
                    term.add(new TimeTermination(LocalDateTime.ofEpochSecond(
                            Long.parseLong(t.replace("time:", "")), 0, ZoneOffset.UTC)));
                } else if (t.startsWith("rounds")) {
                    term.add(
                            new NumberOfRoundsTermination(Integer.parseInt(t.replace("rounds:", ""))));
                }
            }
            return term;
        } else {
            if (terminationArray[0].startsWith("time")) {
                return new TimeTermination(LocalDateTime.ofEpochSecond(
                        Long.parseLong(terminationArray[0].replace("time:", "")), 0, ZoneOffset.UTC));
            } else if (terminationArray[0].startsWith("rounds")) {
                return new NumberOfRoundsTermination(Integer.parseInt(terminationArray[0].replace("rounds:", "")));
            } else {
                return null;
            }
        }
    }
}
