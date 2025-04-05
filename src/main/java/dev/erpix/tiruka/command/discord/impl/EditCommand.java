package dev.erpix.tiruka.command.discord.impl;

import dev.erpix.tiruka.command.discord.DiscordCommand;
import dev.erpix.tiruka.command.discord.DiscordCommandCategory;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
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
import dev.erpix.tiruka.utils.Colors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

// TODO: Ability to edit attachments and components
public class EditCommand implements DiscordCommand {

    @Override
    public SlashCommandData build() {
        return Commands.slash("edit", "Edits Tiruka's specified message.").addSubcommands(
                new SubcommandData("message", "Edits message content.")
                        .addOptions(
                                new OptionData(OptionType.STRING, "message_id", "Message ID", true),
                                new OptionData(OptionType.STRING, "content", "Replacement message content.", true)
                        ),
                new SubcommandData("embed", "Edits message embed.")
                        .addOptions(
                                new OptionData(OptionType.STRING, "message_id", "Message ID", true),
                                new OptionData(OptionType.STRING, "content", "Replacement embed. If no index is used the first embed will be modified", true),
                                new OptionData(OptionType.INTEGER, "index", "Embed index.", false)
                                        .setMinValue(1)
                        )
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent ctx) {
        SlashCommandInteraction interaction = ctx.getInteraction();
        OptionMapping messageIdArg = interaction.getOption("message_id");
        OptionMapping contentArg = interaction.getOption("content");
        String subcommand = interaction.getSubcommandName();

        if (messageIdArg == null || contentArg == null || subcommand == null) return;
        String messageId = messageIdArg.getAsString();
        String content = contentArg.getAsString();

        ctx.getChannel().getHistory().retrievePast(100).queue(history -> {
            boolean messageFound = false;
            for (Message message : history) {
                if (!message.getId().equals(messageId)) continue;

                messageFound = true;
                if (subcommand.equals("message")) {
                    String oldContent = message.getContentRaw();
                    message.editMessage(content).queue(
                            success -> ctx.replyEmbeds(editedMessage(oldContent, content)).setEphemeral(true).queue(),
                            error -> ctx.reply("Failed to edit specified message.").setEphemeral(true).queue()
                    );
                }
                else if (subcommand.equals("embed")) {
                    int embedIndex = Optional.ofNullable(interaction.getOption("index"))
                            .map(OptionMapping::getAsInt).orElse(1) - 1;
                    List<MessageEmbed> embeds = new ArrayList<>(message.getEmbeds());

                    int size = embeds.size();
                    if (size <= embedIndex) {
                        ctx.replyEmbeds(indexOutOfBounds(size, embedIndex + 1)).setEphemeral(true).queue();
                        return;
                    }

                    DataObject oldContent = embeds.get(embedIndex).toData();
                    DataObject newContent = DataObject.fromJson(content);
                    try {
                        MessageEmbed embed = EmbedBuilder.fromData(newContent).build();
                        embeds.set(embedIndex, embed);
                        message.editMessageEmbeds(embeds).queue(
                                success -> ctx.replyEmbeds(editedEmbed(oldContent, newContent, embedIndex)).setEphemeral(true).queue(),
                                error -> ctx.reply("Failed to edit specified message.").setEphemeral(true).queue()
                        );
                    } catch (ParsingException ex) {
                        ctx.replyEmbeds(invalidJsonFormat(ex)).setEphemeral(true).queue();
                    }
                }
            }
            if (!messageFound)
                ctx.replyEmbeds(failedToFindMessage()).setEphemeral(true).queue();
        });
    }

    @Override
    public DiscordCommandCategory getCategory() {
        return DiscordCommandCategory.UTILITY;
    }

    private MessageEmbed editedMessage(String oldContent, String newContent) {
        return new EmbedBuilder()
                .setColor(Colors.SUCCESS_COLOR)
                .setDescription(":white_check_mark: **Successfully edited specified message**")
                .addField("Old content", oldContent, false)
                .addField("New content", newContent, false)
                .build();
    }

    private MessageEmbed editedEmbed(DataObject oldContent, DataObject newContent, int index) {
        return new EmbedBuilder()
                .setColor(Colors.SUCCESS_COLOR)
                .setDescription(":white_check_mark: **Successfully edited embed with index `" + (index + 1) + "`**")
                .addField("Old content", "```json\n " + oldContent.toPrettyString() + "\n```", false)
                .addField("New content", "```json\n " + newContent.toPrettyString() + "\n```", false)
                .build();
    }

    private MessageEmbed failedToFindMessage() {
        return new EmbedBuilder()
                .setColor(Colors.ERROR_COLOR)
                .setDescription("""
                        :x: **Failed to find specified message**
                        
                        Applications can only scan up to 100 last messages, make sure your message is not out of range.
                        """)
                .build();
    }

    private MessageEmbed invalidJsonFormat(ParsingException ex) {
        return new EmbedBuilder()
                .setColor(Colors.ERROR_COLOR)
                .setDescription(":x: **Failed to parse embed JSON format**\n\n" + ex.getMessage())
                .build();
    }

    private MessageEmbed indexOutOfBounds(int size, int index) {
        return new EmbedBuilder()
                .setColor(Colors.ERROR_COLOR)
                .setDescription(":x: **Index out of bounds**\n\n" +
                        "You attempted to edit embed with index `" + (index + 1) + "`, " +
                        "but the highest embed index is `" + size + "`.")
                .build();
    }

}
