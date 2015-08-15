package com.epam.tour.notification.entity;

/**
 * The type Notification.
 */
public class Notification  {


    /**
     * The Message.
     */
    private String message;
    /**
     * The Type.
     */
    private Type type;

    /**
     * Instantiates a new Notification.
     *
     * @param message the message
     * @param type the type
     */
    public Notification(String message, Type type) {
        this.message = message;
        this.type = type;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * The enum Type.
     */
    public enum Type {
        /**
         * The INFO.
         */
        INFO, /**
         * The WARNING.
         */
        WARNING, /**
         * The ERROR.
         */
        ERROR
    }
}
