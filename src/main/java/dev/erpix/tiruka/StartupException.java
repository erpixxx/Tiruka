package dev.erpix.tiruka;

/**
 * <p>
 * Exception thrown when an error occurs during the startup
 * of the application, making it unable to continue the startup process.
 * </p>
 *
 * <p>When this exception is thrown, the application will be shut down.</p>
 */
public class StartupException extends RuntimeException {

    public StartupException(String message) {
        super(message);
        TirukaApp.getInstance().shutdown();
    }

    public StartupException(String message, Throwable cause) {
        super(message, cause);
        TirukaApp.getInstance().shutdown();
    }

}
