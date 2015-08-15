package com.epam.tour.notification.creator;

import com.epam.tour.notification.entity.Notification;
import com.epam.tour.notification.entity.Notification.Type;
import com.epam.tour.resource.MessageManager;

import java.util.Locale;

/**
 * The type Notification creator.
 */
public class NotificationCreator {

    /**
     * Instantiates a new Notification creator.
     */
    private NotificationCreator() {
    }

    /**
     * Create from property.
     *
     * @param propertyName the property name
     * @param type the type
     * @param locale the locale
     * @return the notification
     */
    public static Notification createFromProperty(String propertyName, Type type, Locale locale) {

        Notification notification = new Notification("????? " + propertyName + "??????", type);

        String message = MessageManager.INSTANCE.getMessage(propertyName, locale);
        if (message != null) {
            notification = new Notification(message, type);
        }

        return notification;
    }

    /**
     * Create from property.
     *
     * @param propertyName the property name
     * @param locale the locale
     * @return the notification
     */
    public static Notification createFromProperty(String propertyName, Locale locale) {

        return createFromProperty(propertyName, Type.INFO, locale);
    }
}
