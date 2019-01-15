package com.csselect.game;

import com.csselect.user.Player;

public abstract class Gamemode {

    public abstract Round createRound(Player player, int number);
}
