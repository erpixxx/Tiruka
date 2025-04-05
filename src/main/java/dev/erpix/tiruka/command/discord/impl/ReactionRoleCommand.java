package dev.erpix.tiruka.command.discord.impl;

import dev.erpix.tiruka.TirukaApp;
import dev.erpix.tiruka.command.discord.DiscordCommand;
import dev.erpix.tiruka.command.discord.DiscordCommandCategory;
import dev.erpix.tiruka.model.__CachedGuild;
import dev.erpix.tiruka.model.ReactionRoleManager;
import dev.erpix.tiruka.model.ReactionRoleNode;
import dev.erpix.tiruka.utils.Colors;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import net.dv8tion.jda.api.entities.emoji.*;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

// TODO: check if emote already exists
public class ReactionRoleCommand implements DiscordCommand {

    private static final Logger logger = LoggerFactory.getLogger(ReactionRoleCommand.class);
    private final TirukaApp tiruka;

    public ReactionRoleCommand(TirukaApp tiruka) {
        this.tiruka = tiruka;
    }

    @Override
    public SlashCommandData build() {
        return Commands.slash("reaction_role", "Manages reaction roles.").addSubcommands(
                new SubcommandData("add", "Assign a role to an emoji reaction on a specified message.")
                        .addOptions(
                                new OptionData(OptionType.STRING, "message_id", "Message ID", true),
                                new OptionData(OptionType.STRING, "emoji", "Emoji", true),
                                new OptionData(OptionType.ROLE, "role", "Role", true)
                        ),
                new SubcommandData("clear", "Clears all given reaction roles for specified emoji.")
                        .addOptions(
                                new OptionData(OptionType.STRING, "message_id", "Message ID", true),
                                new OptionData(OptionType.STRING, "emoji", "Emoji", true)
                        ),
                new SubcommandData("info", "Displays information about reaction roles for a specified message.")
                        .addOptions(
                                new OptionData(OptionType.STRING, "message_id", "Message ID", true)
                        ),
                new SubcommandData("multiple_choices", "Toggles multiple choices.")
                        .addOptions(
                                new OptionData(OptionType.STRING, "message_id", "Message ID", true),
                                new OptionData(OptionType.BOOLEAN, "multiple_choices", "Allows multiple choices.", true)
                        ),
                new SubcommandData("list", "Lists all active reaction roles in the server."),
                new SubcommandData("remove", "Removes a specific role or all roles from a specified emoji on a message.")
                        .addOptions(
                                new OptionData(OptionType.STRING, "message_id", "Message ID", true),
                                new OptionData(OptionType.STRING, "emoji", "Emoji", false),
                                new OptionData(OptionType.ROLE, "role", "Emoji", false)
                        )
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent ctx) {
        SlashCommandInteraction interaction = ctx.getInteraction();
        String subcommand = interaction.getSubcommandName();

        Guild guild = ctx.getGuild();
        if (guild == null) return;

        GuildMessageChannel channel = ctx.getGuildChannel();

//        __CachedGuild cachedGuild = tiruka.getCachedGuild(guild.getId());
//        if (cachedGuild == null) return; // TODO: nahh
//
//        JDA jda = tiruka.getJda();
//        ReactionRoleManager rr = cachedGuild.getReactionRoleManager();
//
//        switch (subcommand) {
//            case "add" -> {
//                String messageId = interaction.getOption("message_id", OptionMapping::getAsString);
//                String emojiId = interaction.getOption("emoji", OptionMapping::getAsString);
//                Role role = interaction.getOption("role", OptionMapping::getAsRole);
//
//                if (messageId == null) {
//                    ctx.reply("Message ID cannot be empty.").queue();
//                    return;
//                }
//
//                if (emojiId == null) {
//                    ctx.reply("Emoji cannot be empty.").queue();
//                    return;
//                }
//                Emoji emoji = Emoji.fromFormatted(emojiId);
//                // TODO: Handle unknown emoji
//
//                if (role == null) {
//                    ctx.reply("Role cannot be empty.").queue();
//                    return;
//                }
//
//                rr.addRoleAndApplyReactions(channel.getId(), messageId, emoji, role.getId()).thenAccept(result -> {
//                    if (result) ctx.reply("Added " + emoji.getFormatted() + " reaction for " + role.getAsMention())
//                                .setEphemeral(true)
//                                .queue();
//                    else ctx.reply("Problem adding " + emoji.getFormatted() + " reaction for " + role.getAsMention())
//                                .setEphemeral(true)
//                                .queue();
//                }).exceptionally(ex -> {
//                    logger.error("An error occurred while adding the reaction.", ex);
//                    // consider ctx.reply
//                    return null;
//                });
//            }
//            case "info" -> {
//                String messageId = interaction.getOption("message_id", OptionMapping::getAsString);
//
//                if (messageId == null) {
//                    ctx.reply("Message ID cannot be empty.").queue();
//                    return;
//                }
//
//                ReactionRoleNode node = rr.getNode(messageId);
//                if (node == null) {
//                    ctx.reply("No message found").setEphemeral(true).queue();
//                    return;
//                }
//
//                EmbedBuilder builder = new EmbedBuilder();
//                builder.setColor(Colors.INFO_COLOR);
//                builder.setTitle("Reaction roles for " + messageId);
//                builder.setDescription("Allow multiple choices: `" + node.isMultipleChoices() + "`");
//                rr.getAllEmojis(messageId).forEach(emoji -> {
//                    Set<String> roles = node.getRolesByEmoji(emoji);
//                    StringBuilder fieldValue = new StringBuilder();
//                    for (String roleId : roles) {
//                        Role role = guild.getRoleById(roleId);
//                        if (role != null)
//                            fieldValue.append(role.getAsMention()).append(" ");
//                    }
//                    builder.addField(emoji.getFormatted() + " : `" + roles.size() + "` role(s) assigned",
//                            fieldValue.toString(), true);
//                });
//
//                ctx.replyEmbeds(builder.build()).setEphemeral(true).queue();
//            }
//            case "multiple_choices" -> {
//                String messageId = interaction.getOption("message_id", OptionMapping::getAsString);
//                boolean multipleChoices = Optional.ofNullable(interaction.getOption("multiple_choices",
//                        OptionMapping::getAsBoolean)).orElse(false);
//
//                if (messageId == null) {
//                    ctx.reply("Message ID cannot be empty.").queue();
//                    return;
//                }
//
//                ReactionRoleNode node = rr.getNode(messageId);
//                if (node == null) {
//                    ctx.reply("No message found").setEphemeral(true).queue();
//                    return;
//                }
//
//                node.setMultipleChoices(multipleChoices);
//                ctx.reply("Changed multiple choices to `" + multipleChoices + "`.").setEphemeral(true).queue();
//            }
//            // TODO: Fields are limited to 25 - consider adding pages
//            case "list" -> {
//                EmbedBuilder builder = new EmbedBuilder();
//                builder.setColor(Colors.INFO_COLOR);
//                builder.setTitle("List of reaction roles");
//
//                ctx.deferReply(true).queue(reply -> {
//
//                    rr.getAllNodes().forEach(node -> {
//
//                        String channelId = node.getChannelId();
//                        GuildMessageChannel nodeChannel = (GuildMessageChannel) guild.getGuildChannelById(channelId);
//                        if (nodeChannel == null) return;
//
//                        Message msg = nodeChannel.retrieveMessageById(node.getMessageId()).submit().join();
//                        String author = msg.getAuthor().getName();
//                        String content = String.format("%.20s", msg.getContentRaw());
//
//                        int amount = node.getAllEmojis().size();
//                        boolean multipleChoices = node.isMultipleChoices();
//
//                        builder.addField(msg.getJumpUrl() + " " + author + ": " + content,
//                                String.format("• `%s` reaction roles assigned\n• Allow multiple choices: `%s`",
//                                        amount, multipleChoices), false);
//                    });
//
//                    reply.editOriginalEmbeds(builder.build()).queue();
//                });




//                Set<GuildMessageChannel> channels = reactionRoles.getAllChannels();
//                if (channels.isEmpty()) builder.setDescription("No reaction roles found.");
//                else channels.stream().limit(25).forEach(ch -> {
//                    reactionRoles.getAllMessageIds(ch).forEach(msg -> {
//                        StringBuilder roles = new StringBuilder();
//                        StringBuilder emojis = new StringBuilder();
//                        Set<Emoji> emojiSet = reactionRoles.getAllEmojis(ch, msg);
//                        emojiSet.forEach(emoji -> emojis.append(emoji.getFormatted()).append(" "));
//                        roles.append("`")
//                                .append(emojiSet.size())
//                                .append("` reaction roles: ");
//                        builder.addField(msg.getJumpUrl() + " - " + roles, emojis.toString(), false);
//                    });
//                });
            }

    @Override
    public DiscordCommandCategory getCategory() {
        return DiscordCommandCategory.UTILITY;
    }
//            case "remove" -> {
////                reactionRoles.removeRole();
//            }
//        }
//    }

}
