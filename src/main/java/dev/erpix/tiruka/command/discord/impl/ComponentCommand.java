package dev.erpix.tiruka.command.discord.impl;

import dev.erpix.tiruka.command.discord.DiscordCommand;
import dev.erpix.tiruka.command.discord.DiscordCommandCategory;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class ComponentCommand implements DiscordCommand {

    @Override
    public SlashCommandData build() {
        return Commands.slash("component", "creates new component").addSubcommands(
                new SubcommandData("button", "Creates new button")
                        .addOptions(
                                new OptionData(OptionType.STRING, "message_id", "Message to apply component to", true),
                                new OptionData(OptionType.STRING, "button_type", "Button type", true)
                                        .addChoice("Primary", "1")
                                        .addChoice("Secondary", "2")
                                        .addChoice("Success", "3")
                                        .addChoice("Destructive", "4")
                                        .addChoice("Link", "5"),
                                new OptionData(OptionType.STRING, "button_value", "Button value", true)
                        ),
                new SubcommandData("select_menu", "Creates new select menu")
                        .addOptions(
                                new OptionData(OptionType.STRING, "message_id", "Message to apply component to", true),
                                new OptionData(OptionType.STRING, "type", "Button type", true)
                        )
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent ctx) {
        SlashCommandInteraction interaction = ctx.getInteraction();

        String subcommand = interaction.getSubcommandName();
        if (subcommand == null) return;

        OptionMapping messageIdOpt = interaction.getOption("message_id");
        if (messageIdOpt == null) return;
        String messageId = messageIdOpt.getAsString();

        if (subcommand.equals("button")) {
            OptionMapping buttonTypeOpt = interaction.getOption("button_type");
            if (buttonTypeOpt == null) return;
            OptionMapping buttonValueOpt = interaction.getOption("button_value");
            if (buttonValueOpt == null) return;

            String buttonType = buttonTypeOpt.getAsString();
            String buttonValue = buttonValueOpt.getAsString();

            ButtonStyle buttonStyle = ButtonStyle.fromKey(Integer.parseInt(buttonType));

            ctx.getGuildChannel().retrieveMessageById(messageId).queue(message -> {
                message.editMessageComponents(ActionRow.of(Button.of(buttonStyle, "test", buttonValue))).queue();
            });
        }
    }

    @Override
    public DiscordCommandCategory getCategory() {
        return null;
    }

}
