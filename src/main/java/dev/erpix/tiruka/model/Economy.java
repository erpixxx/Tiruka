package dev.erpix.tiruka.model;

import dev.erpix.tiruka.TirukaApp;
import org.jetbrains.annotations.NotNull;

public class Economy {

    private String currencySymbol;

    private int dailyBaseValue;
    private int dailyStreakBonus;
    private int weeklyStreakBonus;
    private int monthlyStreakBonus;

    private int chatMessageValueMin;
    private int chatMessageValueMax;
    private int chatMessageInterval; // seconds
    private int voiceActivityValueMin;
    private int voiceActivityValueMax;
    private int voiceActivityInterval; // seconds

    public Economy(String currencySymbol, int dailyBaseValue, int dailyStreakBonus,
                   int weeklyStreakBonus, int monthlyStreakBonus, int chatMessageValueMin,
                   int chatMessageValueMax, int chatMessageInterval, int voiceActivityValueMin,
                   int voiceActivityValueMax, int voiceActivityInterval) {
        this.currencySymbol = currencySymbol;
        this.dailyBaseValue = dailyBaseValue;
        this.dailyStreakBonus = dailyStreakBonus;
        this.weeklyStreakBonus = weeklyStreakBonus;
        this.monthlyStreakBonus = monthlyStreakBonus;
        this.chatMessageValueMin = chatMessageValueMin;
        this.chatMessageValueMax = chatMessageValueMax;
        this.chatMessageInterval = chatMessageInterval;
        this.voiceActivityValueMin = voiceActivityValueMin;
        this.voiceActivityValueMax = voiceActivityValueMax;
        this.voiceActivityInterval = voiceActivityInterval;
    }

    public static Economy create() {
        var defaults = TirukaApp.getInstance().getConfig().getFeatures().getEconomy().getDefaultSettings();
        return new Economy(
                defaults.getCurrencySymbol(),
                defaults.getDailyBaseValue(),
                defaults.getDailyStreakBonus(),
                defaults.getWeeklyStreakBonus(),
                defaults.getMonthlyStreakBonus(),
                defaults.getChatMessageValueMin(),
                defaults.getChatMessageValueMax(),
                defaults.getChatMessageInterval(),
                defaults.getVoiceActivityValueMin(),
                defaults.getVoiceActivityValueMax(),
                defaults.getVoiceActivityInterval()
        );
    }

    public @NotNull String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public int getDailyBaseValue() {
        return dailyBaseValue;
    }

    public void setDailyBaseValue(int dailyBaseValue) {
        this.dailyBaseValue = dailyBaseValue;
    }

    public int getDailyStreakBonus() {
        return dailyStreakBonus;
    }

    public void setDailyStreakBonus(int dailyStreakBonus) {
        this.dailyStreakBonus = dailyStreakBonus;
    }

    public int getWeeklyStreakBonus() {
        return weeklyStreakBonus;
    }

    public void setWeeklyStreakBonus(int weeklyStreakBonus) {
        this.weeklyStreakBonus = weeklyStreakBonus;
    }

    public int getMonthlyStreakBonus() {
        return monthlyStreakBonus;
    }

    public void setMonthlyStreakBonus(int monthlyStreakBonus) {
        this.monthlyStreakBonus = monthlyStreakBonus;
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
