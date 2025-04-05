package dev.erpix.tiruka.command.console.model;

/**
 * <p>Functional interface for executing a command with a given context.</p>
 * <p>
 * Implementations of this interface define what happens when a command is executed.
 * The provided {@link CommandContext} contains parsed arguments and other necessary
 * execution details.
 * </p>
 */
@FunctionalInterface
public interface CommandExecutor {

    /**
     * Executes the command with the given execution context.
     *
     * @param ctx The context of the executed command.
     */
    void execute(CommandContext ctx);

}
