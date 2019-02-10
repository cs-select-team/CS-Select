package com.csselect.game;

/**
 * This class represents a termination cause in which case the
 * termination is only caused by a command of the organiser, it is
 * a null object without functionality.
 */
public class OrganiserTermination extends Termination {

    @Override
    public boolean checkTermination() {
        return false;
    }
}
