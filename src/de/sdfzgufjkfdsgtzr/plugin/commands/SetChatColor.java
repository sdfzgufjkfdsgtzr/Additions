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
            if(sender.hasPermission("spl.util.chatformat")){
                Player p = (Player) sender;
                String allowed = "[a-f[k-o]0-9]";
                if(args.length == 0){
                    plugin.cfg.set("user." + p.getUniqueId().toString() + ".prefix_color", plugin.cfg.getString("startup.prefix_color"));
                    plugin.cfg.set("user." + p.getUniqueId().toString() + ".chat_color", plugin.cfg.getString("startup.chat_color"));
                    p.sendMessage(ChatColor.GRAY + plugin.PLUGIN_NAME + " " + cfg.getString(plugin.lang + ".ChatColor.default") +  ":\n§" + plugin.cfg.getString("user." + p.getUniqueId().toString() + ".prefix_color") + "[Name] §" + plugin.cfg.getString("user." + p.getUniqueId().toString() + ".chat_color") + "[Chat]");
                    plugin.saveConfig();
                    return true;
                }
                else if(args.length == 1){
                    if(args[0].matches(allowed)){
                        plugin.cfg.set("user." + p.getUniqueId().toString() + ".prefix_color", args[0].charAt(0));
                        p.sendMessage(ChatColor.GRAY + "Die neue Farbe deines Namens ist: §o" + args[0].charAt(0));
                    }
                    else{
                        p.sendMessage(ChatColor.DARK_RED + "Du darfst nur §7a-f, k-o oder 0-9 §4nutzen!");
                    }
                    plugin.saveConfig();
                    return true;
                }
                else if(args.length == 2) {
                    if (args[0].matches(allowed)) {
                        plugin.cfg.set("user." + p.getUniqueId().toString() + ".prefix_color", args[0].charAt(0));
                        p.sendMessage(ChatColor.GRAY + "Die neue Farbe deines Namens ist: §o" + args[0].charAt(0));
                    }
                    if (args[1].matches(allowed)) {
                        plugin.cfg.set("user." + p.getUniqueId().toString() + ".chat_color", args[1].charAt(0));
                        p.sendMessage(ChatColor.GRAY + "Die neue Farbe deiner Nachricht ist: §o" + args[1].charAt(0));
                    } else {
                        p.sendMessage(ChatColor.DARK_RED + "Du darfst nur §7a-f, k-o oder 0-9 §4 nutzen!");
                    }
                    plugin.saveConfig();
                    return true;
                }
            }else{
                sender.sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " Dir fehlt die notwendige Berechtigung!");
                return true;
            }
        }else{
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " We don't do this here!");
            return true;
        }
        return false;
    }
}
