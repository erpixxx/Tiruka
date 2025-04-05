package dev.erpix.tiruka.event.handler;

import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

public class StringSelectInteractionHandler implements EventHandler<StringSelectInteractionEvent> {

    // SelectRoleManager

    @Override
    public void handle(StringSelectInteractionEvent event) {
        if (event.getComponentId().equals("wojewodztwo")) {
            event.reply("You chose: " + event.getValues().get(0)).setEphemeral(true).queue();
        }
    }

    @Override
    public Class<StringSelectInteractionEvent> getEventClass() {
        return StringSelectInteractionEvent.class;
    }

}
