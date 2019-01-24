package com.csselect.database.mysql;

import com.csselect.Injector;
import com.csselect.database.DatabaseAdapter;
import com.csselect.database.OrganiserAdapter;
import com.csselect.game.Game;
import com.csselect.game.gamecreation.patterns.GameOptions;
import com.csselect.game.gamecreation.patterns.Pattern;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.StringJoiner;

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
        DATABASE_ADAPTER.executeMysqlUpdate("INSERT INTO organisers (email,hash,salt) VALUES (?,?,?);",
                new StringParam(email), new StringParam(hash), new StringParam(salt));
    }

    @Override
    public Collection<Pattern> getPatterns() {
        Collection<Pattern> patterns = new HashSet<>();
        try {
            ResultSet set = DATABASE_ADAPTER.executeMysqlQuery(
                    "SELECT * FROM patterns WHERE organiserId=" + getID() + ";");
            while (set.next()) {
                GameOptions options = new GameOptions();
                options.setTitle(set.getString("gameTitle"));
                options.setDescription(set.getString("description"));
                options.setResultDatabaseAddress(set.getString("database"));
                options.setTermination(parseTermination(set.getString("termination")));
                options.setGamemode(parseGamemode(set.getString("gamemode")));
                patterns.add(new Pattern(options, set.getString("title")));
            }
            set.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patterns;
    }

    @Override
    public void addPattern(Pattern pattern) {
        GameOptions gameOptions = pattern.getGameOptions();
        StringJoiner joiner = new StringJoiner(",");
        gameOptions.getInvitedEmails().forEach(joiner::add);
        String emails = joiner.toString();
        try {
            DATABASE_ADAPTER.executeMysqlUpdate("INSERT INTO patterns"
                    + "(organiserId,title,gameTitle,description,databasename,termination,gamemode,invitedPlayers)"
                    + " VALUES (?,?,?,?,?,?,?,?)", new IntParam(getID()), new StringParam(pattern.getTitle()),
                    new StringParam(gameOptions.getTitle()), new StringParam(gameOptions.getDescription()),
                    new StringParam(gameOptions.getResultDatabaseAddress()),
                    new StringParam(gameOptions.getTermination().toString()),
                    new StringParam(gameOptions.getGamemode().toString()), new StringParam(emails));

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
