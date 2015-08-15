package com.epam.tour.resource;

import org.apache.log4j.Logger;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * The enum Message manager.
 */
public enum MessageManager {
    /**
     * The INSTANCE.
     */
    INSTANCE;

    /**
     * The constant BUNDLE_NAME.
     */
    private static final String BUNDLE_NAME = "message";
    /**
     * The Bundle.
     */
    private ResourceBundle bundle = null;
    /**
     * The Last locale.
     */
    private Locale lastLocale = null;
    /**
     * The Locale manager.
     */
    private final LocaleManager localeManager = LocaleManager.INSTANCE;

    /**
     * Gets message.
     *
     * @param key the key
     * @param locale the locale
     * @return the message
     */
    public synchronized String getMessage(String key, Locale locale) {

        try {
            if (lastLocale != null && lastLocale.equals(locale)) {
                return bundle.getString(key);
            } else {
                Logger.getRootLogger().debug("Message manager reinitialized bundle");
                bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
                lastLocale = locale;
            }
        } catch (MissingResourceException ignored) {
            return "???? " + key + " ????";
        }
        return bundle.getString(key);
    }
}