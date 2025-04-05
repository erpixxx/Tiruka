package dev.erpix.tiruka.model.reaction;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;

import java.util.*;

public class MessageReactionRoles {

    private final Map<Emoji, Set<Role>> roles = new HashMap<>();
    private final Guild guild;
    private final GuildMessageChannel channel;
    private final Message message;
    private boolean allowMultipleChoices = false;

    public MessageReactionRoles(Guild guild, GuildMessageChannel channel, Message message) {
        this.guild = guild;
        this.channel = channel;
        this.message = message;
    }

    public void addRole(Emoji emoji, Role role) {
        roles.computeIfAbsent(emoji, k -> new HashSet<>()).add(role);
    }

    public void addRoleAndApplyReactions(Emoji emoji, Role role) {
        message.addReaction(emoji).queue();
        addRole(emoji, role);
    }

    public void addRoles(Emoji emoji, List<Role> roles) {
        this.roles.computeIfAbsent(emoji, k -> new HashSet<>()).addAll(roles);
    }

    public Set<Emoji> getAllEmojis() {
        return roles.keySet();
    }

    public Optional<Set<Role>> getRolesByEmoji(Emoji emoji) {
        return Optional.ofNullable(roles.get(emoji));
    }

    public void setAllowMultipleChoices(boolean enabled) {
        allowMultipleChoices = enabled;
    }

    public void removeAllRoles(Emoji emoji) {
        roles.remove(emoji);
    }

    public void removeRole(Emoji emoji, Role role) {
        roles.get(emoji).remove(role);
    }

    public void removeRoleAndClearReactions(Emoji emoji, Role role) {
        message.clearReactions(emoji).queue();
        removeRole(emoji, role);
    }

    public Guild getGuild() {
        return guild;
    }

    public GuildMessageChannel getChannel() {
        return channel;
    }

    public Message getMessage() {
        return message;
    }

}
