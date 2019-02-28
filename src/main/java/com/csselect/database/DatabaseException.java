package com.csselect.database;

/**
 * Class indicating that the database is not reachable
 */
public class DatabaseException extends RuntimeException {
    private static final String MESSAGE = "An error occurred communicating with the database!";

    /**
     * Creates a new {@link DatabaseException} from another{@link Exception}
     * @param e Exception to use for creation
     */
    public DatabaseException(Exception e) {
        super(e);
    }

    /**
     * Creates a new {@link DatabaseException}
     */
    public DatabaseException() {
        super(MESSAGE);
    }
}
