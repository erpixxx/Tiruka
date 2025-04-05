package dev.erpix.tiruka.cache.entity;

import dev.erpix.tiruka.model.Economy;
import dev.erpix.tiruka.model.GuildStats;
import dev.erpix.tiruka.model.LevelingSystem;

public class CachedGuild extends CachedEntity {

    private final String guildId;
    private final Economy economy;
    private final LevelingSystem levelingSystem;
    private final GuildStats guildStats;
    private long verifiedRoleId;
    private long loggingChannelId;
    private int features;

    public CachedGuild(int expireAfter, long creationTime,
                       String guildId, long verifiedRoleId,
                       long loggingChannelId, int features,
                       Economy economy, LevelingSystem levelingSystem,
                       GuildStats guildStats) {
        super(expireAfter, creationTime);
        this.guildId = guildId;
        this.verifiedRoleId = verifiedRoleId;
        this.loggingChannelId = loggingChannelId;
        this.features = features;
        this.economy = economy;
        this.levelingSystem = levelingSystem;
        this.guildStats = guildStats;
    }

    public static CachedGuild create(String guildId) {
        return new CachedGuild(
                0,
                0,
                guildId,
                -1L,
                -1L,
                0,
                Economy.create(),
                LevelingSystem.create(),
                GuildStats.create()
        );
    }

    public static String getKey(String guildId) {
        return "guild:" + guildId;
    }

    public String getGuildId() {
        return guildId;
    }

    public long getVerifiedRoleId() {
        return verifiedRoleId;
    }

    public long getLoggingChannelId() {
        return loggingChannelId;
    }

    public int getFeatures() {
        return features;
    }

    public Economy getEconomy() {
        return economy;
    }

    public LevelingSystem getLevelingSystem() {
        return levelingSystem;
    }

    public GuildStats getGuildStats() {
        return guildStats;
    }

}
