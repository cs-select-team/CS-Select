package com.csselect.database.mysql;

import com.csselect.database.PlayerAdapter;
import com.csselect.game.Game;
import com.csselect.game.Round;
import com.csselect.gamification.PlayerStats;

import java.util.Collection;

/**
 * Mysql-Implementation of the {@link PlayerAdapter} Interface
 */
public class MysqlPlayerAdapter extends MysqlUserAdapter implements PlayerAdapter {

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public PlayerStats getPlayerStats() {
        return null;
    }

    @Override
    public Collection<Game> getInvitedGames() {
        return null;
    }

    @Override
    public Collection<Round> getRounds() {
        return null;
    }
}
