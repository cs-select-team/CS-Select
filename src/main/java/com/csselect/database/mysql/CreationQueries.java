package com.csselect.database.mysql;

import org.intellij.lang.annotations.Language;

/**
 * Class containing queries for the Database
 */
final class CreationQueries {

    @Language("sql")
    static final String CREATE_PLAYER_TABLE
            = "CREATE TABLE IF NOT EXISTS " + TableNames.PLAYERS + "("
            + ColumnNames.ID + " INT AUTO_INCREMENT PRIMARY KEY,"
            + ColumnNames.HASH + " VARCHAR(255),"
            + ColumnNames.SALT + " VARCHAR(255),"
            + ColumnNames.EMAIL + " VARCHAR(255),"
            + ColumnNames.LANGUAGE + " VARCHAR(255),"
            + ColumnNames.USERNAME + " VARCHAR(255));";

    @Language("sql")
    static final String CREATE_ORGANISER_TABLE
            = "CREATE TABLE IF NOT EXISTS " + TableNames.ORGANISERS + "("
            + ColumnNames.ID + " INT AUTO_INCREMENT PRIMARY KEY,"
            + ColumnNames.HASH + " VARCHAR(255),"
            + ColumnNames.SALT + " VARCHAR(255),"
            + ColumnNames.EMAIL + " VARCHAR(255),"
            + ColumnNames.LANGUAGE + " VARCHAR(255));";

    @Language("sql")
    static final String CREATE_GAME_TABLE
            = "CREATE TABLE IF NOT EXISTS " + TableNames.GAMES + "("
            + ColumnNames.ID + " INT AUTO_INCREMENT PRIMARY KEY,"
            + ColumnNames.ORGANISER_ID + " INT,"
            + "INDEX organiser_ind (" + ColumnNames.ORGANISER_ID + "),"
            + "FOREIGN KEY (" + ColumnNames.ORGANISER_ID + ") REFERENCES "
            + TableNames.ORGANISERS + "(" + ColumnNames.ID + ") ON DELETE CASCADE,"
            + ColumnNames.TITLE + " VARCHAR(255),"
            + ColumnNames.DESCRIPTION + " VARCHAR(255),"
            + ColumnNames.DATABASE_NAME + " VARCHAR(255),"
            + ColumnNames.IS_TERMINATED + " BOOLEAN,"
            + ColumnNames.DATASET + " VARCHAR(255),"
            + ColumnNames.TERMINATION + " VARCHAR(255),"
            + ColumnNames.GAMEMODE + " VARCHAR(255));";

    @Language("sql")
    static final String CREATE_PATTERN_TABLE
            = "CREATE TABLE IF NOT EXISTS " + TableNames.PATTERNS + "("
            + ColumnNames.ORGANISER_ID + " INT,"
            + "INDEX organiser_ind (" + ColumnNames.ORGANISER_ID + "),"
            + "FOREIGN KEY (" + ColumnNames.ORGANISER_ID + ") REFERENCES "
            + TableNames.ORGANISERS + "(" + ColumnNames.ID + ") ON DELETE CASCADE,"
            + ColumnNames.TITLE + " VARCHAR(255),"
            + "PRIMARY KEY (" + ColumnNames.ORGANISER_ID + ", " + ColumnNames.TITLE + "),"
            + ColumnNames.GAME_TITLE + " VARCHAR(255),"
            + ColumnNames.DESCRIPTION + " VARCHAR(255),"
            + ColumnNames.DATASET + " VARCHAR(255),"
            + ColumnNames.DATABASE_NAME + " VARCHAR(40),"
            + ColumnNames.TERMINATION + " VARCHAR(255),"
            + ColumnNames.GAMEMODE + " VARCHAR(255),"
            + ColumnNames.INVITED_PLAYERS + " VARCHAR(255));";

    @Language("sql")
    static final String CREATE_PLAYERSTATS_TABLE
            = "CREATE TABLE IF NOT EXISTS " + TableNames.PLAYERSTATS + "("
            + ColumnNames.ID + " INT PRIMARY KEY ,"
            + "INDEX player_ind (" + ColumnNames.ID + "),"
            + "FOREIGN KEY (" + ColumnNames.ID + ") REFERENCES "
            + TableNames.PLAYERS + "(" + ColumnNames.ID + ") ON DELETE CASCADE,"
            + ColumnNames.SCORE + " INT,"
            + ColumnNames.ROUNDS_PLAYED + " INT,"
            + ColumnNames.DAILIES_COMPLETED + " INT,"
            + ColumnNames.MAX_ROUND_SCORE + " INT,"
            + ColumnNames.LAST_SCORE + " INT,"
            + ColumnNames.HIGHEST_STREAK + " INT);";

    @Language("sql")
    static final String CREATE_ROUNDS_TABLE
            = "CREATE TABLE IF NOT EXISTS " + TableNames.GAME_ROUNDS + "("
            + ColumnNames.ID + " INT AUTO_INCREMENT PRIMARY KEY,"
            + ColumnNames.PLAYER_ID + " INT,"
            + ColumnNames.TIME + " DATETIME,"
            + ColumnNames.SKIPPED + " BOOLEAN,"
            + ColumnNames.QUALITY + " DOUBLE,"
            + ColumnNames.POINTS + " INT,"
            + ColumnNames.USELESS_FEATURES + " VARCHAR(255),"
            + ColumnNames.CHOSEN_FEATURES + " VARCHAR(255),"
            + ColumnNames.SHOWN_FEATURES + " VARCHAR(255));";

    @Language("sql")
    static final String CREATE_GAMES_PLAYERS_TABLE
            = "CREATE TABLE IF NOT EXISTS " + TableNames.GAME_PLAYERS + "("
            + ColumnNames.EMAIL + " VARCHAR(255) PRIMARY KEY,"
            + ColumnNames.INVITED + " BOOLEAN);";

    private CreationQueries() {
        //Utility-classes shouldn't be instantiated
    }
}
