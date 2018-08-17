package de.sdfzgufjkfdsgtzr.plugin.events;

import de.sdfzgufjkfdsgtzr.plugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerPing implements Listener {


    private Main plugin;
    public ServerPing(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onServerPing(ServerListPingEvent e ){
        String motd = ChatColor.translateAlternateColorCodes('&', plugin.cfg.getString("startup.motd"));
        if (plugin.cfg.getBoolean("startup.maintenance"))
        {
            e.setMotd("§4Der Server ist momentan im Wartungsmodus");
            e.setMaxPlayers(0);
        }
        else
        {
            e.setMotd(motd);
            e.setMaxPlayers(69);
        }
    }
}
