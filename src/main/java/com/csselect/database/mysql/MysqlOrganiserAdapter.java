package com.csselect.database.mysql;

import com.csselect.Injector;
import com.csselect.database.DatabaseAdapter;
import com.csselect.database.OrganiserAdapter;
import com.csselect.game.Game;
import com.csselect.game.gamecreation.patterns.Pattern;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Mysql-Implementation of the {@link OrganiserAdapter} Interface
 */
public class MysqlOrganiserAdapter extends MysqlUserAdapter implements OrganiserAdapter {

    private static final MysqlDatabaseAdapter DATABASE_ADAPTER
            = (MysqlDatabaseAdapter) Injector.getInjector().getInstance(DatabaseAdapter.class);

    /**
     * Creates a new {@link MysqlOrganiserAdapter} with the given id
     *
     * @param id id of the adapter
     */
    MysqlOrganiserAdapter(int id) {
        super(id);
    }

    /**
     * Creates a new {@link MysqlDatabaseAdapter} with the next available id
     * @param email organisers email
     * @param hash organisers password hash
     * @param salt organisers password salt
     * @throws SQLException Thrown if an error occurs while communicating with the database server
     */
    MysqlOrganiserAdapter(String email, String hash, String salt) throws SQLException {
        super(DATABASE_ADAPTER.getNextIdOfTable("organisers"));
        DATABASE_ADAPTER.executeMysqlUpdate("INSERT INTO organisers (email,'hash',salt) VALUES (?,?,?);",
                new StringParam(email), new StringParam(hash), new StringParam(salt));
    }

    @Override
    public Collection<Pattern> getPatterns() {
        return null;
    }

    @Override
    public void addPattern(Pattern pattern) {

    }

    @Override
    String getTableName() {
        return "organisers";
    }

    @Override
    public Collection<Game> getActiveGames() {
        return DATABASE_ADAPTER.getActiveGames(this);
    }

    @Override
    public Collection<Game> getTerminatedGames() {
        return DATABASE_ADAPTER.getTerminatedGames(this);
    }
}
