package de.sdfzgufjkfdsgtzr.plugin.commands;

import de.sdfzgufjkfdsgtzr.plugin.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;

public class Home implements CommandExecutor {

    private Main plugin;
    private FileConfiguration lang;
    private File file;
    private FileConfiguration homes;

    public Home(Main plugin){
        this.plugin = plugin;
        this.lang = plugin.getLanguageFile();
        homes = plugin.getHomeFile();
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("spl.util.home")) {
                Player p = (Player) sender;
                if (args.length == 0) {
                    int coords[] = getHome(p.getUniqueId());
                    if (coords != null) {
                        Location home = new Location(Bukkit.getWorld(homes.getString("users." + p.getUniqueId().toString() + ".world")), coords[0], coords[1], coords[2]);
                        p.teleport(home);
                        p.sendMessage(ChatColor.GRAY + lang.getString(plugin.lang + ".home.teleport-message"));
                        return true;
                    }
                    else{
                        return false;
                    }
                } else if (args.length == 1) {
                    if (args[0].equals("set")) {
                        int x = p.getLocation().getBlockX();
                        int y = p.getLocation().getBlockY();
                        int z = p.getLocation().getBlockZ();
                        setHome(p.getUniqueId(), p.getWorld(), x, y, z);
                        String message = String.format(lang.getString(plugin.lang + ".home.set"), x, y, z, p.getWorld().getName());
                        p.sendMessage(ChatColor.GRAY + message);
                        return true;
                    }
                } else {
                    sender.sendMessage(ChatColor.DARK_RED + lang.getString(plugin.lang + ".home.teleport-usage"));
                    return true;
                }
            } else{
                sender.sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " " + lang.getString(plugin.lang + ".permission-missing"));
                return true;
            }
        }else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " The Console cannot set it's home!");
            return true;
        }
        return false;
    }


    private void setHome(UUID uuid, World world, int x, int y, int z){
        homes.set("users." + uuid.toString() + ".x", x);
        homes.set("users." + uuid.toString() + ".y", y);
        homes.set("users." + uuid.toString() + ".z", z);
        homes.set("users." + uuid.toString() + ".world", world.getName());
        plugin.saveConfigFile(homes, plugin.home_file);
    }


    private int[] getHome(UUID uuid){
        int[] coords = new int[3];
        coords[0] = homes.getInt("users." + uuid.toString() + ".x");
        coords[1] = homes.getInt("users." + uuid.toString() + ".y");
        coords[2] = homes.getInt("users." + uuid.toString() + ".z");
        return coords;
    }
}
