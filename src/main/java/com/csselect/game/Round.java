package com.csselect.game;

import com.csselect.user.Player;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The Round class represents a round that is played by a player {@link Player}, belongs to a game {@link Game} and
 * provides features {@link Feature} from which the player {@link Player} can choose as well as storing more
 * round specific information.
 */
public abstract class Round {

    protected LocalDateTime time;
    protected double quality;
    protected int points;
    protected final Player player;
    protected Game game;

    protected Collection<Feature> uselessFeatures;
    protected Collection<Feature> chosenFeatures;
    protected Collection<Feature> shownFeatures;

    protected List<Feature> features;

    /**
     * Constructor to generalize constructing for all subclasses
     * @param player the player {@link Player} who plays the round
     */
    public Round(Player player) {
        this.player = player;

        this.time = LocalDateTime.now();

        this.uselessFeatures = new ArrayList<>();
        this.chosenFeatures = new ArrayList<>();
        this.shownFeatures = new ArrayList<>();
    }

    /**
     * Setter for the game to which a round belongs
     * @param game the game of the round
     */
    protected void setGame(Game game) {
        this.game = game;
        this.features = new ArrayList<>(game.getFeatureSet().getFeatures());
    }

    /**
     * Getter for the features {@link Feature} shown to the player {@link Player}
     * @return the shown features {@link Feature}
     */
    public Collection<Feature> getShownFeatures() {
        return this.shownFeatures;
    }

    /**
     * Getter for the features {@link Feature} selected by the player {@link Player}
     * @return the selected features {@link Feature}
     */
    public Collection<Feature> getChosenFeatures() {
        return this.chosenFeatures;
    }

    /**
     * Getter for the features {@link Feature} marked by the player {@link Player} to be useless
     * @return the useless features {@link Feature}
     */
    public Collection<Feature> getUselessFeatures() {
        return this.uselessFeatures;
    }

    /**
     * Getter for the time the round was started
     * @return the starting time
     */
    public LocalDateTime getTime() {
        return this.time;
    }

    /**
     * Getter for the quality of the round
     * @return the quality of the round, double between 0 and 1
     */
    public double getQuality() {
        return this.quality;
    }

    /**
     * Getter for the points the player {@link Player} has achieved in this round
     * @return the points achieved in this round
     */
    public int getPoints() {
        return this.points;
    }


    /**
     * Getter for the player {@link Player} who plays the round
     * @return the playing player {@link Player}
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Starts the round, uses provideFeatures to select the features {@link Feature} that are to be shown to the
     * player {@link Player} and returns these.
     * @return the features {@link Feature} that are to be shown to the player {@link Player}
     */
    public List<Feature> start() {
        List<Feature> features = this.provideFeatures();
        this.shownFeatures = features;
        return features;
    }

    /**
     * Ends the round without a selection of features {@link Feature} from the player {@link Player}
     * @param uselessFeatures the IDs of the features {@link Feature} marked by the player {@link Player} to be useless
     */
    public void skip(int[] uselessFeatures) {
        this.addUselessFeatures(uselessFeatures);
        this.player.getStats().skipRound();
        this.game.addFinishedRound(this);
    }

    /**
     * Ends the round, calculates the score and the points of the round and adds it to the game {@link Game}
     * @param selectedFeatures the IDs of the features {@link Feature} selected by the player {@link Player}
     * @param uselessFeatures the IDs of the features {@link Feature} marked by the player {@link Player} to be useless
     * @return returns the points earned in this round
     */
    public int selectFeatures(int[] selectedFeatures, int[] uselessFeatures) {
        this.addUselessFeatures(uselessFeatures);

        for (int id : selectedFeatures) {
            for (Feature feature : this.features) {
                if (feature.getID() == id) {
                    this.chosenFeatures.add(feature);
                }
            }
        }

        String identifier = this.game.getFeatureSet().getIdentifier();
        try {
            this.quality = this.game.getMlserver().getScore(identifier, this.chosenFeatures);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        this.points = this.player.getStats().finishRound(this.quality);

        this.game.addFinishedRound(this);

        return this.points;
    }

    /**
     * Provides the features {@link Feature} that are to be shown to the player {@link Player}
     * @return collection of features {@link Feature} that are to be shown
     */
    public abstract List<Feature> provideFeatures();

    private void addUselessFeatures(int[] uselessFeatures) {
        for (int id : uselessFeatures) {
            for (Feature feature : this.features) {
                if (feature.getID() == id) {
                    this.uselessFeatures.add(feature);
                }
            }
        }
    }

}
