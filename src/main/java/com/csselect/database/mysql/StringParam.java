package com.csselect.database.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Param representing a {@link String} value
 */
class StringParam implements Param {

    private final String value;

    /**
     * Creates a new {@link StringParam}
     * @param value value of the param
     */
    StringParam(String value) {
        this.value = value;
    }

    @Override
    public void apply(PreparedStatement statement, int index) throws SQLException {
        statement.setString(index, value);
    }
}
