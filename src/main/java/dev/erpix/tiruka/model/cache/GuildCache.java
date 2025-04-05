package dev.erpix.tiruka.model.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import dev.erpix.tiruka.model.__CachedGuild;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;

import java.util.concurrent.TimeUnit;


public class GuildCache {

    private final JDA jda;
    private final Cache<String, __CachedGuild> guildCache;

    public GuildCache(JDA jda, long duration) {
        this.jda = jda;
        this.guildCache = CacheBuilder.newBuilder()
                .expireAfterAccess(duration, TimeUnit.MINUTES)
                .build();
    }

    public void cacheGuild(Guild guild) {
//        CachedGuild cachedGuild = new CachedGuild(guild);
//        guildCache.put(cachedGuild.getId(), cachedGuild);
    }

    public __CachedGuild getGuild(String id) {
        return guildCache.getIfPresent(id);
    }

    public void removeGuild(String id) {
        guildCache.invalidate(id);
    }

}
