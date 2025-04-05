package dev.erpix.tiruka.event.handler;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;

public class GuildMemberJoinHandler implements EventHandler<GuildMemberJoinEvent> {

    @Override
    public void handle(GuildMemberJoinEvent event) {
        Member member = event.getMember();

        PrivateChannel privateChannel = member.getUser().openPrivateChannel().complete();

        privateChannel.sendMessageEmbeds(new EmbedBuilder().setTitle("Haiii").setDescription(":333333").build()).queue();
    }

    @Override
    public Class<GuildMemberJoinEvent> getEventClass() {
        return GuildMemberJoinEvent.class;
    }

}
