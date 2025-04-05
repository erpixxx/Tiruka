package dev.erpix.tiruka.command.console.model.argument;

/**
 * <p>Represents a type for command arguments that can be parsed from a string input.</p>
 * <p>
 * This interface defines the contract for argument types used in command nodes.
 * It allows parsing a string input into a specific type and retrieving the type class.
 * </p>
 *
 * @param <T> The type of the argument (e.g., Integer, String, etc.).
 */
public interface ArgumentType<T> {

    /**
     * <p>Parses the given string input into the specific argument type.</p>
     *
     * @param input The string input to parse.
     * @return The parsed argument of type {@code T}.
     * @throws ParsingException If the input is invalid and cannot be parsed into the expected type.
     */
    T parse(String input) throws ParsingException;

    /**
     * Returns the {@link Class} type of the argument.
     *
     * @return The {@link Class} object representing the type {@code T}.
     */
    Class<T> getType();

}
