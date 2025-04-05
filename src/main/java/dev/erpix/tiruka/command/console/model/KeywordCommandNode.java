package dev.erpix.tiruka.command.console.model;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

/**
 * <p>Represents a command node identified by a keyword.</p>
 * <p>
 * This class extends {@link CommandNode} and is used for defining commands
 * that are triggered by specific keywords. It supports multiple aliases
 * for the same command, allowing more flexible command handling.
 * </p>
 */
public class KeywordCommandNode extends CommandNode {

    private final String keyword;
    private final List<String> aliases;

    public KeywordCommandNode(String keyword, List<String> aliases, String description, Collection<KeywordCommandNode> keywords, Collection<ArgumentCommandNode<?>> arguments, CommandExecutor executor) {
        super(description, keywords, arguments, executor);
        this.keyword = keyword;
        this.aliases = aliases;
    }

    /**
     * Returns the primary keyword name of this command.
     *
     * @return The keyword representing this command.
     */
    @Override
    public @NotNull String getName() {
        return keyword;
    }

    /**
     * Returns a list of aliases that can also trigger this command.
     *
     * @return A list of alias names.
     */
    public @NotNull List<String> getAliases() {
        return aliases;
    }

}
