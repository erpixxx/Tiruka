package dev.erpix.tiruka.storage;

import dev.erpix.tiruka.StartupException;

/**
 * Exception thrown when an error occurs during the initialization of the storage.
 */
public class StorageInitializationException extends StartupException {

    public StorageInitializationException(String message) {
        super(message);
    }

    public StorageInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

}
