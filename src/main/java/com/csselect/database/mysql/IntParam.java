package com.csselect.database.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Param representing an int value
 */
class IntParam implements Param {

    private final int value;

    /**
     * Creates a new {@link IntParam}
     * @param value value of the param
     */
    IntParam(int value) {
        this.value = value;
    }

    @Override
    public void apply(PreparedStatement statement, int index) throws SQLException {
        statement.setInt(index, value);
    }
}
