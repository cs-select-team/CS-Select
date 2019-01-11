package com.csselect.database;

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
     * Gets the {@link com.csselect.user.User}s language in form of a langcode
     * @return langcode of users language
     */
    String getLanguage();

    /**
     * Sets the {@link com.csselect.user.User}s email-address
     * @param email email-address to set
     */
    void setEmail(String email);

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
}
