package dev.erpix.tiruka.command.discord.impl;

import dev.erpix.tiruka.command.discord.DiscordCommand;
import dev.erpix.tiruka.command.discord.DiscordCommandCategory;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class LockCommand implements DiscordCommand {

    @Override
    public SlashCommandData build() {
        return Commands.slash("lock", "Locks specified channel").addOptions(
                new OptionData(OptionType.CHANNEL, "channel", "Channel to be locked", false)
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent ctx) {
        OptionMapping channelOpt = ctx.getInteraction().getOption("channel");
        GuildMessageChannel channel = channelOpt != null ? channelOpt.getAsChannel().asGuildMessageChannel() : ctx.getGuildChannel();

    }

    @Override
    public DiscordCommandCategory getCategory() {
        return DiscordCommandCategory.MODERATION;
    }

}
