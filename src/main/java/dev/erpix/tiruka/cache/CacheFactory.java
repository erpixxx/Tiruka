package dev.erpix.tiruka.cache;

import dev.erpix.tiruka.Dependency;
import dev.erpix.tiruka.DependencyLoader;
import dev.erpix.tiruka.TirukaApp;
import dev.erpix.tiruka.cache.handler.MemoryCacheHandler;
import dev.erpix.tiruka.cache.handler.RedisCacheHandler;
import dev.erpix.tiruka.cache.handler.RedisCredentials;
import dev.erpix.tiruka.config.ConfigSchema;
import dev.erpix.tiruka.storage.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheFactory {

    private static final Logger logger = LoggerFactory.getLogger(CacheFactory.class);

    public static CacheManager create() {
        ConfigSchema config = TirukaApp.getInstance().getConfig();
        DependencyLoader dependencyLoader = TirukaApp.getInstance().getDependencyLoader();
        Storage storage = TirukaApp.getInstance().getStorage();

        var memoryHandler = createMemoryCacheHandler(config);
        var redisConfig = config.getCache().getRedis();
        if (redisConfig.getEnabled()) {
            RedisCacheHandler redisHandler = createRedisCacheHandler(redisConfig, dependencyLoader);
            if (redisHandler != null) {
                return new CacheManager(storage, memoryHandler, redisHandler);
            }
        }

        return new CacheManager(storage, memoryHandler);
    }

    private static MemoryCacheHandler createMemoryCacheHandler(ConfigSchema config) {
        var memoryRules = config.getCache().getMemory();
        return new MemoryCacheHandler(
                memoryRules.getExpireAfterAccess(),
                memoryRules.getExpireAfterWrite(),
                memoryRules.getMaximumSize()
        );
    }

    private static RedisCacheHandler createRedisCacheHandler(ConfigSchema.Cache.Redis redisConfig, DependencyLoader dependencyLoader) {
        dependencyLoader.loadAll(Dependency.REDIS, Dependency.COMMONS_POOL2);
        try {
            RedisCredentials credentials = buildRedisCredentials(redisConfig);
            RedisCacheHandler redisHandler = new RedisCacheHandler(credentials);
            redisHandler.testConnection();
            return redisHandler;
        } catch (Exception e) {
            logger.error("Failed to connect to Redis server. Using memory cache as fallback.", e);
            return null;
        }
    }

    private static RedisCredentials buildRedisCredentials(ConfigSchema.Cache.Redis redisConfig) {
        var credentialsConfig = redisConfig.getCredentials();
        var poolSettings = redisConfig.getPoolSettings();

        return new RedisCredentials(
                credentialsConfig.getHostname(),
                credentialsConfig.getPort(),
                credentialsConfig.getUsername(),
                credentialsConfig.getPassword(),
                credentialsConfig.getDatabase(),
                poolSettings.getMaximumIdle(),
                poolSettings.getMaximumPoolSize(),
                poolSettings.getMinimumIdle(),
                redisConfig.getExpireAfterAccess(),
                redisConfig.getExpireAfterWrite()
        );
    }

}
