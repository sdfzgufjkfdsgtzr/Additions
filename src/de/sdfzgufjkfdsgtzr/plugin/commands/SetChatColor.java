package de.sdfzgufjkfdsgtzr.plugin.commands;

import de.sdfzgufjkfdsgtzr.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetChatColor implements CommandExecutor {

    private Main plugin;
    private FileConfiguration cfg;

    public SetChatColor(Main plugin){
        this.plugin = plugin;
        this.cfg = plugin.getLanguageFile();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            if (sender.hasPermission("add.player.chat.appearance")) {
                Player p = (Player) sender;
                String allowed = "[a-f[k-o]0-9]";
                if(args.length == 0){
                    plugin.cfg.set("user." + p.getName() + ".prefix_color", plugin.cfg.getString("startup.prefix_color"));
                    plugin.cfg.set("user." + p.getName() + ".chat_color", plugin.cfg.getString("startup.chat_color"));
                    p.sendMessage(ChatColor.GRAY + plugin.PLUGIN_NAME + " " + cfg.getString(plugin.lang + ".ChatColor.default") + ":\n§" + plugin.cfg.getString("user." + p.getName() + ".prefix_color") + "[Name] §" + plugin.cfg.getString("user." + p.getName() + ".chat_color") + "[Chat]");
                    plugin.saveConfig();
                    return true;
                }
                else if(args.length == 1){
                    if(args[0].matches(allowed)){
                        plugin.cfg.set("user." + p.getName() + ".prefix_color", args[0].charAt(0));
                        String part1 = "§o";
                        String message = String.format(ChatColor.GRAY + plugin.PLUGIN_NAME + " " + cfg.getString(plugin.lang + ".ChatColor.prefixColor"), part1, args[0].charAt(0));
                        p.sendMessage(message);
                    }
                    else{
                        String part1 = "§7a-f, k-o§4";
                        String part2 = "§70-9§4";
                        String message = String.format(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " " + cfg.getString(plugin.lang + ".ChatColor.misusage"), part1, part2);
                        p.sendMessage(message);
                    }
                    plugin.saveConfig();
                    return true;
                }
                else if(args.length == 2) {
                    if (args[0].matches(allowed)) {
                        plugin.cfg.set("user." + p.getName() + ".prefix_color", args[0].charAt(0));
                        String part1 = "§o";
                        String message = String.format(ChatColor.GRAY + plugin.PLUGIN_NAME + " " + cfg.getString(plugin.lang + ".ChatColor.prefixColor"), part1, args[0].charAt(0));
                        p.sendMessage(message);
                    }
                    if (args[1].matches(allowed)) {
                        plugin.cfg.set("user." + p.getName() + ".chat_color", args[1].charAt(0));
                        String part1 = "§o";
                        String message = String.format(ChatColor.GRAY + plugin.PLUGIN_NAME + " " + cfg.getString(plugin.lang + ".ChatColor.chatColor"), part1, args[1].charAt(0));
                        p.sendMessage(message);
                    } else {
                        String part1 = "§7a-f, k-o§4";
                        String part2 = "§70-9§4";
                        String message = String.format(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " " + cfg.getString(plugin.lang + ".ChatColor.misusage"), part1, part2);
                        p.sendMessage(message);
                    }
                    plugin.saveConfig();
                    return true;
                }
            }else{
                sender.sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " " + cfg.getString(plugin.lang + ".permission-missing"));
                return true;
            }
        }else{
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " The Console can't edit it's chat appearance");
            return true;
        }
        return false;
    }
}
