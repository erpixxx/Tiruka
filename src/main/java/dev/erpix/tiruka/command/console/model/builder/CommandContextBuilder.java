package dev.erpix.tiruka.command.console.model.builder;

import dev.erpix.tiruka.command.console.model.CommandContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Builder class for constructing {@link CommandContext} objects.
 */
public class CommandContextBuilder {

    private final Map<String, Object> arguments = new HashMap<>();

    /**
     * Adds an argument to the command context.
     *
     * @param name     The name of the argument.
     * @param argument The value of the argument.
     * @return The current builder instance.
     */
    public CommandContextBuilder addArgument(String name, Object argument) {
        arguments.put(name, argument);
        return this;
    }

    /**
     * Builds and returns a new {@link CommandContext} instance containing the added arguments.
     *
     * @return A newly constructed {@link CommandContext} instance.
     */
    public CommandContext build() {
        return new CommandContext(arguments);
    }

}
