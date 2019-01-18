package com.csselect.game;

import java.time.LocalDateTime;

/**
 * The TimeTermination class is a concrete termination {@link Termination} cause that
 * causes a game {@link Game} to terminate when a certain time has been reached
 */
public class TimeTermination extends Termination {

    private final LocalDateTime date;

    /**
     * Constructor of a time termination object
     * @param date the time at which the game is to be terminated
     */
    public TimeTermination(LocalDateTime date){
        this.date = date;
    }

    @Override
    public boolean checkTermination() {
        if (this.game == null) {
            return false;
        }
        LocalDateTime currentTime = LocalDateTime.now();
        boolean finished = currentTime.isAfter(this.date);
        if(finished) {
            this.game.terminateGame();
        }
        return finished;
    }
}
