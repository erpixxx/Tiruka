package dev.erpix.tiruka.model;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class ReactionRoleManager implements GuildFeature {

    private final JDA jda;
    private final String guildId;
    private final Map<String, ReactionRoleNode> messages = new HashMap<>();

    public ReactionRoleManager(JDA jda, String guildId) {
        this.jda = jda;
        this.guildId = guildId;
    }

    public @NotNull Collection<ReactionRoleNode> getAllNodes() {
        return messages.values();
    }

    public void addRole(String channelId, String messageId, Emoji emoji, String roleId) {
        this.messages.computeIfAbsent(messageId, k -> new ReactionRoleNode(jda, guildId, channelId, messageId))
                .addRole(emoji, roleId);
    }

    public void addRoles(String channelId, String messageId, Emoji emoji, List<String> roles) {
        this.messages.computeIfAbsent(messageId, k -> new ReactionRoleNode(jda, guildId, channelId, messageId))
                .addRoles(emoji, roles);
    }

    public @NotNull CompletableFuture<Boolean> addRoleAndApplyReactions(String channelId, String messageId, Emoji emoji, String roleId) {
        return this.messages.computeIfAbsent(messageId, k -> new ReactionRoleNode(jda, guildId, channelId, messageId))
                .addRoleAndApplyReactions(emoji, roleId);
    }

    public @NotNull Set<Emoji> getAllEmojis(String messageId) {
        ReactionRoleNode node = this.messages.get(messageId);
        if (node == null) return Collections.emptySet();
        return node.getAllEmojis();
    }

    public @Nullable ReactionRoleNode getNode(String messageId) {
        ReactionRoleNode node = this.messages.get(messageId);
        if (node == null) return null;
        return messages.get(messageId);
    }

    public @NotNull Set<String> getRolesByEmoji(String messageId, Emoji emoji) {
        ReactionRoleNode node = this.messages.get(messageId);
        if (node == null) return Collections.emptySet();
        return node.getRolesByEmoji(emoji);
    }

    public void setAllowMultipleChoices(String messageId, boolean enabled) {
        ReactionRoleNode node = this.messages.get(messageId);
        if (node != null) node.setMultipleChoices(enabled);
    }

    public void removeAllRoles(String messageId, Emoji emoji) {
        ReactionRoleNode node = this.messages.get(messageId);
        if (node != null) node.removeAllRoles(emoji);
    }

    public void removeRole(String messageId, Emoji emoji, String roleId) {
        ReactionRoleNode node = this.messages.get(messageId);
        if (node != null) node.removeRole(emoji, roleId);
    }

    public @Nullable CompletableFuture<Boolean> removeRoleAndClearReactions(String messageId, Emoji emoji, String roleId) {
        ReactionRoleNode node = this.messages.get(messageId);
        if (node != null) return node.removeRoleAndClearReactions(emoji, roleId);
        return null;
    }

    @Override
    public @NotNull String getGuildId() {
        return guildId;
    }

}

//
//    public Set<GuildMessageChannel> getAllChannels() {
//        return messages.keySet();
//    }
//
//    public Set<Message> getAllMessageIds(GuildMessageChannel channel) {
//        return messages.get(channel).getAllMessages();
//    }
//
//    public Optional<ChannelReactionRoles> getChannelReactionRoles(GuildMessageChannel channel) {
//        return Optional.ofNullable(messages.get(channel));
//    }
//
//    public Optional<MessageReactionRoles> getMessageReactionRoles(GuildMessageChannel channel, Message message) {
//        return Optional.ofNullable(messages.get(channel)).flatMap(c -> c.getMessageReactionRoles(message));
//    }