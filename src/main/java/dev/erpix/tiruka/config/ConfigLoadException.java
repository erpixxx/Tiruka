package dev.erpix.tiruka.config;

import dev.erpix.tiruka.StartupException;

/**
 * Exception thrown when a configuration file fails to load.
 */
public class ConfigLoadException extends StartupException {

    /**
     * Constructs a new {@code ConfigLoadException} with the specified detail message.
     *
     * @param message the detail message describing the cause of the exception.
     */
    public ConfigLoadException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code ConfigLoadException} with the specified detail message
     * and cause.
     *
     * @param message the detail message describing the cause of the exception.
     * @param cause the cause of the exception (a {@code Throwable} that caused this exception).
     */
    public ConfigLoadException(String message, Throwable cause) {
        super(message, cause);
    }

}
