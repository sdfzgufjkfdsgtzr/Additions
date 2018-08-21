package de.sdfzgufjkfdsgtzr.plugin.events;

import de.sdfzgufjkfdsgtzr.plugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Chat implements Listener {

    private Main plugin;

    public Chat(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        String message = "&" + plugin.cfg.getString("user." + e.getPlayer().getUniqueId().toString() + ".prefix_color") + e.getPlayer().getDisplayName() + "§7: &" + plugin.cfg.getString("user." + e.getPlayer().getUniqueId().toString() + ".chat_color") + e.getMessage();
        e.setFormat(plugin.format(message));
    }

}
