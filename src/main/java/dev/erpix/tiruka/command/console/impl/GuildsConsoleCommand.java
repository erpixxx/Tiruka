package dev.erpix.tiruka.command.console.impl;

import dev.erpix.tiruka.command.console.ConsoleCommand;
import dev.erpix.tiruka.command.console.model.CommandNode;
import dev.erpix.tiruka.command.console.model.builder.ConsoleCommandBuilder;

public class GuildsConsoleCommand implements ConsoleCommand {

    @Override
    public CommandNode build() {
        return ConsoleCommandBuilder.keyword("guilds")
                .description("Shows all guilds that the bot is in.")
                .executes(ctx -> {
                    throw new UnsupportedOperationException("Not implemented yet");
                })
                .build();
    }

}
