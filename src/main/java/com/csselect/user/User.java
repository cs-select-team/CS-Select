package com.csselect.user;

import com.csselect.database.UserAdapter;

/**
 * This class represents an user in our system. All users, despite their role, have access to those methods and
 * must implement a register method. A standard user can log himself in/out and is allowed change his password as well
 * as his email which we store in our database. Additionally, he can set in which language the frontend should
 * be displayed.

 * A user is identified in our system through a ID in our Database, retrievable via the {@link UserAdapter}
 */
public abstract class User{
    private UserAdapter databaseAdapter;


    /**
     * Standard constructor
     */
    User() {}

    /**
     * Constructor for an User object. Database adapter is set to allow communication with our database
     * (object of {@link UserAdapter}). The constructor will be called as soon as a user registers or logs in.
     * Which value the unique ID will have (registration) is determined
     * by the {@link com.csselect.database.DatabaseAdapter}
     * @param databaseAdapter Interface for database communication
     */
    User(UserAdapter databaseAdapter) {
        this.databaseAdapter = databaseAdapter;
    }

    /**
     * If during runtime the ID of a user object is needed, this method enables reading of the ID
     * @return The unique ID which identifies the user in our system
     */
    public int getId() {
        return this.databaseAdapter.getID();

    }

    /**
     * This method forces all of the inheriting classes to implement the register method. It could be that different
     * users need a different amount of parameters, so we choose to set a string array as a parameter. If a new user
     * type is added, the API has to take care that the correct amount of attributes are in the array. To prevent
     * errors, add an assertion for the length of the array.
     * @param args String array of arguments for registration
     * @return If the user was successfully registered
     */
    public abstract boolean register(String[] args);

    /**
     * This method allows a user to log into our system. After the {@link com.csselect.API.APIFacadeUser} gets an user
     * object, it will be checked if the given password is correct before allowing any further interaction with this
     * object.
     * @param password Given password to be validated
     * @return Boolean if the password is correct or not
     */
    public boolean login(String password) {
        return false;
    }

    /**
     * This method is a placeholder if anything has to be done before an user logs out in future versions of the system
     */
    public void logout() {

    }

    /**

     * Changing an users's password means to call this method on an object with the according {@link UserAdapter}.
     * It is assured in our implementation that we can call this method only after the user correctly logged
     * into our system.
     * The password itself will be safely hashed and only this hashed version will be stored in the database.
     * @param password New password which will be set for object's ID in our database.
     */
    public void changePassword(String password) {

    }

    /**
     * Changing an users's email means to call this method on an object with the according {@link UserAdapter}.
     * It is assured in our implementation that we can call this method only after the user correctly logged
     * into our system.
     * @param email New email to which the user ID will refer in our database.
     */
    public void changeEmail(String email) {
        databaseAdapter.setEmail(email);
    }

    /**
     * If the user wishes to change the displayed language, there will be made an entry in our database saving the
     * language code of selected language. The frontend will get the information via {@link UserAdapter#getLanguage()}
     * @param langCode Code of language selected by the user
     */
    public void setLanguage(String langCode) {

    }
}
