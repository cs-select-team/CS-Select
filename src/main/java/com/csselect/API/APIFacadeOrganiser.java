package com.csselect.API;

import com.csselect.game.Game;
import com.csselect.game.gamecreation.patterns.Pattern;
import com.csselect.user.Organiser;

import java.util.LinkedList;
import java.util.List;

public class APIFacadeOrganiser extends APIFacadeUser {
    private Organiser organiser;
    public void register(String email, String password, String param3) {

    }

    public Organiser getOrganiser() {
        return organiser;
    }

    public List<Game> getActiveGames() {
        return new LinkedList<>();
    }

    public List<Game> getTerminatedGames() {
        return new LinkedList<>();
    }

    public void setGameOption(String option, String data ) {
        
    }

    public void savePattern(String title) {

    }

    public void loadPattern(Pattern pattern) {

    }

    public boolean createGame() {
        return false;
    }

    public void invitePlayer(String playerEmail, int gameId) {

    }

    public void terminateGame(int gameId) {

    }

    public void deleteGame(int gameId) {

    }

}
