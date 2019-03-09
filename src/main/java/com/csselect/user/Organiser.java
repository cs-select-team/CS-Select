package com.csselect.user;

import com.csselect.inject.Injector;
import com.csselect.database.OrganiserAdapter;
import com.csselect.game.Game;
import com.csselect.game.gamecreation.GameCreator;
import com.csselect.game.gamecreation.patterns.GameOptions;
import com.csselect.game.gamecreation.patterns.Pattern;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * The organiser is an user in our system which is able to create games, decide in which database the result shall be
 * written and manage his games (inviting players to games, terminate games and delete games from overview).
 * This class represents an organiser in our system and is connected to an {@link GameCreator} object, which is there
 * for creating games.
 */
public class Organiser extends User {
    private final OrganiserAdapter organiserAdapter;
    private final GameCreator gameBuilder;

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
     * @return true if the game was created successfully, false otherwise
     */
    public boolean createGame() {
        Game game = gameBuilder.doCreate();
        return game != null;
    }

    /**
     * Invite registered or new players ({@link Player}) after creating a game ({@link Game}).
     * Therefore, the non-terminated games an organiser object is holding are searched for a game object
     * with according ID which will be noticed that new emails are invited.
     * Also, messages are sent to all invited email-addresses.
     * If the emails are not registered yet, the organiser will determine whether the owner of said email-address
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
                Injector.getInstance().getDatabaseAdapter().removeGame(game);
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
     * Checks whether the given {@link Game} title is already used by the organiser
     * @param title title of the game
     * @return true if title is in use, false otherwise
     */
    public boolean gameTitleInUse(String title) {
        return this.organiserAdapter.gameTitleInUse(title);
    }

    /**
     * Sets options for the game that the organiser is currently creating
     *
     * @param option name of the option
     * @param data value
     */
    public void setGameOption(String option, String data) {
        gameBuilder.setOption(option, data);
    }

    /**
     * Returns ccpy of gameoptions object currently loaded in the game builder ({@link GameCreator})
     * @return cloned object of {@link GameOptions}
     */
    public GameOptions getGameOptions() {
        return this.gameBuilder.getGameOptions();
    }

    @Override
    public final boolean equals(@NotNull Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Organiser)) {
            return false;
        } else {
            Organiser organiser = (Organiser) o;
            return this.organiserAdapter.getID() == organiser.getId();
        }
    }

    /**
     * Creates a {@link Pattern} with the given title from the game with the given gameId
     * @param gameId id of the game to use
     * @param title title to use
     */
    public void createPatternFromGame(final int gameId, final String title) {
        Game game = organiserAdapter.getActiveGames().stream().filter(g -> g.getId() == gameId).findAny()
                .orElse(organiserAdapter.getTerminatedGames().stream().filter(g -> g.getId() == gameId).findAny()
                        .orElse(null));
        if (game == null) {
            throw new IllegalArgumentException("No game with the ID " + gameId + " could be found for the organiser "
                    + "with ID " + this.getId() + " to create a new pattern from!");
        }
        this.organiserAdapter.addPattern(new Pattern(game, title));
    }
}
