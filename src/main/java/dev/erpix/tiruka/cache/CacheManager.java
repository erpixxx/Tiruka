package dev.erpix.tiruka.cache;

import dev.erpix.tiruka.TirukaApp;
import dev.erpix.tiruka.cache.entity.CachedEntity;
import dev.erpix.tiruka.cache.entity.CachedGuild;
import dev.erpix.tiruka.cache.entity.CachedGuildMember;
import dev.erpix.tiruka.cache.handler.MemoryCacheHandler;
import dev.erpix.tiruka.cache.handler.RedisCacheHandler;
import dev.erpix.tiruka.storage.Storage;
import net.dv8tion.jda.api.JDA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class CacheManager {

    private static final Logger logger = LoggerFactory.getLogger(CacheManager.class);

    private final Storage storage;
    private final MemoryCacheHandler memoryCache;
    private final RedisCacheHandler redisCache;

    public CacheManager(Storage storage, MemoryCacheHandler memoryCache, RedisCacheHandler redisCache) {
        this.storage = storage;
        this.memoryCache = memoryCache;
        this.redisCache = redisCache;
    }

    public CacheManager(Storage storage, MemoryCacheHandler memoryCache) {
        this.storage = storage;
        this.memoryCache = memoryCache;
        this.redisCache = null;
    }

    public void syncCache(String key, String field, String value) {
        if (redisCache != null) {

        }
    }

    private <T extends CachedEntity> CompletableFuture<T> getCachedEntity(Class<T> entityClass, String... params) {
        return null;
    }

    public CompletableFuture<CachedGuild> getGuild(String guildId) {
        return CompletableFuture.supplyAsync(() -> {
            CachedGuild guild;

            guild = memoryCache.getGuild(guildId);
            if (guild != null) {
                logger.info("Guild {} retrieved from memory cache ({})", guildId, guild);
                return guild;
            }

            if (redisCache != null) {
                guild = redisCache.getGuild(guildId);
                if (guild != null) {
                    logger.info("Guild {} retrieved from Redis cache", guildId);
                    update(guild, memoryCache::updateGuild);
                    return guild;
                }
            }

            guild = storage.getGuild(guildId).join();
            if (guild != null) {
                logger.info("Guild {} retrieved from storage", guildId);
                update(guild, memoryCache::updateGuild, redisCache != null ? redisCache::updateGuild : null);
                return guild;
            }

            // If guild is not found in any cache or storage, create a new guild object
            guild = CachedGuild.create(guildId);
            update(guild, memoryCache::updateGuild, redisCache != null ? redisCache::updateGuild : null);
            storage.addGuild(guild).thenAccept((var) -> {
                logger.info("Guild {} added to storage", guildId);
            });
            return guild;
        });
    }

    public CompletableFuture<CachedGuildMember> getGuildMember(String guildId, String memberId) {
        return CompletableFuture.supplyAsync(() -> {
            CachedGuildMember member;

            member = memoryCache.getGuildMember(guildId, memberId);
            if (member != null) {
                logger.info("Guild member {} retrieved from memory cache ({})", memberId, member);
                return member;
            }

            if (redisCache != null) {
                member = redisCache.getGuildMember(guildId, memberId);
                if (member != null) {
                    logger.info("Guild member {} retrieved from Redis cache", memberId);
                    update(member, memoryCache::updateGuildMember);
                    return member;
                }
            }

            member = storage.getGuildMember(guildId, memberId).join();
            if (member != null) {
                logger.info("Guild member {} retrieved from storage", memberId);
                update(member, memoryCache::updateGuildMember, redisCache != null ? redisCache::updateGuildMember : null);
                return member;
            }

            // Create new guild member object by retrieving it from Discord API
            throw new UnsupportedOperationException("Not implemented yet");
        });
    }

    @SafeVarargs
    private <T> void update(T value, Consumer<? super T>... updateMethods) {
        for (Consumer<? super T> method : updateMethods) {
            if (method != null) method.accept(value);
        }
    }

}
