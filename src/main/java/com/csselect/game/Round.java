package com.csselect.game;

import com.csselect.user.Player;

import java.time.LocalDate;
import java.util.Collection;

public abstract class Round {

  //  public Round(Player player, int numberOfRound) {

  //  }

    public Collection<Feature> getShownFeatures() {
        return null;
    }

    public Collection<Feature> getChosenFeatures() {
        return null;
    }

    public Collection<Feature> getUselessFeatures() {
        return null;
    }

    public LocalDate getTime() {
        return null;
    }

    public double getQuality() {
        return 0.0;
    }

    public int getPoints() {
        return 0;
    }

    public int getNumberOfRound() {
        return 0;
    }

    public Player getPlayer() {
        return null;
    }

    public Collection<Feature> start() {
        return null;
    }

    public void skip(Collection<Feature> uselessFeatures) {

    }

    public void selectFeatures(Collection<Feature> selectedFeatures, Collection<Feature> uselessFeatures) {

    }

    public abstract Collection<Feature> provideFeatures();
}
