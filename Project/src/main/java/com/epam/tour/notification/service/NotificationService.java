package com.epam.tour.notification.service;

import com.epam.tour.notification.entity.Notification;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Notification service.
 */
public class NotificationService {

    /**
     * The constant SERVICE_VAR.
     */
    private static final String SERVICE_VAR = "_notifications";

    /**
     * Instantiates a new Notification service.
     */
    private NotificationService() {
    }


    /**
     * Push void.
     *
     * @param session the session
     * @param notification the notification
     */
    public static void push(HttpSession session, Notification notification) {
        List<Notification> queue = getNotifications(session);
        queue.add(notification);
    }

    /**
     * Gets notifications.
     *
     * @param session the session
     * @return the notifications
     */
    public static List<Notification> getNotifications(HttpSession session) {

        Object ob = session.getAttribute(SERVICE_VAR);
        List list;
        if (ob != null) {
            list = (List) ob;
        } else {
            list = new LinkedList<>();
            session.setAttribute(SERVICE_VAR, list);
        }

        return list;
    }

    /**
     * Have notifications.
     *
     * @param session the session
     * @return the boolean
     */
    public static boolean haveNotifications(HttpSession session) {
        return !getNotifications(session).isEmpty();
    }

}
