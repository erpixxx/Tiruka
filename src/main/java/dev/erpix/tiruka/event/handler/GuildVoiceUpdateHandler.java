package dev.erpix.tiruka.event.handler;

import dev.erpix.tiruka.TirukaApp;
import dev.erpix.tiruka.model.__CachedGuild;
import dev.erpix.tiruka.model.GuildDynamicVCManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class GuildVoiceUpdateHandler implements EventHandler<GuildVoiceUpdateEvent> {

    private final TirukaApp tiruka;

    public GuildVoiceUpdateHandler(TirukaApp tiruka) {
        this.tiruka = tiruka;
    }

    @Override
    public void handle(GuildVoiceUpdateEvent event) {
        Guild guild = event.getGuild();
        AudioChannelUnion joinedChannel = event.getChannelJoined();
        AudioChannelUnion leftChannel = event.getChannelLeft();

//        __CachedGuild cachedGuild = tiruka.getCachedGuild(guild.getId());
//        if (cachedGuild == null) return;
//
//        GuildDynamicVCManager dynamicVCManager = cachedGuild.getDynamicVCManager();
//
//        if (joinedChannel != null) {
//            String generatorChannelId = dynamicVCManager.getGeneratorChannelId();
//
//            if (joinedChannel.getId().equals(generatorChannelId)) {
//                Category category = joinedChannel.getParentCategory();
//                Member member = event.getMember();
//
//                createVoiceChannel(guild, category, member, dynamicVCManager);
//            }
//        }
//
//        if (leftChannel != null && leftChannel.getMembers().isEmpty()) {
//            if (dynamicVCManager.isActiveVoiceChannel(leftChannel.getId())) {
//                dynamicVCManager.removeVoiceChannel(leftChannel.getId());
//                leftChannel.asVoiceChannel().delete().queue(null, error -> {
//                    // probably the channel was deleted earlier
//                });
//            }
//        }
    }

    private void createVoiceChannel(Guild guild, Category category, Member member, GuildDynamicVCManager dynamicVCManager) {
        String channelName = member.getUser().getName();

        Consumer<VoiceChannel> setupChannel = vc -> {
            dynamicVCManager.addVoiceChannel(vc.getId());
            guild.moveVoiceMember(member, vc).queue();
            vc.getManager().putPermissionOverride(
                            member,
                            List.of(Permission.MANAGE_CHANNEL),
                            Collections.emptyList())
                    .putPermissionOverride(
                            guild.getPublicRole(),
                            List.of(Permission.VOICE_SPEAK,
                                    Permission.VOICE_USE_SOUNDBOARD,
                                    Permission.VOICE_USE_EXTERNAL_SOUNDS,
                                    Permission.VOICE_STREAM),
                            Collections.emptyList())
                    .queue();
        };

        if (category != null) category.createVoiceChannel(channelName).queue(setupChannel);
        else guild.createVoiceChannel(channelName).queue(setupChannel);
    }

    @Override
    public Class<GuildVoiceUpdateEvent> getEventClass() {
        return GuildVoiceUpdateEvent.class;
    }

}
