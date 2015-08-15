package com.epam.tour.exception;

/**
 * The type DAO logical exception.
 */
public class DAOLogicalException extends Exception {
    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -834302877185067546L;

    /**
     * Instantiates a new DAO logical exception.
     */
    public DAOLogicalException() {
    }

    /**
     * Instantiates a new DAO logical exception.
     *
     * @param message the message
     */
    public DAOLogicalException(String message) {
        super(message);
    }

    /**
     * Instantiates a new DAO logical exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public DAOLogicalException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new DAO logical exception.
     *
     * @param cause the cause
     */
    public DAOLogicalException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new DAO logical exception.
     *
     * @param message the message
     * @param cause the cause
     * @param enableSuppression the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public DAOLogicalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
