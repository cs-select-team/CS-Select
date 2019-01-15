package com.csselect.game.gamecreation.patterns;

import com.csselect.game.Gamemode;
import com.csselect.game.Termination;

import java.util.Collection;

public class GameOptions {
    private String title;
    private String description;
    private String nameFeatureDatabase;
    private Termination termination;
    private Gamemode gamemode;
    private Collection<String> invitedEmails;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNameFeatureDatabase() {
        return nameFeatureDatabase;
    }

    public void setNameFeatureDatabase(String nameFeatureDatabase) {
        this.nameFeatureDatabase = nameFeatureDatabase;
    }

    public Termination getTermination() {
        return termination;
    }

    public void setTermination(Termination termination) {
        this.termination = termination;
    }

    public Gamemode getGamemode() {
        return gamemode;
    }

    public void setGamemode(Gamemode gamemode) {
        this.gamemode = gamemode;
    }

    public Collection<String> getInvitedEmails() {
        return invitedEmails;
    }

    public void setInvitedEmails(Collection<String> invitedEmails) {
        this.invitedEmails = invitedEmails;
    }
}
