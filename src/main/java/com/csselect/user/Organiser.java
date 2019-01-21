package com.csselect.user;

import com.csselect.Injector;
import com.csselect.database.DatabaseAdapter;
import com.csselect.database.OrganiserAdapter;
import com.csselect.game.Game;
import com.csselect.game.gamecreation.GameCreator;
import com.csselect.game.gamecreation.patterns.Pattern;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * The organiser is an user in our system which is able to create games, decide in which database the result shall be
 * written and manage his games (inviting players to games, terminate games and delete games from overview).
 * This class represents an organiser in our system and is connected to an {@link GameCreator} object, which is there
 * for creating games.
 */
public class Organiser extends User implements Comparable {
    private static final DatabaseAdapter DATABASE_ADAPTER = Injector.getInjector().getInstance(DatabaseAdapter.class);
    private OrganiserAdapter organiserAdapter;
    private GameCreator gameBuilder;

    /**
     * Constructor for an Organiser object. Database adapter is set to allow communication with our database
     * (object of {@link OrganiserAdapter}. The constructor will be called as soon as an organiser registers
     * or logs in. Which value the unique ID will have (registration) is determined by the
     * {@link com.csselect.database.DatabaseAdapter}
     * @param organiserAdapter Interface for database communication with organiser tables
     */
    public Organiser(OrganiserAdapter organiserAdapter) {
        super(organiserAdapter);
        this.organiserAdapter = organiserAdapter;
        this.gameBuilder = new GameCreator(this);
    }

    /**
     * Whilst in the game creation interface (front-end), an organiser is able to select certain parameters for his game
     * ({@link Game} the created game will have. The organiser might want to select the same pattern in the future,
     * so he is able to save his chosen parameters. Therefore, we save this so called "pattern" ({@link Pattern} which
     * holds all selections in our database (patterns are individually saved for each organiser)
     * @param title String which shall refer to pattern in our database
     */
    public void savePattern(String title) {
        Pattern createdPattern = gameBuilder.makePattern(title);
        organiserAdapter.addPattern(createdPattern);
    }

    /**
     * To list all saved patterns ({@link Pattern}) an organiser chose in the game creation interface (front-end),
     * we have to load all patterns from the database.
     * @return All Pattern objects saved for this organiser
     */
    public Collection<Pattern> getPatterns() {
        return organiserAdapter.getPatterns();
    }

    /**
     * To restore all the parameters saved in a pattern ({@link Pattern} we have to extract all game options (fields)
     * saved in it (-> {@link com.csselect.game.gamecreation.patterns.GameOptions}.
     * @param pattern Settings (the organiser already chose and saved) the organiser wants to choose
     */
    public void loadPattern(Pattern pattern) {
        gameBuilder.loadPattern(pattern);
    }

    /**
     * Creating a game is easier than configure it before construction.
     * We just call according method on {@link GameCreator}.
     */
    public void createGame() {
        Game game = gameBuilder.doCreate();
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
    public void invitePlayers(Collection<String> playerEmails, int gameId) {
        organiserAdapter.getActiveGames().forEach((Game game) -> {
            if (game.getId() == gameId) {
                game.invitePlayers(playerEmails);
            }
        });
    }

    /**
     * Terminate a game ({@link Game}) before the termination's {@link com.csselect.game.Termination} conditions
     * are reached.
     * @param gameId Unique ID of the game that shall be terminated
     */
    public void terminateGame(int gameId) {
        organiserAdapter.getActiveGames().forEach((Game game) -> {
            if (game.getId() == gameId) {
                game.terminateGame();
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
        organiserAdapter.getTerminatedGames().forEach((Game game) -> {
            if (game.getId() == gameId && game.isTerminated()) {
                DATABASE_ADAPTER.removeGame(game);
            }
        });
    }

    /**
     * Get all non-terminated games from our database the organiser created
     * @return Games which are not terminated and belong to the organiser
     */
    public Collection<Game> getActiveGames() {
        return organiserAdapter.getActiveGames();
    }

    /**
     * Get all games from our database the organiser once created
     * @return Games which are terminated and belong to the organiser
     */
    public Collection<Game> getTerminatedGames() {
        return organiserAdapter.getTerminatedGames();
    }

    /**
     * Sets options for the game that the organiser is currently creating
     * if there is no game being created right now, this will start the process
     *
     * @param option name of the option
     * @param data value
     */
    public void setGameOption(String option, String data) {

    }

    /**
     * Assuming an organiser only is compared to another organiser, this object is compared to another object
     * @param o Object to compare (class organiser)
     * @return int representing if this id is greater (1), equal (0) or smaller (-1) than object's id
     */
    @Override
    public int compareTo(@NotNull Object o) {
        Organiser otherOrganiser = (Organiser) o;
        return Integer.compare(organiserAdapter.getID(), otherOrganiser.getId());
    }

    @Override
    public boolean equals(@NotNull Object o) {
        if (!(o instanceof Organiser)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        Organiser otherOrganiser = (Organiser) o;
        return organiserAdapter.getID() == otherOrganiser.getId();
    }
}
