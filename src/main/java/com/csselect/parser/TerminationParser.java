package com.csselect.parser;

import com.csselect.game.NumberOfRoundsTermination;
import com.csselect.game.Termination;
import com.csselect.game.TimeTermination;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *  This class is there to enable retrieving of {@link Termination} objects out of a String
 */
public final class TerminationParser {

    private TerminationParser() {

    }

    /**
     * Creates a {@link Termination} object out of a String
     * @param args String array of parameters for a termination
     * @return Termination object
     */
    public static Termination getTermination(String[] args) {
        assert args.length == 2;
        String type = args[0];
        String value = args[1];
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME; // Syntax = '2011-12-03T10:15:30'
        Termination termination = null;
        if (type.equals("time")) {
            termination = new TimeTermination(LocalDateTime.parse(value, formatter));
        }
        if (type.equals("rounds")) {
            termination = new NumberOfRoundsTermination(Integer.parseInt(value));
        }
        return termination;
    }
}
