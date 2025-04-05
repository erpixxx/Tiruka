package dev.erpix.tiruka.cache.handler;

public record RedisCredentials(
        String address,
        int port,
        String username,
        String password,
        String database,
        int maximumIdle,
        int maximumPoolSize,
        int minimumIdle,
        int expireAfterAccess,
        int expireAfterWrite
) { }
