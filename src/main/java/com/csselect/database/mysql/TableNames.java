package com.csselect.database.mysql;

/**
 * Class containing constants of the table names used in the database
 */
final class TableNames {

    static final String GAMES = "games";
    static final String GAME_ROUNDS = "rounds";
    static final String GAME_PLAYERS = "players";
    static final String ORGANISERS = "organisers";
    static final String PLAYERS = "players";
    static final String PLAYERSTATS = "playerstats";
    static final String PATTERNS = "patterns";

    private TableNames() {
        //Utility-classes shouldn't be instantiated
    }
}
