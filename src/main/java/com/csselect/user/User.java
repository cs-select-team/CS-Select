package com.csselect.user;

import com.csselect.database.UserAdapter;

public abstract class User{
    protected int id;
    private UserAdapter dataBaseAdapter;

    User(int id, UserAdapter dataBaseAdapter) {
        this.id = id;
        this.dataBaseAdapter = dataBaseAdapter;
    }

    public int getId() {
        return this.id;
    }

    public abstract boolean register(String[] args);

    public boolean login(String password) {
        return false;
    }

    public void logout() {

    }

    public void changePassword(String password) {

    }

    public void changeEmail(String email) {

    }

    public void setLanguage(String langCode) {

    }
}
