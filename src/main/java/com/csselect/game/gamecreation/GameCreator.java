package com.csselect.game.gamecreation;

import com.csselect.game.Game;
import com.csselect.game.gamecreation.patterns.GameOptions;
import com.csselect.game.gamecreation.patterns.Pattern;
import com.csselect.user.Organiser;

/**
 * This class is a builder class for a game {@link Game} object. An organiser is able to create a game, so in order to
 * not use a constructor with a long list of parameters we use setters and getters to do the trick.
 * GameCreator is used by an {@link Organiser} object to load settings he already chose or create a new game object.
 */
public class GameCreator {
    private GameOptions gameOptions;

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
        return null;
    }
}
