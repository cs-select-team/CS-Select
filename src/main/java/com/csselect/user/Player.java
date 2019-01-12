package com.csselect.user;

import com.csselect.database.PlayerAdapter;
import com.csselect.game.Feature;
import com.csselect.game.Game;
import com.csselect.game.Round;
import com.csselect.gamification.Gamification;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class Player extends User {
    private PlayerAdapter databaseAdapter;
    private Collection<Game> games;
    private Gamification gamification;
    private Round activeRound;

    Player(PlayerAdapter databaseAdapter) {
        this.databaseAdapter = databaseAdapter;
        this.games = new HashSet<>();
        this.activeRound = null;
    }

    public boolean register(String[] args) {
        assert args.length == 3;
        return false;
    }

    public void acceptInvite(int gameId) {

    }

    public void declineInvite(int gameId) {

    }

    public Collection<Feature> startRound(int gameId) {
        return null;
    }

    public void setActiveRound(Round round) {
        this.activeRound = round;
    }

    public Round getActiveRound() {
        return this.activeRound;
    }

    public void selectFeatures(Collection<Feature> selectedFeatures, Collection<Feature> uselessFeatures) {

    }

    public void skipRound(Collection<Feature> features) {

    }

    public Gamification getStats() {
        return this.gamification;
    }

    public List<Player> getLeaderboard() {
        return null;
    }
}
