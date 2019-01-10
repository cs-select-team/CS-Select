package com.csselect.database;

/**
 * Abstraction from the underlying database representation of a user
 * Loads and stores user related values
 */
public interface UserAdapter {

    /**
     * Gets the users ID
     * @return the users ID
     */
    int getID();

    /**
     * Gets the users email-address
     * @return the users email-address
     */
    String getEmail();

    /**
     * Gets the users hashed password
     * @return users hashed password
     */
    String getPasswordHash();

    /**
     * Gets the users language specified by the languages langcode
     * @return langcode of the users language
     */
    String getLanguage();

    /**
     * Sets the users email
     * @param email email-address to be set
     */
    void setEmail(String email);

    /**
     * Sets the users password hash and salt
     * @param hash hash of users password
     * @param salt salt used while encrypting the password
     */
    void setPassword(String hash, String salt);

    /**
     * Sets the users language
     * @param langCode langcode of users language
     */
    void setLanguage(String langCode);

}
