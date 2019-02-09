package com.csselect.user.management;

import com.csselect.Injector;
import com.csselect.configuration.Configuration;
import com.csselect.user.Organiser;
import com.csselect.user.management.safety.Encrypter;

/**
 * {@link UserManagement} class for {@link Organiser}
 */
public final class OrganiserManagement extends UserManagement {
    
    @Override
    public Organiser register(String[] parameters) {
        assert parameters.length == 3;
        Configuration config = Injector.getInstance().getConfiguration();
        String email = parameters[0];
        String password = parameters[1];
        String globalPassword = parameters[2];

        if (config.getOrganiserPassword().equals(globalPassword)) {
            String salt = Encrypter.getRandomSalt();
            password += salt;
            String encryptedPassword = Encrypter.encrypt(password);
            Organiser organiser = Injector.getInstance().getDatabaseAdapter().createOrganiser(email, encryptedPassword, salt);
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
        Organiser organiser = Injector.getInstance().getDatabaseAdapter().getOrganiser(email);
        if (organiser == null) {
            return null; //email not found
        }
        String savedEncryptedPassword = Injector.getInstance().getDatabaseAdapter().getOrganiserHash(organiser.getId());
        String salt = Injector.getInstance().getDatabaseAdapter().getOrganiserSalt(organiser.getId());
        String concatenated = password + salt;

        if (Encrypter.compareStringToHash(concatenated, savedEncryptedPassword)) {
            organiser.login();
            return organiser;
        } else {
            return null;
        }
    }
}
