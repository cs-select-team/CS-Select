package com.csselect.API;

/**
 * The facade for all calls to the system that are not specific to organisers or players
 */
@SuppressWarnings("WeakerAccess")

public abstract class APIFacadeUser {


    /** logs in a user for this api. This is the first method that has to be called before any other methods start
     *  making sense.
     *
     * @param email Email of the user to login
     * @param password Password of the user to login
     *
     * @return true if login successfull, false if not
     */
    public abstract boolean login(String email, String password);

    /** logs out the current user from this {@link APIFacadeUser}
     * after calling logout this object will behave as if no user has been logged in
     *
     */
    public abstract void logout();

    /** Changes the email address of the current user.
     *
     * @param email new email address for this user
     */
    public abstract void changeEmail(String email);

    /** Changes the password for the current user
     *
     * @param password new password for the current user
     */
    public abstract void changePassword(String password);

    /** Changes the language for the current user. This is only relevant for the front end.
     *
     * @param languageCode language code for the new language( i.e. de-de, en-us ...)
     */
    public abstract void setLanguage(String languageCode);

    /**
     * @return the language that the user selected
     */
    public abstract String getLanguage();

}
