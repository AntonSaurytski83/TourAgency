package com.epam.tour.resource;

import javax.servlet.ServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The enum Locale manager.
 */
public enum LocaleManager {
    /**
     * The INSTANCE.
     */
    INSTANCE;

    /**
     * The constant REQUEST_PARAMETER.
     */
    public static final String REQUEST_PARAMETER = "lang";

    /**
     * The Default locale.
     */
    private Locale defaultLocale = Locale.forLanguageTag("ru");

    /**
     * The Locales.
     */
    private Map<String, Locale> locales = Collections.synchronizedMap(new HashMap<String, Locale>());

    {
        locales.put("English", Locale.forLanguageTag("en"));
        locales.put("Русский", Locale.forLanguageTag("ru"));
    }

    /**
     * Gets default locale.
     *
     * @return the default locale
     */
    public Locale getDefaultLocale() {
        return defaultLocale;
    }

    /**
     * Gets locales.
     *
     * @return the locales
     */
    public Map<String, Locale> getLocales() {
        return Collections.unmodifiableMap(locales);
    }

    /**
     * Resolve locale.
     *
     * @param request the request
     * @return the locale
     */
    public Locale resolveLocale(ServletRequest request) {
        String code = request.getParameter(REQUEST_PARAMETER);
        if (code != null) {
            for (Entry<String, Locale> stringLocaleEntry : locales.entrySet()) {
                Locale locale = stringLocaleEntry.getValue();
                if (locale.toLanguageTag().equalsIgnoreCase(code)) {
                    return locale;
                }
            }
        }
        return defaultLocale;
    }
}
