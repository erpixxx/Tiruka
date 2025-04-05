package dev.erpix.tiruka.model;

public class WelcomeSettings {

    private final String guildId;
    private boolean enableJoinMessage;
    private boolean enableLeaveMessage;

    public WelcomeSettings(String guildId) {
        this.guildId = guildId;
    }

}
