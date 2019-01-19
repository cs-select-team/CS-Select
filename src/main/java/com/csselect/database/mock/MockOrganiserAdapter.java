package com.csselect.database.mock;

import com.csselect.Injector;
import com.csselect.database.DatabaseAdapter;
import com.csselect.database.OrganiserAdapter;
import com.csselect.game.Game;
import com.csselect.game.gamecreation.patterns.Pattern;

import java.util.Collection;
import java.util.HashSet;

/**
 * Mock-Implementation of the {@link OrganiserAdapter} Interface
 */
public class MockOrganiserAdapter extends MockUserAdapter implements OrganiserAdapter {

    private final Collection<Pattern> patterns;
    private final MockDatabaseAdapter mockDatabaseAdapter;

    /**
     * Creates a new {@link OrganiserAdapter} with the given id
     * @param id id of the adapter
     */
    MockOrganiserAdapter(int id) {
        super(id);
        patterns = new HashSet<>();
        mockDatabaseAdapter = (MockDatabaseAdapter) Injector.getInjector().getInstance(DatabaseAdapter.class);
    }

    @Override
    public Collection<Pattern> getPatterns() {
        return patterns;
    }

    @Override
    public void addPattern(Pattern pattern) {
        patterns.add(pattern);
    }

    @Override
    public Collection<Game> getActiveGames() {
        return mockDatabaseAdapter.getActiveGames(this);
    }

    @Override
    public Collection<Game> getTerminatedGames() {
        return mockDatabaseAdapter.getTerminatedGames(this);
    }
}
