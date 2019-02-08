package com.csselect.user.management;

import com.csselect.Injector;
import com.csselect.configuration.Configuration;
import com.csselect.database.DatabaseAdapter;
import com.csselect.user.Organiser;
import com.csselect.user.management.safety.Encrypter;

/**
 * {@link UserManagement} class for {@link Organiser}
 */
public final class OrganiserManagement extends UserManagement {
    private static final DatabaseAdapter DATABASE_ADAPTER = Injector.getInstance().getDatabaseAdapter();

    @Override
    public Organiser register(String[] parameters) {
        assert parameters.length == 3;
        Configuration config = Injector.getInstance().getConfiguration();
        String email = parameters[0];
        String password = parameters[1];
        String globalPassword = parameters[2];

        if (config.getOrganiserPassword().equals(globalPassword)) {
            String salt = Encrypter.getRandomSalt();
            String encryptedPassword = Encrypter.encrypt(password, salt);
            Organiser organiser = DATABASE_ADAPTER.createOrganiser(email, encryptedPassword, salt);
            if (organiser != null) {
                organiser.login();
            }
            return organiser;
        } else {
            return null;
        }
    }

    @Override
    public Organiser login(String email, String password) {
        Organiser organiser = DATABASE_ADAPTER.getOrganiser(email);
        if (organiser == null) {
            return null; //email not found
        }
        String savedEncryptedPassword = DATABASE_ADAPTER.getOrganiserHash(organiser.getId());
        String salt = DATABASE_ADAPTER.getOrganiserSalt(organiser.getId());
        if (Encrypter.compareStringToHash(password, salt, savedEncryptedPassword)) {
            organiser.login();
            return organiser;
        } else {
            return null;
        }
    }
}
