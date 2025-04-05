package dev.erpix.tiruka.command.console.impl;

import dev.erpix.tiruka.TirukaApp;
import dev.erpix.tiruka.command.CommandRegistrar;
import dev.erpix.tiruka.command.console.ConsoleCommand;
import dev.erpix.tiruka.command.console.model.CommandNode;
import dev.erpix.tiruka.command.console.model.KeywordCommandNode;
import dev.erpix.tiruka.command.console.model.builder.ConsoleCommandBuilder;
import dev.erpix.tiruka.console.Console;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HelpConsoleCommand implements ConsoleCommand {

    @Override
    public CommandNode build() {
        return ConsoleCommandBuilder.keyword("help")
                .aliases("?")
                .description("Displays a list of available commands.")
                .executes(ctx -> {
                    CommandRegistrar registrar = TirukaApp.getInstance().getCommandRegistrar();

                    Console.println("Available commands:");
                    for (CommandNode node : registrar.getConsoleCommands()) {
                        KeywordCommandNode root = (KeywordCommandNode) node;

                        List<String> aliases = new ArrayList<>();
                        aliases.add(root.getName());
                        aliases.addAll(root.getAliases());

                        Console.println("- " + String.join(", ", aliases) + " - " + root.getDescription());
                    }
                })
                .build();
    }

    // TODO: Implement the command tree building
    private String buildCommandTree(Collection<CommandNode> nodes) {
        StringBuilder builder = new StringBuilder("├─");
        for (CommandNode node : nodes) {
            List<String> aliases = new ArrayList<>();
            aliases.add(node.getName());

            if (node instanceof KeywordCommandNode keyword) {
                aliases.addAll(keyword.getAliases());
            }

            for (String alias : aliases) {
                builder.append(" / ").append(alias);
            }

            while (!node.getChildren().isEmpty()) {
                builder.append("\n").append(buildCommandTree(node.getChildren()));
            }
        }
        return builder.toString();
    }

    private int getColor(int index) {
        return switch (index % 4) {
            case 0 -> 0xFF6961;
            case 1 -> 0x80EF80;
            case 2 -> 0xB3EBF2;
            case 3 -> 0xFFEE8C;
            default -> 0xFFFFFF;
        };
    }

}
