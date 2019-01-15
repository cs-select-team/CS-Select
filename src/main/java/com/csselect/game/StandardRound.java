package com.csselect.game;

import com.csselect.user.Player;

import java.util.Collection;

public class StandardRound extends Round {

    public StandardRound(Player player, int numberOfRound, int numberOfSelections, int featuresPerSelection, int minSelect, int maxSelect) {

    }

    public int getNumberOfSelections() {
        return 0;
    }

    public int getFeaturesPerSelection() {
        return 0;
    }

    public int getMinSelect() {
        return 0;
    }

    public int getMaxSelect() {
        return 0;
    }

    public Collection<Feature> provideFeatures() {
        return null;
    }
}
