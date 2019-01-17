package com.csselect.game;

/**
 * The Termination class represents a general termination cause belonging to a game. It is abstract and generalizes
 * a method to check if a termination cause is reached.
 */
public abstract class Termination {

    /**
     * Checks if the termination cause is reached and terminates the game if it is reached
     * @return true if it is reached, false else
     */
    public abstract boolean checkTermination();
}
