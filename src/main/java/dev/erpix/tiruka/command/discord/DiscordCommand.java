package dev.erpix.tiruka.command.discord;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import dev.erpix.tiruka.command.CommandBase;

public interface DiscordCommand extends CommandBase<SlashCommandData> {

    @Override
    SlashCommandData build();

    void execute(SlashCommandInteractionEvent ctx);

    DiscordCommandCategory getCategory();

    default String getName() {
        return build().getName();
    }

    // default Map<String, DiscordCommand> getRegistered()

}
