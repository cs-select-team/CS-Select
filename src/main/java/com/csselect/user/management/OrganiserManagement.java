package com.csselect.user.management;

import com.csselect.Injector;
import com.csselect.configuration.Configuration;
import com.csselect.database.DatabaseAdapter;
import com.csselect.user.Organiser;
import com.csselect.user.management.safety.Encrypter;

/**
 * {@link UserManagement} class for {@link Organiser}
 */
public class OrganiserManagement extends UserManagement {
    private static final DatabaseAdapter DATABASE_ADAPTER = Injector.getInjector().getInstance(DatabaseAdapter.class);

    @Override
    public Organiser register(String[] parameters) {
        assert parameters.length == 3;
        Configuration config = Injector.getInjector().getInstance(Configuration.class);
        String email = parameters[0];
        String password = parameters[1];
        String globalPassword = parameters[2];

        if (config.getOrganiserPassword().equals(globalPassword)) {
            String salt = Encrypter.getRandomSalt();
            password += salt;
            String encryptedPassword = Encrypter.encrypt(password);
            Organiser organiser = DATABASE_ADAPTER.createOrganiser(email, encryptedPassword, salt);
            organiser.login();
            return organiser;
        } else {
            return null;
        }
    }

    @Override
    public Organiser login(String email, String password) {
        Organiser organiser = DATABASE_ADAPTER.getOrganiser(email);
        String savedEncryptedPassword = DATABASE_ADAPTER.getOrganiserHash(organiser.getId());
        String salt = DATABASE_ADAPTER.getOrganiserSalt(organiser.getId());
        String concatenated = password + salt;

        String encryptedPassword = Encrypter.encrypt(concatenated);
        if (encryptedPassword.equals(savedEncryptedPassword)) {
            organiser.login();
            return organiser;
        } else {
            return null;
        }
    }
}
