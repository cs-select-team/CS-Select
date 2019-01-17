package com.csselect.game.gamecreation.patterns;

import com.csselect.game.Gamemode;
import com.csselect.game.Termination;

import java.util.Collection;
import java.util.HashSet;

/**
 *
 */
public class GameOptions implements  Cloneable{
    private String title;
    private String description;
    private String nameFeatureDatabase;
    private Termination termination;
    private Gamemode gamemode;
    private Collection<String> invitedEmails;

    public GameOptions() {
        this.invitedEmails = new HashSet<>();
    }
    /**
     * Get title String
     * @return game title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title String
     * @param title game title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get description String
     * @return game description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description String
     * @param description game description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get String representing address of the database where the results shall be saved in
     * @return database address
     */
    public String getNameFeatureDatabase() {
        return nameFeatureDatabase;
    }

    /**
     * Set String representing address of the database where the results shall be saved in
     */
    public void setNameFeatureDatabase(String nameFeatureDatabase) {
        this.nameFeatureDatabase = nameFeatureDatabase;
    }

    /**
     * Get termination conditions
     * @return termination object
     */
    public Termination getTermination() {
        return termination;
    }

    /**
     * Set termination conditions
     */
    public void setTermination(Termination termination) {
        this.termination = termination;
    }

    /**
     * Get gamemode of game
     * @return gamemode object
     */
    public Gamemode getGamemode() {
        return gamemode;
    }

    /**
     * Get gamemode of game
     */
    public void setGamemode(Gamemode gamemode) {
        this.gamemode = gamemode;
    }

    /**
     * Get Collection of String representing emails which are invited to the game
     * @return emails
     */
    public Collection<String> getInvitedEmails() {
        return invitedEmails;
    }

    /**
     * Invite new emails to the game
     * @param invitedEmails emails to invite
     */
    public void addInvitedEmails(Collection<String> invitedEmails) {
        this.invitedEmails.addAll(invitedEmails);
    }

    /**
     * Remove emails from invited emails
     * @param invitedEmails emails to remove
     */
    public void removeInvitedEmails(Collection<String> invitedEmails) {
        this.invitedEmails.removeAll(invitedEmails);
    }

    @Override
    public Object clone() {
        try {
            GameOptions copyObject = (GameOptions) super.clone();
            copyObject.setTitle(this.title);
            copyObject.setDescription(this.description);
            copyObject.setGamemode(this.gamemode);
            copyObject.addInvitedEmails(this.invitedEmails);
            copyObject.setNameFeatureDatabase(this.getNameFeatureDatabase());
            copyObject.setTermination(this.termination);
            return copyObject;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
