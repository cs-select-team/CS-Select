package com.csselect.user.management;

import com.csselect.email.EmailSender;
import com.csselect.inject.Injector;
import com.csselect.user.User;
import com.csselect.user.management.safety.Encrypter;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * Abstract Management class for {@link User}s
 */
public abstract class UserManagement {

    /**
     * Error String if Email is in use (registration)
     */
    public static final String EMAIL_IN_USE = "Email is already in use";

    private static final int MIN_TEMP_PASSWORD_LENGTH = 16;
    private static final int MAX_TEMP_PASSWORD_LENGTH = 20;
    private static final int MIN_SALT_LENGTH = 40;
    private static final int MAX_SALT_LENGTH = 70;
    private static final String REGISTRATION_CONFIRMATION_HEADER = "CS:Select Registration";
    private static final String REGISTRATION_CONFIRMATION_MESSAGE = String.format(
            "Welcome to CS:Select, your temporary password is '%s'! Please login with your temporary password on the"
            + " <a href=%s>CS:Select homepage</a> and change it to an own, safe one as fast as possible!",
            "%s", Injector.getInstance().getConfiguration().getCSSelectURL());


    /**
     * Resets the password of the {@link User} with the given email address to a temporary one and sends a mail
     * containing the temporary password to his email address
     * @param email email address of the user
     */
    public void resetPassword(String email) {
        String tempPassword = this.createTemporaryPassword();
        String salt = RandomStringUtils.randomAlphanumeric(MIN_SALT_LENGTH, MAX_SALT_LENGTH);
        String encryptedPasswort = Encrypter.encrypt(tempPassword, salt);
        User user = getUser(email);
        user.resetPassword(tempPassword, encryptedPasswort, salt);
    }

    /**
     * Gets a random temporary password
     * @return temp password
     */
    String createTemporaryPassword() {
        return RandomStringUtils.randomAlphanumeric(MIN_TEMP_PASSWORD_LENGTH, MAX_TEMP_PASSWORD_LENGTH);
    }

    /**
     * Sends a registration mail to the user with the given email and sends his temporary password
     * @param email email to send to
     * @param tempPassword password to include
     */
    void sendConfirmationMail(String email, String tempPassword) {
        EmailSender.sendEmail(email, REGISTRATION_CONFIRMATION_HEADER,
                String.format(REGISTRATION_CONFIRMATION_MESSAGE, tempPassword));
    }

    /**
     * Gets a {@link User} of the type the extension of {@link UserManagement} represents by email
     * @param email email of the user
     * @return the user
     */
    abstract User getUser(String email);
}
