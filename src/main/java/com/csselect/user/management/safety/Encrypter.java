package com.csselect.user.management.safety;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * This class is there to encrypt Strings using an specified algorithm and provide a method to get an
 * randomised String ("Salt") for safety measures.
 */
public final class Encrypter {
    private static final int MIN_SALT_LENGTH = 40;
    private static final int MAX_SALT_LENGTH = 70;

    /**
     * Help classes should not have a public constructor
     */
    private Encrypter() {

    }

    /**
     * Generates a random alphanumeric String with a specified length
     * @return Random String (used as salt)
     */
    public static String getRandomSalt() {
        return RandomStringUtils.randomAlphanumeric(MIN_SALT_LENGTH, MAX_SALT_LENGTH);
    }

    /**
     * Encrypts a String using sha256
     * @param word String to encrypt
     * @param salt to use when encrypting
     * @return Encrypted String
     */
    public static String encrypt(String word, String salt) {
        return DigestUtils.sha256Hex(word + salt);
    }

    /**
     * Compares if a String equals a hash. Therefore, we encrypt the String and compare it to the Hash
     * @param word String which is not hashed
     * @param salt salt to use while encrypting
     * @param hash Hash to compare String to
     * @return If hashed value of word equals Hash
     */
    public static boolean compareStringToHash(String word, String salt, String hash) {
        String encrypted = encrypt(word, salt);
        return encrypted.equals(hash);
    }
}
