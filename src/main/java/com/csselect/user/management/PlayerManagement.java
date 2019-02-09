package com.csselect.user.management;

import com.csselect.Injector;
import com.csselect.database.DatabaseAdapter;
import com.csselect.user.Player;
import com.csselect.user.management.safety.Encrypter;

/**
 * {@link UserManagement} class for {@link Player}
 */
public final class PlayerManagement extends UserManagement {
    
    @Override
    public Player register(String[] parameters) {
        assert parameters.length == 3;
        String email = parameters[0];
        String password = parameters[1];
        String username = parameters[2];

        String salt = Encrypter.getRandomSalt();
        password += salt;
        String encryptedPassword = Encrypter.encrypt(password);
        Player player = Injector.getInstance().getDatabaseAdapter().createPlayer(email, encryptedPassword, salt, username);
        if (player != null) {
            player.login();
        }
        return player;
    }

    @Override
    public Player login(String email, String password) {
        Player player = Injector.getInstance().getDatabaseAdapter().getPlayer(email);
        if (player == null) {
            return null; //email not found
        }
        String savedEncryptedPassword = Injector.getInstance().getDatabaseAdapter().getPlayerHash(player.getId());
        String salt = Injector.getInstance().getDatabaseAdapter().getPlayerSalt(player.getId());
        String concatenated = password + salt;

        if (Encrypter.compareStringToHash(concatenated, savedEncryptedPassword)) {
            player.login();
            return player;
        } else {
            return null;
        }
    }
}
