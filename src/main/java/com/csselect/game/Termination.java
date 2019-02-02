package com.csselect.game;

/**
 * The Termination class represents a general termination cause belonging to a game. It is abstract and generalizes
 * a method to check if a termination cause is reached.
 */
public abstract class Termination {

    protected Game game;
    /**
     * Checks if the termination cause is reached and terminates the game if it is reached
     * @return true if it is reached, false else
     */
    public abstract boolean checkTermination();

    /**
     * Setter for the game of the termination
     * @param game the game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Getter for the name of the termination
     * @return returns the name of the class of the termination
     */
    public String getName() {
        return this.getClass().getSimpleName();
    }
}
