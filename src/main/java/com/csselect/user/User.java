package com.csselect.user;

import com.csselect.database.UserAdapter;
import com.csselect.user.management.safety.Encrypter;

/**
 * This class represents an user in our system. All users, despite their role, have access to those methods.
 * A standard user can log himself in/out and is allowed change his password as well as his email which we store
 * in our database.
 * Additionally, he can set in which language the frontend should be displayed.
 * A user is identified in our system through a ID in our Database, retrievable via the {@link UserAdapter}
 */
public class User {
    private UserAdapter userAdapter;
    protected boolean loggedIn;

    /**
     * Default constructor
     */
    User() {
        //does not do anything
    }

    /**
     * Constructor for an User object. Database adapter is set to allow communication with our database
     * (object of {@link UserAdapter}). The constructor will be called as soon as a user registers or logs in.
     * Which value the unique ID will have (registration) is determined
     * by the {@link com.csselect.database.DatabaseAdapter}
     * @param userAdapter Interface for database communication
     */
    public User(UserAdapter userAdapter) {
        this.userAdapter = userAdapter;
    }

    /**
     * If during runtime the ID of a user object is needed, this method enables reading of the ID
     * @return The unique ID which identifies the user in our system
     */
    public int getId() {
        return this.userAdapter.getID();
    }

    /**
     * This method allows a user to log into our system. The API will call this function on an object stored in a user
     * facade. Logging in and validation of the password is taken care of {@link com.csselect.API.APIFacadeUser} and
     * {@link com.csselect.database.DatabaseAdapter}
     */
    public void login() {
        this.loggedIn = true;
    }

    /**
     * Method to notify the object that the user is not logged in anymore
     **/
    public void logout() {
        this.loggedIn = false;
    }

    /**
     * Getter for loggedIn attribute representing if the object shall be able to operate in our system
     * @return boolean if this object is logged in
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * Changing an users's password means to call this method on an object with the according {@link UserAdapter}.
     * It is assured in our implementation that we can call this method only after the user correctly logged
     * into our system.
     * The password itself will be safely hashed and only this hashed version will be stored in the database.
     * @param password New password which will be set for object's ID in our database.
     */
    public void changePassword(String password) {
        String salt = Encrypter.getRandomSalt();
        userAdapter.setPassword(Encrypter.encrypt(password, salt), salt);
    }

    /**
     * Changing an users's email means to call this method on an object with the according {@link UserAdapter}.
     * It is assured in our implementation that we can call this method only after the user correctly logged
     * into our system.
     * @param email New email to which the user ID will refer in our database.
     * @throws IllegalArgumentException Throws error if something is not right with the input email address
     */
    public void changeEmail(String email) throws IllegalArgumentException {
        try {
            userAdapter.setEmail(email);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Email taken");
        }
    }

    /**
     * If the user wishes to change the displayed language, there will be made an entry in our database saving the
     * language code of selected language. The frontend will get the information via {@link UserAdapter#getLanguage()}
     * @param langCode Code of language selected by the user
     */
    public void setLanguage(String langCode) {
        userAdapter.setLanguage(langCode);
    }

    /**
     * Returns language code of language selected by the user
     * @return String representing lang code
     */
    public String getLanguage() {
        return userAdapter.getLanguage();
    }

    @Override
    public int hashCode() {
        return userAdapter.getID();
    }
}
