package com.csselect.database.mysql;

import com.csselect.database.UserAdapter;
import com.csselect.game.Game;

import java.util.Collection;

/**
 * Mysql-Implementation of the {@link UserAdapter} Interface
 */
public abstract class MysqlUserAdapter implements UserAdapter {
    @Override
    public int getID() {
        return 0;
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getPasswordHash() {
        return null;
    }

    @Override
    public String getPasswordSalt() {
        return null;
    }

    @Override
    public String getLanguage() {
        return null;
    }

    @Override
    public void setEmail(String email) {

    }

    @Override
    public void setPassword(String hash, String salt) {

    }

    @Override
    public void setLanguage(String langCode) {

    }

    @Override
    public Collection<Game> getActiveGames() {
        return null;
    }

    @Override
    public Collection<Game> getTerminatedGames() {
        return null;
    }
}
