package com.csselect.game;

import com.csselect.Injector;
import com.csselect.database.DatabaseAdapter;
import com.csselect.database.GameAdapter;
import com.csselect.mlserver.MLServer;
import com.csselect.user.Player;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The Game class represents a game, manages invited and playing players, the information the organiser specified
 * as well as the gamemode, the termination cause and the finished rounds.
 */

public class Game {
    private String title;
    private String description;
    private final int id;
    private String addressOrganiserDatabase;
    private FeatureSet featureSet;
    private final GameAdapter database;


    /**
     * Constructor for a game object.
     * @param id the unigue numerical identifier of a game
     */
    public Game(int id) {
        this.id = id;
        DatabaseAdapter adapter = Injector.getInstance().getDatabaseAdapter();
        this.database =  adapter.getGameAdapter(this.id);
    }

    /**
     * Getter for the title of the game
     * @return the title of the fame
     */
    public String getTitle() {
        if (this.title == null) {
            this.title = this.database.getTitle();
        }
        return this.title;
    }

    /**
     * Getter for the description of the game
     * @return the description of the game
     */
    public String getDescription() {
        if (this.description == null) {
            this.description = this.database.getDescription();
        }
        return this.description;
    }

    /**
     * Getter for the number of rounds {@link Round} that were finished already
     * @return the number of started rounds {@link Round}
     */
    public int getNumberOfRounds() {
        return this.database.getNumberOfRounds();
    }

    /**
     * Getter for the unique game identifier
     * @return the numerical game identifier
     */
    public int getId() {
        return this.id;
    }

    /**
     * Getter for the status of the game
     * @return if the game is finished, return true, else false
     */
    public boolean isTerminated() {
        if (this.database.isFinished()) {
            return true;
        }
        if (this.getTermination().checkTermination()) {
            this.terminateGame();
            return true;
        }
        return false;
    }

    /**
     * Getter for the email-addresses of the players {@link Player} who are currently invited to the game
     * @return the email-addresses of the players who are invited to the game but did not accept or decline yet
     */
    public Collection<String> getInvitedPlayers() {
        return this.database.getInvitedPlayers();
    }

    /**
     * Getter for the address of the database in which round data is stored
     * @return the address of the database
     */
    public String getAddressOrganiserDatabase() {
        if (this.addressOrganiserDatabase == null) {
            this.addressOrganiserDatabase = this.database.getDatabaseName();
        }
        return this.addressOrganiserDatabase;
    }

    /**
     * Getter for the {@link Player}s who accepted an invite and are allowed to start and play {@link Round}s
     * @return a collection of the players {@link Player} who can play rounds {@link Round}
     */
    public Collection<Player> getPlayingPlayers() {
        return this.database.getPlayingPlayers();
    }

    /**
     * Getter for the termination {@link Termination} cause which causes the game to end
     * @return the termination {@link Termination} cause
     */
    public Termination getTermination() {
        Termination termination = database.getTermination();
        if (termination != null) {
            termination.setGame(this);
        }
        return termination;
    }

    /**
     * Getter for the feature set {@link FeatureSet} that includes the features {@link Feature} for the game
     * @return the feature set {@link FeatureSet}
     */
    FeatureSet getFeatureSet() {
        if (this.featureSet == null) {
            this.featureSet = this.database.getFeatures();
        }
        return this.featureSet;
    }

    /**
     * Getter for the mode of the game {@link Gamemode}
     * @return the game mode belonging to the game
     */
    public Gamemode getGamemode() {
        return database.getGamemode();
    }


    /**
     * Getter for the rounds {@link Round} that are already finished
     * @return the finished rounds {@link Round}
     */
    public Collection<Round> getRounds() {
        return this.database.getRounds();
    }

    /**
     * Setter for the title of the game
     * @param title the title of the game
     */
    public void setTitle(String title) {
        this.title = title;
        database.setTitle(title);
    }

    /**
     * Setter for the description of the game
     * @param description the description of the game
     */
    public void setDescription(String description) {
        this.description = description;
        database.setDescription(description);
    }

    /**
     * Setter for the address of the database in which the round {@link Round} data is stored
     * @param addressOrganiserDatabase the address of the database
     */
    public void setAddressOrganiserDatabase(String addressOrganiserDatabase) {
        this.addressOrganiserDatabase = addressOrganiserDatabase;
        database.setDatabase(addressOrganiserDatabase);
    }

