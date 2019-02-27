package com.csselect.database;

/**
 * Class indicating that the database is not reachable
 */
public class DatabaseException extends RuntimeException {
    private static final String MESSAGE = "";

    /**
     * Creates a new {@link DatabaseException}
     */
    public DatabaseException() {
        super(MESSAGE);
    }
}
