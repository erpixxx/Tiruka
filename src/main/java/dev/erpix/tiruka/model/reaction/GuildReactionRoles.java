package dev.erpix.tiruka.model.reaction;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;

import java.util.*;

public class GuildReactionRoles {

    private final Map<GuildMessageChannel, ChannelReactionRoles> messages = new HashMap<>();
    private final Guild guild;

    public GuildReactionRoles(Guild guild) {
        this.guild = guild;
    }

    public void addRole(GuildMessageChannel channel, Message message, Emoji emoji, Role role ) {
        messages.computeIfAbsent(channel, k -> new ChannelReactionRoles(guild, channel)).addRole(message, emoji, role);
    }

    public void addRoles(GuildMessageChannel channel, Message message, Emoji emoji, List<Role> roles) {
        messages.computeIfAbsent(channel, k -> new ChannelReactionRoles(guild, channel)).addRoles(message, emoji, roles);
    }

    public void addRoleAndApplyReactions(GuildMessageChannel channel, Message message, Emoji emoji, Role role) {
        messages.computeIfAbsent(channel, k -> new ChannelReactionRoles(guild, channel)).addRoleAndApplyReactions(message, emoji, role);
    }

    public Set<Emoji> getAllEmojis(GuildMessageChannel channel, Message message) {
        return messages.get(channel).getAllEmojis(message);
    }

    public Set<GuildMessageChannel> getAllChannels() {
        return messages.keySet();
    }

    public Set<Message> getAllMessageIds(GuildMessageChannel channel) {
        return messages.get(channel).getAllMessages();
    }

    public Optional<ChannelReactionRoles> getChannelReactionRoles(GuildMessageChannel channel) {
        return Optional.ofNullable(messages.get(channel));
    }

    public Optional<MessageReactionRoles> getMessageReactionRoles(GuildMessageChannel channel, Message message) {
        return Optional.ofNullable(messages.get(channel)).flatMap(c -> c.getMessageReactionRoles(message));
    }

    public Optional<Set<Role>> getRolesByEmoji(GuildMessageChannel channel, Message message, Emoji emoji) {
        return Optional.ofNullable(messages.get(channel)).flatMap(c -> c.getRolesByEmoji(message, emoji));
    }

    public void setAllowMultipleChoices(GuildMessageChannel channel, Message message, boolean enabled) {
        ChannelReactionRoles rr = messages.get(channel);
        if (rr != null) rr.setAllowMultipleChoices(message, enabled);
    }

    public void removeAllRoles(GuildMessageChannel channel, Message message, Emoji emoji) {
        messages.get(channel).removeAllRoles(message, emoji);
    }

    public void removeRole(GuildMessageChannel channel, Message message, Emoji emoji, Role role) {
        messages.get(channel).removeRole(message, emoji, role);
    }

    public Guild getGuild() {
        return guild;
    }

}
