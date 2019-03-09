package com.csselect.game;

import com.csselect.user.Player;

/**
 * The Gamemode class represents a game mode. It is abstract and generalizes a method to create a concrete
 * round {@link Round} of this game mode by using the fabric method pattern.
 *
 *  * If adding a new gamemode type, you need to add that type to the {@link Gamemode#parseGamemode(String)} ,
 *  * in such a way that parseGamemode(newMode.toString).equals(newMode)
 */
public abstract class Gamemode {


    /**
     * Parses a database saved {@link Gamemode} into a gamemode object
     * @param gamemode gamemode string to parse
     * @return created gamemode
     */
    public static Gamemode parseGamemode(String gamemode) {
        if (gamemode.startsWith(BinarySelect.TYPE)) {
            return new BinarySelect();
        } else if (gamemode.startsWith(MatrixSelect.TYPE)) {
            String[] args = gamemode.split(",");
            return new MatrixSelect(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
        } else {
            return null;
        }
    }

    /**
     * Creates a round {@link Round} object of this game mode
     * @param player the player {@link Player} who plays the round {@link Round}
     * @return returns the round {@link Round} object
     */
    public abstract Round createRound(Player player);

    /**
     * Getter for the name of the gamemode
     * @return returns the name of the class of the gamemode
     */
    public String getName() {
        return this.getClass().getSimpleName();
    }
}
