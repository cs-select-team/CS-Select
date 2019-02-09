package com.csselect.game;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * The TimeTermination class is a concrete termination {@link Termination} cause that
 * causes a game {@link Game} to terminate when a certain time has been reached
 */
public class TimeTermination extends Termination {

    /**
     * Type-String used in parsing from/to String
     */
    public static final String TYPE = "time";

    private final LocalDateTime date;

    /**
     * Constructor of a time termination object
     * @param date the time at which the game is to be terminated
     */
    public TimeTermination(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean checkTermination() {
        if (this.game == null) {
            return false;
        }
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.isAfter(this.date);
    }
    
    @Override
    public String toString() {
        return TYPE + ":" + date.toEpochSecond(ZoneOffset.UTC);
    }
}