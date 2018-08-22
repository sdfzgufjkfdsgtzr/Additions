package de.sdfzgufjkfdsgtzr.plugin.events;

import de.sdfzgufjkfdsgtzr.plugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeave implements Listener {

    private Main plugin;
    private FileConfiguration cfg;

    public JoinLeave(Main plugin){
        this.plugin = plugin;
        this.cfg = plugin.getLanguageFile();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if(!plugin.cfg.contains(e.getPlayer().getUniqueId().toString())){
            plugin.cfg.set("user." + e.getPlayer().getUniqueId().toString() + ".prefix_color", plugin.cfg.getString("startup.prefix_color"));
            plugin.cfg.set("user." + e.getPlayer().getUniqueId().toString() + ".chat_color", plugin.cfg.getString("startup.chat_color"));
            plugin.cfg.set("user." + e.getPlayer().getUniqueId().toString() + ".notify", false);
            plugin.cfg.options().copyDefaults(true);
            plugin.saveConfig(); }
        if(plugin.maintenance){
            if(!e.getPlayer().hasPermission("spl.util.service")){
                e.getPlayer().kickPlayer(ChatColor.RED + cfg.getString(plugin.lang + ".permission-join-missing"));
            }
        }
        String player = e.getPlayer().getName();
        String message = ChatColor.translateAlternateColorCodes('&',String.format(cfg.getString(plugin.lang + ".event.join"), player));
        e.setJoinMessage(message);
        e.getPlayer().sendMessage("§CBitte das Zuhause neu setzen!");
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        String player = e.getPlayer().getName();
        String message = ChatColor.translateAlternateColorCodes('&',String.format(cfg.getString(plugin.lang + ".event.leave"), player));
        e.setQuitMessage(message);
    }
}
