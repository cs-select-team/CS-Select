package com.csselect.game;

import com.csselect.mlserver.MLServer;
import com.csselect.user.Player;

import java.util.Collection;
import java.util.List;

public class Game {
    private boolean terminated;

    public Game(int id) {

    }

    public String getTitle() {
        return "";
    }

    public String getDescription() {
        return "";
    }

    public int getNumberOfRounds() {
        return 0;
    }

    public int getId() {
        return 0;
    }

    public boolean isTerminated() {
        return false;
    }

    public Collection<String> getInvitedPlayers() {
        return null;
    }

    public String getAddressOrganiserDatabase() {
        return "";
    }

    public Collection<Player> getPlayingPlayers() {
        return null;
    }

    public Gamemode getGamemode() {
        return null;
    }

    public MLServer getMlserver() {
        return null;
    }

    public List<Round> getRounds() {
        return null;
    }

    public void setTitle(String title) {

    }

    public void setDescription(String description) {

    }

    public void setAddressOrganiserDatabase(String addressOrganiserDatabase) {

    }

    public void setTermination(Termination termination) {

    }

    public void setFeatureSet(FeatureSet featureSet) {

    }

    public void setGamemode(Gamemode gamemode) {

    }

    public void setMlserver(MLServer mlserver) {

    }

    public boolean invitePlayers(Collection<String> playerEmails) {
        return false;
    }

    public boolean acceptInvite(Player player, String email) {
        return false;
    }

    public boolean declineInvite(String email) {
        return false;
    }

    public Collection<Feature> startRound(int playerID) {
        return null;
    }

    public boolean terminateGame() {
        return false;
    }

    public void addFinishedRound(Round round) {

    }


    // Doppelt mit terminateGame
    public void setTerminated(boolean value) {
        this.terminated = value;
    }


}
