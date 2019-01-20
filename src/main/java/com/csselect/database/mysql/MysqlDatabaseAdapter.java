package com.csselect.database.mysql;

import com.csselect.database.DatabaseAdapter;
import com.csselect.database.GameAdapter;
import com.csselect.database.OrganiserAdapter;
import com.csselect.database.PlayerAdapter;
import com.csselect.game.Game;
import com.csselect.user.Organiser;
import com.csselect.user.Player;

import java.util.Collection;

/**
 * Mysql-Implementation of the {@link DatabaseAdapter} Interface
 */
public class MysqlDatabaseAdapter implements DatabaseAdapter {

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
}
