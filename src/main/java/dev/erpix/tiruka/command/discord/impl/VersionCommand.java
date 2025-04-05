package dev.erpix.tiruka.command.discord.impl;

import dev.erpix.tiruka.command.discord.DiscordCommand;
import dev.erpix.tiruka.command.discord.DiscordCommandCategory;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.jetbrains.annotations.NotNull;
import dev.erpix.tiruka.TirukaConstants;

import java.util.Collections;
import java.util.List;

public class VersionCommand implements DiscordCommand {

    @Override
    public SlashCommandData build() {
        return Commands.slash("version", "Shows version information.");
    }

    @Override
    public void execute(SlashCommandInteractionEvent ctx) {
        ctx.getInteraction().reply("Running Tiruka v" + TirukaConstants.VERSION)
                .setEphemeral(true).queue();
    }

    @Override
    public DiscordCommandCategory getCategory() {
        return DiscordCommandCategory.UTILITY;
    }

}
