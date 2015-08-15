package com.epam.tour.resource;

import java.util.ResourceBundle;

/**
 * The enum Path manager.
 */
public enum PathManager {
    /**
     * The INSTANCE.
     */
    INSTANCE;

    /**
     * The constant BUNDLE_NAME.
     */
    private static final String BUNDLE_NAME = "path";
    /**
     * The Bundle.
     */
    private final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);

    /**
     * Gets string.
     *
     * @param key the key
     * @return the string
     */
    public synchronized String getString(String key) {
        return bundle.getString(key);
    }
}
