package dev.erpix.tiruka.command.console.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>Represents a single node in a command tree structure.</p>
 * <p>
 * This class serves as the base for all command nodes in the system. Each node
 * can represent either a keyword (a fixed command name) or an argument (a variable input).
 * Nodes are organized hierarchically, allowing complex command structures.
 * </p>
 *
 * <p>Node structure:</p>
 * <ul>
 *     <li><b>Children:</b> Each node can have child nodes, which can be either keywords or arguments.</li>
 *     <li><b>Executor:</b> A node may have an executor, which is triggered when the node is reached in the command tree.</li>
 *     <li><b>Description:</b> Each node has an optional description, typically used in help messages.</li>
 *     <li><b>Identification:</b> A node is uniquely identified by its name within its parent node.</li>
 * </ul>
 */
public abstract class CommandNode {

    private final Map<String, CommandNode> children = new LinkedHashMap<>();
    private final Map<String, KeywordCommandNode> keywords = new LinkedHashMap<>();
    private final Map<String, ArgumentCommandNode<?>> arguments = new LinkedHashMap<>();
    private final String description;
    private final CommandExecutor executor;

    protected CommandNode(String description, Collection<KeywordCommandNode> keywords, Collection<ArgumentCommandNode<?>> arguments, CommandExecutor executor) {
        this.description = description;
        this.executor = executor;
        keywords.forEach(keyword -> {
            keyword.getAliases().forEach(alias -> {
                this.children.put(alias, keyword);
                this.keywords.put(alias, keyword);
            });
            this.children.put(keyword.getName(), keyword);
            this.keywords.put(keyword.getName(), keyword);
        });
        arguments.forEach(argument -> {
            this.children.put(argument.getName(), argument);
            this.arguments.put(argument.getName(), argument);
        });
    }

    /**
     * Gets the name of this command node.
     *
     * @return The name of this node.
     */
    public abstract @NotNull String getName();

    /**
     * Executes this command using the provided {@link CommandContext}.
     *
     * @param context The context of the command execution, including arguments and sender.
     */
    public void execute(CommandContext context) {
        executor.execute(context);
    }

    /**
     * Checks if this node has an associated executor.
     *
     * @return {@code true} if the node is executable, {@code false} otherwise.
     */
    public boolean isExecutable() {
        return executor != null;
    }

    /**
     * Retrieves a child node by its name.
     *
     * @param name The name of the child node.
     * @return The corresponding {@link CommandNode}, or {@code null} if no such child exists.
     */
    public @Nullable CommandNode getChild(String name) {
        return children.get(name);
    }

    /**
     * Returns a collection of all child nodes of this command node.
     *
     * @return A collection of all child nodes.
     */
    public @NotNull Collection<CommandNode> getChildren() {
        return children.values();
    }

    /**
     * Returns a collection of all keyword-based child nodes.
     *
     * @return A collection of {@link KeywordCommandNode} nodes.
     */
    public @NotNull Collection<KeywordCommandNode> getKeywords() {
        return keywords.values();
    }

    /**
     * Retrieves an argument-based child node by its name.
     *
     * @param name The name of the argument node.
     * @return The corresponding {@link ArgumentCommandNode}, or {@code null} if no such argument exists.
     */
    public @Nullable ArgumentCommandNode<?> getArgument(String name) {
        return arguments.get(name);
    }

    /**
     * Returns a collection of all argument-based child nodes.
     *
     * @return A collection of {@link ArgumentCommandNode} nodes.
     */
    public @NotNull Collection<ArgumentCommandNode<?>> getArguments() {
        return arguments.values();
    }

    /**
     * Returns the description of this command node.
     *
     * @return A string containing the node's description.
     */
    public @Nullable String getDescription() {
        return description;
    }

}
