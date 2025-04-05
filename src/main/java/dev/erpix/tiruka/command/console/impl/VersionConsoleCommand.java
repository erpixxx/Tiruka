package dev.erpix.tiruka.command.console.impl;

import dev.erpix.tiruka.command.console.ConsoleCommand;
import dev.erpix.tiruka.command.console.model.CommandNode;
import dev.erpix.tiruka.command.console.model.builder.ConsoleCommandBuilder;
import dev.erpix.tiruka.console.Console;
import org.jetbrains.annotations.NotNull;
import dev.erpix.tiruka.TirukaConstants;

import java.util.List;

import static dev.erpix.tiruka.console.Console.TERMINAL;

public class VersionConsoleCommand implements ConsoleCommand {

    @Override
    public CommandNode build() {
        return ConsoleCommandBuilder.keyword("version")
                .description("Displays the current version of Tiruka")
                .executes(ctx -> {
                    Console.println("Running Tiruka v%s", TirukaConstants.VERSION);
                })
                .build();
    }

}
