package com.csselect.database;

/**
 * Class indicating that the database is not reachable
 */
public class DatabaseException extends RuntimeException {

    /**
     * Creates a new {@link DatabaseException} from another{@link Exception}
     * @param e Exception to use for creation
     */
    public DatabaseException(Exception e) {
        super(e);
    }

    /**
     * Creates a new {@link DatabaseException} with the given cause
     * @param cause cause of the DatabaseException
     */
    public DatabaseException(String cause) {
        super(cause);
    }
}
