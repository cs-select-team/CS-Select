package com.csselect.game;

/**
 * This class represents a termination cause in which case the
 * termination is only caused by a command of the organiser, it is
 * a null object without functionality.
 */
public class OrganiserTermination extends Termination {

    private static final String TYPE = "organiser";
    @Override
    public boolean checkTermination() {
        return false;
    }

    @Override
    public String toString() {
        return TYPE;
    }
}
