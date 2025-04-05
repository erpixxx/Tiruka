CREATE TABLE IF NOT EXISTS `{prefix}guilds` (
	`guild_id` BIGINT NOT NULL,
	`verified_role_id` BIGINT,
	`logging_channel_id` BIGINT,
	`features` BIGINT NOT NULL,
	`economy_currency_symbol` VARCHAR(16) NOT NULL,
	`economy_daily_base_value` INT NOT NULL,
	`economy_daily_streak_bonus` INT NOT NULL,
	`economy_weekly_streak_bonus` INT NOT NULL,
	`economy_monthly_streak_bonus` INT NOT NULL,
	`economy_chat_message_value_min` INT NOT NULL,
	`economy_chat_message_value_max` INT NOT NULL,
	`economy_chat_message_interval` INT NOT NULL,
	`economy_voice_activity_value_min` INT NOT NULL,
	`economy_voice_activity_value_max` INT NOT NULL,
	`economy_voice_activity_interval` INT NOT NULL,
	`level_system_base_max_experience` INT NOT NULL,
	`level_system_chat_message_value_min` INT NOT NULL,
	`level_system_chat_message_value_max` INT NOT NULL,
	`level_system_chat_message_interval` INT NOT NULL,
	`level_system_voice_activity_value_min` INT NOT NULL,
	`level_system_voice_activity_value_max` INT NOT NULL,
	`level_system_voice_activity_interval` INT NOT NULL,
	`stats_total_messages_sent` BIGINT NOT NULL,
	`stats_total_commands_executed` BIGINT NOT NULL,
	`stats_total_balance` BIGINT NOT NULL,
	PRIMARY KEY (`guild_id`)
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `{prefix}guild_members` (
	`guild_id` BIGINT NOT NULL,
	`member_id` BIGINT NOT NULL,
	`balance` BIGINT NOT NULL,
	`level` INT NOT NULL,
	`experience` BIGINT NOT NULL,
	INDEX `guild_member` (`guild_id`, `member_id`)
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;