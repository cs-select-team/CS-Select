package com.csselect.game;

import java.time.LocalDate;

/**
 * The TimeTermination class is a concrete termination {@link Termination} cause that
 * causes a game {@link Game} to terminate when a certain time has been reached
 */
public class TimeTermination extends Termination {

    private final LocalDate date;

    /**
     * Constructor of a time termination object
     * @param date the time at which the game is to be terminated
     */
    public TimeTermination(LocalDate date){
        this.date = date;
    }

    @Override
    public boolean checkTermination() {
        return false;
    }
}
