package de.sdfzgufjkfdsgtzr.plugin.commands;

import de.sdfzgufjkfdsgtzr.plugin.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Home implements CommandExecutor {

    private Main plugin;

    public Home(Main plugin){
        this.plugin = plugin;
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(plugin.conActive) {
            if (sender instanceof Player) {
                if (sender.hasPermission("spl.util.home")) {
                    Player p = (Player) sender;
                    if (args.length == 0) {
                        int coords[] = getHome(p.getUniqueId());
                        if (coords != null) {
                            Location home = new Location(p.getWorld(), coords[0], coords[1], coords[2]);
                            p.teleport(home);
                            p.sendMessage(ChatColor.GRAY + "Du bist nun Zuhause");
                            return true;
                        }
                    } else if (args.length == 1) {
                        if (args[0].equals("set")) {
                            int x = p.getLocation().getBlockX();
                            int y = p.getLocation().getBlockY();
                            int z = p.getLocation().getBlockZ();
                            setHome(p.getUniqueId(), x, y, z);
                            p.sendMessage(ChatColor.GRAY + "Dein Zuhause wurde bei X: " + x + " Y: " + y + " Z: " + z + " gesetzt");
                            return true;
                        }
                    } else {
                        sender.sendMessage("§CBitte /home <set> nutzen");
                        return true;
                    }
                } else{
                    sender.sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " Dir fehlt die notwendige Berechtigung!");
                    return true;
                }
            }else {
                Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " We don't do this here!");
                return true;
            }
        }else{
            sender.sendMessage("§cInterner Serverfehler...");
            return true;
        }
        return false;
    }


    private void setHome(UUID uuid, int x, int y, int z){
        try{
            PreparedStatement statement = plugin.con.getConnection().prepareStatement("UPDATE " + plugin.con.table + " SET HOMEX=?,HOMEY=?,HOMEZ=? WHERE UUID=?");
            statement.setInt(1, x);
            statement.setInt(2, y);
            statement.setInt(3, z);
            statement.setString(4, uuid.toString());
            statement.executeUpdate();
        }catch(SQLException e){
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + plugin.PLUGIN_NAME + " Error setting home for UUID: " + uuid.toString());
        }
    }


    private int[] getHome(UUID uuid){
        int[] coords = new int[3];
        try{
            PreparedStatement statement = plugin.con.getConnection().prepareStatement("SELECT * FROM " + plugin.con.table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();

            int x = results.getInt("HOMEX");
            int y = results.getInt("HOMEY");
            int z = results.getInt("HOMEZ");
            coords[0] = x;
            coords[1] = y;
            coords[2] = z;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return coords;
    }
}
