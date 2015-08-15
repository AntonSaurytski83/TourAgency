package com.epam.tour.resource;

import java.util.ResourceBundle;

/**
 * The enum DB configuration manager.
 */
public enum DBConfigurationManager {
    /**
     * The INSTANCE.
     */
    INSTANCE;


    /**
     * The constant DATABASE_CONNECTION_URL.
     */
    public static final String DATABASE_CONNECTION_URL = "url";
    /**
     * The constant DATABASE_USERNAME.
     */
    public static final String DATABASE_USERNAME = "username";
    /**
     * The constant DATABASE_PASSWORD.
     */
    public static final String DATABASE_PASSWORD = "password";
    /**
     * The constant BUNDLE_NAME.
     */
    private static final String BUNDLE_NAME = "database";
    /**
     * The Bundle.
     */
    private transient ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);

    /**
     * Gets string.
     *
     * @param key the key
     * @return the string
     */
    public String getString(String key) {
        return bundle.getString(key);
    }
}
