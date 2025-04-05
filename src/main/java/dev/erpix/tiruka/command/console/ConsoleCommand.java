package dev.erpix.tiruka.command.console;

import dev.erpix.tiruka.command.console.model.CommandNode;
import dev.erpix.tiruka.command.CommandBase;

/**
 * <p>Interface for console commands.</p>
 * <p>All console commands are built into a {@link CommandNode} then parsed by the {@link dev.erpix.tiruka.console.ConsoleCommandProcessor}
 * when executed. Implementations of this interface should define the structure of the command tree using the {@link #build()} method.</p>
 */
public interface ConsoleCommand extends CommandBase<CommandNode> {

    /**
     * <p>Builds the command tree for this console command.</p>
     * <p>Implementations should use the {@link dev.erpix.tiruka.command.console.model.builder.ConsoleCommandBuilder} to build the command tree.</p>
     *
     * @return The root node of the command tree.
     */
    @Override
    CommandNode build();

}
