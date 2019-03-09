package com.csselect.database.mysql;

/**
 * Class containing constants of the column names used in the database
 */
final class ColumnNames {

    static final String ID = "id";

    static final String EMAIL = "email";
    static final String HASH = "hash";
    static final String SALT = "salt";
    static final String LANGUAGE = "language";
    static final String USERNAME = "username";
    static final String ORGANISER_ID = "organiserId";
    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String DATABASE_NAME = "databaseName";
    static final String IS_TERMINATED = "isTerminated";
    static final String DATASET = "dataset";
    static final String TERMINATION = "termination";
    static final String GAMEMODE = "gamemode";
    static final String GAME_TITLE = "gameTitle";
    static final String INVITED_PLAYERS = "invitedPlayers";
    static final String SCORE = "score";
    static final String ROUNDS_PLAYED = "roundsPlayed";
    static final String DAILIES_COMPLETED = "dailiesCompleted";
    static final String MAX_ROUND_SCORE = "maxRoundScore";
    static final String LAST_SCORE = "lastScore";
    static final String HIGHEST_STREAK = "highestStreak";
    static final String PLAYER_ID = "playerId";
    static final String TIME = "time";
    static final String SKIPPED = "skipped";
    static final String QUALITY = "quality";
    static final String POINTS = "points";
    static final String USELESS_FEATURES = "uselessFeatures";
    static final String CHOSEN_FEATURES = "chosenFeatures";
    static final String SHOWN_FEATURES = "shownFeatures";
    static final String INVITED = "invited";

    private ColumnNames() {
        //We don't want to instantiate this
    }
}
