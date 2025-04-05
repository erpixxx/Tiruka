package dev.erpix.tiruka.cache.handler;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import dev.erpix.tiruka.cache.entity.CachedEntity;
import dev.erpix.tiruka.cache.entity.CachedGuild;
import dev.erpix.tiruka.cache.entity.CachedGuildMember;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * <p><b>Memory Cache Implementation</b></p>
 *
 * This class is responsible for caching frequently accessed data directly in memory,
 * providing the fastest read access compared to Redis or the database.</p>
 */
public class MemoryCacheHandler implements CacheHandler {

    private final Cache<String, CachedEntity> cache;

    private final Cache<String, CachedGuild> guildCache = CacheBuilder.newBuilder()
            .expireAfterAccess(150, TimeUnit.SECONDS)
            .expireAfterWrite(150, TimeUnit.SECONDS)
            .maximumSize(100)
            .recordStats()
            .build();
    private final Cache<String, CachedGuildMember> guildMemberCache = CacheBuilder.newBuilder()
            .expireAfterAccess(150, TimeUnit.SECONDS)
            .expireAfterWrite(150, TimeUnit.SECONDS)
            .maximumSize(100)
            .recordStats()
            .build();

    public MemoryCacheHandler(long expireAfterAccess, long expireAfterWrite, long maximumSize) {
        this.cache = CacheBuilder.newBuilder()
                .expireAfterAccess(expireAfterAccess, TimeUnit.SECONDS)
                .expireAfterWrite(expireAfterWrite, TimeUnit.SECONDS)
                .maximumSize(maximumSize)
                .recordStats()
                .build();
    }

    @Override
    public void updateEntity(CachedEntity entity, Class<? extends CachedEntity> entityClass, String param) {
        try {
            Method getKey = entityClass.getDeclaredMethod("getKey", String.class);
            String key = (String) getKey.invoke(entityClass, param);
            cache.put(key, entity);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public @Nullable CachedGuild getGuild(String guildId) {
        return (CachedGuild) cache.getIfPresent(CachedGuild.getKey(guildId));
    }

    @Override
    public @Nullable CachedGuildMember getGuildMember(String guildId, String memberId) {
        return (CachedGuildMember) cache.getIfPresent(CachedGuildMember.getKey(guildId + ':' + memberId));
    }

//    @Override
//    public void updateGuild(CachedGuild cachedGuild) {
//        cache.put(CachedGuild.getKey(cachedGuild.getGuildId()), cachedGuild);
//
//        cache.asMap().forEach((k, v) -> {
//            System.out.println(k + " -> " + v);
//        });
//    }

}
