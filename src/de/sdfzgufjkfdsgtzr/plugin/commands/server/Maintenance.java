package de.sdfzgufjkfdsgtzr.plugin.commands.server;

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
        this.cfg = plugin.getLanguages();
    }


    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (command.getName().equalsIgnoreCase("service")) {
            if (sender.hasPermission("add.server.service")) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("on")) {
                        for (Player p: Bukkit.getOnlinePlayers()) {
                            if (p.hasPermission("add.server.service")) {
                                String name = sender.getName();
                                String message = String.format(cfg.getString(plugin.lang + ".service.activated"), name);
                                p.sendMessage(ChatColor.DARK_GREEN + plugin.PLUGIN_NAME + " " + message);
                            }
                        }
                        plugin.cfg.set("startup.maintenance", true);
                        plugin.maintenance = true;
                        plugin.saveConfig();
                    } else if (args[0].equalsIgnoreCase("off")) {
                        for (Player p: Bukkit.getOnlinePlayers()) {
                            if (p.hasPermission("add.server.service")) {
                                String name = sender.getName();
                                String message = String.format(cfg.getString(plugin.lang + ".service.deactivated"), name);
                                p.sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " " + message);
                            }
                        }
                        plugin.cfg.set("startup.maintenance", false);
                        plugin.maintenance = false;
                        plugin.saveConfig();
                    }
                    return true;
                } else if (args.length == 0) {
                    boolean value = plugin.maintenance;
                    String message = String.format(cfg.getString(plugin.lang + ".service.value"), value);
                    sender.sendMessage(ChatColor.GRAY + plugin.PLUGIN_NAME + " " + message);
                    return true;
                }
            }else{
                sender.sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " " +cfg.getString(plugin.lang + ".permission-missing"));
                return true;
            }
        }
        return false;
    }
}