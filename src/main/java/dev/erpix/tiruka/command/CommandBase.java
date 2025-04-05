package dev.erpix.tiruka.command;

/**
 * Base interface for all commands.
 *
 * @param <T> the type of the command
 */
public interface CommandBase<T> {

    /**
     * Builds the command.
     *
     * @return the built command
     */
    T build();

}
