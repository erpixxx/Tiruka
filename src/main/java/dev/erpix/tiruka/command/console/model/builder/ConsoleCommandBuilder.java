package dev.erpix.tiruka.command.console.model.builder;

import dev.erpix.tiruka.command.console.model.argument.ArgumentType;

/**
 * <p>A utility builder class for creating command nodes in the console command structure.</p>
 * <p>
 * This class provides static methods to create instances of {@link KeywordNodeBuilder} and
 * {@link ArgumentNodeBuilder}, which can then be used to construct a tree of commands and arguments.
 * It simplifies the process of building command structures with keywords and arguments.
 * </p>
 *
 */
public class ConsoleCommandBuilder {

    /**
     * Creates a builder for constructing a keyword command node.
     *
     * @param keyword The keyword for the command.
     * @return A {@link KeywordNodeBuilder} for constructing a keyword command node.
     */
    public static KeywordNodeBuilder keyword(String keyword) {
        return new KeywordNodeBuilder(keyword);
    }

    /**
     * Creates a builder for constructing an argument command node.
     *
     * @param name The name of the argument.
     * @param type The type of the argument.
     * @param <T> The type of the argument.
     * @return An {@link ArgumentNodeBuilder} for constructing an argument command node.
     */
    public static <T> ArgumentNodeBuilder<T> argument(String name, ArgumentType<T> type) {
        return new ArgumentNodeBuilder<>(name, type);
    }

}
