package com.csselect.user.management;

import com.csselect.user.User;

/**
 * This class returns user objects on registration/login.
 */
public abstract class UserManagement {
    /**
     * For registration, it could be that for different types of users ({@link User} different parameters are needed.
     * Therefore a String array representing said parameters is expected. In anything goes wrong, null shall be returned.
     * Every manageable user is supposed to have a management class with this method.
     * @param parameters Registration parameters
     * @return {@link User} object
     */
    public abstract User register(String[] parameters);

    /**
     * To login an {@link User}, we need to know the email and the password he typed in. If the
     * password is correct, return an user object, return null otherwise.
     * @param email Email of the user
     * @param password Password he typed in
     * @return User object or null
     */
    public abstract User login(String email, String password);
}
