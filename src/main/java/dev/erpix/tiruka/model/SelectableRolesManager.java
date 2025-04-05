package dev.erpix.tiruka.model;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import dev.erpix.tiruka.model.reaction.GuildReactionRoles;

import java.util.HashMap;
import java.util.Map;

public class SelectableRolesManager {
    
    private final Map<Guild, GuildReactionRoles> reactionRolesManagers = new HashMap<>();
    private JDA jda;
    private boolean initialized;

    public void init(JDA jda) {
        if (initialized) return;

        this.jda = jda;
        jda.getGuilds().forEach(guild -> reactionRolesManagers.put(guild, new GuildReactionRoles(guild)));
        initialized = true;
    }

    public GuildReactionRoles reactionRoles(Guild guild) {
        return reactionRolesManagers.get(guild);
    }
    
}
