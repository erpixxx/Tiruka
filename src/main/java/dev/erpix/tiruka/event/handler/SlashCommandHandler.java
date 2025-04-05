package dev.erpix.tiruka.event.handler;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.erpix.tiruka.command.CommandRegistrar;

public class SlashCommandHandler implements EventHandler<SlashCommandInteractionEvent> {

    private final Logger logger = LoggerFactory.getLogger(SlashCommandHandler.class);
    private final CommandRegistrar registrar;

    public SlashCommandHandler(CommandRegistrar registrar) {
        this.registrar = registrar;
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        registrar.getDiscordCommand(event.getName()).execute(event);
    }

    @Override
    public Class<SlashCommandInteractionEvent> getEventClass() {
        return SlashCommandInteractionEvent.class;
    }

}
