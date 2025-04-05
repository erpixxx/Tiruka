package dev.erpix.tiruka.cache.entity;

/**
 * Represents a cached entity.
 */
public abstract class CachedEntity{

    private transient final int expireAfter;
    private transient final long creationTime;

    public CachedEntity(int expireAfter, long creationTime) {
        this.expireAfter = expireAfter;
        this.creationTime = creationTime;
    }

    public int getExpireAfter() {
        return expireAfter;
    }

    public long getCreationTime() {
        return creationTime;
    }

}
