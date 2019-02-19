package com.csselect.database.mysql;

import org.intellij.lang.annotations.Language;

/**
 * Class containing queries for the Database
 */
final class CreationQueries {

    @Language("sql")
    static final String CREATE_PLAYER_TABLE
            = "CREATE TABLE IF NOT EXISTS " + TableNames.PLAYERS + "("
            + "id INT AUTO_INCREMENT PRIMARY KEY,"
            + "hash VARCHAR(255),"
            + "salt VARCHAR(255),"
            + "email VARCHAR(255),"
            + "language VARCHAR(255),"
            + "username VARCHAR(255));";

    @Language("sql")
    static final String CREATE_ORGANISER_TABLE
            = "CREATE TABLE IF NOT EXISTS " + TableNames.ORGANISERS + "("
            + "id INT AUTO_INCREMENT PRIMARY KEY,"
            + "hash VARCHAR(255),"
            + "salt VARCHAR(255),"
            + "email VARCHAR(255),"
            + "language VARCHAR(255));";

    @Language("sql")
    static final String CREATE_GAME_TABLE
            = "CREATE TABLE IF NOT EXISTS " + TableNames.GAMES + "("
            + "id INT AUTO_INCREMENT PRIMARY KEY,"
            + "organiserId INT,"
            + "INDEX organiser_ind (organiserId),"
            + "FOREIGN KEY (organiserId) REFERENCES organisers(id) ON DELETE CASCADE,"
            + "title VARCHAR(255),"
            + "description VARCHAR(255),"
            + "databasename VARCHAR(255),"
            + "isTerminated BOOLEAN,"
            + "dataset VARCHAR(255),"
            + "termination VARCHAR(255),"
            + "gamemode VARCHAR(255));";

    @Language("sql")
    static final String CREATE_PATTERN_TABLE
            = "CREATE TABLE IF NOT EXISTS " + TableNames.PATTERNS + "("
            + "organiserId INT,"
            + "INDEX organiser_ind (organiserId),"
            + "FOREIGN KEY (organiserId) REFERENCES organisers(id) ON DELETE CASCADE,"
            + "title VARCHAR(255),"
            + "PRIMARY KEY (organiserId, title),"
            + "gameTitle VARCHAR(255),"
            + "description VARCHAR(255),"
            + "dataset VARCHAR(255),"
            + "databasename VARCHAR(40),"
            + "termination VARCHAR(255),"
            + "gamemode VARCHAR(255),"
            + "invitedPlayers VARCHAR(255));";

    @Language("sql")
    static final String CREATE_PLAYERSTATS_TABLE
            = "CREATE TABLE IF NOT EXISTS " + TableNames.PLAYERSTATS + "("
            + "id INT PRIMARY KEY ,"
            + "INDEX player_ind (id),"
            + "FOREIGN KEY (id) REFERENCES players(id) ON DELETE CASCADE,"
            + "score INT,"
            + "roundsPlayed INT,"
            + "dailiesCompleted INT,"
            + "maxRoundScore INT,"
            + "lastScore INT,"
            + "highestStreak INT);";

    @Language("sql")
    static final String CREATE_ROUNDS_TABLE
            = "CREATE TABLE IF NOT EXISTS " + TableNames.GAME_ROUNDS + "("
            + "id INT AUTO_INCREMENT PRIMARY KEY,"
            + "playerId INT,"
            + "time DATETIME,"
            + "skipped BOOLEAN,"
            + "quality DOUBLE,"
            + "points INT,"
            + "uselessFeatures VARCHAR(255),"
            + "chosenFeatures VARCHAR(255),"
            + "shownFeatures VARCHAR(255));";

    @Language("sql")
    static final String CREATE_GAMES_PLAYERS_TABLE
            = "CREATE TABLE IF NOT EXISTS " + TableNames.GAME_PLAYERS + "("
            + "email VARCHAR(255) PRIMARY KEY,"
            + "invited BOOLEAN);";

    private CreationQueries() {
        //Utility-classes shouldn't be instantiated
    }
}