    /**
     * Setter for the termination {@link Termination} cause of the game
     * @param termination the termination {@link Termination} cause
     */
    public void setTermination(Termination termination) {
        termination.setGame(this);
        database.setTermination(termination);
    }

    /**
     * Setter for the feature set {@link FeatureSet} for the game
     * @param featureSet the feature set {@link FeatureSet}
     */
    public void setFeatureSet(FeatureSet featureSet) {
        this.featureSet = featureSet;
        database.setFeatures(featureSet);
    }

    /**
     * Setter for the game mode {@link Gamemode} of the game
     * @param gamemode the game mode {@link Gamemode}
     */
    public void setGamemode(Gamemode gamemode) {
        database.setGamemode(gamemode);
    }


    /**
     * Adds invited the email-addresses of invited players to the collection invitedPlayers
     * @param playerEmails the collection of email-addresses of invited players
     */
    public void invitePlayers(Collection<String> playerEmails) {
        if (this.isTerminated()) {
            return;
        }

        this.database.addInvitedPlayers(playerEmails);
    }

    /**
     * Gives a player {@link Player} who was invited before the right to start rounds, adds him to the collection
     * playingPlayers
     * @param playerID the ID {@link Player} of the player who accepted the invite
     * @param email the email-address of the player who accepted the invite
     */
    public void acceptInvite(int playerID, String email) {
        if (this.isTerminated()) {
            return;
        }

        Collection<String> invitedPlayers = this.database.getInvitedPlayers();

        boolean isInvited = false;
        for (String invPlayerEmail : invitedPlayers) {
            if (invPlayerEmail.equals(email)) {
                isInvited = true;
            }
        }

        if (!isInvited) {
            return;
        }

        this.database.addPlayingPlayer(playerID);
    }

    /**
     * Deletes the email-address from the collection invitedPlayers when a player declines an invite
     * @param email the email-address of the declining player
     */
    public void declineInvite(String email) {
        if (this.isTerminated()) {
            return;
        }

        if (!this.checkInvitedPlayers(email)) {
            return;
        }

        Collection<String> invitedPlayer = new ArrayList<>();
        invitedPlayer.add(email);
        this.database.removeInvitedPlayers(invitedPlayer);
    }

    /**
     * Starts a round {@link Round} for a player {@link Player} who is allowed to do that, lets the gamemode
     * {@link Gamemode} create a round {@link Round} object and starts it
     * @param player the player {@link Player} who starts the round {@link Round}
     * @return the collection of features {@link Feature} the round.start {@link Round} returns if successful, null
     * if the player is not allowed to start rounds or the game is terminated already
     */
    public Collection<Feature> startRound(Player player) {
        if (this.isTerminated() || player == null) {
            return null;
        }

        Collection<Player> players = this.database.getPlayingPlayers();
        for (Player compPlayer : players) {
            if (player.getId() == compPlayer.getId()) {
                Round round = this.getGamemode().createRound(player);
                round.setGame(this);
                return round.start();
            }
        }

        return null;


    }

    /**
     * Terminates the game, sets terminated to true and adjusts the game status in the database
     */
    public void terminateGame() {

        if (this.database.isFinished()) {
            return;
        }

        this.database.setFinished();
    }

    /**
     * Adds a finished round to the system and causes the round to be stored in the database
     * @param round the finished round
     */
    void addFinishedRound(Round round) {
        this.database.addRound(round);
        this.isTerminated();
    }

    private boolean checkInvitedPlayers(String email) {
        Collection<String> invitedPlayers = this.database.getInvitedPlayers();

        for (String invPlayerEmail : invitedPlayers) {
            if (invPlayerEmail.equals(email)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Game)) {
            return false;
        } else {
            return this.id == ((Game) o).id;
        }
    }

    @Override
    public int hashCode() {
        return id;
    }

    /**
     * Checks whether the given {@link Collection} of {@link Feature}s was already displayed for selection in this game
     * @param features features to check
     * @return true if features were already displayed, false otherwise
     */
    boolean checkDuplicateFeatureProvision(Collection<Feature> features) {
        return this.database.checkDuplicateFeatureProvision(features);
    }
}
