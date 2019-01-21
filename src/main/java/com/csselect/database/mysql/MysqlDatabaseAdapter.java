package com.csselect.database.mysql;

import com.csselect.configuration.Configuration;
import com.csselect.database.DatabaseAdapter;
import com.csselect.database.GameAdapter;
import com.csselect.database.OrganiserAdapter;
import com.csselect.database.PlayerAdapter;
import com.csselect.game.Game;
import com.csselect.user.Organiser;
import com.csselect.user.Player;
import com.google.inject.Inject;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Mysql-Implementation of the {@link DatabaseAdapter} Interface
 */
public class MysqlDatabaseAdapter implements DatabaseAdapter {

    private static final String PRODUCT_DATABASE_NAME = "CS_SELECT";

    private final String hostname;
    private final int port;
    private final String username;
    private final String password;
    private final Map<String, MysqlDataSource> dataSources;

    /**
     * Creates a new MysqlDatabaseAdapter
     * @param configuration configuration to use
     */
    @Inject
    MysqlDatabaseAdapter(Configuration configuration) {
        this.hostname = configuration.getDatabaseHostname();
        this.port = configuration.getDatabasePort();
        this.username = configuration.getDatabaseUsername();
        this.password = configuration.getDatabasePassword();
        this.dataSources = new HashMap<>();
        this.dataSources.put(PRODUCT_DATABASE_NAME, createDataSource(PRODUCT_DATABASE_NAME));
    }

    @Override
    public PlayerAdapter getPlayerAdapter(int id) {
        return null;
    }

    @Override
    public OrganiserAdapter getOrganiserAdapter(int id) {
        return null;
    }

    @Override
    public GameAdapter getGameAdapter(int id) {
        return null;
    }

    @Override
    public Player getPlayer(String email) {
        return null;
    }

    @Override
    public Player getPlayer(int id) {
        return null;
    }

    @Override
    public Organiser getOrganiser(String email) {
        return null;
    }

    @Override
    public Collection<Player> getPlayers() {
        return null;
    }

    @Override
    public int getNextGameID() {
        return 0;
    }

    @Override
    public String getPlayerHash(int id) {
        return null;
    }

    @Override
    public String getPlayerSalt(int id) {
        return null;
    }

    @Override
    public String getOrganiserHash(int id) {
        return null;
    }

    @Override
    public String getOrganiserSalt(int id) {
        return null;
    }

    @Override
    public Player createPlayer(String email, String hash, String salt, String username) {
        return null;
    }

    @Override
    public Organiser createOrganiser(String email, String hash, String salt) {
        return null;
    }

    @Override
    public void registerGame(Organiser organiser, Game game) {

    }

    @Override
    public void removeGame(Game game) {

    }

    /**
     * Executes the given Mysql-query on the main database
     * @param query query to execute
     * @return ResultSet of the operation
     * @throws SQLException Thrown when there is an error executing the given statement
     */
    ResultSet executeMysqlQuery(@Language("sql") String query) throws SQLException {
        return executeMysqlQuery(query, PRODUCT_DATABASE_NAME);
    }

    /**
     * Executes the given Mysql-query on the given database
     * @param query query to execute
     * @param databaseName database to execute the query on
     * @return ResultSet of the operation
     * @throws SQLException Thrown when there is an error executing the given statement
     */
    ResultSet executeMysqlQuery(@Language("sql") String query, String databaseName) throws SQLException {
        MysqlDataSource dataSource = dataSources.getOrDefault(databaseName, createDataSource(databaseName));
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        statement.close();
        connection.close();
        return resultSet;
    }

    /**
     * Executes the given Mysql-Update on the main database
     * @param update update query to execute
     * @throws SQLException Thrown when there is an error executing the given statement
     */
    void executeMysqlUpdate(@Language("sql") String update) throws SQLException {
        executeMysqlUpdate(update, PRODUCT_DATABASE_NAME);
    }

    /**
     * Executes the given Mysql-Update on the given database
     * @param update update query to execute
     * @param databaseName database to execute the query on
     * @throws SQLException Thrown when there is an error executing the given statement
     */
    void executeMysqlUpdate(@Language("sql") String update, String databaseName) throws SQLException {
        MysqlDataSource dataSource = dataSources.getOrDefault(databaseName, createDataSource(databaseName));
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(update);
        statement.close();
        connection.close();
    }

    private MysqlDataSource createDataSource(String databaseName) {
        MysqlDataSource source = new MysqlDataSource();
        source.setServerName(hostname);
        source.setPort(port);
        source.setUser(username);
        source.setPassword(password);
        source.setDatabaseName(databaseName);
        try {
            source.setServerTimezone("CET");
            source.setCreateDatabaseIfNotExist(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dataSources.put(databaseName, source);
        return source;
    }
}
