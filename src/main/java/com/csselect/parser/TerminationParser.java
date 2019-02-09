package com.csselect.parser;

import com.csselect.game.NumberOfRoundsTermination;
import com.csselect.game.Termination;
import com.csselect.game.TerminationComposite;
import com.csselect.game.TimeTermination;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

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
                if (t.startsWith(TimeTermination.TYPE)) {
                    term.add(new TimeTermination(LocalDateTime.ofEpochSecond(
                            Long.parseLong(t.replace(TimeTermination.TYPE + ":", "")), 0, ZoneOffset.UTC)));
                } else if (t.startsWith(NumberOfRoundsTermination.TYPE)) {
                    term.add(
                            new NumberOfRoundsTermination(
                                    Integer.parseInt(t.replace(NumberOfRoundsTermination.TYPE + ":", ""))));
                }
            }
            return term;
        } else {
            if (terminationArray[0].startsWith(TimeTermination.TYPE)) {
                return new TimeTermination(LocalDateTime.ofEpochSecond(Long.parseLong(
                        terminationArray[0].replace(TimeTermination.TYPE + ":", "")), 0, ZoneOffset.UTC));
            } else if (terminationArray[0].startsWith(NumberOfRoundsTermination.TYPE)) {
                return new NumberOfRoundsTermination(Integer.parseInt(
                        terminationArray[0].replace(NumberOfRoundsTermination.TYPE + ":", "")));
            } else {
                return null;
            }
        }
    }
}
