package com.csselect.user.management;

import com.csselect.Injector;
import com.csselect.database.DatabaseAdapter;
import com.csselect.user.Player;
import com.csselect.user.management.safety.Encrypter;

/**
 * {@link UserManagement} class for {@link Player}
 */
public final class PlayerManagement extends UserManagement {
    private static final DatabaseAdapter DATABASE_ADAPTER = Injector.getInstance().getDatabaseAdapter();

    @Override
    public Player register(String[] parameters) {
        assert parameters.length == 3;
        String email = parameters[0];
        String password = parameters[1];
        String username = parameters[2];
        String salt = Encrypter.getRandomSalt();
        String encryptedPassword = Encrypter.encrypt(password, salt);
        Player player = DATABASE_ADAPTER.createPlayer(email, encryptedPassword, salt, username);
        if (player != null) {
            player.login();
        }
        return player;
    }

    @Override
    public Player login(String email, String password) {
        Player player = DATABASE_ADAPTER.getPlayer(email);
        if (player == null) {
            return null; //email not found
        }
        String savedEncryptedPassword = DATABASE_ADAPTER.getPlayerHash(player.getId());
        String salt = DATABASE_ADAPTER.getPlayerSalt(player.getId());
        if (Encrypter.compareStringToHash(password, salt, savedEncryptedPassword)) {
            player.login();
            return player;
        } else {
            return null;
        }
    }
}
