package de.sdfzgufjkfdsgtzr.plugin.economy;

import de.sdfzgufjkfdsgtzr.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class EconomyMain implements CommandExecutor {

    private static Main plugin;

    public EconomyMain(Main plugin){
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String eco = plugin.cfg.getString("economy.name");
        String currency = plugin.cfg.getString("economy.currency");
        if(label.equals("balance")){
            if (sender.hasPermission("spl.eco.lookup")){
                if (args.length == 0) {
                    if (sender instanceof Player){
                        Player p = (Player) sender;
                        double balance = getBalanceByUUID(p.getUniqueId());
                        sender.sendMessage("§2" + eco + "§7 Du hast " + balance + currency);
                    } else {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " The Console doesn't own any money");
                    }
                    return true;
                } else if(args.length == 1){
                    if (plugin.sqlgetset.playerExists(this.getUUID(args[0]))) {
                        double balance = getBalanceByName(args[0]);
                        sender.sendMessage(ChatColor.DARK_GREEN + eco + " §7" + args[0] + " hat " + balance + currency);
                    } else {
                        sender.sendMessage(ChatColor.DARK_GREEN + eco + " §7Es ist momentan nicht möglich die Bilanz von " + args[0] + " zu einzusehen!");
                    }
                    return true;
                }
            }else{
                sender.sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " Dir fehlt die notwendige Berechtigung!");
                return true;
            }
        }

        if(label.equals("pay")){
            if (sender.hasPermission("spl.eco.pay")){
                if(args.length == 2){
                    if(sender instanceof Player) {
                        if (plugin.sqlgetset.playerExists(this.getUUID(args[0]))) {
                            try {
                                double balance = getBalanceByUUID(((Player) sender).getUniqueId());
                                if (balance < Double.parseDouble(args[1])) {
                                    sender.sendMessage(ChatColor.DARK_RED + eco + " §7Du hast nicht genug Geld, dir fehlen: " + (Double.parseDouble(args[1]) - balance) + currency);
                                    return true;
                                }
                                else{
                                    setBalance(((Player) sender).getUniqueId(), balance-Double.parseDouble(args[1]));
                                    setBalance(getUUID(args[0]), (getBalanceByUUID(getUUID(args[0]))+Double.parseDouble(args[1])));
                                    if(Bukkit.getPlayer(args[0]).isOnline()){
                                        Bukkit.getPlayer(args[0]).sendMessage(ChatColor.DARK_GREEN + eco + " §7" + sender.getName() + " hat dir " + Double.parseDouble(args[1]) + currency + " überwiesen");

                                    }
                                    sender.sendMessage(ChatColor.DARK_GREEN + eco + " §7Du hast " + args[0] + " " + Double.parseDouble(args[1]) + currency + " überwiesen");
                                    sender.sendMessage(ChatColor.DARK_GREEN + eco + " §7Dein neuer Kontostand beträgt: "  + getBalanceByUUID(((Player) sender).getUniqueId()) + currency);
                                    Bukkit.getPlayer(args[0]).sendMessage(ChatColor.DARK_GREEN + eco + " §7Dein neuer Kontostand beträgt: " + getBalanceByUUID(getUUID(args[0])) + currency);
                                }
                            }catch(NumberFormatException e){
                                sender.sendMessage(ChatColor.DARK_RED + eco + " §7Das zweite Argument ist ungültig");
                                return false;
                            }
                        } else {
                            sender.sendMessage(ChatColor.DARK_GREEN + eco + " §7Der Spieler " + args[0] + " existiert nicht!");
                        }
                        return true;
                    }
                    else{
                        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " The Console doesn't own any money");
                        return true;
                    }
                } else{
                     return false;
                }
            }else{
                sender.sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " Dir fehlt die notwendige Berechtigung!");
                return true;
            }
        }
        return false;
    }

    private static void setBalance(UUID uuid, Double balance){
        try{
            PreparedStatement statement = plugin.con.getConnection().prepareStatement("UPDATE " + plugin.con.table + " SET BALANCE=? WHERE UUID=?");
            statement.setDouble(1, balance);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        }catch(SQLException e){
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + plugin.PLUGIN_NAME + " Error setting Balance for UUID: " + uuid.toString());
        }
    }

    private double getBalanceByUUID(UUID uuid){
        double result = -0;
        try{
            PreparedStatement statement = plugin.con.getConnection().prepareStatement("SELECT * FROM " + plugin.con.table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();

            result = results.getDouble("BALANCE");
        }catch(SQLException e){
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + plugin.PLUGIN_NAME + " Error getting Balance for UUID: " + uuid.toString());
        }
        return result;
    }

    private double getBalanceByName(String name){
        double result = -0;
        try{
            PreparedStatement statement = plugin.con.getConnection().prepareStatement("SELECT * FROM " + plugin.con.table + " WHERE user_name=?");
            statement.setString(1, name);
            ResultSet results = statement.executeQuery();
            results.next();

            result = results.getDouble("BALANCE");
        }catch(SQLException e){
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + plugin.PLUGIN_NAME + " Error getting Balance for User: " + name);
        }
        return result;
    }

    private UUID getUUID(String name){
        String result = "";
        try{
            PreparedStatement statement = plugin.con.getConnection().prepareStatement("SELECT * FROM " + plugin.con.table + " WHERE user_name=?");
            statement.setString(1, name);
            ResultSet results = statement.executeQuery();
            results.next();

            result = results.getString("UUID");
        }catch(SQLException e){
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + plugin.PLUGIN_NAME + " Error getting Balance for UUID: " + name);
        }
        return UUID.fromString(result);
    }
}
