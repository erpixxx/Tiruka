package dev.erpix.tiruka.command;

import dev.erpix.tiruka.TirukaApp;
import dev.erpix.tiruka.command.console.model.CommandNode;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.erpix.tiruka.command.console.ConsoleCommand;
import dev.erpix.tiruka.command.discord.DiscordCommand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Class responsible for managing and registering both console and Discord commands.
 */
public class CommandRegistrar {

    private static final Logger logger = LoggerFactory.getLogger(CommandRegistrar.class);

    private final Map<String, CommandNode> consoleCommands = new LinkedHashMap<>();
    private final Map<String, DiscordCommand> discordCommands = new LinkedHashMap<>();

    /**
     * Registers a command and its aliases to the command map.
     *
     * @param cmd The command to be registered.
     */
    public void register(@NotNull CommandBase<?> cmd) {
        if (cmd instanceof ConsoleCommand consoleCmd) {
            // Pass console commands to the console command processor
            CommandNode node = consoleCmd.build();
            TirukaApp.getInstance().getConsole().getCommandProcessor().register(node);
            consoleCommands.put(node.getName(), node);
        }
        else if (cmd instanceof DiscordCommand discordCmd) {
            discordCommands.put(discordCmd.getName(), discordCmd);
        }
        else throw new IllegalArgumentException("Unknown command type.");
    }

    /**
     * Registers an array of commands to the command map.
     *
     * @param cmds Commands to be registered.
     */
    public void registerAll(@NotNull CommandBase<?>... cmds) {
        for (CommandBase<?> cmd : cmds) register(cmd);
    }

    /**
     * Pushes the registered commands to the Discord.
     */
    public void update(@NotNull ShardManager shardManager) {
        // TODO: Remove test guild
        Guild testGuild = shardManager.getGuildById(1295510570472177704L);
        if (testGuild != null) {
            List<CommandData> commands = new ArrayList<>();
            discordCommands.values().forEach(cmd -> commands.add(cmd.build()));
            testGuild.updateCommands().addCommands(commands).queue(
                    success -> {},
                    error -> logger.error("Error updating discord commands", error));
        }
    }

    public CommandNode getConsoleCommand(@NotNull String name) {
        return consoleCommands.get(name);
    }

    public Collection<CommandNode> getConsoleCommands() {
        return consoleCommands.values();
    }

    public DiscordCommand getDiscordCommand(@NotNull String name) {
        return discordCommands.get(name);
    }

    public Collection<DiscordCommand> getDiscordCommands() {
        return discordCommands.values();
    }

}
