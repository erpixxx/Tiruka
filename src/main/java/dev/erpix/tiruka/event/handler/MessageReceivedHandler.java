package dev.erpix.tiruka.event.handler;

import dev.erpix.tiruka.TirukaApp;
import dev.erpix.tiruka.model.__CachedGuild;
import dev.erpix.tiruka.model.VoteReactionChannel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.GuildMessageChannelUnion;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;

public class MessageReceivedHandler implements EventHandler<MessageReceivedEvent> {

    private final Logger logger = LoggerFactory.getLogger(MessageReceivedHandler.class);
    private final List<String> triggers = List.of("hai", "hej", "cześć", "czesc", "siema");
    private final String triggerAnswer = "Haii!!! :3";

    private final TirukaApp tiruka;

    public MessageReceivedHandler(TirukaApp tiruka) {
        this.tiruka = tiruka;
    }

    @Override
    public void handle(@NotNull MessageReceivedEvent event) {
        SelfUser selfUser = event.getJDA().getSelfUser();
        User author = event.getAuthor();

        if (author == selfUser) return;

        Guild guild = event.getGuild();
        GuildMessageChannelUnion channel = event.getGuildChannel();

//        __CachedGuild cachedGuild = tiruka.getCachedGuild(guild.getId());
//        if (cachedGuild == null) return;
//
//        VoteReactionChannel voteReactionChannel = cachedGuild.getVoteReactionChannel(channel.getId());
//        if (voteReactionChannel != null) {
//            event.getMessage().addReaction(Emoji.fromFormatted("<:plus:1308496012754813029>")).queue();
//            event.getMessage().addReaction(Emoji.fromFormatted("<:minus:1308496010481369170>")).queue();
//        }
//
//        Message message = event.getMessage();
//        String content = message.getContentRaw();
//
//        if (content.toLowerCase(Locale.ENGLISH).startsWith(selfUser.getAsMention())) {
//            String[] splitMessage = content.split(" ");
//            if (splitMessage.length > 1 && triggers.contains(splitMessage[1])) {
//                message.reply(triggerAnswer).queue();
//            }
//        }
    }

    @Override
    public Class<MessageReceivedEvent> getEventClass() {
        return MessageReceivedEvent.class;
    }

}
