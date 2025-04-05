package dev.erpix.tiruka.cache;

import redis.clients.jedis.JedisPubSub;

public class MemoryCacheUpdater extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        super.onMessage(channel, message);
    }

}
