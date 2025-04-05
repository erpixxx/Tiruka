package dev.erpix.tiruka.command.console.impl;

import dev.erpix.tiruka.TirukaApp;
import dev.erpix.tiruka.cache.CacheManager;
import dev.erpix.tiruka.command.console.ConsoleCommand;
import dev.erpix.tiruka.command.console.model.CommandNode;
import dev.erpix.tiruka.command.console.model.argument.StringArgumentType;
import dev.erpix.tiruka.command.console.model.builder.ConsoleCommandBuilder;
import dev.erpix.tiruka.console.Console;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheConsoleCommand implements ConsoleCommand {

    private static final Logger logger = LoggerFactory.getLogger(CacheConsoleCommand.class);

    @Override
    public CommandNode build() {
        return ConsoleCommandBuilder.keyword("cache")
                .description("Manage cache")
                .child(ConsoleCommandBuilder.keyword("check")
                        .child(ConsoleCommandBuilder.keyword("guild")
                                .child(ConsoleCommandBuilder.argument("guild", new StringArgumentType())
                                        .executes(ctx -> {
                                            CacheManager cache = TirukaApp.getInstance().getCache();
                                            String guildId = ctx.getArgument("guild", String.class);
                                            Console.println("Checking guild %s", guildId);

                                            cache.getGuild(guildId).thenAccept(cachedGuild -> {
                                                if (cachedGuild == null) {
                                                    Console.println("Guild %s not found in cache", guildId);
                                                    return;
                                                }
                                                Console.println("Guild %s found in cache", guildId);
                                                Console.println("logging channel id: %s", cachedGuild.getLoggingChannelId());
                                                Console.println("verified role id: %s", cachedGuild.getVerifiedRoleId());
                                                Console.println("features: %s", cachedGuild.getFeatures());
                                            });
                                        })
                                        .build())
                                .build())
                        .child(ConsoleCommandBuilder.keyword("guild_member")
                                .child(ConsoleCommandBuilder.argument("guild", new StringArgumentType())
                                        .child(ConsoleCommandBuilder.argument("member", new StringArgumentType())
                                                .executes(ctx -> {
                                                    CacheManager cache = TirukaApp.getInstance().getCache();
                                                    String guildId = ctx.getArgument("guild", String.class);
                                                    String memberId = ctx.getArgument("member", String.class);
                                                    logger.info("Checking guild member {} in guild {}", memberId, guildId);

                                                    cache.getGuildMember(guildId, memberId).thenAccept(cachedGuildMember -> {
                                                        if (cachedGuildMember == null) {
                                                            logger.info("Guild member {} not found in cache", memberId);
                                                            return;
                                                        }
                                                        logger.info("Guild member {} found in cache", memberId);
                                                        logger.info("balance: {}", cachedGuildMember.getBalanceFormatted());
                                                        logger.info("level: {}", cachedGuildMember.getLevel());
                                                        logger.info("experience: {}", cachedGuildMember.getExperience());
                                                    });
                                                })
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();
    }

}
