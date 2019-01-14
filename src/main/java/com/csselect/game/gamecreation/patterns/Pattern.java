package com.csselect.game.gamecreation.patterns;

/**
 * The Pattern class represents a memento which can store a {@link GameOptions} object for creating games.
 */
public class Pattern {
    private String title;
    private GameOptions gameOptions;

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
     * This method returns the String representing the title of the pattern
     * @return Name of the pattern
     */
    public String getString() {
        return title;
    }

    public GameOptions getGameOptions() {
        GameOptions copyObject = new GameOptions();
        copyObject.setTitle(gameOptions.getTitle());
        copyObject.setDescription(gameOptions.getDescription());
        copyObject.setGamemode(gameOptions.getGamemode());
        copyObject.setInvitedEmails(gameOptions.getInvitedEmails());
        copyObject.setNameFeatureDatabase(gameOptions.getNameFeatureDatabase());
        copyObject.setTermination(gameOptions.getTermination());
        return copyObject;
    }
}
