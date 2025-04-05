package dev.erpix.tiruka.model;

import dev.erpix.tiruka.TirukaApp;
import net.dv8tion.jda.api.entities.Member;

// SQL Bulk Updates
public class LevelingSystem {

    private int baseMaxExperience;

//    private int maxLevel = 50;
    ;
    private int chatMessageValueMin;
    private int chatMessageValueMax;
    private int chatMessageInterval; // seconds
    private int voiceActivityValueMin;
    private int voiceActivityValueMax;
    private int voiceActivityInterval; // seconds

//    private long levelProgressChannelId = 0L;

    // recalculate method

    public LevelingSystem(int baseMaxExperience, int chatMessageValueMax, int chatMessageValueMin, int chatMessageInterval, int voiceActivityValueMax, int voiceActivityValueMin, int voiceActivityInterval) {
        this.baseMaxExperience = baseMaxExperience;
        this.chatMessageValueMax = chatMessageValueMax;
        this.chatMessageValueMin = chatMessageValueMin;
        this.chatMessageInterval = chatMessageInterval;
        this.voiceActivityValueMax = voiceActivityValueMax;
        this.voiceActivityValueMin = voiceActivityValueMin;
        this.voiceActivityInterval = voiceActivityInterval;
    }

    public static LevelingSystem create() {
        var defaults = TirukaApp.getInstance().getConfig().getFeatures().getLevelingSystem().getDefaultSettings();
        return new LevelingSystem(
                defaults.getBaseMaxExperience(),
                defaults.getChatMessageValueMax(),
                defaults.getChatMessageValueMin(),
                defaults.getChatMessageInterval(),
                defaults.getVoiceActivityValueMax(),
                defaults.getVoiceActivityValueMin(),
                defaults.getVoiceActivityInterval()
        );
    }

    public void levelUpMessage(Member member, int level) {
        // embed
    }

    public long getExperienceForLevel(int level) {
        return (long) ((baseMaxExperience * Math.pow(level - 1, 0.8)) / 100) * 10;
    }

    public int getBaseMaxExperience() {
        return baseMaxExperience;
    }

    public void setBaseMaxExperience(int baseMaxExperience) {
        this.baseMaxExperience = baseMaxExperience;
    }

    public int getChatMessageValueMin() {
        return chatMessageValueMin;
    }

    public void setChatMessageValueMin(int chatMessageValueMin) {
        this.chatMessageValueMin = chatMessageValueMin;
    }

    public int getChatMessageValueMax() {
        return chatMessageValueMax;
    }

    public void setChatMessageValueMax(int chatMessageValueMax) {
        this.chatMessageValueMax = chatMessageValueMax;
    }

    public int getChatMessageInterval() {
        return chatMessageInterval;
    }

    public void setChatMessageInterval(int chatMessageInterval) {
        this.chatMessageInterval = chatMessageInterval;
    }

    public int getVoiceActivityValueMin() {
        return voiceActivityValueMin;
    }

    public void setVoiceActivityValueMin(int voiceActivityValueMin) {
        this.voiceActivityValueMin = voiceActivityValueMin;
    }

    public int getVoiceActivityValueMax() {
        return voiceActivityValueMax;
    }

    public void setVoiceActivityValueMax(int voiceActivityValueMax) {
        this.voiceActivityValueMax = voiceActivityValueMax;
    }

    public int getVoiceActivityInterval() {
        return voiceActivityInterval;
    }

    public void setVoiceActivityInterval(int voiceActivityInterval) {
        this.voiceActivityInterval = voiceActivityInterval;
    }
}
