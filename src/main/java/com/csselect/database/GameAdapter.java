package com.csselect.database;

import com.csselect.game.Feature;
import com.csselect.game.FeatureSet;
import com.csselect.game.Gamemode;
import com.csselect.game.Round;
import com.csselect.game.Termination;
import com.csselect.user.Organiser;
import com.csselect.user.Player;

import java.util.Collection;

/**
 * Interface abstracting a {@link com.csselect.game.Game} from its database representation
 * Adapters are matched to their games unanimously by their IDs
 */
public interface GameAdapter {

    /**
     * Gets the {@link com.csselect.game.Game}s ID
     * @return ID
     */
    int getID();

    /**
     * Gets the {@link com.csselect.game.Game}s title
     * @return title
     */
    String getTitle();

    /**
     * Gets the {@link com.csselect.game.Game}s description
     * @return description
     */
    String getDescription();

    /**
     * Gets the {@link com.csselect.game.Game}s database-name
     * @return database-name
     */
    String getDatabaseName();

    /**
     * Gets the {@link com.csselect.game.Game}s features in form of a {@link FeatureSet}
     * @return featureSet
     */
    FeatureSet getFeatures();

    /**
     * Gets the number of rounds which is to be played
     * @return number of rounds
     */
    int getNumberOfRounds();

    /**
     * Gets a {@link Collection} of the email addresses of all invited {@link Player}s
     * @return email addresses
     */
    Collection<String> getInvitedPlayers();

    /**
     * Gets a {@link Collection} of all playing {@link Player}s
     * @return players
     */
    Collection<Player> getPlayingPlayers();

    /**
     * Gets a {@link Collection} of all played {@link Round}s
     * @return rounds
     */
    Collection<Round> getRounds();

    /**
     * Gets the {@link Gamemode}
     * @return gamemode
     */
    Gamemode getGamemode();

    /**
     * Gets the {@link Termination} the {@link com.csselect.game.Game} the game uses
     * @return termination
     */
    Termination getTermination();

    /**
     * Gets the {@link Organiser} the {@link com.csselect.game.Game} belongs to
     * @return organiser
     */
    Organiser getOrganiser();

    /**
     * Sets the {@link Termination}
     * @param termination termination to be set
     */
    void setTermination(Termination termination);

    /**
     * Sets the {@link FeatureSet} to be used for {@link com.csselect.game.Feature}-Selection
     * @param featureSet featureSet to be used
     */
    void setFeatures(FeatureSet featureSet);

    /**
     * Marks the {@link com.csselect.game.Game} as finished
     */
    void setFinished();

    /**
     * Sets the {@link com.csselect.game.Game}s title
     * @param title title to be set
     */
    void setTitle(String title);

    /**
     * Sets the {@link com.csselect.game.Game}s description
     * @param description description to be set
     */
    void setDescription(String description);

    /**
     * Sets the {@link com.csselect.game.Game}s database-name
     * @param name name of the database
     */
    void setDatabase(String name);

    /**
     * Sets the {@link com.csselect.game.Game}s gamemode
     * @param gamemode gamemode to be set
     */
    void setGamemode(Gamemode gamemode);

    /**
     * Sets the organiser who owns the game
     * @param organiser new owner of the game
     */
    void setOrganiser(Organiser organiser);

    /**
     * Checks if the game is finished
     * @return true if game is finished, false otherwise
     */
    boolean isFinished();

    /**
     * Adds a {@link Round} to the {@link com.csselect.game.Game}
     * @param round round to be added
     */
    void addRound(Round round);

    /**
     * Adds a {@link Collection} of email-addresses to the invited players
     * @param emails emails of the invited players
     */
    void addInvitedPlayers(Collection<String> emails);

    /**
     * Adds a {@link Collection} of email-addresses to the playing players
     * @param emails emails of the playing players
     */
    void addPlayingPlayers(Collection<String> emails);

    /**
     * Adds an invited {@link Player} to the playing players
     * @param id id of the player to be added
     */
    void addPlayingPlayer(int id);

    /**
     * Removes a {@link Collection} of email-addresses from the invited players
     * @param emails emails of the players to be removed
     */
    void removeInvitedPlayers(Collection<String> emails);

    /**
     * Checks whether the given {@link Collection} of {@link Feature}s was already displayed for selection
     * @param features features to check
     * @return true if features were already displayed, false otherwise
     */
    boolean checkDuplicateFeatureProvision(Collection<Feature> features);
}
