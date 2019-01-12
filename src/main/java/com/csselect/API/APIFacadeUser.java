package com.csselect.API;

import com.csselect.user.User;

@SuppressWarnings("WeakerAccess")
abstract public class APIFacadeUser {

    /** Registers a new user.
     * This creates a new user in the database.
     * Password and email are later required to log into the system with {@link APIFacadeUser#login(String, String)}
     * @param args String array of arguments for registration
     */
    abstract public void register(String[] args);

    /** logs in a user for this api. This is the first method that has to be called before any other methods start
     *  making sense.
     *
     * @param email Email of the user to login
     * @param password Password of the user to login
     */
    public void login(String email, String password) {

    }

    /** logs out the current user from this {@link APIFacadeUser}
     * after calling logout this object will behave as if no user has been logged in
     *
     */
    public void logout() {

    }

    /** Changes the email address of the current user.
     *
     * @param email new email address for this user
     */
    public void changeEmail(String email) {

    }

    /** Changes the password for the current user
     *
     * @param password new password for the current user
     */
    public void changePassword(String password) {

    }

    /** Changes the language for the current user. This is only relevant for the front end.
     *
     * @param languageCode language code for the new language( i.e. de-de, en-us ...)
     */
    public void setLanguage(String languageCode) {

    }

    /** sends an email to the email address of the current user to recover the password
     * TODO das macht hier keinen Sinn, weil man muss ja angemeldet sein damit die Facade weiß welchem Nutzer das zu schicken ist.
     *
     */
    public void recoverPassword() {

    }

    /** validates the email of the currently logged in user. This means that the system can confirm that the
     * user is in control of the email account they supplied in {@link APIFacadeUser#register(String, String, String)}
     *
     */
    public void validateEmail() {

    }


}
