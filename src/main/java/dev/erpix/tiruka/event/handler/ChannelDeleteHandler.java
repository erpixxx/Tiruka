//package dev.erpix.tiruka.event.handler;
//
//import dev.erpix.tiruka.TirukaApp;
//import dev.erpix.tiruka.model.CachedGuild;
//import dev.erpix.tiruka.model.GuildDynamicVoiceChatManager;
//import net.dv8tion.jda.api.events.channel.ChannelDeleteEvent;
//
//public class ChannelDeleteHandler implements EventHandler<ChannelDeleteEvent> {
//
//    private final TirukaApp tiruka;
//
//    public ChannelDeleteHandler(TirukaApp tiruka) {
//        this.tiruka = tiruka;
//    }
//
//    @Override
//    public void handle(ChannelDeleteEvent event) {
//        CachedGuild cachedGuild = tiruka.getCachedGuild(event.getGuild().getId());
//        if (cachedGuild == null) return;
//
//        GuildDynamicVoiceChatManager dynamicVCManager = cachedGuild.getDynamicVCManager();
//        String channelId = event.getChannel().getId();
//
//        if (dynamicVCManager.isActiveVoiceChannel(channelId)) {
//            dynamicVCManager.removeVoiceChannel(channelId);
//        }
//    }
//
//    @Override
//    public Class<ChannelDeleteEvent> getEventClass() {
//        return ChannelDeleteEvent.class;
//    }
//
//}
