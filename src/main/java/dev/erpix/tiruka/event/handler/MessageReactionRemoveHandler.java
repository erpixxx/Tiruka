package dev.erpix.tiruka.event.handler;

import dev.erpix.tiruka.model.SelectableRolesManager;
import dev.erpix.tiruka.model.reaction.GuildReactionRoles;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.unions.GuildMessageChannelUnion;
import net.dv8tion.jda.api.entities.emoji.EmojiUnion;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;

import java.util.Collections;

public class MessageReactionRemoveHandler implements EventHandler<MessageReactionRemoveEvent> {

    private final SelectableRolesManager selectableRolesManager;

    public MessageReactionRemoveHandler(SelectableRolesManager selectableRolesManager) {
        this.selectableRolesManager = selectableRolesManager;
    }

    @Override
    public void handle(MessageReactionRemoveEvent event) {
        if (event.getUser() == event.getJDA().getSelfUser()) return;

        Guild guild = event.getGuild();
        GuildMessageChannelUnion channel = event.getGuildChannel();
        String messageId = event.getMessageId();
        EmojiUnion emoji = event.getEmoji();
        String memberId = event.getUserId();

//        GuildReactionRoles rr = selectableRolesManager.reactionRoles(guild);
//        channel.retrieveMessageById(messageId).queue(message -> {
//            rr.getRolesByEmoji(channel, message, emoji).ifPresent(roles -> {
//                Member member = guild.retrieveMemberById(memberId).submit().join();
//                guild.modifyMemberRoles(member, Collections.emptyList(), roles).queue();
//            });
//        });
    }

    @Override
    public Class<MessageReactionRemoveEvent> getEventClass() {
        return MessageReactionRemoveEvent.class;
    }

}
