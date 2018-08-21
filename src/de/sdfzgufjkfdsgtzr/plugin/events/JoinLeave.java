package de.sdfzgufjkfdsgtzr.plugin.events;

import de.sdfzgufjkfdsgtzr.plugin.Main;
import org.bukkit.Bukkit;
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
        try {
            plugin.sqlgetset.createPlayer(e.getPlayer().getUniqueId(), e.getPlayer());
            if(!plugin.cfg.contains(e.getPlayer().getUniqueId().toString())){
                plugin.cfg.set("user." + e.getPlayer().getUniqueId().toString() + ".prefix_color", plugin.cfg.getString("startup.prefix_color"));
                plugin.cfg.set("user." + e.getPlayer().getUniqueId().toString() + ".chat_color", plugin.cfg.getString("startup.chat_color"));
                plugin.cfg.set("user." + e.getPlayer().getUniqueId().toString() + ".notify", false);
                plugin.cfg.options().copyDefaults(true);
                plugin.saveConfig();
            }
        }catch (Exception sql){
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + plugin.PLUGIN_NAME + " Player already existing in database");
        }
        if(!plugin.conActive){
            e.getPlayer().sendMessage(ChatColor.DARK_RED + "Die Nutzung von Befehlen wie Home ist nur eingeschränkt möglich");
        }
        if(plugin.maintenance){
            if(!e.getPlayer().hasPermission("spl.util.service")){
                e.getPlayer().kickPlayer(ChatColor.RED + cfg.getString(plugin.lang + ".permission-join-missing"));
            }
        }
        String part1 = "§7";
        String part2 = e.getPlayer().getName();
        String part3 = "§a";
        String message = String.format(cfg.getString(plugin.lang + ".Event.join"), part1, part2, part3);
        e.setJoinMessage(message);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        String part1 = "§7";
        String part2 = e.getPlayer().getName();
        String part3 = "§c";
        String message = String.format(cfg.getString(plugin.lang + ".Event.leave"), part1, part2, part3);
        e.setQuitMessage(message);
    }
}
