package dev.erpix.tiruka.model;

public class CachedUser {

    // guild
    private final LevelingSystem levelingSystem;
    private int level;
    private long experience;
    private long maxExperience;
    private int balance;

    public CachedUser(LevelingSystem levelingSystem, int level, long experience, long maxExperience, int balance) {
        this.levelingSystem = levelingSystem;
        this.level = level;
        this.experience = experience;
        this.maxExperience = maxExperience;
        this.balance = balance;
    }

    public void levelDown() {
        level--;
        maxExperience = levelingSystem.getExperienceForLevel(level);
    }

    public void levelUp() {
        level++;
        maxExperience = levelingSystem.getExperienceForLevel(level);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        experience = 0L;
        maxExperience = levelingSystem.getExperienceForLevel(level);
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

    public int getBalance() {
        return balance;
    }

    public String getBalanceFormatted() {
        // use BigDecimal with formatting
        return "balance";
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void removeBalance(int value) {
        balance -= value;
    }

}
