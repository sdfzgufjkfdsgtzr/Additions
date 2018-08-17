package de.sdfzgufjkfdsgtzr.plugin.SQL;

import de.sdfzgufjkfdsgtzr.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLGetterSetter {

    private static Main plugin;

    public SQLGetterSetter(Main plugin) {
        this.plugin = plugin;
    }

    public boolean playerExists(UUID uuid){
        try{
            PreparedStatement statement = plugin.con.getConnection().prepareStatement("SELECT * FROM " + plugin.con.table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());

            ResultSet results = statement.executeQuery();

            if(results.next()){
                Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + plugin.PLUGIN_NAME + " Player found!");
                return true;
            }
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " Player not found!");
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public void createPlayer(final UUID uuid, Player player){
        try {
            PreparedStatement statement = plugin.con.getConnection().prepareStatement("SELECT * FROM " + plugin.con.table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();
            if(!this.playerExists(uuid)){
                PreparedStatement insert = plugin.con.getConnection()
                        .prepareStatement("INSERT INTO " + plugin.con.table + " (UUID, NAME, BALANCE, HOMEX, HOMEY, HOMEZ) VALUE (?,?,?,?,?,?)");
                insert.setString(1, uuid.toString());
                insert.setString(2, player.getName());
                insert.setDouble(3, 0);
                insert.setInt(4, player.getWorld().getSpawnLocation().getBlockX());
                insert.setInt(5, player.getWorld().getSpawnLocation().getBlockY()+1);
                insert.setInt(6, player.getWorld().getSpawnLocation().getBlockZ());
                insert.executeUpdate();

                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + plugin.PLUGIN_NAME + " Player successfully inserted!");
            }
        }catch(SQLException e){
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " Failed to insert Player to Database!");
        }
    }
}
