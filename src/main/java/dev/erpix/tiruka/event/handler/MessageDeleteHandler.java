package dev.erpix.tiruka.event.handler;

import net.dv8tion.jda.api.events.message.MessageDeleteEvent;

public class MessageDeleteHandler implements EventHandler<MessageDeleteEvent> {

    @Override
    public void handle(MessageDeleteEvent event) {

    }

    @Override
    public Class<MessageDeleteEvent> getEventClass() {
        return null;
    }

}
