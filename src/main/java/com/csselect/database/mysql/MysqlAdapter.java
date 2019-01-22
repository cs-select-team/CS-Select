package com.csselect.database.mysql;

import com.csselect.Injector;
import com.csselect.database.DatabaseAdapter;
import org.intellij.lang.annotations.Language;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Abstract superclass for mysql-adapters that provides basic mysql getters and setters
 */
public abstract class MysqlAdapter {

    private static final MysqlDatabaseAdapter DATABASE_ADAPTER
            = (MysqlDatabaseAdapter) Injector.getInjector().getInstance(DatabaseAdapter.class);

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
     * @throws SQLException Thrown if an error occurs while communicating with the database
     */
    abstract ResultSet getRow() throws SQLException;

    /**
     * Gets a String from the given columnlabel
     * @param columnLabel columnlabel to get the string from
     * @return retrieved string
     */
    String getString(String columnLabel) {
        try {
            ResultSet resultSet = getRow();
            if (resultSet.next()) {
                String res = resultSet.getString(columnLabel);
                resultSet.close();
                return res;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sets a String on the given columnlabel
     * @param columnLabel columnlabel to set the String on
     * @param value string to set
     */
    void setString(String columnLabel, String value) {
        try {
            @Language("sql")
            String query = "UPDATE " + getTableName() + " SET " + columnLabel + " = '" + value
                    + "' WHERE (id='" + id + "');";
            DATABASE_ADAPTER.executeMysqlUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                int res = resultSet.getInt(columnLabel);
                resultSet.close();
                return res;
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Sets an int on the given columnlabel
     * @param columnLabel columnlabel to set the int on
     * @param value int to set
     */
    void setInt(String columnLabel, int value) {
        setString(columnLabel, "" + value);
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
        try {
            @Language("sql")
            String query = "UPDATE " + getTableName()
                    + " SET " + columnLabel + " = " + columnLabel + " + " + value + " WHERE (id='" + id + "');";
            DATABASE_ADAPTER.executeMysqlUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets a boolean on the given columnlabel
     * @param columnLabel colunlabel to set
     * @param value boolean to set
     */
    void setBoolean(String columnLabel, boolean value) {
        setString(columnLabel, "" + (value ? "" + 1 : "" + 0));
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
                boolean res = resultSet.getBoolean(columnLabel);
                resultSet.close();
                return res;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns the table name used by the queries in the adapter
     * @return tablename
     */
    abstract String getTableName();
}
