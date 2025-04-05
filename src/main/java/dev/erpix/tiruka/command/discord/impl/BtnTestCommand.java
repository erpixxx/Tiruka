package dev.erpix.tiruka.command.discord.impl;

import dev.erpix.tiruka.command.discord.DiscordCommand;
import dev.erpix.tiruka.command.discord.DiscordCommandCategory;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class BtnTestCommand implements DiscordCommand {

    @Override
    public SlashCommandData build() {
        return Commands.slash("btn", "Test button command").addOptions(
                new OptionData(OptionType.STRING, "test", "test text", true)
                        .addChoice("Channel", "channel")
                        .addChoice("Role", "role")
                        .addChoice("String", "string")
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent ctx) {
        ctx.reply("")
                .addActionRow(StringSelectMenu.create("plec")
                        .addOption("Chłopak", "chlopak", Emoji.fromUnicode("♂"))
                        .addOption("Dziewczyna", "Dziewczyna", Emoji.fromUnicode("♀"))
                        .addOption("Nonbinary", "nonbinary", Emoji.fromCustom("nonbinary", 1308208333123096617L, false))
                        .addOption("Transgender", "transgender", Emoji.fromCustom("transgender", 1308208343164391516L, false))
                        .addOption("Genderfluid", "genderfluid", Emoji.fromCustom("genderfluid", 1308208354618900530L, false))
                        .setPlaceholder("Wybierz swoją płeć")
                        .build())
                .addActionRow(StringSelectMenu.create("wiek")
                        .addOption("16-17", "chlopak", Emoji.fromUnicode("💜"))
                        .addOption("18-21", "Dziewczyna", Emoji.fromUnicode("❤"))
                        .addOption("22-25", "nonbinary", Emoji.fromUnicode("💙"))
                        .addOption("26+", "transgender", Emoji.fromUnicode("💚"))
                        .setPlaceholder("Wybierz swój wiek")
                        .build())
                .addActionRow(StringSelectMenu.create("wojewodztwo")
                        .addOption("Dolnośląskie", "dolnoslaskie", Emoji.fromCustom("dolnoslaskie", 1303025219618869280L, false))
                        .addOption("Kujawsko-Pomorskie", "kujawsko_pomorskie", Emoji.fromCustom("kujawsko_pomorskie", 1303025238635843634L, false))
                        .addOption("Lubelskie", "lubelskie", Emoji.fromCustom("lubelskie", 1303025246546428016L, false))
                        .addOption("Lubuskie", "lubuskie", Emoji.fromCustom("lubuskie", 1303025255908114554L, false))
                        .addOption("Łódzkie", "lodzkie", Emoji.fromCustom("lodzkie", 1303027236751937606L, false))
                        .addOption("Małopolskie", "malopolskie", Emoji.fromCustom("malopolskie", 1303027265772453930L, false))
                        .addOption("Mazowieckie", "mazowieckie", Emoji.fromCustom("mazowieckie", 1303027275494719528L, false))
                        .addOption("Opolskie", "opolskie", Emoji.fromCustom("opolskie", 1303027300153036880L, false))
                        .addOption("Podkarpackie", "podkarpackie", Emoji.fromCustom("podkarpackie", 1303027326610702409L, false))
                        .addOption("Podlaskie", "podlaskie", Emoji.fromCustom("podlaskie", 1303027343727661136L, false))
                        .addOption("Pomorskie", "pomorskie", Emoji.fromCustom("pomorskie", 1303027373402361896L, false))
                        .addOption("Śląskie", "slaskie", Emoji.fromCustom("slaskie", 1303027391286870077L, false))
                        .addOption("Świętokrzyskie", "swietokrzyskie", Emoji.fromCustom("swietokrzyskie", 1303027406432501810L, false))
                        .addOption("Warmińsko-Mazurskie", "warminsko_mazurskie", Emoji.fromCustom("warminsko_mazurskie", 1303027726520811560L, false))
                        .addOption("Wielkopolskie", "wielkopolskie", Emoji.fromCustom("wielkopolskie", 1303027742832591003L, false))
                        .addOption("Zachodniopomorskie", "zachodniopomorskie", Emoji.fromCustom("zachodniopomorskie", 1303027753767145564L, false))
                        .addOption("Inne", "inne", Emoji.fromUnicode("🌎"))
                        .setPlaceholder("Wybierz swoje województwo")
                        .build())
                .addActionRow(StringSelectMenu.create("zainteresowania")
                        .addOption("Anime", "anime", Emoji.fromUnicode("🎀"))
                        .addOption("Fotografia", "fotografia", Emoji.fromUnicode("📷"))
                        .addOption("Gotowanie", "gotowanie", Emoji.fromUnicode("🍳"))
                        .addOption("Gry", "gry", Emoji.fromUnicode("🎮"))
                        .addOption("Muzyka", "muzyka", Emoji.fromUnicode("🎶"))
                        .addOption("Programowanie", "programowanie", Emoji.fromUnicode("💻"))
                        .addOption("Rysowanie", "rysowanie", Emoji.fromUnicode("✏"))
                        .setPlaceholder("Wybierz swoje zainteresowania")
                        .setMaxValues(7)
                        .build())
                .addActionRow(StringSelectMenu.create("dodatkowe")
                        .addOption("Dead Chat", "deadchat", "Będziesz dostawał powiadomienie gdy ktoś spinguje tę rolę", Emoji.fromUnicode("💀"))
                        .addOption("VC Ping", "vcping", "Będziesz dostawał powiadomienie gdy ktoś spinguje tę rolę", Emoji.fromUnicode("📣"))
                        .setPlaceholder("Wybierz dodatkowe role")
                        .setMaxValues(2)
                        .build())
                .queue();
    }

    @Override
    public DiscordCommandCategory getCategory() {
        return null;
    }

}
