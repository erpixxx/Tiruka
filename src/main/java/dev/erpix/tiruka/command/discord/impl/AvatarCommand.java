package dev.erpix.tiruka.command.discord.impl;

import dev.erpix.tiruka.command.discord.DiscordCommand;
import dev.erpix.tiruka.command.discord.DiscordCommandCategory;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.utils.ImageProxy;

import java.util.Optional;

public class AvatarCommand implements DiscordCommand {

    private static final String DEFAULT_PROFILE = "https://cdn.discordapp.com/embed/avatars/0.png";

    @Override
    public SlashCommandData build() {
        return Commands.slash("avatar", "Retrieves specified user's profile picture.").addOptions(
                new OptionData(OptionType.USER, "user", "The user whose avatar should be retrieved.", true),
                new OptionData(OptionType.BOOLEAN, "silent", "Makes result only visible to you.", false)
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent ctx) {
        SlashCommandInteraction interaction = ctx.getInteraction();

        User user = Optional.ofNullable(interaction.getOption("user"))
                .map(OptionMapping::getAsUser).orElse(null);
        if (user == null) return;

        boolean silent = Optional.ofNullable(interaction.getOption("silent"))
                .map(OptionMapping::getAsBoolean).orElse(false);

        ImageProxy avatar = user.getAvatar();
        if (avatar == null) ctx.reply(DEFAULT_PROFILE).setEphemeral(silent).queue();
        else ctx.reply(avatar.getUrl(2048)).setEphemeral(silent).queue();
    }

    @Override
    public DiscordCommandCategory getCategory() {
        return DiscordCommandCategory.UTILITY;
    }

}
