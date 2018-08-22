package de.sdfzgufjkfdsgtzr.plugin.economy;

import de.sdfzgufjkfdsgtzr.plugin.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import java.util.UUID;

public class Economy implements CommandExecutor {

    private Main plugin;

    public Economy(Main plugin){
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String eco = plugin.cfg.getString("economy.name");
        String currency = plugin.cfg.getString("economy.currency");
        if (label.equals("balance")) {

        }

        if (label.equals("pay")) {

        }
        return false;
    }

    private static void setBalance(UUID uuid, Double balance){

    }

    private double getBalanceByUUID(UUID uuid){
        double balance  = 0.0;
        return balance;
    }
}
