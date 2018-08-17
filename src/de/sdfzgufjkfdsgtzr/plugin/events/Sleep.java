package de.sdfzgufjkfdsgtzr.plugin.events;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import java.util.ArrayList;

public class Sleep implements Listener {

    private static ArrayList<Player> playersSleeping = new ArrayList<>();

    @EventHandler
    public void onPlayerGoToBed(PlayerBedEnterEvent e){
        playersSleeping.add(e.getPlayer());
        System.out.println("Schlafende Spieler: " + playersSleeping.size() + " Anzahl Spieler in Welt: " + e.getPlayer().getWorld().getPlayers().size());
        if((e.getPlayer().getWorld().getPlayers().size()/3 <= playersSleeping.size() && validTime(e.getPlayer().getWorld())) || (e.getPlayer().getWorld().getPlayers().size()/3 <= playersSleeping.size() && e.getPlayer().getWorld().isThundering())){
            e.getPlayer().getWorld().setTime(0);
            e.getPlayer().getServer().broadcastMessage("§7Die Sonne geht langsam auf...");
            e.getPlayer().getWorld().setThundering(false);
            e.getPlayer().getWorld().setStorm(false);
        } else{
            e.getPlayer().sendMessage("§7Es ist momentan nicht möglich zu schlafen...");
            e.getPlayer().sendMessage("§7Es schlafen nur " + playersSleeping.size() + " von " + e.getPlayer().getWorld().getPlayers().size() / 3 + " nötigen Spielern!");
        }
    }

    @EventHandler
    public void onPlayerLeaveBed(PlayerBedLeaveEvent e){
        playersSleeping.remove(e.getPlayer());
        System.out.println("Schlafende Spieler: " + playersSleeping.size() + " Anzahl Spieler in Welt: " + e.getPlayer().getWorld().getPlayers().size());
    }

    private static boolean validTime(World w){
        long time = w.getTime();
        return (time < 24000 && time > 12000);
    }
}
