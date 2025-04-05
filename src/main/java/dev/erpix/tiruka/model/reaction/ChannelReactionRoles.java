package dev.erpix.tiruka.model.reaction;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;

import java.util.*;

public class ChannelReactionRoles {

    private final Map<Message, MessageReactionRoles> messages = new HashMap<>();
    private final Guild guild;
    private final GuildMessageChannel channel;

    public ChannelReactionRoles(Guild guild, GuildMessageChannel channel) {
        this.guild = guild;
        this.channel = channel;
    }

    public void addRole(Message message, Emoji emoji, Role role ) {
        messages.computeIfAbsent(message, k -> new MessageReactionRoles(guild, channel, message)).addRole(emoji, role);
    }

    public void addRoleAndApplyReactions(Message message, Emoji emoji, Role role) {
        messages.computeIfAbsent(message, k -> new MessageReactionRoles(guild, channel, message)).addRoleAndApplyReactions(emoji, role);
    }

    public void addRoles(Message message, Emoji emoji, List<Role> roles) {
        messages.computeIfAbsent(message, k -> new MessageReactionRoles(guild, channel, message)).addRoles(emoji, roles);
    }

    public Set<Emoji> getAllEmojis(Message message) {
        return messages.get(message).getAllEmojis();
    }

    public Set<Message> getAllMessages() {
        return messages.keySet();
    }

    public Optional<MessageReactionRoles> getMessageReactionRoles(Message message) {
        return Optional.ofNullable(messages.get(message));
    }

    public Optional<Set<Role>> getRolesByEmoji(Message message, Emoji emoji) {
        return Optional.ofNullable(messages.get(message)).flatMap(m -> m.getRolesByEmoji(emoji));
    }

    public void setAllowMultipleChoices(Message message, boolean enabled) {
        MessageReactionRoles rr = messages.get(message);
        if (rr != null) rr.setAllowMultipleChoices(enabled);
    }

    public void removeAllRoles(Message message, Emoji emoji) {
        messages.get(message).removeAllRoles(emoji);
    }

    public void removeRole(Message message, Emoji emoji, Role role) {
        messages.get(message).removeRole(emoji, role);
    }

    public Guild getGuild() {
        return guild;
    }

    public GuildMessageChannel getChannel() {
        return channel;
    }

}
