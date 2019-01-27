package com.csselect.database.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Parameter representing a double value
 */
class DoubleParam implements Param {

    private final double value;

    /**
     * Creates a new {@link DoubleParam}
     * @param value value of the param
     */
    DoubleParam(double value) {
        this.value = value;
    }

    @Override
    public void apply(PreparedStatement statement, int index) throws SQLException {
        statement.setDouble(index, value);
    }
}
