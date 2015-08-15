package com.epam.tour.exception;

/**
 * The type Build exception.
 */
public class BuildException extends Exception {
    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -4151475321740999881L;

    /**
     * Instantiates a new Build exception.
     */
    public BuildException() {
    }

    /**
     * Instantiates a new Build exception.
     *
     * @param message the message
     */
    public BuildException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Build exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public BuildException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Build exception.
     *
     * @param cause the cause
     */
    public BuildException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Build exception.
     *
     * @param message the message
     * @param cause the cause
     * @param enableSuppression the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public BuildException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
