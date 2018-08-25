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

    public Home(Main plugin) {
        this.plugin = plugin;
        this.lang = plugin.getLanguageFile();
        this.homes = plugin.getHomeFile();
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (label.equalsIgnoreCase("home")) {
                if (sender.hasPermission("add.player.home.teleport")) {
                    Player player = (Player) sender;
                    if (args.length == 0) {
                        int coords[] = getDefaultHome(player);
                        if (coords != null) {
                            Location home = new Location(Bukkit.getWorld(homes.getString("users." + player.getName() + ".default.world")), coords[0], coords[1], coords[2]);
                            player.teleport(home);
                            player.sendMessage(ChatColor.GRAY + lang.getString(plugin.lang + ".home.teleport-message"));
                        } else {
                            sender.sendMessage(ChatColor.DARK_RED + lang.getString(plugin.lang + ".home.not-set"));
                        }
                    } else if (args.length == 1) {
                        int coords[] = getHome(player, args[0]);
                        if (coords != null) {
                            Location home = new Location(Bukkit.getWorld(homes.getString("users." + player.getName() + "." + args[0] + ".world")), coords[0], coords[1], coords[2]);
                            player.teleport(home);
                            player.sendMessage(ChatColor.GRAY + lang.getString(plugin.lang + ".home.teleport-message"));
                        } else {
                            sender.sendMessage(ChatColor.DARK_RED + lang.getString(plugin.lang + ".home.not-set-multiple"));
                        }
                    } else {
                        sender.sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " " + lang.getString(plugin.lang + ".home.teleport-usage"));
                    }
                    return true;
                } else {
                    sender.sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " " + lang.getString(plugin.lang + ".permission-missing"));
                    return true;
                }
            }

            if (label.equalsIgnoreCase("sethome")) {
                if (sender.hasPermission("add.player.home.set")) {
                    Player player = (Player) sender;
                    int x = player.getLocation().getBlockX();
                    int y = player.getLocation().getBlockY();
                    int z = player.getLocation().getBlockZ();
                    if (args.length == 0) {
                        setDefaultHome(player, player.getWorld(), x, y, z);
                        String message = String.format(lang.getString(plugin.lang + ".home.set.default"), x, y, z, player.getWorld().getName());
                        player.sendMessage(ChatColor.GRAY + message);
                    } else if (args.length == 1) {
                        if (player.hasPermission("add.player.home.set.multiple")) {
                            setHome(player, player.getWorld(), x, y, z, args[0]);
                            String message = String.format(lang.getString(plugin.lang + ".home.set"), args[0], x, y, z, player.getWorld().getName());
                            player.sendMessage(ChatColor.GRAY + message);
                        } else {
                            sender.sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " " + lang.getString(plugin.lang + ".home.perm-multiple-missing"));
                        }
                    } else {
                        sender.sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " " + lang.getString(plugin.lang + ".home.teleport-usage-set"));
                    }
                    return true;
                } else {
                    sender.sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " " + lang.getString(plugin.lang + ".permission-missing"));
                    return true;
                }
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " The Console cannot set it's home!");
            return true;
        }
        return false;
    }


    private void setHome(Player player, World world, int x, int y, int z, String home) {
        homes.set("users." + player.getName() + "." + home + ".x", x);
        homes.set("users." + player.getName() + "." + home + ".y", y);
        homes.set("users." + player.getName() + "." + home + ".z", z);
        homes.set("users." + player.getName() + "." + home + ".world", world.getName());
        plugin.saveConfigFile(homes, plugin.home_file);
    }

    private void setDefaultHome(Player player, World world, int x, int y, int z) {
        homes.set("users." + player.getName() + ".default.x", x);
        homes.set("users." + player.getName() + ".default.y", y);
        homes.set("users." + player.getName() + ".default.z", z);
        homes.set("users." + player.getName() + ".default.world", world.getName());
        plugin.saveConfigFile(homes, plugin.home_file);
    }


    private int[] getHome(Player player, String home) {
        int[] coords = new int[3];

        if (hasHome(player, home)) {
            coords[0] = homes.getInt("users." + player.getName() + "." + home + ".x");
            coords[1] = homes.getInt("users." + player.getName() + "." + home + ".y");
            coords[2] = homes.getInt("users." + player.getName() + "." + home + ".z");
            return coords;
        }
        return null;
    }

    private int[] getDefaultHome(Player player) {
        int[] coords = new int[3];

        if (homes.isSet("users." + player.getName() + ".default")) {
            coords[0] = homes.getInt("users." + player.getName() + ".default.x");
            coords[1] = homes.getInt("users." + player.getName() + ".default.y");
            coords[2] = homes.getInt("users." + player.getName() + ".default.z");
            return coords;
        }
        return null;
    }

    private boolean hasHome(Player player, String home) {
        return (homes.isSet("users." + player.getName() + "." + home + ".x") && homes.isSet("users." + player.getName() + "." + home + ".y") && homes.isSet("users." + player.getName() + "." + home + ".z"));
    }
}