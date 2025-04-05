package dev.erpix.tiruka.command.discord.impl;

import dev.erpix.tiruka.command.discord.DiscordCommand;
import dev.erpix.tiruka.command.discord.DiscordCommandCategory;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

// Todo: Roll with users
public class RollCommand implements DiscordCommand {

    private static final int DEFAULT_MINIMUM = 1;
    private static final int DEFAULT_MAXIMUM = 6;
    private final Random random = new Random();

    @Override
    public SlashCommandData build() {
        return Commands.slash("roll", "Rolls a dice.").addOptions(
                new OptionData(OptionType.INTEGER, "min", "Minimum value for the roll."),
                new OptionData(OptionType.INTEGER, "max", "Maximum value for the roll.")
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent ctx) {
        int min = Optional.ofNullable(ctx.getOption("min")).map(OptionMapping::getAsInt).orElse(DEFAULT_MINIMUM);
        int max = Optional.ofNullable(ctx.getOption("max")).map(OptionMapping::getAsInt).orElse(DEFAULT_MAXIMUM);

        if (min > max) {
            ctx.reply(":x: Minimum value cannot be higher than maximum value you baka...")
                    .setEphemeral(true).queue();
            return;
        }

        int result = random.nextInt((max - min) + 1) + min;
        ctx.reply(":game_die: Rolled: `" + result + "`!").queue();
    }

    @Override
    public DiscordCommandCategory getCategory() {
        return DiscordCommandCategory.FUN;
    }

}
