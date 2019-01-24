package com.csselect.database.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Parameter used by the {@link MysqlDatabaseAdapter} to apply values in prepared statements
 */
interface Param {

    /**
     * Applies the value of this param to the given statement on the given index
     * @param statement statement to apply the param
     * @param index index where the param should be applied
     * @throws SQLException Thrown if an error occurs while communicating with the database
     */
    void apply(PreparedStatement statement, int index) throws SQLException;
}
