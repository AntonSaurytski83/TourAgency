package com.epam.tour.exception;

/**
 * The type DAO technical exception.
 */
public class DAOTechnicalException extends Exception {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -3889786803972718817L;

    /**
     * Instantiates a new DAO technical exception.
     */
    public DAOTechnicalException() {
    }

    /**
     * Instantiates a new DAO technical exception.
     *
     * @param message the message
     */
    public DAOTechnicalException(String message) {
        super(message);
    }

    /**
     * Instantiates a new DAO technical exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public DAOTechnicalException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new DAO technical exception.
     *
     * @param cause the cause
     */
    public DAOTechnicalException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new DAO technical exception.
     *
     * @param message the message
     * @param cause the cause
     * @param enableSuppression the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public DAOTechnicalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
