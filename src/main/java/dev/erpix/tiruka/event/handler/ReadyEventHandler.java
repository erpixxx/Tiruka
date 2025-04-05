package dev.erpix.tiruka.event.handler;

import net.dv8tion.jda.api.events.session.ReadyEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadyEventHandler implements EventHandler<ReadyEvent> {

    private final Logger logger = LoggerFactory.getLogger(ReadyEventHandler.class);

    @Override
    public void handle(@NotNull ReadyEvent event) {

    }

    @Override
    public @NotNull Class<ReadyEvent> getEventClass() {
        return ReadyEvent.class;
    }

}
