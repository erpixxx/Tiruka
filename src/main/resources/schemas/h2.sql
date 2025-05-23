CREATE TABLE IF NOT EXISTS GUILDS (
    GUILD_ID BIGINT NOT NULL,
    VERIFIED_ROLE_ID BIGINT NOT NULL,
    LOGGING_CHANNEL_ID BIGINT NOT NULL,
    FEATURES BIGINT NOT NULL,
    ECONOMY_CURRENCY_SYMBOL VARCHAR(16) NOT NULL,
    ECONOMY_DAILY_BASE_VALUE INT NOT NULL DEFAULT 500,
    ECONOMY_DAILY_STREAK_BONUS INT NOT NULL DEFAULT 250,
    ECONOMY_WEEKLY_STREAK_BONUS INT NOT NULL DEFAULT 1000,
    ECONOMY_MONTHLY_STREAK_BONUS INT NOT NULL DEFAULT 5000,
    ECONOMY_CHAT_MESSAGE_VALUE_MAX INT NOT NULL DEFAULT 10,
    ECONOMY_CHAT_MESSAGE_VALUE_MIN INT NOT NULL DEFAULT 15,
    ECONOMY_CHAT_MESSAGE_INTERVAL INT NOT NULL DEFAULT 30,
    ECONOMY_VOICE_ACTIVITY_VALUE_MAX INT NOT NULL DEFAULT 500,
    ECONOMY_VOICE_ACTIVITY_VALUE_MIN INT NOT NULL DEFAULT 750,
    ECONOMY_VOICE_ACTIVITY_VALUE_INTERVAL INT NOT NULL DEFAULT 300,
    LEVEL_SYSTEM_BASE_MAX_EXPERIENCE INT NOT NULL,
    LEVEL_SYSTEM_CHAT_MESSAGE_VALUE_MAX INT NOT NULL,
    LEVEL_SYSTEM_CHAT_MESSAGE_VALUE_MIN INT NOT NULL,
    LEVEL_SYSTEM_CHAT_MESSAGE_INTERVAL INT NOT NULL,
    LEVEL_SYSTEM_VOICE_ACTIVITY_VALUE_MAX INT NOT NULL,
    LEVEL_SYSTEM_VOICE_ACTIVITY_VALUE_MIN INT NOT NULL,
    LEVEL_SYSTEM_VOICE_ACTIVITY_INTERVAL INT NOT NULL,
    STATS_TOTAL_MESSAGES_SENT INT NOT NULL,
    STATS_TOTAL_COMMANDS_EXECUTED INT NOT NULL,
    STATS_TOTAL_BALANCE INT NOT NULL,
    PRIMARY KEY (GUILD_ID)
);

CREATE TABLE IF NOT EXISTS GUILD_MEMBERS (
    GUILD_ID BIGINT NOT NULL,
    MEMBER_ID BIGINT NOT NULL,
    BALANCE BIGINT NOT NULL,
    LEVEL INT NOT NULL,
    EXPERIENCE BIGINT NOT NULL
);

CREATE INDEX IF NOT EXISTS GUILD_MEMBER_IDX ON GUILD_MEMBERS (GUILD_ID, MEMBER_ID);