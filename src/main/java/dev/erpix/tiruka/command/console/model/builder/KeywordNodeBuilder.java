package dev.erpix.tiruka.command.console.model.builder;

import dev.erpix.tiruka.command.console.model.KeywordCommandNode;
import dev.erpix.tiruka.command.console.model.CommandNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder class for constructing {@link KeywordCommandNode} objects.
 */
public class KeywordNodeBuilder extends CommandNodeBuilder<KeywordNodeBuilder> {

    private final String keyword;
    private final List<String> aliases;

    public KeywordNodeBuilder(String keyword) {
        this.keyword = keyword;
        aliases = new ArrayList<>();
    }

    @Override
    protected KeywordNodeBuilder self() {
        return this;
    }

    /**
     * Adds aliases for the keyword.
     *
     * @param aliases The aliases to add.
     * @return The current builder instance.
     */
    public KeywordNodeBuilder aliases(String... aliases) {
        this.aliases.addAll(List.of(aliases));
        return self();
    }

    /**
     * Returns a list of all aliases added to this keyword node.
     *
     * @return A list of aliases.
     */
    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public CommandNode build() {
        return new KeywordCommandNode(keyword, getAliases(), getDescription(), getKeywords(), getArguments(), getExecutor());
    }

}
