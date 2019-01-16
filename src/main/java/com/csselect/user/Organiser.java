package com.csselect.user;

import com.csselect.database.OrganiserAdapter;
import com.csselect.game.Game;
import com.csselect.game.gamecreation.GameCreator;
import com.csselect.game.gamecreation.patterns.Pattern;

import java.util.Collection;
import java.util.HashSet;

/**
 * The organiser is an user in our system which is able to create games, decide in which database the result shall be
 * written and manage his games (inviting players to games, terminate games and delete games from overview).
 * This class represents an organiser in our system and is connected to an {@link GameCreator} object, which is there
 * for creating games.
 */
public class Organiser extends User {
    private OrganiserAdapter databaseAdapter;
    private GameCreator gameBuilder;
    private Collection<Game> games;
    private Collection<Game> terminatedGames;

    /**
     * Constructor for an Organiser object. Database adapter is set to allow communication with our database
     * (object of {@link OrganiserAdapter}. The constructor will be called as soon as an organiser registers
     * or logs in. Which value the unique ID will have (registration) is determined by the
     * {@link com.csselect.database.DatabaseAdapter}
     * @param databaseAdapter Interface for database communication with organiser tables
     */
    Organiser(OrganiserAdapter databaseAdapter) {
        this.databaseAdapter = databaseAdapter;
        this.gameBuilder = new GameCreator();
        this.games = new HashSet<>();
        this.terminatedGames = new HashSet<>();
    }

    /**
     * To register an organiser in our database, we need 3 Strings representing email, password and global password.
     * @param args String array of arguments for registration (has to have length of 3 cells)
     * @return If the organiser was successfully registered
     **/
    public boolean register(String[] args) {
        assert args.length == 3;
        return true;
    }

    /**
     * Whilst in the game creation interface (front-end), an organiser is able to select certain parameters for his game
     * ({@link Game} the created game will have. The organiser might want to select the same pattern in the future,
     * so he is able to save his chosen parameters. Therefore, we save this so called "pattern" ({@link Pattern} which
     * holds all selections in our database (patterns are individually saved for each organiser)
     * @param title String which shall refer to pattern in our database
     */
    public void savePattern(String title) {

    }

    /**
     * To list all saved patterns ({@link Pattern}) an organiser chose in the game creation interface (front-end),
     * we have to load all patterns from the database.
     * @return All Pattern objects saved for this organiser
     */
    public Collection<Pattern> getPatterns() {
        return databaseAdapter.getPatterns();
    }

    /**
     * To restore all the parameters saved in a pattern ({@link Pattern} we have to extract all game options (fields)
     * saved in it (-> {@link com.csselect.game.gamecreation.patterns.GameOptions}.
     * @param pattern Settings (the organiser already chose and saved) the organiser wants to choose
     */
    public void loadPattern(Pattern pattern) {

        databaseAdapter.addPattern(pattern);
    }

    /**
     * Creating a game is easier than configure it before construction.
     * We just call according method on {@link GameCreator}.
     */
    public void createGame() {
        gameBuilder.doCreate();
    }

    /**
     * Invite registered or new players ({@link Player}) after creating a game ({@link Game}).
     * Therefore, the non-terminated games an organiser object is holding are searched for a game object
     * with according ID which will be noticed that new emails are invited.
     * Also, messages are sent to all invited email-addresses.
     * If the emails are not registered yet, the organiser will determine weather the owner of said email-address
     * will be invited for registration (and to the game) in CS:Select.
     * @param playerEmails Emails invited to the game
     * @param gameId Unique ID of the game in our system the organiser modified
     */
    public void invitePlayers(String[] playerEmails, int gameId) {

    }

    /**
     * Terminate a game ({@link Game}) before the termination's {@link com.csselect.game.Termination} conditions
     * are reached.
     * @param gameId Unique ID of the game that shall be terminated
     */
    public void terminateGame(int gameId) {
        games.forEach((Game element) -> {
            if (element.getId() == gameId) {
                element.terminateGame();
                games.remove(element);
                terminatedGames.add(element);
            }
        });
    }

    /**
     * Delete a game ({@link Game}) which is terminated. The results will not be deleted, but the game won't be shown in
     * the organiser's front-end interface.
     * The entry in our database which links a game to an organiser will be deleted.
     * @param gameId Unique ID of the game that shall be deleted
     */
    public void deleteGame(int gameId) {
        games.removeIf((Game element) -> element.isTerminated() && element.getId() == gameId);
    }
}
