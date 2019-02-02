package com.csselect.parser;

import com.csselect.game.BinarySelect;
import com.csselect.game.Gamemode;
import com.csselect.game.MatrixSelect;

/**
 *  This class is there to enable retrieving of {@link Gamemode} objects out of a String
 */
public final class GamemodeParser {

    private GamemodeParser() {

    }

    /**
     * Creates a {@link Gamemode} object out of a String
     * @param args arguments for type and value of gamemode
     * @return Gamemode object
     */
    public static Gamemode getGamemode(String[] args) {
        Gamemode gamemode = null;
        String type = args[0];
        String arguments = args[1];
        if (type.equals(new MatrixSelect(0, 0, 0).getName())) {
            String[] splitArguments = arguments.split("-"); //Syntax: "num~x-min~y-max~z"
            int number = Integer.parseInt(splitArguments[0].split("~")[1]);
            int min = Integer.parseInt(splitArguments[1].split("~")[1]);
            int max = Integer.parseInt(splitArguments[2].split("~")[1]);
            gamemode = new MatrixSelect(number, min, max);
        } else if (type.equals(new BinarySelect().getName())) {
            gamemode = new BinarySelect();
        }
        return gamemode;
    }
}
