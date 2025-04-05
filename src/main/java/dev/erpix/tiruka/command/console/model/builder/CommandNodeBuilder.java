package dev.erpix.tiruka.command.console.model.builder;

import dev.erpix.tiruka.command.console.model.ArgumentCommandNode;
import dev.erpix.tiruka.command.console.model.CommandExecutor;
import dev.erpix.tiruka.command.console.model.CommandNode;
import dev.erpix.tiruka.command.console.model.KeywordCommandNode;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>Builder class for constructing {@link CommandNode} objects.</p>
 * <p>
 * This class is used to build complex command nodes by chaining method calls.
 * It supports adding child nodes (both keyword-based and argument-based),
 * setting descriptions, and providing executors for the command nodes.
 * </p>
 *
 * @param <T> The builder type, ensuring method chaining can return the correct builder type.
 */
public abstract class CommandNodeBuilder<T extends CommandNodeBuilder<T>> {

    private final Map<String, KeywordCommandNode> keywords = new LinkedHashMap<>();
    private final Map<String, ArgumentCommandNode<?>> arguments = new LinkedHashMap<>();
    private CommandExecutor executor;
    private String description;

    /**
     * Adds a child node to this builder.
     * <p>The child can either be a {@link KeywordCommandNode} or an {@link ArgumentCommandNode}.</p>
     *
     * @param node The child node to add.
     * @return The current builder instance.
     */
    public T child(CommandNode node) {
        if (node instanceof KeywordCommandNode) {
            keywords.put(node.getName(), (KeywordCommandNode) node);
        }
        else if (node instanceof ArgumentCommandNode) {
            arguments.put(node.getName(), (ArgumentCommandNode<?>) node);
        }
        return self();
    }

    /**
     * Returns a collection of all keyword command nodes added to this builder.
     *
     * @return A collection of {@link KeywordCommandNode} objects.
     */
    public Collection<KeywordCommandNode> getKeywords() {
        return keywords.values();
    }

    /**
     * Returns a collection of all argument command nodes added to this builder.
     *
     * @return A collection of {@link ArgumentCommandNode} objects.
     */
    public Collection<ArgumentCommandNode<?>> getArguments() {
        return arguments.values();
    }

    /**
     * Sets the description for the command node being built.
     *
     * @param description The description to set.
     * @return The current builder instance.
     */
    public T description(String description) {
        this.description = description;
        return self();
    }

    /**
     * Returns the description of the command node.
     *
     * @return The description of the command node.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the executor for the command node.
     * <p>The executor defines what should happen when the command node is triggered.</p>
     *
     * @param executor The executor to set.
     * @return The current builder instance.
     */
    public T executes(CommandExecutor executor) {
        this.executor = executor;
        return self();
    }

    /**
     * Returns the executor set for this command node.
     *
     * @return The executor for this command node.
     */
    public CommandExecutor getExecutor() {
        return executor;
    }

    /**
     * This method is a protected abstract method that must be implemented by concrete builders.
     * It ensures that the correct type of builder is returned, allowing for method chaining.
     *
     * @return The current builder instance.
     */
    protected abstract T self();

    /**
     * Builds and returns the constructed {@link CommandNode}.
     * <p>This method should be called after all desired configurations have been applied to the builder.</p>
     *
     * @return The constructed {@link CommandNode}.
     */
    public abstract CommandNode build();

}
