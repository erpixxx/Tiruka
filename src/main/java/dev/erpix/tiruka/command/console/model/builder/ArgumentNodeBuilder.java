package dev.erpix.tiruka.command.console.model.builder;

import dev.erpix.tiruka.command.console.model.ArgumentCommandNode;
import dev.erpix.tiruka.command.console.model.CommandNode;
import dev.erpix.tiruka.command.console.model.argument.ArgumentType;

/**
 * Builder class for constructing {@link ArgumentCommandNode} objects.
 *
 * @param <T> The type of the argument.
 */
public class ArgumentNodeBuilder<T> extends CommandNodeBuilder<ArgumentNodeBuilder<T>> {

    private final String name;
    private final ArgumentType<T> type;

    public ArgumentNodeBuilder(String name, ArgumentType<T> type) {
        this.name = name;
        this.type = type;
    }

    @Override
    protected ArgumentNodeBuilder<T> self() {
        return this;
    }

    @Override
    public CommandNode build() {
        return new ArgumentCommandNode<>(name, type, getDescription(), getKeywords(), getArguments(), getExecutor());
    }

}
