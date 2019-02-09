package com.csselect.game;

/**
 * The NumberOfRoundsTermination class is a concrete termination {@link Termination} cause that
 * causes a game {@link Game} to terminate when a certain amount of rounds {@link Round} has been played
 */
public class NumberOfRoundsTermination extends Termination {

    /**
     * Type-String used in parsing from/to String
     */
    public static final String TYPE = "rounds";

    private final int number;
    /**
     * Constructor for a number of rounds termination object
     * @param number the number of rounds {@link Round} after that the game {@link Game} terminates
     */
    public NumberOfRoundsTermination(int number) {
        this.number = number;
    }

    @Override
    public boolean checkTermination() {
        return this.number <= this.game.getNumberOfRounds();
    }
    
    @Override
    public String toString() {
        return TYPE + ":" + number;
    }
}