package de.sdfzgufjkfdsgtzr.plugin.commands.world;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spawn implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (label.equalsIgnoreCase("spawn")) {
                player.teleport(Bukkit.getWorld("world").getSpawnLocation());
            } else if (label.equalsIgnoreCase("setspawn")) {
                Bukkit.getWorld("world").setSpawnLocation(player.getLocation());
            }
            return true;
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "The console cannot spawn in the world");
            return true;
        }
    }
}
