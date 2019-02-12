package com.csselect.database;

import com.csselect.game.Game;

import java.util.Collection;

/**
 * Interface abstracting a {@link com.csselect.user.User} from its database representation
 * Adapters are matched to their users unanimously by their IDs
 */
public interface UserAdapter {

    /**
     * Gets the {@link com.csselect.user.User}s ID
     * @return ID
     */
    int getID();

    /**
     * Gets the {@link com.csselect.user.User}s email
     * @return email
     */
    String getEmail();

    /**
     * Gets the {@link com.csselect.user.User}s hashed password
     * @return hashed password
     */
    String getPasswordHash();

    /**
     * Gets the {@link com.csselect.user.User}s salt used while hashing
     * @return salt used while hashing
     */
    String getPasswordSalt();

    /**
     * Gets the {@link com.csselect.user.User}s language in form of a langcode
     * @return langcode of users language
     */
    String getLanguage();

    /**
     * Sets the {@link com.csselect.user.User}s email-address
     * @param email email-address to set
     * @throws IllegalArgumentException thrown if an error occurs setting the email (e.g. email already in use)
     */
    void setEmail(String email) throws IllegalArgumentException;

    /**
     * Sets the {@link com.csselect.user.User}s password-hash and -salt
     * @param hash hashed password
     * @param salt salt used while hashing
     */
    void setPassword(String hash, String salt);

    /**
     * Sets the {@link com.csselect.user.User}s language in form of a langcode
     * @param langCode langcode of users language
     */
    void setLanguage(String langCode);


    /**
     * Gets a {@link Collection} of all active {@link Game}s the {@link com.csselect.user.User} owns or participates in
     * @return active games
     */
    Collection<Game> getActiveGames();

    /**
     * Gets a {@link Collection} of all terminated {@link Game}s the {@link com.csselect.user.User} owns or
     * participates in
     * @return terminated games
     */
    Collection<Game> getTerminatedGames();
}
