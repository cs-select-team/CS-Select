package com.csselect.database.mysql;

import com.csselect.inject.Injector;
import org.intellij.lang.annotations.Language;
import org.pmw.tinylog.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Abstract superclass for mysql-adapters that provides basic mysql getters and setters
 */
public abstract class MysqlAdapter {

    private static final MysqlDatabaseAdapter DATABASE_ADAPTER
            = (MysqlDatabaseAdapter) Injector.getInstance().getDatabaseAdapter();

    private final int id;

    /**
     * Creates a new {@link MysqlAdapter}
     * @param id id of the adapter
     */
    MysqlAdapter(int id) {
        this.id = id;
    }
    /**
     * Gets the adapters id
     * @return id
     */
    public int getID() {
        return id;
    }

    /**
     * Gets the adapters row in the table as a {@link ResultSet}
     * @return resultSet of the adapters row
     */
    abstract ResultSet getRow();

    /**
     * Gets a String from the given columnlabel
     * @param columnLabel columnlabel to get the string from
     * @return retrieved string
     */
    String getString(String columnLabel) {
        try {
            ResultSet resultSet = getRow();
            if (resultSet.next()) {
                return resultSet.getString(columnLabel);
            } else {
                return null;
            }
        } catch (SQLException e) {
            Logger.error(e);
            return null;
        }
    }

    /**
     * Sets a String on the given columnlabel
     * @param columnLabel columnlabel to set the String on
     * @param value string to set
     */
    void setString(String columnLabel, String value) {
        @Language("sql")
        String query = "UPDATE " + getTableName() + " SET " + columnLabel + " = ? WHERE " + ColumnNames.ID + "=?;";
        DATABASE_ADAPTER.executeMysqlUpdate(query, new StringParam(value), new IntParam(id));
    }

    /**
     * Gets an int from the given columnLabel
     * @param columnLabel columnlabel to get the int from
     * @return retrieved int, -1 if an error occurs
     */
    int getInt(String columnLabel) {
        try {
            ResultSet resultSet = getRow();
            if (resultSet.next()) {
                return resultSet.getInt(columnLabel);
            } else {
                return -1;
            }
        } catch (SQLException e) {
            Logger.error(e);
            return -1;
        }
    }

    /**
     * Sets an int on the given columnlabel
     * @param columnLabel columnlabel to set the int on
     * @param value int to set
     */
    void setInt(String columnLabel, int value) {
        @Language("sql")
        String query = "UPDATE " + getTableName() + " SET " + columnLabel + " = ? WHERE " + ColumnNames.ID + "=?;";
        DATABASE_ADAPTER.executeMysqlUpdate(query, new IntParam(value), new IntParam(id));
    }

    /**
     * Increments an int on the given columnlabel
     * @param columnLabel columnlabel to increment
     */
    void incrementValue(String columnLabel) {
        addInt(columnLabel, 1);
    }

    /**
     * Adds the given int to the int at the given columnlabel
     * @param columnLabel columnlabel to add the int to
     * @param value value to add
     */
    void addInt(String columnLabel, int value) {
        @Language("sql")
        String query = "UPDATE " + getTableName()
                + " SET " + columnLabel + " = " + columnLabel + " + ? WHERE (" + ColumnNames.ID + "=?);";
        DATABASE_ADAPTER.executeMysqlUpdate(query, new IntParam(value), new IntParam(id));
    }

    /**
     * Sets a boolean on the given columnlabel
     * @param columnLabel columnlabel to set
     * @param value boolean to set
     */
    void setBoolean(String columnLabel, boolean value) {
        @Language("sql")
        String query = "UPDATE " + getTableName() + " SET " + columnLabel + " = ? WHERE " + ColumnNames.ID + "=?;";
        DATABASE_ADAPTER.executeMysqlUpdate(query, new BooleanParam(value), new IntParam(id));
    }

    /**
     * Gets a boolean value saved at the given columnlabel
     * @param columnLabel columnlabel to load from
     * @return retrieved boolean
     */
    boolean getBoolean(String columnLabel) {
        try {
            ResultSet resultSet = getRow();
            if (resultSet.next()) {
                return resultSet.getBoolean(columnLabel);
            } else {
                return false;
            }
        } catch (SQLException e) {
            Logger.error(e);
            return false;
        }
    }

    /**
     * Returns the table name used by the queries in the adapter
     * @return tablename
     */
    abstract String getTableName();

}
