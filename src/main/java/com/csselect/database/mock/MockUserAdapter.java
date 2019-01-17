package com.csselect.database.mock;

import com.csselect.database.UserAdapter;

/**
 * Mock-Implementation of the {@link UserAdapter} interface
 */
public abstract class MockUserAdapter implements UserAdapter {

    private final int id;
    private String email;
    private String hash;
    private String salt;
    private String language;

    /**
     * Creates a MockUserAdapter with the given id
     * @param id id of the adapter
     */
    MockUserAdapter(int id) {
        this.id = id;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPasswordHash() {
        return hash;
    }

    @Override
    public String getPasswordSalt() {
        return salt;
    }

    @Override
    public String getLanguage() {
        return language;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setPassword(String hash, String salt) {
        this.hash = hash;
        this.salt = salt;
    }

    @Override
    public void setLanguage(String langCode) {
        this.language = langCode;
    }
}
