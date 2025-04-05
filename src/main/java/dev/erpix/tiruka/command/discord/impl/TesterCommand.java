package dev.erpix.tiruka.command.discord.impl;

import dev.erpix.tiruka.command.discord.DiscordCommand;
import dev.erpix.tiruka.command.discord.DiscordCommandCategory;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.unions.GuildMessageChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;
import dev.erpix.tiruka.WelcomeTest;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class TesterCommand implements DiscordCommand {

    private final WelcomeTest welcomeTest = new WelcomeTest();
    private Message message;

    @Override
    public SlashCommandData build() {
        return Commands.slash("tester", "test").addSubcommands(
                new SubcommandData("user_info", "Shows user info"),
                new SubcommandData("embeds", "Embedsss"),
                new SubcommandData("image", "image")
                        .addOption(OptionType.STRING, "text", "Texttt"),
                new SubcommandData("msg1", "msg1")
                        .addOption(OptionType.STRING, "message_id", "message_id"),
                new SubcommandData("msg2", "msg2")
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent ctx) {

        OptionMapping textOpt = ctx.getInteraction().getOption("text");
        String subcommand = ctx.getSubcommandName();
        if (subcommand == null) return;

        if (subcommand.equals("embeds")) {
            ctx.getChannel().sendMessageEmbeds(
                    new EmbedBuilder().setDescription("First embed").build(),
                    new EmbedBuilder().setDescription("Second embed").build(),
                    new EmbedBuilder().setDescription("Third embed").build()
            ).queue();
        }

        if (subcommand.equals("image")) {
            if (textOpt == null) return;

            ctx.getUser().getAvatar().download(512).thenAccept(avatar -> {
                InputStream image = welcomeTest.imagine(textOpt.getAsString(), avatar);
                ctx.replyFiles(FileUpload.fromData(image, "image.png")).queue();
            });
        }

        if (subcommand.equals("msg1")) {
            OptionMapping opt = ctx.getInteraction().getOption("message_id");
            if (opt == null) return;

            String messageId = opt.getAsString();
            GuildMessageChannelUnion channel = ctx.getGuildChannel();
            channel.retrieveMessageById(messageId).queue(msg -> {
                this.message = msg;
                ctx.reply("Found message").queue();
            });
        }

        if (subcommand.equals("msg2")) {

            ctx.reply(message.toString()).queue();
        }

        if (subcommand.equals("user_info")) {
            ctx.reply("").queue();
        }
    }

    @Override
    public DiscordCommandCategory getCategory() {
        return DiscordCommandCategory.DEVELOPER;
    }

}
