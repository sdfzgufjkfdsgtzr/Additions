package de.sdfzgufjkfdsgtzr.plugin.events.world;

import de.sdfzgufjkfdsgtzr.plugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import java.util.ArrayList;

public class Sleep implements Listener {

    private ArrayList<Player> playersSleeping = new ArrayList<>();
    private FileConfiguration cfg;
    private Main plugin;

    public Sleep(Main plugin){
        this.plugin = plugin;
        this.cfg = plugin.getLanguages();
    }

    @EventHandler
    public void onPlayerGoToBed(PlayerBedEnterEvent e){
        World world = e.getPlayer().getWorld();
        playersSleeping.add(e.getPlayer());
        if((world.getPlayers().size()/3 <= playersSleeping.size() && validTime(world)) || (world.getPlayers().size()/3 <= playersSleeping.size() && world.isThundering())){
            world.setTime(0);
            world.setThundering(false);
            world.setStorm(false);
            for (Player p : e.getPlayer().getWorld().getPlayers()) {
                if (p.getWorld().equals(world)) {
                    p.sendMessage(ChatColor.GRAY + cfg.getString(plugin.lang + ".event.sleep"));
                }
            }
        } else{
            String message = String.format(ChatColor.GRAY + cfg.getString(plugin.lang + ".event.no-sleep"),  playersSleeping.size(), (world.getPlayers().size() / 3));
            e.getPlayer().sendMessage(message);
        }
    }

    @EventHandler
    public void onPlayerLeaveBed(PlayerBedLeaveEvent e){
        playersSleeping.remove(e.getPlayer());
    }

    /**
     * @param w world to be checked for night time
     * @return if it is night nor if it is not in the world
     */
    private boolean validTime(World w){
        long time = w.getTime();
        return (time < 24000 && time > 12000);
    }
}
