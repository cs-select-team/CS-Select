package com.csselect.user.management;

import com.csselect.user.User;
import com.csselect.user.management.safety.Encrypter;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * Abstract Management class for {@link User}s
 */
public abstract class UserManagement {

    private static final int MIN_TEMP_PASSWORD_LENGTH = 16;
    private static final int MAX_TEMP_PASSWORD_LENGTH = 20;
    private static final int MIN_SALT_LENGTH = 40;
    private static final int MAX_SALT_LENGTH = 70;

    /**
     * Resets the password of the {@link User} with the given email address to a temporary one and sends a mail
     * containing the temporary password to his email address
     * @param email email address of the user
     */
    public void resetPassword(String email) {
        String tempPassword = RandomStringUtils.randomAlphanumeric(MIN_TEMP_PASSWORD_LENGTH, MAX_TEMP_PASSWORD_LENGTH);
        String salt = RandomStringUtils.randomAlphanumeric(MIN_SALT_LENGTH, MAX_SALT_LENGTH);
        String encryptedPasswort = Encrypter.encrypt(tempPassword, salt);
        User user = getUser(email);
        user.resetPassword(tempPassword, encryptedPasswort, salt);
    }

    /**
     * Gets a {@link User} of the type the extension of {@link UserManagement} represents by email
     * @param email email of the user
     * @return the user
     */
    abstract User getUser(String email);
}
