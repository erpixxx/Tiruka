package dev.erpix.tiruka.command.console.model;

import dev.erpix.tiruka.command.console.model.argument.ArgumentType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * <p>Represents a command node that expects an argument of a specific type.</p>
 * <p>
 * This class extends {@link CommandNode} and is used for handling command arguments.
 * The argument type is defined generically, allowing flexibility in parsing different
 * data types (e.g., integers, strings, booleans).
 * </p>
 *
 * @param <T> The expected type of the argument.
 */
public class ArgumentCommandNode<T> extends CommandNode {

    private final String name;
    private final ArgumentType<T> type;

    public ArgumentCommandNode(String name, ArgumentType<T> type, String description, Collection<KeywordCommandNode> keywords, Collection<ArgumentCommandNode<?>> arguments, CommandExecutor executor) {
        super(description, keywords, arguments, executor);
        this.name = name;
        this.type = type;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    /**
     * Parses the given input string into the expected type.
     *
     * @param input The string input to be parsed.
     * @return The parsed value of the argument.
     */
    public @NotNull T parse(String input) {
        return type.parse(input);
    }

    /**
     * Returns the argument type.
     *
     * @return The {@link ArgumentType} defining the expected type of this argument.
     */
    public @NotNull ArgumentType<T> getType() {
        return type;
    }

}
