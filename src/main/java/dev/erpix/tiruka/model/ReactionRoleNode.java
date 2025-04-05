package dev.erpix.tiruka.model;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.CompletableFuture;

// TODO: Consider using emoji ID instead of Emoji object (exclude emoji name)
public class ReactionRoleNode {

    private final JDA jda;
    private final String guildId;
    private final String channelId;
    private final String messageId;

    private final Map<Emoji, Set<String>> roles = new HashMap<>();
    private boolean multipleChoices = false;

    public ReactionRoleNode(JDA jda, String guildId, String channelId, String messageId) {
        this.jda = jda;
        this.guildId = guildId;
        this.channelId = channelId;
        this.messageId = messageId;
    }

    // TODO: Notify user if emoji is already associated with role
    public void addRole(Emoji emoji, String roleId) {
        roles.computeIfAbsent(emoji, k -> new HashSet<>()).add(roleId);
    }

    public @NotNull CompletableFuture<Boolean> addRoleAndApplyReactions(Emoji emoji, String roleId) {
        Guild guild = jda.getGuildById(guildId);
        if (guild == null) return CompletableFuture.completedFuture(false);

        GuildMessageChannel channel = (GuildMessageChannel) guild.getGuildChannelById(channelId);
        if (channel == null) return CompletableFuture.completedFuture(false);

        return channel.retrieveMessageById(messageId).submit().thenCompose(msg ->
                msg.addReaction(emoji).submit().thenApply(v -> {
                    addRole(emoji, roleId);
                    return true;
                }).exceptionally(ex -> false)
        ).exceptionally(ex -> false);
    }

    public void addRoles(Emoji emoji, List<String> roles) {
        this.roles.computeIfAbsent(emoji, k -> new HashSet<>()).addAll(roles);
    }

    public @NotNull Set<Emoji> getAllEmojis() {
        return roles.keySet();
    }

    public @NotNull Set<String> getRolesByEmoji(Emoji emoji) {
        return roles.get(emoji);
    }

    public void setMultipleChoices(boolean enabled) {
        multipleChoices = enabled;
    }

    public void removeAllRoles(Emoji emoji) {
        roles.remove(emoji);
    }

    public void removeRole(Emoji emoji, String roleId) {
        roles.get(emoji).remove(roleId);
    }

    public @NotNull CompletableFuture<Boolean> removeRoleAndClearReactions(Emoji emoji, String roleId) {
        Guild guild = jda.getGuildById(guildId);
        if (guild == null) return CompletableFuture.completedFuture(false);

        GuildMessageChannel channel = (GuildMessageChannel) guild.getGuildChannelById(channelId);
        if (channel == null) return CompletableFuture.completedFuture(false);

        return channel.retrieveMessageById(messageId).submit().thenCompose(msg ->
                msg.clearReactions(emoji).submit().thenApply(v -> {
                    removeRole(emoji, roleId);
                    return true;
                }).exceptionally(ex -> false)
        ).exceptionally(ex -> false);
    }

    public boolean isMultipleChoices() {
        return multipleChoices;
    }

    public String getGuildId() {
        return guildId;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getMessageId() {
        return messageId;
    }

}
