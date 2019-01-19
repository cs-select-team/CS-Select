package com.csselect.database.mock;

import com.csselect.Injector;
import com.csselect.database.DatabaseAdapter;
import com.csselect.database.PlayerAdapter;
import com.csselect.database.PlayerStatsAdapter;
import com.csselect.game.Game;
import com.csselect.game.Round;
import com.csselect.gamification.PlayerStats;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Mock-Implementation of the {@link PlayerAdapter} Interface
 */
public class MockPlayerAdapter extends MockUserAdapter implements PlayerAdapter {

    private String username;
    private final MockDatabaseAdapter mockDatabaseAdapter;
    private static final HashMap<Integer, PlayerStatsAdapter> playerStatsAdapters = new HashMap<>();

    /**
     * Creates a new {@link MockPlayerAdapter} with the given id
     * @param id id of the adapter
     */
    MockPlayerAdapter(int id) {
        super(id);
        mockDatabaseAdapter = (MockDatabaseAdapter) Injector.getInjector().getInstance(DatabaseAdapter.class);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public PlayerStats getPlayerStats() {
        if (playerStatsAdapters.containsKey(this.getID())) {
            return new PlayerStats(playerStatsAdapters.get(this.getID()));
        } else {
            PlayerStatsAdapter adapter = new MockPlayerStatsAdapter();
            playerStatsAdapters.put(this.getID(), adapter);
            return new PlayerStats(adapter);
        }
    }

    @Override
    public Collection<Game> getInvitedGames() {
        Collection<Game> allGames = new HashSet<>(mockDatabaseAdapter.getActiveGames(this));
        allGames.addAll(mockDatabaseAdapter.getActiveGames(this));
        return allGames;
    }

    @Override
    public Collection<Round> getRounds() {
        return mockDatabaseAdapter.getRounds(this);
    }

    @Override
    public Collection<Game> getActiveGames() {
        return mockDatabaseAdapter.getActiveGames(this);
    }

    @Override
    public Collection<Game> getTerminatedGames() {
        return mockDatabaseAdapter.getTerminatedGames(this);
    }

    /**
     * Sets the {@link PlayerAdapter}s username. Only available in package to prevent unallowed changing of the username
     * @param username username to set
     */
    void setUsername(String username) {
        this.username = username;
    }
}
