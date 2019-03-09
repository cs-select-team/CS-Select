package com.csselect.database;

import com.csselect.game.Game;
import com.csselect.user.Organiser;
import com.csselect.user.Player;

import java.util.Collection;

/**
 * Abstraction from the database implementation
 * This class is the entry point for database accesses
 * To ensure only one entry point exists at all times the class is realized as a singleton
 */
public interface DatabaseAdapter {

    /**
     * Gets the {@link PlayerAdapter} for the {@link Player} with the given id
     * @param id id of the player
     * @return playerAdapter
     */
    PlayerAdapter getPlayerAdapter(int id);

    /**
     * Gets the {@link OrganiserAdapter} for the {@link Organiser} with the given id
     * @param id id of the organiser
     * @return organiserAdapter
     */
    OrganiserAdapter getOrganiserAdapter(int id);

    /**
     * Gets the {@link GameAdapter} for the {@link Game} with the given id
     * @param id id of the game
     * @return gameAdapter
     */
    GameAdapter getGameAdapter(int id);

    /**
     * Gets a new {@link GameAdapter} for a new {@link Game}
     * @return new gameAdapter
     */
    GameAdapter getNewGameAdapter();

    /**
     * Gets the {@link Player} with the given email-address
     * @param email players email
     * @return player
     */
    Player getPlayer(String email);

    /**
     * Gets the {@link Player} with the given id
     * @param id players id
     * @return player
     */
    Player getPlayer(int id);

    /**
     * Gets the {@link Organiser} with the given email-address
     * @param email organisers email
     * @return organiser
     */
    Organiser getOrganiser(String email);

    /**
     * Gets a {@link Collection} of all {@link Player}s registered in the database
     * @return players
     */
    Collection<Player> getPlayers();

    /**
     * Gets the ID the next registered {@link Game} will have
     * @return id
     */
    int getNextGameID();

    /**
     * Gets the password hash of the {@link Player} with the given id
     * @param id id of the player
     * @return hashed password
     */
    String getPlayerHash(int id);

    /**
     * Gets the salt used while hashing the {@link Player}s password
     * @param id id of the player
     * @return salt
     */
    String getPlayerSalt(int id);

    /**
     * Gets the password hash of the {@link Organiser} with the given id
     * @param id id of the organiser
     * @return hashed password
     */
    String getOrganiserHash(int id);

    /**
     * Gets the salt used while hashing the {@link Organiser}s password
     * @param id id of the organiser
     * @return salt
     */
    String getOrganiserSalt(int id);

    /**
     * Creates a new {@link Player} in the database
     *
     * @param email players email
     * @param hash players hashed password
     * @param salt salt used while hashing
     * @param username players username
     * @return the created player, null if a player with the same email or username already exists
     * @throws IllegalArgumentException if email or username were already in use
     */
    Player createPlayer(String email, String hash, String salt, String username) throws IllegalArgumentException;

    /**
     * Creates a new {@link Organiser} in the database
     *
     * @param email organisers email
     * @param hash organisers hashed password
     * @param salt salt used while hashing
     * @return the created organiser, null if an organiser with the same email already exists
     */
    Organiser createOrganiser(String email, String hash, String salt);

    /**
     * Creates a new {@link Game} attributed to the given {@link Organiser}
     * @param organiser organiser who the game belongs to
     * @return the created game
     */
    Game createGame(Organiser organiser);

    /**
     * Removes a game from the database
     *
     * Note: Gameresults are still available in the gamespecific database. Only records in the main database will be
     * removed so the game doesn't appear in CS:Select anymore
     * @param game game to be deleted
     */
    void removeGame(Game game);

    /**
     * Checks whether the given database name already exists
     * @param databaseName database name to check
     * @return true if given database is a duplicate, false otherwise
     */
    boolean checkDuplicateDatabase(String databaseName);

}
