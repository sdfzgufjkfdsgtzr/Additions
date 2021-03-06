package de.sdfzgufjkfdsgtzr.plugin.events.server;

import de.sdfzgufjkfdsgtzr.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class JoinLeave implements Listener {

    private Main plugin;
    private FileConfiguration cfg;

    public JoinLeave(Main plugin){
        this.plugin = plugin;
        this.cfg = plugin.getLanguages();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!plugin.cfg.contains(e.getPlayer().getName())) {
            plugin.cfg.set("user." + e.getPlayer().getName() + ".prefix_color", plugin.cfg.getString("startup.prefix_color"));
            plugin.cfg.set("user." + e.getPlayer().getName() + ".chat_color", plugin.cfg.getString("startup.chat_color"));
            plugin.cfg.set("user." + e.getPlayer().getName() + ".notify", false);
            plugin.saveConfig(); }

        String player = e.getPlayer().getName();
        String message = ChatColor.translateAlternateColorCodes('&',String.format(cfg.getString(plugin.lang + ".event.join"), player));
        e.setJoinMessage(message);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        String player = e.getPlayer().getName();
        String message = ChatColor.translateAlternateColorCodes('&',String.format(cfg.getString(plugin.lang + ".event.leave"), player));
        e.setQuitMessage(message);
    }

    @EventHandler
    public void onPlayerJoinSession(AsyncPlayerPreLoginEvent e){
        if(plugin.maintenance){
            if(!getPlayerByUUID(e.getUniqueId()).hasPermission("spl.util.service")){
                e.setKickMessage(ChatColor.RED + cfg.getString(plugin.lang + ".permission-join-missing"));
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_FULL, e.getKickMessage());
            }
        }
    }

    /**
     * @return the player specified by the UUID
     */
    private Player getPlayerByUUID(UUID uuid){
        return Bukkit.getPlayer(uuid);
    }
}
