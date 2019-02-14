package com.csselect.utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class to access the resource bundle containing the localisations
 */
public final class Localisation {

    private static final String BUNDLE_NAME = "locale/Locale";

    /**
     * Gets the localised string with the given id in the language with the given langCode
     * @param langCode langCode of the language to get the key from
     * @param key key to get
     * @return localised string
     */
    public static String get(String langCode, String key) {
        return ResourceBundle.getBundle(BUNDLE_NAME, new Locale(langCode)).getString(key);
    }

    private Localisation() {
        //Util class
    }
}
