package com.csselect.database.mock;

import com.csselect.inject.Injector;
import com.csselect.database.GameAdapter;
import com.csselect.game.Feature;
import com.csselect.game.FeatureSet;
import com.csselect.game.Gamemode;
import com.csselect.game.Round;
import com.csselect.game.Termination;
import com.csselect.user.Organiser;
import com.csselect.user.Player;

import java.util.Collection;
import java.util.HashSet;

/**
 * Mock-Implementation of the {@link GameAdapter} Interface
 */
public class MockGameAdapter implements GameAdapter {

    private final int id;
    private String title;
    private String description;
    private String addressOrganiserDatabase;
    private Termination termination;
    private FeatureSet featureSet;
    private Gamemode gamemode;

    private Organiser organiser;
    private boolean finished;

    private final Collection<String> invitedPlayers;
    private final Collection<Player> playingPlayers;
    private final Collection<Round> rounds;

    /**
     * Creates a new {@link MockGameAdapter} with the given id
     * @param id id of the adapter
     */
    MockGameAdapter(int id) {
        this.id = id;
        invitedPlayers = new HashSet<>();
        playingPlayers = new HashSet<>();
        rounds = new HashSet<>();
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getDatabaseName() {
        return addressOrganiserDatabase;
    }

    @Override
    public FeatureSet getFeatures() {
        return featureSet;
    }

    @Override
    public int getNumberOfRounds() {
        return getRounds().size();
    }

    @Override
    public Collection<String> getInvitedPlayers() {
        return invitedPlayers;
    }

    @Override
    public Collection<Player> getPlayingPlayers() {
        return playingPlayers;
    }

    @Override
    public Collection<Round> getRounds() {
        return rounds;
    }

    @Override
    public Gamemode getGamemode() {
        return gamemode;
    }

    @Override
    public Termination getTermination() {
        return termination;
    }

    @Override
    public Organiser getOrganiser() {
        return organiser;
    }

    @Override
    public void setTermination(Termination termination) {
        this.termination = termination;
    }

    @Override
    public void setFeatures(FeatureSet featureSet) {
        this.featureSet = featureSet;
    }

    @Override
    public void setFinished() {
        this.finished = true;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setDatabase(String name) {
        this.addressOrganiserDatabase = name;
    }

    @Override
    public void setGamemode(Gamemode gamemode) {
        this.gamemode = gamemode;
    }

    @Override
    public void setOrganiser(Organiser organiser) {
        this.organiser = organiser;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void addRound(Round round) {
        rounds.add(round);
    }

    @Override
    public void addInvitedPlayers(Collection<String> emails) {
        invitedPlayers.addAll(emails);
    }

    @Override
    public void addPlayingPlayers(Collection<String> emails) {
        emails.forEach(e -> playingPlayers.add(Injector.getInstance().getDatabaseAdapter().getPlayer(e)));
        removeInvitedPlayers(emails);

    }

    @Override
    public void addPlayingPlayer(int id) {
        playingPlayers.add(Injector.getInstance().getDatabaseAdapter().getPlayer(id));
    }

    @Override
    public void removeInvitedPlayers(Collection<String> emails) {
        invitedPlayers.removeAll(emails);
    }

    @Override
    public boolean checkDuplicateFeatureProvision(Collection<Feature> features) {
        return rounds.stream().anyMatch(r -> r.getShownFeatures().equals(features));
    }
}
