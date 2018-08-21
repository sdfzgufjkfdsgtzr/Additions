package de.sdfzgufjkfdsgtzr.plugin.commands;

import de.sdfzgufjkfdsgtzr.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ChunkNotifier implements CommandExecutor {

    private Main plugin;
    private FileConfiguration cfg;

    public ChunkNotifier(Main plugin){
        this.plugin = plugin;
        this.cfg = plugin.getLanguageFile();
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission("spl.util.chunkNotifier")){
                if(plugin.cfg.getBoolean("user." + p.getUniqueId().toString() + ".notify")){
                    plugin.cfg.set("user." + p.getUniqueId().toString() + ".notify", false);
                    p.sendMessage(cfg.getString("german.ChunkNotifier.No-Notify"));
                    plugin.saveConfig();
                }else{
                    plugin.cfg.set("user." + p.getUniqueId().toString() + ".notify", true);
                    p.sendMessage(cfg.getString("german.ChunkNotifier.Notify"));
                    plugin.saveConfig();
                }
                return true;
            }else{
                p.sendMessage(plugin.format(cfg.getString("german.permission-missing")));
            }
        }else{
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " It's only possible for players to use this command");
            return true;
        }
        return false;
    }
}
