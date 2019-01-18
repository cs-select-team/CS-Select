package com.csselect.database.mock;

import com.csselect.database.DatabaseAdapter;
import com.csselect.database.GameAdapter;
import com.csselect.database.OrganiserAdapter;
import com.csselect.database.PlayerAdapter;
import com.csselect.game.Game;
import com.csselect.game.Round;
import com.csselect.user.Organiser;
import com.csselect.user.Player;
import com.google.inject.Inject;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Mock-Implementation of the {@link DatabaseAdapter} Interface
 */
public class MockDatabaseAdapter implements DatabaseAdapter {

    private final Map<Integer, PlayerAdapter> playerAdapterMap;
    private final Map<Integer, OrganiserAdapter> organiserAdapterMap;
    private final Map<Game, Organiser> gameMap;
    private int nextGameId;
    private int nextPlayerId;
    private int nextOrganiserId;

    /**
     * Creates a new {@link MockDatabaseAdapter}
     */
    @Inject
    MockDatabaseAdapter() {
        playerAdapterMap = new HashMap<>();
        organiserAdapterMap = new HashMap<>();
        gameMap = new HashMap<>();
        nextGameId = 0;
        nextPlayerId = 0;
        nextOrganiserId = 0;
    }

    @Override
    public PlayerAdapter getPlayerAdapter(int id) {
        return playerAdapterMap.get(id);
    }

    @Override
    public OrganiserAdapter getOrganiserAdapter(int id) {
        return organiserAdapterMap.get(id);
    }

    @Override
    public GameAdapter getGameAdapter(int id) {
        return new MockGameAdapter(id);
    }

    @Override
    public Player getPlayer(String email) {
        PlayerAdapter adapter = playerAdapterMap.values().stream().filter(p -> p.getEmail().equals(email)).findFirst()
                .orElse(null);
        if (adapter == null) {
            return null;
        } else {
            return new Player(adapter);
        }
    }

    @Override
    public Organiser getOrganiser(String email) {
        OrganiserAdapter adapter = organiserAdapterMap.values().stream().filter(p -> p.getEmail().equals(email))
                .findFirst().orElse(null);
        if (adapter == null) {
            return null;
        } else {
            return new Organiser(adapter);
        }
    }

    @Override
    public Collection<Player> getPlayers() {
        Collection<Player> players = new HashSet<>();
        playerAdapterMap.values().forEach(a -> players.add(new Player(a)));
        return players;
    }

    @Override
    public int getNextGameID() {
        return nextGameId;
    }

    @Override
    public String getPlayerHash(int id) {
        return playerAdapterMap.get(id).getPasswordHash();
    }

    @Override
    public String getPlayerSalt(int id) {
        return playerAdapterMap.get(id).getPasswordSalt();
    }

    @Override
    public String getOrganiserHash(int id) {
        return organiserAdapterMap.get(id).getPasswordHash();
    }

    @Override
    public String getOrganiserSalt(int id) {
        return organiserAdapterMap.get(id).getPasswordSalt();
    }

    @Override
    public Player createPlayer(String email, String hash, String salt, String username) {
        for (PlayerAdapter p : playerAdapterMap.values()) {
            if (p.getEmail().equalsIgnoreCase(email)) {
                return new Player(p);
            }
        }
        MockPlayerAdapter adapter = new MockPlayerAdapter(nextPlayerId);
        adapter.setEmail(email);
        adapter.setPassword(hash, salt);
        adapter.setUsername(username);
        playerAdapterMap.put(nextPlayerId, adapter);
        nextPlayerId++;
        return new Player(adapter);
    }

    @Override
    public Organiser createOrganiser(String email, String hash, String salt) {
        for (OrganiserAdapter o : organiserAdapterMap.values()) {
            if (o.getEmail().equalsIgnoreCase(email)) {
                return new Organiser(o);
            }
        }
        OrganiserAdapter adapter = new MockOrganiserAdapter(nextOrganiserId);
        adapter.setEmail(email);
        adapter.setPassword(hash, salt);
        organiserAdapterMap.put(nextOrganiserId, adapter);
        nextOrganiserId++;
        return new Organiser(adapter);
    }

    @Override
    public void registerGame(Organiser organiser, Game game) {
        if (!gameMap.containsKey(game)) {
            gameMap.put(game, organiser);
            nextGameId++;
        }
    }

    @Override
    public void removeGame(Game game) {
        gameMap.remove(game);
    }

    /**
     * Gets the active games the given {@link PlayerAdapter} participates in or is invited to
     * @param adapter adapter to get games for
     * @return active games
     */
    Collection<Game> getActiveGames(PlayerAdapter adapter) {
        return getActiveGames().stream().filter(game ->
                game.getPlayingPlayers().stream().anyMatch(player -> player.getId() == adapter.getID())
                || game.getInvitedPlayers().contains(adapter.getEmail()))
                .collect(Collectors.toSet());
    }

    /**
     * Gets the terminated games the given {@link PlayerAdapter} participates in or is invited to
     * @param adapter adapter to get games for
     * @return terminated games
     */
    Collection<Game> getTerminatedGames(PlayerAdapter adapter) {
        return getTerminatedGames().stream().filter(game ->
                game.getPlayingPlayers().stream().anyMatch(player -> player.getId() == adapter.getID())
                || game.getInvitedPlayers().contains(adapter.getEmail()))
                .collect(Collectors.toSet());
    }

    /**
     * Gets the active games owned by the given {@link OrganiserAdapter}
     * @param adapter adapter to get games for
     * @return active games
     */
    Collection<Game> getActiveGames(OrganiserAdapter adapter) {
        Collection<Game> games = new HashSet<>();
        gameMap.forEach((game, organiser) -> {
            if (organiser.getId() == adapter.getID() && !game.isTerminated()) {
                games.add(game);
            }
        });
        return games;
    }

    /**
     * Gets the terminated games owned by the given {@link OrganiserAdapter}
     * @param adapter adapter to get games for
     * @return terminated games
     */
    Collection<Game> getTerminatedGames(OrganiserAdapter adapter) {
        Collection<Game> games = new HashSet<>();
        gameMap.forEach((game, organiser) -> {
            if (organiser.getId() == adapter.getID() && game.isTerminated()) {
                games.add(game);
            }
        });
        return games;
    }

    private Collection<Game> getActiveGames() {
        return gameMap.keySet().stream().filter(game -> !game.isTerminated()).collect(Collectors.toSet());
    }

    private Collection<Game> getTerminatedGames() {
        return gameMap.keySet().stream().filter(Game::isTerminated).collect(Collectors.toSet());

    }

    /**
     * Gets all rounds the player has participated in
     * @param adapter adapter of the player
     * @return rounds
     */
    Collection<Round> getRounds(PlayerAdapter adapter) {
        Collection<Round> rounds = new HashSet<>();
        gameMap.keySet().forEach(game -> game.getRounds().forEach(round -> {
            if (round.getPlayer().getId() == adapter.getID()) {
                rounds.add(round);
            }
        }));
        return rounds;
    }
}