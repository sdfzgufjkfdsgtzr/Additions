package de.sdfzgufjkfdsgtzr.plugin.commands;

import de.sdfzgufjkfdsgtzr.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChunkNotifier implements CommandExecutor {

    private Main plugin;

    public ChunkNotifier(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission("spl.util.chunkNotifier")){
                if(plugin.cfg.getBoolean("user." + p.getUniqueId().toString() + ".notify")){
                    plugin.cfg.set("user." + p.getUniqueId().toString() + ".notify", false);
                    p.sendMessage("§7Du erhälst ab sofort keine Benachrichtigungen mehr  über Slime Chunks");
                    plugin.saveConfig();
                }else{
                    plugin.cfg.set("user." + p.getUniqueId().toString() + ".notify", true);
                    p.sendMessage("§7Du erhälst ab sofort Benachrichtigungen über Slime Chunks");
                    plugin.saveConfig();
                }
                return true;
            }else{
                sender.sendMessage("§4Dir fehlt die notwendige Berechtigung!");
            }
        }else{
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " It's only possible for players to use this command");
            return true;
        }
        return false;
    }
}
