package com.csselect.user;

import com.csselect.database.OrganiserAdapter;
import com.csselect.game.Game;
import com.csselect.game.gamecreation.GameCreator;
import com.csselect.game.gamecreation.patterns.Pattern;

import java.util.Collection;
import java.util.HashSet;

public class Organiser extends User {
    private OrganiserAdapter databaseAdapter;
    private GameCreator gameBuilder;
    private Collection<Game> games;

    Organiser(OrganiserAdapter databaseAdapter) {
        this.databaseAdapter = databaseAdapter;
        this.gameBuilder = new GameCreator();
        this.games = new HashSet<>();
    }

    public boolean register(String[] args) {
        assert args.length == 3;
        return true;
    }

    public void savePattern(String title) {

    }

    public Collection<Pattern> getPatterns() {
        return databaseAdapter.getPatterns();
    }

    public void loadPattern(Pattern pattern) {

        databaseAdapter.addPattern(pattern);
    }

    public void createGame() {
        gameBuilder.doCreate();
    }

    public void invitePlayer(String playerEmail, Game game) {

    }

    public void terminateGame(int gameId) {
        games.forEach((Game element) -> {
            if (element.getId() == gameId) {
                element.setTerminated(true);
            }
        });
    }

    public void deleteGame(int gameId) {
        games.removeIf((Game element) -> element.isTerminated() && element.getId() == gameId);
    }
}
