package dev.erpix.tiruka.cache.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import dev.erpix.tiruka.cache.entity.CachedEntity;
import dev.erpix.tiruka.cache.entity.CachedGuild;
import dev.erpix.tiruka.cache.entity.CachedGuildMember;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Connection;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.params.GetExParams;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <p><b>Redis Cache Implementation using Multi-Level Caching (Memory → Redis → Database)</b></p>
 *
 * <p><b>Overview:</b><br>
 * This caching mechanism optimizes data retrieval by utilizing three layers of storage:</p>
 *
 * <ol>
 *   <li><b>Memory:</b> Fastest data retrieval with the shortest TTL.</li>
 *   <li><b>Redis:</b> Secondary cache with a longer TTL to reduce database queries.</li>
 *   <li><b>Database:</b> Persistent storage, accessed only when necessary.</li>
 * </ol>
 *
 * <p><b>Data Flow:</b></p>
 * <ol>
 *   <li>Check <b>Memory</b> → If data exists, return it immediately.</li>
 *   <li>If not in Memory, check <b>Redis</b> → If data exists, return it and cache in Memory.</li>
 *   <li>If not in Redis, fetch from <b>Database</b> → Cache the data in Redis and Memory.</li>
 *   <li>When data is updated, it is <b>written to the Database first</b>, then updated in <b>Redis</b>, and finally in <b>Memory</b>.</li>
 * </ol>
 *
 * <p>Note: This strategy is still experimental and may require further testing and optimization.</p>
 */
public class RedisCacheHandler implements CacheHandler {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheHandler.class);

    private final JedisPooled jedis;
    private final Gson gson;
    private final int expireAfterAccess;
    private final int expireAfterWrite;

    public RedisCacheHandler(RedisCredentials credentials) {
        GenericObjectPoolConfig<Connection> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxTotal(credentials.maximumPoolSize());
        poolConfig.setMaxIdle(credentials.maximumIdle());
        poolConfig.setMinIdle(credentials.minimumIdle());

        this.jedis = new JedisPooled(poolConfig, credentials.address(), credentials.port(), credentials.username(), credentials.password());
        this.gson = new GsonBuilder().create();
        this.expireAfterAccess = credentials.expireAfterAccess();
        this.expireAfterWrite = credentials.expireAfterWrite();
    }

    public void testConnection() {
        jedis.ping();
    }

    @Override
    public void updateEntity(CachedEntity entity, Class<? extends CachedEntity> entityClass, String param) {
        String json = gson.toJson(entity, entityClass);
        try {
            Method getKey = entityClass.getDeclaredMethod("getKey", String.class);
            String key = (String) getKey.invoke(entityClass, param);
            jedis.setex(key, expireAfterWrite, json);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @Nullable CachedGuild getGuild(String guildId) {
        String guildJson = jedis.getEx(CachedGuild.getKey(guildId), new GetExParams().ex(expireAfterAccess));
        if (guildJson != null) {
            try {
                return gson.fromJson(guildJson, CachedGuild.class);
            } catch (JsonSyntaxException e) {
                logger.error("Error occurred while parsing JSON value from Redis: {}", guildJson, e);
            }
        }
        return null;
    }

    @Override
    public @Nullable CachedGuildMember getGuildMember(String guildId, String memberId) {
        String guildJson = jedis.getEx(CachedGuildMember.getKey(guildId + ':' + memberId), new GetExParams().ex(expireAfterAccess));
        if (guildJson != null) {
            try {
                return gson.fromJson(guildJson, CachedGuildMember.class);
            } catch (JsonSyntaxException e) {
                logger.error("Error occurred while parsing JSON value from Redis: {}", guildJson, e);
            }
        }
        return null;
    }

//    @Override
//    public void updateGuild(CachedGuild cachedGuild) {
//        updateEntity(cachedGuild, cachedGuild.getClass(), cachedGuild.getGuildId());
////        String guildJson = gson.toJson(cachedGuild);
////        jedis.setex(CachedGuild.getKey(cachedGuild.getGuildId()), ttl, guildJson);
//    }

}
