package dev.erpix.tiruka.model;

import java.util.HashSet;
import java.util.Set;

public class VoteReactionChannel {

    private final String channelId;
    private final Set<String> emojis = new HashSet<>();
    // emojis
    // stats

    public VoteReactionChannel(String channelId) {
        this.channelId = channelId;
    }

    public String getId() {
        return channelId;
    }
}
