package dev.erpix.tiruka.event.handler;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;

public class GuildReadyHandler implements EventHandler<GuildReadyEvent> {

    @Override
    public void handle(GuildReadyEvent event) {

    }

    @Override
    public Class<GuildReadyEvent> getEventClass() {
        return GuildReadyEvent.class;
    }

}
