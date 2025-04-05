package dev.erpix.tiruka.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GuildDynamicVCManager {

    private final List<String> activeChannelsIds = new ArrayList<>();
    private String generatorChannelId = "";

    public void addVoiceChannel(String channelId) {
        activeChannelsIds.add(channelId);
    }

    public List<String> getActiveVoiceChannels() {
        return Collections.unmodifiableList(activeChannelsIds);
    }

    public boolean isActiveVoiceChannel(String channelId) {
        return activeChannelsIds.contains(channelId);
    }

    public void removeVoiceChannel(String channelId) {
        activeChannelsIds.remove(channelId);
    }

    public String getGeneratorChannelId() {
        return generatorChannelId;
    }

    public void setGeneratorChannelId(String generatorChannelId) {
        this.generatorChannelId = generatorChannelId;
    }

}
