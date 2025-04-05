package dev.erpix.tiruka.logging;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class GuildLogger {

    private final Logger logger = LoggerFactory.getLogger(GuildLogger.class);
    private final Map<Guild, TextChannel> channels = new HashMap<>();

    public void log(GenericEvent event) {
        if (event instanceof MessageDeleteEvent e) {
            Guild guild = e.getGuild();
            TextChannel channel = channels.get(guild);

            if (channel != null) {
                MessageEmbed msg = new EmbedBuilder().setTitle("Message delete").setDescription("test").build();
                channel.sendMessageEmbeds(msg).queue();
            }
        }
    }

    public void findAndRegisterChannel(Guild guild, String channelName) {
        guild.getTextChannelsByName(channelName, true).forEach(channel -> {
            if (channels.putIfAbsent(guild, channel) == null)
                logger.info("Found logging channel for guild '{} ({})'", guild.getName(), guild.getId());
        });
    }

}
