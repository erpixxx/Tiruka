package dev.erpix.tiruka.cache.handler;

import dev.erpix.tiruka.cache.entity.CachedEntity;
import dev.erpix.tiruka.cache.entity.CachedGuild;
import dev.erpix.tiruka.cache.entity.CachedGuildMember;
import org.jetbrains.annotations.Nullable;

/**
 * <p><b>Cache Handler Interface</b></p>
 *
 * This interface is responsible for handling the caching of frequently accessed data.
 */
public interface CacheHandler {

    @Nullable CachedGuild getGuild(String guildId);

    // getGuildOrNew

    @Nullable CachedGuildMember getGuildMember(String guildId, String memberId);

    void updateEntity(CachedEntity entity, Class<? extends CachedEntity> entityClass, String param);

    default void updateGuild(CachedGuild cachedGuild) {
        updateEntity(cachedGuild, CachedGuild.class, cachedGuild.getGuildId());
    }

    default void updateGuildMember(CachedGuildMember cachedGuildMember) {
        String key = cachedGuildMember.getGuildId() + ':' + cachedGuildMember.getMemberId();
        updateEntity(cachedGuildMember, CachedGuildMember.class, key);
    }

}
