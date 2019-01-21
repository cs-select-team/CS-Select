package com.csselect.database.mysql;

import com.csselect.Injector;
import com.csselect.database.DatabaseAdapter;
import com.csselect.database.GameAdapter;
import com.csselect.game.FeatureSet;
import com.csselect.game.Gamemode;
import com.csselect.game.Round;
import com.csselect.game.Termination;
import com.csselect.user.Organiser;
import com.csselect.user.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Mysql-Implementation of the {@link GameAdapter} Interface
 */
public class MysqlGameAdapter extends MysqlAdapter implements GameAdapter {

    private static final MysqlDatabaseAdapter DATABASE_ADAPTER
            = (MysqlDatabaseAdapter) Injector.getInjector().getInstance(DatabaseAdapter.class);

    /**
     * Creates a new {@link MysqlGameAdapter} with the given id
     * @param id adapters id
     */
    MysqlGameAdapter(int id) {
        super(id);
    }

    /**
     * Creates a new {@link MysqlGameAdapter} with the next available id
     * @throws SQLException Thrown if an error occurs while communicating with the database
     */
    MysqlGameAdapter() throws SQLException {
        super(DATABASE_ADAPTER.getNextGameID());
        DATABASE_ADAPTER.executeMysqlUpdate("INSERT INTO games () VALUES ();");
    }

    @Override
    public int getID() {
        return super.getID();
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getDatabaseName() {
        return null;
    }

    @Override
    public FeatureSet getFeatures() {
        return null;
    }

    @Override
    public int getNumberOfRounds() {
        return 0;
    }

    @Override
    public Collection<String> getInvitedPlayers() {
        return null;
    }

    @Override
    public Collection<Player> getPlayingPlayers() {
        return null;
    }

    @Override
    public Collection<Round> getRounds() {
        return null;
    }

    @Override
    public Gamemode getGamemode() {
        return null;
    }

    @Override
    public Termination getTermination() {
        return null;
    }

    @Override
    public Organiser getOrganiser() {
        return null;
    }

    @Override
    public void setTermination(Termination termination) {

    }

    @Override
    public void setFeatures(FeatureSet featureSet) {

    }

    @Override
    public void setFinished() {

    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setDescription(String description) {

    }

    @Override
    public void setDatabase(String name) {

    }

    @Override
    public void setGamemode(Gamemode gamemode) {

    }

    @Override
    public void setOrganiser(Organiser organiser) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void addRound(Round round) {

    }

    @Override
    public void addInvitedPlayers(Collection<String> emails) {

    }

    @Override
    public void addPlayingPlayers(Collection<String> emails) {

    }

    @Override
    public void addPlayingPlayer(int id) {

    }

    @Override
    public void removeInvitedPlayers(Collection<String> emails) {

    }

    @Override
    ResultSet getRow() throws SQLException {
        return DATABASE_ADAPTER.executeMysqlQuery("SELECT * FROM games WHERE (ID='" + getID() + "');");
    }

    @Override
    String getTableName() {
        return "games";
    }
}
