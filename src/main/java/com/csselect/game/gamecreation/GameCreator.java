package com.csselect.game.gamecreation;

import com.csselect.Injector;
import com.csselect.database.DatabaseAdapter;
import com.csselect.email.EmailSender;
import com.csselect.game.Game;
import com.csselect.game.gamecreation.patterns.GameOptions;
import com.csselect.game.gamecreation.patterns.Pattern;
import com.csselect.mlserver.MLServer;
import com.csselect.user.Organiser;

/**
 * This class is a builder class for a game {@link Game} object. An organiser is able to create a game, so in order to
 * not use a constructor with a long list of parameters we use setters and getters to do the trick.
 * GameCreator is used by an {@link Organiser} object to load settings he already chose or create a new game object.
 */
public class GameCreator {
    private Organiser organiser;
    private GameOptions gameOptions;

    /**
     * Public constructor for a GameCreator object
     * @param organiser {@link Organiser} object to which this GameCreator instance belongs to
     */
    public GameCreator(Organiser organiser) {
        this.organiser = organiser;
        this.gameOptions = new GameOptions();
    }

    /**
     * Getter for the {@link GameOptions} attribute
     * @return GameOptions
     */
    public GameOptions getGameOptions() {
        return gameOptions;
    }

    /**
     * Set a game option using Strings as parameters.
     * @param option Option of {@link GameOptions} which are set
     * @param data Value of the option
     */
    public void setOption(String option, String data) {

    }

    /**
     * Load a pattern into the GameCreator
     * @param pattern {@link Pattern} object which is to load
     */
    public void loadPattern(Pattern pattern) {
        this.gameOptions = pattern.getGameOptions();
    }

    /**
     * Create a new {@link Pattern} object containing {@link GameOptions} attribute
     * @param title Title of the pattern
     * @return {@link Pattern} object
     */
    public Pattern makePattern(String title) {
        return new Pattern(gameOptions, title);
    }

    /**
     * Method to create a new game using the selected game options ({@link GameOptions}
     * @return New {@link Game} object
     */
    public Game doCreate() {
        DatabaseAdapter databaseAdapter = Injector.getInjector().getInstance(DatabaseAdapter.class);
        int gameId = databaseAdapter.getNextGameID();
        Game game = new Game(gameId);
        game.setTitle(gameOptions.getTitle());
        game.setDescription(gameOptions.getDescription());
        game.setAddressOrganiserDatabase(gameOptions.getResultDatabaseAddress());
        game.setTermination(gameOptions.getTermination());
        game.setGamemode(gameOptions.getGamemode());
        game.setMlserver(Injector.getInjector().getInstance(MLServer.class));
        databaseAdapter.registerGame(organiser, game);
        game.invitePlayers(gameOptions.getInvitedEmails());
        for (String mail : gameOptions.getInvitedEmails()) {
            EmailSender.sendEmail("me", mail, "Hello", "txt");
        }
        return null;
    }
}
