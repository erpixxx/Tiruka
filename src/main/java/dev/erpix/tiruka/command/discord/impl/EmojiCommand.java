package dev.erpix.tiruka.command.discord.impl;

import dev.erpix.tiruka.command.discord.DiscordCommand;
import dev.erpix.tiruka.command.discord.DiscordCommandCategory;
import net.dv8tion.jda.api.entities.emoji.Emoji;
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

public class EmojiCommand implements DiscordCommand {

    @Override
    public SlashCommandData build() {
        return Commands.slash("emoji", "Converts emoji to image format.").addOptions(
                new OptionData(OptionType.STRING, "emoji", "Emoji", true),
                new OptionData(OptionType.BOOLEAN, "silent", "Makes result only visible to you.", false)
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent ctx) {
        SlashCommandInteraction interaction = ctx.getInteraction();

        OptionMapping emojiOpt = interaction.getOption("emoji");
        if (emojiOpt == null) return;

        boolean silent = Optional.ofNullable(interaction.getOption("silent"))
                .map(OptionMapping::getAsBoolean).orElse(false);

        String emojiString = emojiOpt.getAsString();
        try {
            String imageUrl = Emoji.fromFormatted(emojiString).asCustom().getImageUrl();
            ctx.reply(imageUrl).setEphemeral(silent).queue();
        } catch (IllegalStateException ex) {
            ctx.reply("Cannot convert this emoji to an image").setEphemeral(true).queue();
        }

    }

    @Override
    public DiscordCommandCategory getCategory() {
        return DiscordCommandCategory.UTILITY;
    }

}
