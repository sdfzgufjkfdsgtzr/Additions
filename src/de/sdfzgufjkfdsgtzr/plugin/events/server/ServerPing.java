package de.sdfzgufjkfdsgtzr.plugin.events.server;

import de.sdfzgufjkfdsgtzr.plugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerPing implements Listener {


    private Main plugin;
    private FileConfiguration cfg;

    public ServerPing(Main plugin){
        this.plugin = plugin;
        this.cfg = plugin.getLanguages();
    }

    @EventHandler
    public void onServerPing(ServerListPingEvent e ){
        String motd = ChatColor.translateAlternateColorCodes('&', plugin.cfg.getString("startup.motd"));
        if (plugin.cfg.getBoolean("startup.maintenance"))
        {
            e.setMotd(ChatColor.RED + cfg.getString(plugin.lang + ".service.ping"));
            e.setMaxPlayers(0);
        }
        else
        {
            e.setMotd(motd);
            e.setMaxPlayers(69);
        }
    }
}
