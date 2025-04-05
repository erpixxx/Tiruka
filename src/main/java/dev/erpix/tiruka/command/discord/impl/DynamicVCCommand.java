package dev.erpix.tiruka.command.discord.impl;

import dev.erpix.tiruka.TirukaApp;
import dev.erpix.tiruka.command.discord.DiscordCommand;
import dev.erpix.tiruka.command.discord.DiscordCommandCategory;
import dev.erpix.tiruka.model.__CachedGuild;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DynamicVCCommand implements DiscordCommand {

    private final TirukaApp tiruka;

    public DynamicVCCommand(TirukaApp tiruka) {
        this.tiruka = tiruka;
    }

    @Override
    public SlashCommandData build() {
        return Commands.slash("dynamic_vc", "Manages temporary voice channels.").addOptions(
                new OptionData(OptionType.CHANNEL, "channel", "Channel", true)
                        .setChannelTypes(ChannelType.VOICE),
                new OptionData(OptionType.BOOLEAN, "enabled", "Enable/Disable vote reactions", false)
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent ctx) {
        SlashCommandInteraction interaction = ctx.getInteraction();

        OptionMapping channelOpt = interaction.getOption("channel");
        if (channelOpt == null) return;
        GuildMessageChannel channel = channelOpt.getAsChannel().asGuildMessageChannel();

        boolean enable = Optional.ofNullable(interaction.getOption("enabled"))
                .map(OptionMapping::getAsBoolean).orElse(true);

        Guild guild = ctx.getGuild();
        if (guild == null) return;

//        __CachedGuild cachedGuild = tiruka.getCachedGuild(guild.getId());
//        if (cachedGuild == null) return;
//
//        cachedGuild.getDynamicVCManager().setGeneratorChannelId(channel.getId());
//        ctx.reply("Added").setEphemeral(true).queue();
    }

    @Override
    public DiscordCommandCategory getCategory() {
        return DiscordCommandCategory.DEVELOPER;
    }

}
