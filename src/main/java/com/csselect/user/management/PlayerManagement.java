package com.csselect.user.management;

import com.csselect.inject.Injector;
import com.csselect.user.Player;
import com.csselect.user.User;
import com.csselect.user.management.safety.Encrypter;

/**
 * Management class for registration and login of {@link Player}
 */
public final class PlayerManagement extends UserManagement {

    /**
     * Error string if username is already in use
     */
    public static final String USERNAME_IN_USE = "Username is already in use";
    /**
     * Register a player with parameters email, password and username
     * @param email players email
     * @param username players username
     * @return {@link Player} object
     * @throws IllegalArgumentException if email or username were already in use
     */
    public Player register(String email, String username) throws IllegalArgumentException {
        String password = this.createTemporaryPassword();
        String salt = Encrypter.getRandomSalt();
        String encryptedPassword = Encrypter.encrypt(password, salt);
        Player player = Injector.getInstance().getDatabaseAdapter()
                .createPlayer(email, encryptedPassword, salt, username);
        if (player == null) {
            throw new IllegalArgumentException(EMAIL_IN_USE);
        }
        sendConfirmationMail(email, password);
        return player;
    }

    /**
     * To login an {@link Player}, we need to know the email and the password he typed in. If the
     * password is correct, return a player object, return null otherwise.
     * @param email Email of the player
     * @param password Password he typed in
     * @return {@link Player} object or null
     */
    public Player login(String email, String password) {
        Player player = Injector.getInstance().getDatabaseAdapter().getPlayer(email);
        if (player == null) {
            return null; //email not found
        }
        String savedEncryptedPassword = Injector.getInstance().getDatabaseAdapter().getPlayerHash(player.getId());
        String salt = Injector.getInstance().getDatabaseAdapter().getPlayerSalt(player.getId());
        if (Encrypter.compareStringToHash(password, salt, savedEncryptedPassword)) {
            player.login();
            return player;
        } else {
            return null;
        }
    }

    @Override
    User getUser(String email) {
        return Injector.getInstance().getDatabaseAdapter().getPlayer(email);
    }
}
