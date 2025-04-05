package dev.erpix.tiruka.command.discord.impl;

import dev.erpix.tiruka.command.discord.DiscordCommand;
import dev.erpix.tiruka.command.discord.DiscordCommandCategory;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

// TODO: Purge 1 not working
// TODO: Purge not working in vc (Message Id provided was older than 2 weeks. Id: 1300801569746452523)
public class PurgeCommand implements DiscordCommand {

    @Override
    public SlashCommandData build() {
        return Commands.slash("purge", "Purges messages in channel").setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MESSAGE_MANAGE))
                .addOptions(
                new OptionData(OptionType.INTEGER, "amount", "How many message to purge", true)
                        .setRequiredRange(1, 100));
    }

    @Override
    public void execute(SlashCommandInteractionEvent ctx) {
        OptionMapping amountOpt = ctx.getInteraction().getOption("amount");
        if (amountOpt == null) return;

        int amount = amountOpt.getAsInt();

        GuildMessageChannel channel = ctx.getChannel().asGuildMessageChannel();
        channel.getHistory().retrievePast(amount).queue(history -> channel.deleteMessages(history).queue());

        ctx.reply(":white_check_mark: Purged `" + amount + "` messages in current channel.")
                .setEphemeral(true).queue();
    }

    @Override
    public DiscordCommandCategory getCategory() {
        return DiscordCommandCategory.MODERATION;
    }

}
