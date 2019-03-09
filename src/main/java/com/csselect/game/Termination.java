package com.csselect.game;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * The Termination class represents a general termination cause belonging to a game. It is abstract and generalizes
 * a method to check if a termination cause is reached.
 *
 * If adding a new termination type, you need to add that type to the {@link Termination#parseTermination(String)},
 * in such a way that parseTermination(newTerm.toString).equals(newTerm)
 */
public abstract class Termination {

    protected Game game;

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
                return new OrganiserTermination(); //Organiser-Termination is the fallback we use
            }
        }
    }

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
