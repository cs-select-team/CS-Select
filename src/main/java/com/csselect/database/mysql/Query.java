package com.csselect.database.mysql;

import org.intellij.lang.annotations.Language;

/**
 * Class containing queries for the Database
 */
final class Query {

    @Language("sql")
    static final String CREATE_PLAYER_TABLE
            = "CREATE TABLE IF NOT EXISTS players("
            + "id INT AUTO_INCREMENT PRIMARY KEY,"
            + "hash VARCHAR(255),"
            + "salt VARCHAR(255),"
            + "email VARCHAR(255),"
            + "language VARCHAR(40),"
            + "username VARCHAR(40));";

    @Language("sql")
    static final String CREATE_ORGANISER_TABLE
            = "CREATE TABLE IF NOT EXISTS organisers("
            + "id INT AUTO_INCREMENT PRIMARY KEY,"
            + "hash VARCHAR(255),"
            + "salt VARCHAR(255),"
            + "email VARCHAR(255),"
            + "language VARCHAR(40));";

    @Language("sql")
    static final String CREATE_GAME_TABLE
            = "CREATE TABLE IF NOT EXISTS games("
            + "id INT AUTO_INCREMENT PRIMARY KEY,"
            + "organiserId INT,"
            + "INDEX organiser_ind (organiserId),"
            + "FOREIGN KEY (organiserId) REFERENCES organisers(id) ON DELETE CASCADE,"
            + "title VARCHAR(40),"
            + "description VARCHAR(255),"
            + "databasename VARCHAR(40),"
            + "isTerminated BOOLEAN,"
            + "dataset VARCHAR(255),"
            + "termination VARCHAR(255),"
            + "gamemode VARCHAR(40));";

    @Language("sql")
    static final String CREATE_PATTERN_TABLE
            = "CREATE TABLE IF NOT EXISTS patterns("
            + "id INT AUTO_INCREMENT PRIMARY KEY,"
            + "organiserId INT,"
            + "INDEX organiser_ind (organiserId),"
            + "FOREIGN KEY (organiserId) REFERENCES organisers(id) ON DELETE CASCADE,"
            + "title VARCHAR(40),"
            + "gameTitle VARCHAR(40),"
            + "description VARCHAR(255),"
            + "databasename VARCHAR(40),"
            + "termination VARCHAR(255),"
            + "gamemode VARCHAR(40),"
            + "invitedPlayers VARCHAR(255));";

    @Language("sql")
    static final String CREATE_PLAYERSTATS_TABLE
            = "CREATE TABLE IF NOT EXISTS playerstats("
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
            = "CREATE TABLE IF NOT EXISTS rounds("
            + "id INT AUTO_INCREMENT PRIMARY KEY,"
            + "player_id INT,"
            + "time DATETIME,"
            + "quality DOUBLE,"
            + "points INT,"
            + "uselessFeatures VARCHAR(255),"
            + "chosenFeatures VARCHAR(255),"
            + "shownFeatures VARCHAR(255));";

    @Language("sql")
    static final String CREATE_GAMES_PLAYERS_TABLE
            = "CREATE TABLE IF NOT EXISTS players("
            + "email VARCHAR(255) PRIMARY KEY,"
            + "invited BOOLEAN);";

    private Query() {
        //Utility-classes shouldn't be instantiated
    }
}
