package dev.erpix.tiruka.command.console.model;

import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * <p>Stores the execution context of a command, including parsed arguments.</p>
 * <p>
 * This class acts as a container for arguments that were provided when executing
 * a command. Arguments are stored as a key-value map and can be retrieved in a type-safe manner.
 * </p>
 */
public class CommandContext {

    private final Map<String, Object> arguments;

    public CommandContext(Map<String, Object> arguments) {
        this.arguments = arguments;
    }

    /**
     * Retrieves an argument by name and casts it to the expected type.
     *
     * @param name The name of the argument.
     * @param type The expected class type of the argument.
     * @param <T>  The type parameter corresponding to the expected type.
     * @return The argument value cast to the specified type, or {@code null} if not found.
     * @throws ClassCastException if the argument cannot be cast to the specified type.
     */
    public @Nullable <T> T getArgument(String name, Class<T> type) {
        return type.cast(arguments.get(name));
    }

}
