package com.csselect.game.gamecreation;

import com.csselect.game.gamecreation.patterns.GameOptions;
import com.csselect.game.gamecreation.patterns.Pattern;
import com.csselect.user.Organiser;

public class GameCreator {
    private Organiser organiser;
    private GameOptions gameOptions;

    public GameOptions getGameOptions() {
        return new GameOptions();
    }

    public void loadPattern(Pattern pattern) {
        this.gameOptions = pattern.getGameOptions();
    }

    public Pattern makePattern(String title) {
        return new Pattern(gameOptions, title);
    }

    public void doCreate() {

    }
}
