package de.sdfzgufjkfdsgtzr.plugin.events;

import de.sdfzgufjkfdsgtzr.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeave implements Listener {

    private Main plugin;
    public JoinLeave(Main plugin){
        this.plugin = plugin;
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
                e.getPlayer().kickPlayer("§cDir ist es momentan nicht erlaubt, den Server zu betreten!");
            }
        }
        e.setJoinMessage("§7" + e.getPlayer().getName() + " ist jetzt §aonline§7!");
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        e.setQuitMessage("§7" + e.getPlayer().getName() + " ist jetzt §coffline§7!");
    }
}
