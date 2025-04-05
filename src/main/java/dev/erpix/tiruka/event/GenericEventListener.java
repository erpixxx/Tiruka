package dev.erpix.tiruka.event;


import dev.erpix.tiruka.logging.LoggableEvent;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;
import dev.erpix.tiruka.event.handler.EventHandler;
import dev.erpix.tiruka.logging.GuildLogger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GenericEventListener implements EventListener {

    private final Map<Class<?>, EventHandler<?>> handlers = new ConcurrentHashMap<>();

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        dispatch(event);

        if (event instanceof LoggableEvent log) {
            log.log();
        }
    }

    public <E extends GenericEvent> void registerHandler(EventHandler<E> handler) {
        handlers.put(handler.getEventClass(), handler);
    }

    @SuppressWarnings("unchecked")
    private <E extends GenericEvent> void dispatch(E event) {
        EventHandler<E> handler = (EventHandler<E>) handlers.get(event.getClass());
        if (handler != null) {
            handler.handle(event);
        }
    }

}
