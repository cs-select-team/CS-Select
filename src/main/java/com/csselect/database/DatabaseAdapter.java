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
     * Gets the {@link Player} with the given email-address
     * @param email players email
     * @return player
     */
    Player getPlayer(String email);

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
     * Registers a new {@link Player} in the database
     * @param email players email
     * @param password players password
     * @param username players username
     */
    void registerPlayer(String email, String password, String username);

    /**
     * Registers a new {@link Organiser} in the database
     *
     * If the given organiser-password is not correct, no organiser will be registered
     *
     * @param email organisers email
     * @param password organisers password
     * @param givenOrganiserPassword organiser-password given by the user attempting to register
     */
    void registerOrganiser(String email, String password, String givenOrganiserPassword);

    /**
     * Registers a new {@link Game} attributed to the given {@link Organiser}
     * @param organiser organiser who the game belongs to
     * @param game game to be registered
     */
    void registerGame(Organiser organiser, Game game);

    /**
     * Removes a game from the database
     *
     * Note: Gameresults are still available in the gamespecific database. Only records in the main database will be
     * removed so the game doesn't appear in CS:Select anymore
     * @param game game to be deleted
     */
    void removeGame(Game game);

}
