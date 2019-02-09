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
}
