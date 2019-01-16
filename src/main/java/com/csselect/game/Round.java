package com.csselect.game;

import com.csselect.user.Player;

import java.time.LocalDate;
import java.util.Collection;

/**
 * The Round class represents a round that is played by a player {@link Player}, belongs to a game {@link Game} and
 * provides features {@link Feature} from which the player {@link Player} can choose as well as storing more
 * round specific information.
 */
public abstract class Round {

  //  /**
  //   * Constructor to generalize constructing for all subclasses
    //    * @param player the player {@link Player} who plays the round
     //  * @param numberOfRound the number of the round
  //   */
  //  public Round(Player player, int numberOfRound) {

  //  }

    /**
     * Getter for the features {@link Feature} shown to the player {@link Player}
     * @return the shown features {@link Feature}
     */
    public Collection<Feature> getShownFeatures() {
        return null;
    }

    /**
     * Getter for the features {@link Feature} selected by the player {@link Player}
     * @return the selected features {@link Feature}
     */
    public Collection<Feature> getChosenFeatures() {
        return null;
    }

    /**
     * Getter for the features {@link Feature} marked by the player {@link Player} to be useless
     * @return the useless features {@link Feature}
     */
    public Collection<Feature> getUselessFeatures() {
        return null;
    }

    /**
     * Getter for the time the round was started
     * @return the starting time
     */
    public LocalDate getTime() {
        return null;
    }

    /**
     * Getter for the quality of the round
     * @return the quality of the round, double between 0 and 1
     */
    public double getQuality() {
        return 0.0;
    }

    /**
     * Getter for the points the player {@link Player} has achieved in this round
     * @return the points achieved in this round
     */
    public int getPoints() {
        return 0;
    }

    /**
     * Getter for the number the round
     * @return the number of the round
     */
    public int getNumberOfRound() {
        return 0;
    }

    /**
     * Getter for the player {@link Player} who plays the round
     * @return the playing player {@link Player}
     */
    public Player getPlayer() {
        return null;
    }

    /**
     * Starts the round, uses provideFeatures to select the features {@link Feature} that are to be shown to the
     * player {@link Player} and returns these.
     * @return the features {@link Feature} that are to be shown to the player {@link Player}
     */
    public Collection<Feature> start() {
        return null;
    }

    /**
     * Ends the round without a selection of features {@link Feature} from the player {@link Player}
     * @param uselessFeatures the features {@link Feature} marked by the player {@link Player} to be useless
     */
    public void skip(Collection<Feature> uselessFeatures) {

    }

    /**
     * Ends the round, calculates the score and the points of the round and adds it to the game {@link Game}
     * @param selectedFeatures the features {@link Feature} selected by the player {@link Player}
     * @param uselessFeatures the features {@link Feature} marked by the player {@link Player} to be useless
     */
    public void selectFeatures(Collection<Feature> selectedFeatures, Collection<Feature> uselessFeatures) {

    }

    /**
     * Provides the features {@link Feature} that are to be shown to the player {@link Player}
     * @return collection of features {@link Feature} that are to be shown
     */
    public abstract Collection<Feature> provideFeatures();
}
