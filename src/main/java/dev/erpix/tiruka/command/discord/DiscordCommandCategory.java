package dev.erpix.tiruka.command.discord;

public enum DiscordCommandCategory {

    GENERAL("General"),
    MODERATION("Moderation"),
    FUN("Fun"),
    UTILITY("Utility"),
    MUSIC("Music"),
    CONFIGURATION("Configuration"),
    DEVELOPER("Developer");

    private final String name;

    DiscordCommandCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
