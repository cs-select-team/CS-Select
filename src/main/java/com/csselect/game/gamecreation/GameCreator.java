package com.csselect.game.gamecreation;

import com.csselect.Injector;
import com.csselect.database.DatabaseAdapter;
import com.csselect.email.EmailSender;
import com.csselect.game.Game;
import com.csselect.game.gamecreation.patterns.GameOptions;
import com.csselect.game.gamecreation.patterns.Pattern;
import com.csselect.parser.GamemodeParser;
import com.csselect.parser.TerminationParser;
import com.csselect.user.Organiser;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

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
        String[] arguments = data.split(":");
        assert arguments.length > 0;
        switch(option) {
            case "title":
                gameOptions.setTitle(arguments[0]);
                break;
            case "description":
                gameOptions.setDescription(arguments[0]);
                break;
            case "featureSet":
                gameOptions.setDataset(arguments[0]);
                break;
            case "addressOrganiserDatabase":
                gameOptions.setResultDatabaseAddress(arguments[0]);
                break;
            case "termination":
                assert arguments.length == 2; // [type, arguments]
                gameOptions.setTermination(TerminationParser.getTermination(arguments));
                assert this.gameOptions.getTermination() != null;
                break;
            case "gamemode":
                assert arguments.length == 2; // [type, arguments]
                gameOptions.setGamemode(GamemodeParser.getGamemode(arguments));
                assert this.gameOptions.getGamemode() != null;
                break;
            case "addPlayers":
                gameOptions.addInvitedEmails(new HashSet<>(Arrays.asList(arguments)));
                break;
            default:
                break;
        }
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
        DatabaseAdapter databaseAdapter = Injector.getInstance().getDatabaseAdapter();
        int gameId = databaseAdapter.getNextGameID();
        Game game = new Game(gameId);
        game.setTitle(gameOptions.getTitle());
        game.setDescription(gameOptions.getDescription());
        game.setAddressOrganiserDatabase(gameOptions.getResultDatabaseAddress());
        game.setTermination(gameOptions.getTermination());
        game.setGamemode(gameOptions.getGamemode());
        try {
            game.setFeatureSet(Injector.getInstance().getMLServer().getFeatures(gameOptions.getDataset()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        databaseAdapter.registerGame(organiser, game);
        if (!gameOptions.getInvitedEmails().isEmpty()) {
            game.invitePlayers(gameOptions.getInvitedEmails());
            for (String mail : gameOptions.getInvitedEmails()) {
                EmailSender.sendEmail(mail, "CS:Select Invitation",
                        "Your knowledge is needed in a CS:Select game. Log in and check your notifications!");
            }
        }
        return game;
    }
}
