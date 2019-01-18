package com.csselect.user.management.safety;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class is there to encrypt Strings using an specified algorithm and provide a method to get an
 * randomised String ("Salt") for safety measures.
 */
public class Encrypter {
    private final static String ALGORITHM = "SHA-256";
    private final static int MIN_SALT_LENGTH = 40;
    private final static int MAX_SALT_LENGTH = 70;

    /**
     * Generates a random alphanumeric String with a specified length
     * @return Random String (used as salt)
     */
    public static String getRandomSalt() {
        return RandomStringUtils.randomAlphanumeric(MIN_SALT_LENGTH, MAX_SALT_LENGTH);
    }

    /**
     * Encrypts a String using an specified algorithm
     * @param word String to encrypt
     * @return Encrypted String
     */
    public static String encrypt(String word) {
        MessageDigest encryption;
        try {
            encryption = MessageDigest.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        encryption.update(word.getBytes());
        return new String(encryption.digest());
    }
}
