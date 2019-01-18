package com.csselect.user.management;

import com.csselect.Injector;
import com.csselect.database.DatabaseAdapter;
import com.csselect.user.Player;
import com.csselect.user.management.safety.Encrypter;

/**
 * {@link UserManagement} class for {@link Player}
 */
public class PlayerManagement extends UserManagement {
    private final static DatabaseAdapter DATABASE_ADAPTER = Injector.getInjector().getInstance(DatabaseAdapter.class);

    @Override
    public Player register(String[] parameters) {
        assert parameters.length == 3;
        String email = parameters[0];
        String password = parameters[1];
        String username = parameters[2];

        String salt = Encrypter.getRandomSalt();
        password += salt;
        String encryptedPassword = Encrypter.encrypt(password);
        Player player = DATABASE_ADAPTER.createPlayer(email, encryptedPassword, salt, username);
        player.login();
        return player;
    }

    @Override
    public Player login(String email, String password) {
        Player player = DATABASE_ADAPTER.getPlayer(email);
        String savedEncryptedPassword = DATABASE_ADAPTER.getPlayerHash(player.getId());
        String salt = DATABASE_ADAPTER.getPlayerSalt(player.getId());
        password += salt;
        String encryptedPassword = Encrypter.encrypt(password);
        if (encryptedPassword.equals(savedEncryptedPassword)) {
            player.login();
            return player;
        } else {
            return null;
        }
    }
}
