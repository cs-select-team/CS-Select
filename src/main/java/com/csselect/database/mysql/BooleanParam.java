package com.csselect.database.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Parameter representing a boolean value
 */
class BooleanParam implements Param {

    private final boolean value;

    /**
     * Creates a new {@link BooleanParam}
     * @param value value of the param
     */
    BooleanParam(boolean value) {
        this.value = value;
    }

    @Override
    public void apply(PreparedStatement statement, int index) throws SQLException {
        statement.setBoolean(index, value);
    }
}
