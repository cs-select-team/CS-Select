package com.csselect.database.mysql;

import com.csselect.Injector;
import com.csselect.database.DatabaseAdapter;
import com.csselect.game.BinarySelect;
import com.csselect.game.Gamemode;
import com.csselect.game.MatrixSelect;
import com.csselect.game.NumberOfRoundsTermination;
import com.csselect.game.Termination;
import com.csselect.game.TerminationComposite;
import com.csselect.game.TimeTermination;
import org.intellij.lang.annotations.Language;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

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
            String query = "UPDATE " + getTableName() + " SET " + columnLabel + " = ? WHERE id=" + id + ";";
            DATABASE_ADAPTER.executeMysqlUpdate(query, new StringParam(value));
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
        try {
            @Language("sql")
            String query = "UPDATE " + getTableName() + " SET " + columnLabel + " = ? WHERE id=" + id + ";";
            DATABASE_ADAPTER.executeMysqlUpdate(query, new IntParam(value));
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                    + " SET " + columnLabel + " = " + columnLabel + " + ? WHERE (id=" + id + ");";
            DATABASE_ADAPTER.executeMysqlUpdate(query, new IntParam(value));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets a boolean on the given columnlabel
     * @param columnLabel columnlabel to set
     * @param value boolean to set
     */
    void setBoolean(String columnLabel, boolean value) {
        try {
            @Language("sql")
            String query = "UPDATE " + getTableName() + " SET " + columnLabel + " = ? WHERE id=" + id + ";";
            DATABASE_ADAPTER.executeMysqlUpdate(query, new BooleanParam(value));
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    /**
     * Parses a database saved {@link Gamemode} into a gamemode object
     * @param gamemode gamemode string to parse
     * @return created gamemode
     */
    Gamemode parseGamemode(String gamemode) {
        if (gamemode.startsWith("binarySelect")) {
            return new BinarySelect();
        } else if (gamemode.startsWith("matrixSelect")) {
            String[] args = gamemode.split(",");
            return new MatrixSelect(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
        } else {
            return null;
        }
    }

    /**
     * Parses a database saved {@link Termination} into a termination object
     * @param termination termination string to parse
     * @return created termination
     */
    Termination parseTermination(String termination) {
        String[] terminations = termination.split(",");
        TerminationComposite term = new TerminationComposite();
        for (String t : terminations) {
            if (t.startsWith("time")) {
                term.add(new TimeTermination(LocalDateTime.ofEpochSecond(
                        Long.parseLong(t.replace("time:", "")), 0, ZoneOffset.UTC)));
            } else if (t.startsWith("rounds")) {
                term.add(
                        new NumberOfRoundsTermination(Integer.parseInt(t.replace("rounds:", ""))));
            }
        }
        return term;
    }
}
