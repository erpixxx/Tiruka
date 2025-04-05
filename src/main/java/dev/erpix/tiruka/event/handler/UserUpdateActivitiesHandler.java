package dev.erpix.tiruka.event.handler;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.user.update.UserUpdateActivitiesEvent;

public class UserUpdateActivitiesHandler implements EventHandler<UserUpdateActivitiesEvent> {

    @Override
    public void handle(UserUpdateActivitiesEvent event) {
        event.getMember().getActivities().forEach(activity -> {
            User user = event.getUser();
            String name = activity.getName();

            TextChannel channel = event.getGuild().getTextChannelById(1107396222593024062L);

            if (channel == null) return;

            if (name.equalsIgnoreCase("roblox")) {
                channel.sendMessage(user.getAsMention() + " https://cdn.discordapp.com/attachments/1100368807786201128/1307810899167281172/gat0ps_1731876063835.mp4?ex=673ba950&is=673a57d0&hm=0f857b8ab50ac66224d1c8e7702936bfe888ade55db4e29fd4f78b650910b64b&").queue();
            }
            if (name.equalsIgnoreCase("league of legends")) {
                channel.sendMessage(":warning: :warning: :warning:  " + user.getAsMention() + " PROSIMY O WZIĘCIE PRYSZNICA I WYŁĄCZENIE GRY \"LEAGUE OF LEGENDS\"!!!").queue();
            }
        });
    }

    @Override
    public Class<UserUpdateActivitiesEvent> getEventClass() {
        return UserUpdateActivitiesEvent.class;
    }

}
