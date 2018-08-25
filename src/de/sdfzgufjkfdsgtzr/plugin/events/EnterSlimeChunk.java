package de.sdfzgufjkfdsgtzr.plugin.events;

import de.sdfzgufjkfdsgtzr.plugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Random;

public class EnterSlimeChunk implements Listener {

    private Main plugin;
    private FileConfiguration cfg;

    public EnterSlimeChunk(Main plugin){
        this.plugin = plugin;
        this.cfg = plugin.getLanguageFile();
    }

    @EventHandler
    public void onSlimeChunkUpdate(PlayerMoveEvent e){
        if(wantsNotification(e.getPlayer())){
            Chunk oldLocation = e.getFrom().getChunk();
            Chunk newLocation = e.getTo().getChunk();
            long seed = e.getPlayer().getWorld().getSeed();


            if(isSlimeChunk(seed, newLocation) && (oldLocation != newLocation)){
                e.getPlayer().sendMessage(ChatColor.DARK_GREEN + plugin.PLUGIN_NAME + " " + cfg.getString(plugin.lang + ".ChunkNotifier.enter-chunk"));
            }
            else if(!isSlimeChunk(seed, newLocation) && (oldLocation != newLocation) && isSlimeChunk(seed, oldLocation)){
                e.getPlayer().sendMessage(ChatColor.DARK_RED + plugin.PLUGIN_NAME + " " + cfg.getString(plugin.lang + ".ChunkNotifier.leave-chunk"));
            }
        }
    }

    private boolean isSlimeChunk(long seed, Chunk loc){
        double xPos = loc.getX();
        long x = (long) Math.floor(xPos);
        double zPos =loc.getZ();
        long z = (long) Math.floor(zPos);
        Random rnd = new Random(seed +
                (x * x * 0x4c1906) +
                (x * 0x5ac0db) +
                (z * z) * 0x4307a7L +
                (z * 0x5f24f) ^ 0x3ad8025f);
        return rnd.nextInt(10) == 0;
    }

    private boolean wantsNotification(Player p){
        return plugin.cfg.getBoolean("user." + p.getName() + ".notify");
    }
}
