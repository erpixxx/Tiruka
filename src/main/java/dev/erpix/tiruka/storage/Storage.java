package dev.erpix.tiruka.storage;

import dev.erpix.tiruka.StartupException;
import dev.erpix.tiruka.TirukaApp;
import dev.erpix.tiruka.cache.entity.CachedGuild;
import dev.erpix.tiruka.cache.entity.CachedGuildMember;
import dev.erpix.tiruka.model.Economy;
import dev.erpix.tiruka.model.GuildStats;
import dev.erpix.tiruka.model.LevelingSystem;
import dev.erpix.tiruka.storage.connection.ConnectionFactory;
import dev.erpix.tiruka.storage.connection.StatementProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class Storage {

    private static final Logger logger = LoggerFactory.getLogger(Storage.class);

    private final String SELECT_GUILD = "SELECT * FROM {prefix}guilds WHERE guild_id = ?";
    private final String INSERT_GUILD = """
            INSERT INTO {prefix}guilds (
                guild_id,
                verified_role_id,
                logging_channel_id,
                features,
                economy_currency_symbol,
                economy_daily_base_value,
                economy_daily_streak_bonus,
                economy_weekly_streak_bonus,
                economy_monthly_streak_bonus,
                economy_chat_message_value_min,
                economy_chat_message_value_max,
                economy_chat_message_interval,
                economy_voice_activity_value_min,
                economy_voice_activity_value_max,
                economy_voice_activity_interval,
                level_system_base_max_experience,
                level_system_chat_message_value_max,
                level_system_chat_message_value_min,
                level_system_chat_message_interval,
                level_system_voice_activity_value_max,
                level_system_voice_activity_value_min,
                level_system_voice_activity_interval,
                stats_total_messages_sent,
                stats_total_commands_executed,
                stats_total_balance
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
            """;

    private final String SELECT_MEMBER = "SELECT * FROM {prefix}guild_members WHERE guild_id = ? AND member_id = ?";

    private final TirukaApp tiruka;
    private final ConnectionFactory connectionFactory;
    private final StatementProcessor statementProcessor;

    Storage(TirukaApp tiruka, ConnectionFactory connectionFactory, String prefix) {
        this.tiruka = tiruka;
        this.connectionFactory = connectionFactory;
        this.statementProcessor = connectionFactory.getStatementProcessor()
                .combine(stmt -> stmt.replace("{prefix}", prefix));
    }

    public void init() {
        try {
            connectionFactory.init();
            applySchema();
        } catch (Exception e) {
            throw new StorageInitializationException("Could not initialize storage", e);
        }
    }

    public void close() {
        if (connectionFactory != null) {
            try {
                connectionFactory.close();
            } catch (Exception e) {
                throw new RuntimeException("Could not close storage", e);
            }
        }
    }

    private void applySchema() {
        String schema = "schemas/" + connectionFactory.getName().toLowerCase() + ".sql";

        try (InputStream is = Storage.class.getClassLoader().getResourceAsStream(schema)) {
            if (is == null) {
                throw new StorageInitializationException("Could not find schema file");
            }

            try (Connection conn = connectionFactory.getConnection()) {
                if (conn == null) {
                    throw new StorageInitializationException("Could not get connection while applying schema");
                }

                String queries = new String(is.readAllBytes());
                for (String query : queries.split(";")) {
                    if (!query.isEmpty())
                        conn.createStatement().execute(statementProcessor.process(query));
                }
            } catch (SQLException e) {
                throw new StorageInitializationException("Could not get connection while applying schema");
            }
        } catch (IOException e) {
            throw new StorageInitializationException("Could not read schema file", e);
        }
    }

    public CompletableFuture<Void> addGuild(CachedGuild guild) {
        return CompletableFuture.runAsync(() -> {
            try (Connection conn = connectionFactory.getConnection()) {

                String processedQuery = statementProcessor.process(INSERT_GUILD);

                try (PreparedStatement stmt = conn.prepareStatement(processedQuery)) {
                    stmt.setString(1, guild.getGuildId());
                    stmt.setLong(2, guild.getVerifiedRoleId());
                    stmt.setLong(3, guild.getLoggingChannelId());
                    stmt.setInt(4, guild.getFeatures());
                    stmt.setString(5, guild.getEconomy().getCurrencySymbol());
                    stmt.setInt(6, guild.getEconomy().getDailyBaseValue());
                    stmt.setInt(7, guild.getEconomy().getDailyStreakBonus());
                    stmt.setInt(8, guild.getEconomy().getWeeklyStreakBonus());
                    stmt.setInt(9, guild.getEconomy().getMonthlyStreakBonus());
                    stmt.setInt(10, guild.getEconomy().getChatMessageValueMin());
                    stmt.setInt(11, guild.getEconomy().getChatMessageValueMax());
                    stmt.setInt(12, guild.getEconomy().getChatMessageInterval());
                    stmt.setInt(13, guild.getEconomy().getVoiceActivityValueMin());
                    stmt.setInt(14, guild.getEconomy().getVoiceActivityValueMax());
                    stmt.setInt(15, guild.getEconomy().getVoiceActivityInterval());
                    stmt.setInt(16, guild.getLevelingSystem().getBaseMaxExperience());
                    stmt.setInt(17, guild.getLevelingSystem().getChatMessageValueMax());
                    stmt.setInt(18, guild.getLevelingSystem().getChatMessageValueMin());
                    stmt.setInt(19, guild.getLevelingSystem().getChatMessageInterval());
                    stmt.setInt(20, guild.getLevelingSystem().getVoiceActivityValueMax());
                    stmt.setInt(21, guild.getLevelingSystem().getVoiceActivityValueMin());
                    stmt.setInt(22, guild.getLevelingSystem().getVoiceActivityInterval());
                    stmt.setLong(23, guild.getGuildStats().getTotalMessagesSent());
                    stmt.setLong(24, guild.getGuildStats().getTotalCommandsExecuted());
                    stmt.setLong(25, guild.getGuildStats().getTotalBalance());

                    stmt.executeUpdate();
                } catch (SQLException e) {
                    logger.error("Could not execute query while adding guild", e);
                }
            } catch (SQLException e) {
                logger.error("Could not get connection while adding guild", e);
            }
        });
    }

    public CompletableFuture<CachedGuild> getGuild(String guildId) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection conn = connectionFactory.getConnection()) {

                String processedQuery = statementProcessor.process(SELECT_GUILD);

                try (PreparedStatement stmt = conn.prepareStatement(processedQuery)) {
                    stmt.setString(1, guildId);

                    try (ResultSet rs = stmt.executeQuery()) {
                        if (!rs.next()) {
                            return null;
                        }

                        long verifiedRoleId = rs.getLong("verified_role_id");
                        long loggingChannelId = rs.getLong("logging_channel_id");
                        int features = rs.getInt("features");

                        Economy economy = new Economy(
                                rs.getString("economy_currency_symbol"),
                                rs.getInt("economy_daily_base_value"),
                                rs.getInt("economy_daily_streak_bonus"),
                                rs.getInt("economy_weekly_streak_bonus"),
                                rs.getInt("economy_monthly_streak_bonus"),
                                rs.getInt("economy_chat_message_value_min"),
                                rs.getInt("economy_chat_message_value_max"),
                                rs.getInt("economy_chat_message_interval"),
                                rs.getInt("economy_voice_activity_value_min"),
                                rs.getInt("economy_voice_activity_value_max"),
                                rs.getInt("economy_voice_activity_interval")
                        );

                        LevelingSystem levelingSystem = new LevelingSystem(
                                rs.getInt("level_system_base_max_experience"),
                                rs.getInt("level_system_chat_message_value_max"),
                                rs.getInt("level_system_chat_message_value_min"),
                                rs.getInt("level_system_chat_message_interval"),
                                rs.getInt("level_system_voice_activity_value_max"),
                                rs.getInt("level_system_voice_activity_value_min"),
                                rs.getInt("level_system_voice_activity_interval")
                        );

                        GuildStats guildStats = new GuildStats(
                                rs.getLong("stats_total_messages_sent"),
                                rs.getLong("stats_total_commands_executed"),
                                rs.getLong("stats_total_balance")
                        );

                        return new CachedGuild(
                                60,
                                System.currentTimeMillis(),
                                guildId,
                                verifiedRoleId,
                                loggingChannelId,
                                features,
                                economy,
                                levelingSystem,
                                guildStats
                        );
                    } catch (SQLException e) {
                        logger.error("Could not execute query while getting guild", e);
                        return null;
                    }
                }
            } catch (SQLException e) {
                logger.error("Could not get connection while getting guild", e);
                return null;
            }
        });
    }

    public CompletableFuture<CachedGuildMember> getGuildMember(String guildId, String memberId) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection conn = connectionFactory.getConnection()) {

                String processedQuery = statementProcessor.process(SELECT_MEMBER);

                try (PreparedStatement stmt = conn.prepareStatement(processedQuery)) {
                    stmt.setString(1, guildId);
                    stmt.setString(2, memberId);

                    try (ResultSet rs = stmt.executeQuery()) {
                        if (!rs.next()) {
                            return null;
                        }

                        long balance = rs.getLong("balance");
                        int level = rs.getInt("level");
                        long experience = rs.getLong("experience");

                        return new CachedGuildMember(
                                60,
                                System.currentTimeMillis(),
                                guildId,
                                memberId,
                                balance,
                                level,
                                experience
                        );
                    } catch (SQLException e) {
                        logger.error("Could not execute query while getting guild member", e);
                        return null;
                    }
                }
            } catch (SQLException e) {
                logger.error("Could not get connection while getting guild member", e);
                return null;
            }
        });
    }

}
