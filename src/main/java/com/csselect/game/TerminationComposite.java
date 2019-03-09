package com.csselect.game;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

/**
 * The TerminationComposite class is a concrete termination {@link Termination} cause that combines different
 * termination {@link Termination} causes into one object by using the composite design pattern. It contains termination
 * {@link Termination} causes and checks each of them if a termination {@link Termination} cause is reached.
 */
public class TerminationComposite extends Termination {

    private final Set<Termination> terminations;

    /**
     * Constructor of a termination composite object.
     */
    public TerminationComposite() {
        this.terminations = new HashSet<>();
    }

    /**
     * Checks in each included termination {@link Termination} cause if a termination is reached
     * @return true if at least one termination cause {@link Termination} is reached, false else
     */
    public boolean checkTermination() {
        for (Termination termination : this.terminations) {
            if (termination.checkTermination()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a termination {@link Termination} cause to the set of terminations {@link Termination}
     * @param termination the termination {@link Termination} cause to be added
     */
    public void add(Termination termination) {
        if (termination == null) {
            return;
        }

        if (this.game != null) {
            termination.setGame(this.game);
        }

        this.terminations.add(termination);
    }

    /**
     * Getter for the terminations included in the TerminationComposite
     * @return the terminations
     */
    public Collection<Termination> getTerminations() {
        return this.terminations;
    }


    @Override
    public void setGame(Game game) {
        this.game = game;
        for (Termination termination : this.terminations) {
            termination.setGame(game);
        }
    }
    
    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",");
        terminations.forEach(t -> joiner.add(t.toString()));
        return joiner.toString();
    }
}