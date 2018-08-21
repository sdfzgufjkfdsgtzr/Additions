package de.sdfzgufjkfdsgtzr.plugin.commands;

import de.sdfzgufjkfdsgtzr.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Maintenance implements CommandExecutor
{
    private Main plugin;
    private FileConfiguration cfg;

    public Maintenance(Main plugin){
        this.plugin = plugin;
        this.cfg = plugin.getLanguageFile();
    }


    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (command.getName().equalsIgnoreCase("service")) {
            if(sender.hasPermission("spl.util.service")) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("on")) {
                        plugin.cfg.set("startup.maintenance", true);
                        for (Player p: Bukkit.getOnlinePlayers()) {
                            if(p.hasPermission("spl.util.service")) {
                                p.sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " " + sender.getName() + " " + cfg.get(plugin.lang + ".service.activated"));
                            }
                        }
                        plugin.maintenance = true;
                        plugin.saveConfig();
                    } else if (args[0].equalsIgnoreCase("off")) {
                        plugin.getConfig().set("startup.maintenance", false);
                        for (Player p: Bukkit.getOnlinePlayers()) {
                            if (p.hasPermission("spl.util.service")) {
                                p.sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " " + sender.getName() + " " + cfg.get(plugin.lang + ".service.deactivated"));
                            }
                        }
                        plugin.cfg.set("startup.maintenance", false);
                        plugin.maintenance = true;
                        plugin.saveConfig();
                    }
                    return true;
                } else if (args.length == 0) {
                    sender.sendMessage(ChatColor.GRAY + plugin.PLUGIN_NAME + " " + cfg.get(plugin.lang + ".service.value") + " " + plugin.maintenance);
                    return true;
                }
            }else{
                sender.sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + cfg.getString(plugin.lang + ".permission-missing"));
                return true;
            }
        }
        return false;
    }
}