package com.csselect.game.gamecreation.patterns;

import com.csselect.game.Game;

/**
 * The Pattern class represents a memento which can store a {@link GameOptions} object for creating games.
 */
public class Pattern {
    private final String title;
    private final GameOptions gameOptions;

    /**
     * This method creates a new Pattern-Object which is there for storing GameOptions
     * @param gameOptions Object of GameOption representing options of a game (e.g Title, mode, ...)
     * @param title How the pattern is gonna be named
     */
    public Pattern(GameOptions gameOptions, String title) {
        this.title = title;
        this.gameOptions = gameOptions;
    }

    /**
     * Creates a new Pattern-Object from an existing {@link Game}
     * @param game game whose settings the pattern represents
     * @param title title of the pattern
     */
    public Pattern(Game game, String title) {
        this.title = title;
        this.gameOptions = new GameOptions(game);
    }

    /**
     * This method returns the String representing the title of the pattern
     * @return Name of the pattern
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for game options. Creates a cloned object to not manipulate the old object.
     * @return {@link GameOptions} object saved in Pattern
     */
    public GameOptions getGameOptions() {
        return (GameOptions) gameOptions.clone();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Pattern)) {
            return false;
        } else {
            Pattern pattern = (Pattern) o;
            return title.equals(pattern.title);
        }
    }

    @Override
    public final int hashCode() {
        return title.hashCode();
    }
}
