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

public class Home implements CommandExecutor {

    private Main plugin;
    private FileConfiguration lang;
    private FileConfiguration homes;

    public Home(Main plugin){
        this.plugin = plugin;
        this.lang = plugin.getLanguageFile();
        this.homes = plugin.getHomeFile();
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("add.player.home")) {
                Player player = (Player) sender;
                if (args.length == 0) {
                    int coords[] = getHome(player);
                    if (coords != null) {
                        Location home = new Location(Bukkit.getWorld(homes.getString("users." + player.getUniqueId().toString() + ".world")), coords[0], coords[1], coords[2]);
                        player.teleport(home);
                        player.sendMessage(ChatColor.GRAY + lang.getString(plugin.lang + ".home.teleport-message"));
                        return true;
                    }
                    else{
                        return false;
                    }
                } else if (args.length == 1) {
                    if (args[0].equals("set")) {
                        int x = player.getLocation().getBlockX();
                        int y = player.getLocation().getBlockY();
                        int z = player.getLocation().getBlockZ();
                        setHome(player, player.getWorld(), x, y, z);
                        String message = String.format(lang.getString(plugin.lang + ".home.set"), x, y, z, player.getWorld().getName());
                        player.sendMessage(ChatColor.GRAY + message);
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


    private void setHome(Player player, World world, int x, int y, int z) {
        homes.set("users." + player.getName() + ".x", x);
        homes.set("users." + player.getName() + ".y", y);
        homes.set("users." + player.getName() + ".z", z);
        homes.set("users." + player.getName() + ".world", world.getName());
        plugin.saveConfigFile(homes, plugin.home_file);
    }


    private int[] getHome(Player player) {
        int[] coords = new int[3];
        coords[0] = homes.getInt("users." + player.getName() + ".x");
        coords[1] = homes.getInt("users." + player.getName() + ".y");
        coords[2] = homes.getInt("users." + player.getName() + ".z");
        return coords;
    }
}
