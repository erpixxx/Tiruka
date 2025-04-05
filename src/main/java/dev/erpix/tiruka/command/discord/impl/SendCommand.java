package dev.erpix.tiruka.command.discord.impl;

import dev.erpix.tiruka.command.discord.DiscordCommand;
import dev.erpix.tiruka.command.discord.DiscordCommandCategory;
import dev.erpix.tiruka.utils.Colors;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.unions.GuildMessageChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.exceptions.ParsingException;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.utils.data.DataObject;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class SendCommand implements DiscordCommand {

    @Override
    public SlashCommandData build() {
        return Commands.slash("send", "Sends message to current channel.").addSubcommands(
                new SubcommandData("message", "Sends message")
                        .addOptions(new OptionData(OptionType.STRING, "content", "Message content", true)),
                new SubcommandData("embed", "Sends embed")
                        .addOptions(
                                new OptionData(OptionType.STRING, "embed", "Embed json", true),
                                new OptionData(OptionType.STRING, "content", "Message content", false)
                        )
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent ctx) {
        SlashCommandInteraction interaction = ctx.getInteraction();
        String subcommand = interaction.getSubcommandName();

        if (subcommand == null) return;

        OptionMapping content = interaction.getOption("content");
        OptionMapping embed = interaction.getOption("embed");
        GuildMessageChannelUnion channel = ctx.getGuildChannel();

        if (subcommand.equals("message")) {
            if (content == null) return;

            channel.sendMessage(content.getAsString())
                    .queue(m -> ctx.reply("Sent message").setEphemeral(true).queue());
        }
        // TODO: Support sending multiple embeds
        else if (subcommand.equals("embed")) {
            String message = "";
            if (content != null) message = content.getAsString();
            if (embed == null) return;

            try {
                channel.sendMessage(message).addEmbeds(EmbedBuilder.fromData(DataObject.fromJson(embed.getAsString())).build())
                        .queue(m -> ctx.reply("Sent embed").setEphemeral(true).queue());
            } catch (ParsingException ex) {
                ctx.replyEmbeds(invalidJsonFormat(ex)).setEphemeral(true).queue();
            }
        }
    }

    @Override
    public DiscordCommandCategory getCategory() {
        return null;
    }

    private MessageEmbed invalidJsonFormat(ParsingException ex) {
        return new EmbedBuilder()
                .setColor(Colors.ERROR_COLOR)
                .setDescription(":x: **Failed to parse embed JSON format**\n\n" + ex.getMessage())
                .build();
    }

}
