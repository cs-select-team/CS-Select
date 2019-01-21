package com.csselect.database.mysql;

import com.csselect.Injector;
import com.csselect.database.DatabaseAdapter;
import com.csselect.database.UserAdapter;
import com.csselect.game.Game;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Mysql-Implementation of the {@link UserAdapter} Interface
 */
public abstract class MysqlUserAdapter implements UserAdapter {

    private final int id;
    private static final MysqlDatabaseAdapter DATABASE_ADAPTER = (MysqlDatabaseAdapter) Injector.getInjector()
            .getInstance(DatabaseAdapter.class);

    /**
     * Creates a new {@link MysqlUserAdapter} with the given id
     * @param id id of the adapter
     */
    public MysqlUserAdapter(int id) {
        this.id = id;
    }
    @Override
    public int getID() {
        return id;
    }

    @Override
    public String getEmail() {
        return getString("EMAIL");
    }

    @Override
    public String getPasswordHash() {
        return getString("HASH");
    }

    @Override
    public String getPasswordSalt() {
        return getString("SALT");
    }

    @Override
    public String getLanguage() {
        return getString("LANGUAGE");
    }

    @Override
    public void setEmail(String email) {
        setString("EMAIL", email);
    }

    @Override
    public void setPassword(String hash, String salt) {
        try {
            DATABASE_ADAPTER.executeMysqlQuery("UPDATE " + getTableName() + " SET HASH = " + hash + ", SALT = " + salt
                    + " WHERE ID IS" + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setLanguage(String langCode) {
        setString("LANGUAGE", langCode);
    }

    @Override
    public Collection<Game> getActiveGames() {
        return null;
    }

    @Override
    public Collection<Game> getTerminatedGames() {
        return null;
    }

    private ResultSet getRow() throws SQLException {
        return DATABASE_ADAPTER.executeMysqlQuery("SELECT * FROM " + getTableName() + " WHERE ID IS " + id);
    }

    private String getString(String columnLabel) {
        try {
            ResultSet resultSet = getRow();
            resultSet.next();
            String res = resultSet.getString(columnLabel);
            resultSet.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setString(String columnLabel, String value) {
        try {
            DATABASE_ADAPTER.executeMysqlUpdate("UPDATE " + getTableName() + " SET " + columnLabel + " = " + value + " WHERE id IS " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the table name used by the queries in the adapter
     * @return tablename
     */
    abstract String getTableName();
}
