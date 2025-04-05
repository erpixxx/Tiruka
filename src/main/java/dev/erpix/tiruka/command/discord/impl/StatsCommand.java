package dev.erpix.tiruka.command.discord.impl;

import dev.erpix.tiruka.TirukaApp;
import dev.erpix.tiruka.command.discord.DiscordCommand;
import dev.erpix.tiruka.command.discord.DiscordCommandCategory;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.sharding.ShardManager;

public class StatsCommand implements DiscordCommand {

    @Override
    public SlashCommandData build() {
        return Commands.slash("stats", "Shows bot statistics.");
    }

    @Override
    public void execute(SlashCommandInteractionEvent ctx) {
        ShardManager shardManager = TirukaApp.getInstance().getShardManager();
    }

    @Override
    public DiscordCommandCategory getCategory() {
        return DiscordCommandCategory.UTILITY;
    }

}
