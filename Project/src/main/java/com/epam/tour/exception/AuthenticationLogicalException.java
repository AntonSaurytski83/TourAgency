package com.epam.tour.exception;

/**
 * The type Authentication logical exception.
 */
public class AuthenticationLogicalException extends Exception {
    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 8269126317479403709L;

    /**
     * Instantiates a new Authentication logical exception.
     */
    public AuthenticationLogicalException() {
    }

    /**
     * Instantiates a new Authentication logical exception.
     *
     * @param message the message
     */
    public AuthenticationLogicalException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Authentication logical exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public AuthenticationLogicalException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Authentication logical exception.
     *
     * @param cause the cause
     */
    public AuthenticationLogicalException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Authentication logical exception.
     *
     * @param message the message
     * @param cause the cause
     * @param enableSuppression the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public AuthenticationLogicalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
