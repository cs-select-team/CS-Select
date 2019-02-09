package com.csselect.user.management;

import com.csselect.Injector;
import com.csselect.configuration.Configuration;
import com.csselect.user.Organiser;
import com.csselect.user.management.safety.Encrypter;

/**
 * Management class for registration and login of {@link Organiser}
 */
public final class OrganiserManagement {

    /**
     * Register an organiser with 3 parameters email, password and global password
     * @param parameters Registration parameters
     * @return {@link Organiser} object
     */
    public Organiser register(String[] parameters) {
        assert parameters.length == 3;
        Configuration config = Injector.getInstance().getConfiguration();
        String email = parameters[0];
        String password = parameters[1];
        String globalPassword = parameters[2];

        if (config.getOrganiserPassword().equals(globalPassword)) {
            String salt = Encrypter.getRandomSalt();
            String encryptedPassword = Encrypter.encrypt(password, salt);
            Organiser organiser = Injector.getInstance().getDatabaseAdapter().createOrganiser(email, encryptedPassword, salt);
            if (organiser != null) {
                organiser.login();
            }
            return organiser;
        } else {
            return null;
        }
    }

    /**
     * To login an {@link Organiser}, we need to know the email and the password he typed in. If the
     * password is correct, return an organiser object, return null otherwise.
     * @param email Email of the organiser
     * @param password Password he typed in
     * @return {@link Organiser} object or null
     */
    public Organiser login(String email, String password) {
        Organiser organiser = Injector.getInstance().getDatabaseAdapter().getOrganiser(email);
        if (organiser == null) {
            return null; //email not found
        }
        String savedEncryptedPassword = Injector.getInstance().getDatabaseAdapter().getOrganiserHash(organiser.getId());
        String salt = Injector.getInstance().getDatabaseAdapter().getOrganiserSalt(organiser.getId());
        if (Encrypter.compareStringToHash(password, salt, savedEncryptedPassword)) {
            organiser.login();
            return organiser;
        } else {
            return null;
        }
    }
}
