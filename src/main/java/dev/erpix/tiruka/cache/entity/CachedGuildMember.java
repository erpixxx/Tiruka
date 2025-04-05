package dev.erpix.tiruka.cache.entity;

import dev.erpix.tiruka.TirukaApp;
import dev.erpix.tiruka.cache.CacheManager;
import dev.erpix.tiruka.model.LevelingSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CachedGuildMember extends CachedEntity {

    private static final Logger logger = LoggerFactory.getLogger(CachedGuildMember.class);

    private final String guildId;
    private final String memberId;
    @Path("balance")
    private long balance;
    private int level;
    private long experience;
    private transient long maxExperience;

    public CachedGuildMember(int expireAfter, long creationTime, String guildId, String memberId, long balance, int level, long experience) {
        super(expireAfter, creationTime);
        this.guildId = guildId;
        this.memberId = memberId;
        this.balance = balance;
        this.level = level;
        this.experience = experience;
        TirukaApp.getInstance().getCache().getGuild(guildId).thenAccept(guild -> {
            LevelingSystem levelingSystem = guild.getLevelingSystem();

            this.maxExperience = levelingSystem.getExperienceForLevel(level);
        });
    }

    public static String getKey(String guildIdAndMemberId) {
        return "guild_member:" + guildIdAndMemberId;
    }

    public String getGuildId() {
        return guildId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void levelDown() {
        CacheManager cache = TirukaApp.getInstance().getCache();
        TirukaApp.getInstance().getCache().getGuild(guildId).thenAccept(guild -> {
            LevelingSystem levelingSystem = guild.getLevelingSystem();

            level--;
            maxExperience = levelingSystem.getExperienceForLevel(level);

            // update redis cache, send pub/sub to other instances and add to queue
//            cache.syncCache();
        });
    }

    public void levelUp() {
        TirukaApp.getInstance().getCache().getGuild(guildId).thenAccept(guild -> {
            LevelingSystem levelingSystem = guild.getLevelingSystem();

            level++;
            maxExperience = levelingSystem.getExperienceForLevel(level);
        });
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        TirukaApp.getInstance().getCache().getGuild(guildId).thenAccept(guild -> {
            LevelingSystem levelingSystem = guild.getLevelingSystem();

            this.level = level;
            experience = 0L;
            maxExperience = levelingSystem.getExperienceForLevel(level);
        });
    }

    public void addExperience(long value) {
        experience += value;
        while (experience >= maxExperience) {
            experience -= maxExperience;
            levelUp();
        }
    }

    public long getExperience() {
        return experience;
    }

    public void setExperience(long experience) {
        if (experience < 0) throw new IllegalArgumentException("Experience cannot be negative");
        while (experience >= maxExperience) {
            experience -= maxExperience;
            levelUp();
        }
        this.experience = experience;
    }

    public void removeExperience(long value) {
        experience -= value;
        while (experience < 0) {
            experience += maxExperience;
            levelDown();
        }
    }

    public long getMaxExperience() {
        return maxExperience;
    }

    public void addBalance(int value) {
        balance += value;
    }

    public long getBalance() {
        return balance;
    }

    public String getBalanceFormatted() {
        return new DecimalFormat("#,###", new DecimalFormatSymbols(Locale.ENGLISH)).format(new BigDecimal(balance));
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void removeBalance(int value) {
        balance -= value;
    }

}
