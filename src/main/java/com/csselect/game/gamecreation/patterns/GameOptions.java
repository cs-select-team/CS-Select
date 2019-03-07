package com.csselect.game.gamecreation.patterns;

import com.csselect.game.Game;
import com.csselect.inject.Injector;
import com.csselect.game.Gamemode;
import com.csselect.game.Termination;

import java.util.Collection;
import java.util.HashSet;

/**
 *  This class represents the fields which an organiser {@link com.csselect.user.Organiser} can modify whilst in
 *  gamecreation interface.
 */
public class GameOptions implements Cloneable {
    private String title;
    private String description;
    private String resultDatabaseName;
    private String dataset;
    private Termination termination;
    private Gamemode gamemode;
    private Collection<String> invitedEmails;

    /**
     * Public constructor for a game options object
     */
    public GameOptions() {
        this.invitedEmails = new HashSet<>();
    }

    /**
     * Creates a new {@link GameOptions} object from an existing {@link Game}
     * @param game game to create GameOptions from
     */
    public GameOptions(Game game) {
        this();
        this.title = game.getTitle();
        this.description = game.getDescription();
        this.resultDatabaseName = game.getNameOrganiserDatabase();
        this.dataset = game.getFeatureSet().getIdentifier();
        this.termination = game.getTermination();
        this.gamemode = game.getGamemode();
        this.invitedEmails.addAll(game.getInvitedPlayers());
        game.getPlayingPlayers().forEach(p -> this.invitedEmails.add(p.getEmail()));
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
    public String getResultDatabaseName() {
        return resultDatabaseName;
    }

    /**
     * Get dataset String
     * @return game description
     */
    public String getDataset() {
        return dataset;
    }

    /**
     * Set dataset String
     * @param dataset dataset to use
     */
    public void setDataset(String dataset) {
        this.dataset = dataset;
    }

    /**
     * Set String representing address of the database where the results shall be saved in
     * @param nameFeatureDatabase Database address
     */
    public void setResultDatabaseName(String nameFeatureDatabase) {
        this.resultDatabaseName = nameFeatureDatabase;
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
     * @param termination {@link Termination} object
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
     * @param gamemode  {@link Gamemode} object
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

    /**
     * Checks whether all options that are necessary for a game have been set correctly
     * @return true if all options are set, false otherwise
     */
    public boolean isComplete() {
        return title != null && description != null && resultDatabaseName != null && dataset != null
                && termination != null && gamemode != null
                && Injector.getInstance().getMLServer().isValidDataset(dataset);
    }

    /**
     * Empties the complete HashSet which holds all invited Emails. Do this after creating a game with
     * {@link com.csselect.game.gamecreation.GameCreator}, otherwise
     * it could be that players get invited by accident next time.
     */
    public void resetEmails() {
        this.invitedEmails = new HashSet<>();
    }

    @Override
    public Object clone() {
        try {
            GameOptions copyObject = (GameOptions) super.clone();
            copyObject.setTitle(this.title);
            copyObject.setDescription(this.description);
            copyObject.setDataset(this.dataset);
            copyObject.setGamemode(this.gamemode);
            copyObject.addInvitedEmails(this.invitedEmails);
            copyObject.setResultDatabaseName(this.getResultDatabaseName());
            copyObject.setTermination(this.termination);
            return copyObject;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
