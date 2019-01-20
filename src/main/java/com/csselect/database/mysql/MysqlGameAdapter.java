package com.csselect.database.mysql;

import com.csselect.database.GameAdapter;
import com.csselect.game.FeatureSet;
import com.csselect.game.Gamemode;
import com.csselect.game.Round;
import com.csselect.game.Termination;
import com.csselect.user.Organiser;
import com.csselect.user.Player;

import java.util.Collection;

/**
 * Mysql-Implementation of the {@link GameAdapter} Interface
 */
public class MysqlGameAdapter implements GameAdapter {

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getDatabaseName() {
        return null;
    }

    @Override
    public FeatureSet getFeatures() {
        return null;
    }

    @Override
    public int getNumberOfRounds() {
        return 0;
    }

    @Override
    public Collection<String> getInvitedPlayers() {
        return null;
    }

    @Override
    public Collection<Player> getPlayingPlayers() {
        return null;
    }

    @Override
    public Collection<Round> getRounds() {
        return null;
    }

    @Override
    public Gamemode getGamemode() {
        return null;
    }

    @Override
    public Termination getTermination() {
        return null;
    }

    @Override
    public Organiser getOrganiser() {
        return null;
    }

    @Override
    public void setTermination(Termination termination) {

    }

    @Override
    public void setFeatures(FeatureSet featureSet) {

    }

    @Override
    public void setFinished() {

    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setDescription(String description) {

    }

    @Override
    public void setDatabase(String name) {

    }

    @Override
    public void setGamemode(Gamemode gamemode) {

    }

    @Override
    public void setOrganiser(Organiser organiser) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void addRound(Round round) {

    }

    @Override
    public void addInvitedPlayers(Collection<String> emails) {

    }

    @Override
    public void addPlayingPlayers(Collection<String> emails) {

    }

    @Override
    public void addPlayingPlayer(int id) {

    }

    @Override
    public void removeInvitedPlayers(Collection<String> emails) {

    }
}
