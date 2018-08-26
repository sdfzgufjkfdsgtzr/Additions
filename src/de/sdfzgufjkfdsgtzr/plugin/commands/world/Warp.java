package de.sdfzgufjkfdsgtzr.plugin.commands.world;

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

public class Warp implements CommandExecutor {

    private Main plugin;
    private FileConfiguration lang;
    private FileConfiguration warps;
    private File warpFile;

    public Warp(Main plugin) {
        this.plugin = plugin;
        this.lang = plugin.getLanguages();
        this.warps = plugin.getWarps();
        this.warpFile = plugin.getWarpFile();
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (label.equalsIgnoreCase("warp")) {
                if (player.hasPermission("add.player.warp.teleport")) {
                    if (args.length == 1) {
                        int coords[] = getWarp(args[0]);
                        if (coords != null) {
                            Location home = new Location(Bukkit.getWorld(warps.getString("warp." + args[0] + ".world")), coords[0], coords[1], coords[2]);
                            player.teleport(home);
                            String message = String.format(lang.getString(plugin.lang + ".warp.message"), args[0]);
                            player.sendMessage(ChatColor.GRAY + message);
                        } else {
                            String message = String.format(lang.getString(plugin.lang + ".warp.not-set"), args[0]);
                            player.sendMessage(ChatColor.DARK_RED + message);
                        }
                    } else {
                        player.sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " " + lang.getString(plugin.lang + ".warp.usage"));
                    }
                    return true;
                } else {
                    player.sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " " + lang.getString(plugin.lang + ".permission-missing"));
                    return true;
                }
            }

            if (label.equalsIgnoreCase("setwarp")) {
                if (player.hasPermission("add.player.warp.set")) {
                    int x = player.getLocation().getBlockX();
                    int y = player.getLocation().getBlockY();
                    int z = player.getLocation().getBlockZ();
                    if (args.length == 1) {
                        setWarp(player.getWorld(), x, y, z, args[0]);
                        String message = String.format(lang.getString(plugin.lang + ".warp.set"), args[0], x, y, z, player.getWorld().getName());
                        player.sendMessage(ChatColor.GRAY + message);
                    } else {
                        player.sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " " + lang.getString(plugin.lang + ".warp.usage-set"));
                    }
                    return true;
                } else {
                    player.sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " " + lang.getString(plugin.lang + ".permission-missing"));
                    return true;
                }
            }
            if (label.equalsIgnoreCase("delwarp")) {
                if (player.hasPermission("add.player.warp.del")) {
                    int x = player.getLocation().getBlockX();
                    int y = player.getLocation().getBlockY();
                    int z = player.getLocation().getBlockZ();
                    if (args.length == 1) {
                        if (warpExists(args[0])) {
                            deleteWarp(args[0]);
                            String message = String.format(lang.getString(plugin.lang + ".warp.del"), args[0], x, y, z, player.getWorld().getName());
                            player.sendMessage(ChatColor.GRAY + message);
                        } else {
                            String message = String.format(lang.getString(plugin.lang + ".warp.not-set"), args[0]);
                            player.sendMessage(ChatColor.DARK_RED + message);
                        }
                    } else {
                        player.sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " " + lang.getString(plugin.lang + ".warp.usage-del"));
                    }
                    return true;
                } else {
                    player.sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " " + lang.getString(plugin.lang + ".permission-missing"));
                    return true;
                }
            }

        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " The Console's warp point is the shell!");
            return true;
        }
        return false;
    }


    /**
     * sets the warp point
     */
    private void setWarp(World world, int x, int y, int z, String warp) {
        warps.set("warp." + warp + ".x", x);
        warps.set("warp." + warp + ".y", y);
        warps.set("warp." + warp + ".z", z);
        warps.set("warp." + warp + ".world", world.getName());
        plugin.saveConfigFile(warps, warpFile);
    }


    /**
     * @return returns the specified warp if existing
     */
    private int[] getWarp(String warp) {
        int[] coords = new int[3];

        if (warpExists(warp)) {
            coords[0] = warps.getInt("warp." + warp + ".x");
            coords[1] = warps.getInt("warp." + warp + ".y");
            coords[2] = warps.getInt("warp." + warp + ".z");
            return coords;
        }
        return null;
    }


    /**
     * deletes the provided warp
     */
    private void deleteWarp(String warp) {
        warps.set("warp." + warp, null);
        plugin.saveConfigFile(warps, warpFile);
    }

    /**
     * @return if the home was found
     */
    private boolean warpExists(String warp) {
        return (warps.isSet("warp." + warp + ".x") && warps.isSet("warp." + warp + ".y") && warps.isSet("warp." + warp + ".z"));
    }


}