package dev.erpix.tiruka.command.discord.impl;

import dev.erpix.tiruka.command.discord.DiscordCommand;
import dev.erpix.tiruka.command.discord.DiscordCommandCategory;
import dev.erpix.tiruka.utils.Colors;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
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

public class ShipCommand implements DiscordCommand {

    @Override
    public SlashCommandData build() {
        return Commands.slash("ship", "Ship :3").addOptions(
                new OptionData(OptionType.USER, "user1", "user1", true),
                new OptionData(OptionType.USER, "user2", "user2", false)
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent ctx) {
        SlashCommandInteraction interaction = ctx.getInteraction();

        User user1 = Optional.ofNullable(interaction.getOption("user1"))
                .map(OptionMapping::getAsUser).orElse(null);
        if (user1 == null) return;

        User user2 = Optional.ofNullable(interaction.getOption("user2"))
                .map(OptionMapping::getAsUser).orElse(ctx.getUser());

        String user1Id = user1.getId();
        String user2Id = user2.getId();
        String combinedIds = user1Id.compareTo(user2Id) < 0 ? user1Id + user2Id : user2Id + user1Id;
        int result = Math.abs(combinedIds.hashCode() % 101); // Use Math.abs(), because hashCode() can be negative

        ctx.replyEmbeds(formatReply(user1.getAsMention(), user2.getAsMention(), result)).queue();
    }

    @Override
    public DiscordCommandCategory getCategory() {
        return DiscordCommandCategory.FUN;
    }

    private MessageEmbed formatReply(String user1, String user2, int result) {
        return new EmbedBuilder()
                .setColor(Colors.INFO_COLOR)
                .setTitle(":heart: Ship results")
                .setDescription(user1 + " and " + user2 + " match with a compatibility of " + result + "%")
                .build();
    }

}
