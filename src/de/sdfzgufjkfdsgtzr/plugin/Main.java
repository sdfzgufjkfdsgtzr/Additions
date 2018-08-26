package de.sdfzgufjkfdsgtzr.plugin;

import de.sdfzgufjkfdsgtzr.plugin.commands.player.Home;
import de.sdfzgufjkfdsgtzr.plugin.commands.player.SetChatColor;
import de.sdfzgufjkfdsgtzr.plugin.commands.server.Maintenance;
import de.sdfzgufjkfdsgtzr.plugin.commands.world.ChunkNotifier;
import de.sdfzgufjkfdsgtzr.plugin.commands.world.Spawn;
import de.sdfzgufjkfdsgtzr.plugin.commands.world.Warp;
import de.sdfzgufjkfdsgtzr.plugin.events.player.Chat;
import de.sdfzgufjkfdsgtzr.plugin.events.server.JoinLeave;
import de.sdfzgufjkfdsgtzr.plugin.events.server.ServerPing;
import de.sdfzgufjkfdsgtzr.plugin.events.world.EnterSlimeChunk;
import de.sdfzgufjkfdsgtzr.plugin.events.world.Sleep;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;


public class Main extends JavaPlugin {

    public FileConfiguration cfg;
    public boolean maintenance;
    public final String PLUGIN_NAME = "[Additions]";
    private File lang_file = new File(this.getDataFolder() + "/language.yml");
    private File home_file = new File(this.getDataFolder() + "/homes.yml");
    private File warp_file = new File(this.getDataFolder() + "/warps.yml");
    private FileConfiguration languages = YamlConfiguration.loadConfiguration(lang_file);
    private FileConfiguration homes = YamlConfiguration.loadConfiguration(home_file);
    private FileConfiguration warps = YamlConfiguration.loadConfiguration(warp_file);
    public String lang;



    @Override
    public void onEnable(){
        PluginManager pm;
        cfg = getConfig();
        addGermanLanguageDefaults();
        addEnglishLanguageDefaults();
        this.cfg.addDefault("startup.language", "en");
        loadConfig();
        this.lang = cfg.getString("startup.language");

        pm = getServer().getPluginManager();
        pm.registerEvents(new JoinLeave(this), this);
        pm.registerEvents(new ServerPing(this), this);
        pm.registerEvents(new Sleep(this), this);
        pm.registerEvents(new Chat(this), this);
        pm.registerEvents(new EnterSlimeChunk(this), this);

        this.getCommand("color").setExecutor(new SetChatColor(this));
        this.getCommand("home").setExecutor(new Home(this));
        this.getCommand("sethome").setExecutor(new Home(this));
        this.getCommand("delhome").setExecutor(new Home(this));
        this.getCommand("service").setExecutor(new Maintenance(this));
        /*this.getCommand("balance").setExecutor(new Economy(this));
        this.getCommand("pay").setExecutor(new Economy(this));*/
        this.getCommand("slimecheck").setExecutor(new ChunkNotifier(this));
        this.getCommand("warp").setExecutor(new Warp(this));
        this.getCommand("setwarp").setExecutor(new Warp(this));
        this.getCommand("delwarp").setExecutor(new Warp(this));
        this.getCommand("warps").setExecutor(new Warp(this));
        this.getCommand("setspawn").setExecutor(new Spawn());

        maintenance = cfg.getBoolean("startup.maintenance");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + PLUGIN_NAME + " Successfully loaded!");
    }

    @Override
    public void onDisable(){
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + PLUGIN_NAME +" Successfully unloaded!");
    }


    /**
     * loads config.yml, language.yml and homes.yml
     */
    private void loadConfig(){
        cfg.options().copyDefaults(true);
        saveConfig();
        getLanguages().options().copyDefaults(true);
        saveConfigFile(languages, lang_file);
        saveConfigFile(homes, home_file);
    }

    /**
     * @return returns the Languages
     */
    public FileConfiguration getLanguages() {
        return languages;
    }

    /**
     *
     * @return returns the Warps
     */
    public FileConfiguration getWarps() {
        return warps;
    }

    /**
     * @return returns the Homes
     */
    public FileConfiguration getHomes() {
        return homes;
    }

    /**
     * @return returns the WarpFile
     */
    public File getWarpFile() {
        return warp_file;
    }

    /**
     * @return returns the HomeFile
     */
    public File getHomeFile() {
        return home_file;
    }


    /**
     * saves the custom Config
     * @param configFile the File to be saves
     * @param file the file's destination
     */
    public void saveConfigFile(FileConfiguration configFile, File file) {
        try {
            configFile.save(file);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + PLUGIN_NAME + " Error saving locales. Please check the existance of the file \"language.yml\"");
        }
    }

    /**
     * sets german language defaults
     */
    private void addGermanLanguageDefaults() {
        this.getLanguages().addDefault("de.permission-missing", "Du hast keine Berechtigung das zu tun");
        this.getLanguages().addDefault("de.permission-join-missing", "Du hast keine Berechtigung den Server zu betreten");

        this.getLanguages().addDefault("de.ChunkNotifier.Notify", "Du erhälst ab sofort Benachrichtigungen über Slimechunks");
        this.getLanguages().addDefault("de.ChunkNotifier.No-Notify", "Du erhälst ab sofort keine Benachrichtigungen über Slimechunks mehr");
        this.getLanguages().addDefault("de.ChunkNotifier.enter-chunk", "Du befindest dich nun in einem Slimechunk");
        this.getLanguages().addDefault("de.ChunkNotifier.leave-chunk", "Du befindest dich nun nicht mehr in einem Slimechunk");

        this.getLanguages().addDefault("de.home.message", "Du bist nun Zuhause");
        this.getLanguages().addDefault("de.home.usage", "Bitte /home <home> nutzen");
        this.getLanguages().addDefault("de.home.set", "Dein Zuhause: \"%s\" wurde bei X: %d Y: %d Z: %d in der Welt: \"%s\" gesetzt");
        this.getLanguages().addDefault("de.home.set-default", "Dein Zuhause wurde bei X: %d Y: %d Z: %d in der Welt: \"%s\" gesetzt");
        this.getLanguages().addDefault("de.home.del", "Dein Zuhause: \"%s\" bei X: %d Y: %d Z: %d in der Welt: \"%s\" wurde gelöscht");
        this.getLanguages().addDefault("de.home.del-default", "Dein Zuhause bei X: %d Y: %d Z: %d in der Welt: \"%s\" wurde gelöscht");
        this.getLanguages().addDefault("de.home.not-set", "Du hast noch kein Zuhause!");
        this.getLanguages().addDefault("de.home.not-set-multiple", "Du hast noch kein Zuhause mit diesem Namen!");
        this.getLanguages().addDefault("de.home.perm-multiple-missing", "Du darfst nicht mehrere Homes haben!");

        this.getLanguages().addDefault("de.warp.message", "Du wurdest zum Punkt \"%s\" gewarpt");
        this.getLanguages().addDefault("de.warp.usage", "Bitte nutze /warp <warp>");
        this.getLanguages().addDefault("de.warp.usage-set", "Bitte nutze /setwarp <warp>");
        this.getLanguages().addDefault("de.warp.usage-del", "Bitte nutze /delwarp <warp>");
        //this.getLanguages().addDefault("de.warp.usage-list", "Bitte nutze /warps um alle Warps zu sehen");
        this.getLanguages().addDefault("de.warp.set", "Der Warp: \"%s\" wurde bei X: %d Y: %d Z: %d in der Welt: \"%s\" gesetzt");
        this.getLanguages().addDefault("de.warp.del", "Der Warp: \"%s\" bei X: %d Y: %d Z: %d in der Welt: \"%s\" wurde gelöscht");
        this.getLanguages().addDefault("de.warp.not-set", "Es gibt keinen Warp mit dem Namen \"%s\"!");
        //this.getLanguages().addDefault("de.warp.list", "Liste aller existierenden Warps:");

        this.getLanguages().addDefault("de.service.deactivated", "%s hat den Wartungsmodus deaktiviert");
        this.getLanguages().addDefault("de.service.activated", "%s hat den Wartungsmodus aktiviert");
        this.getLanguages().addDefault("de.service.ping", "Der Server ist gerade im Wartungsmodus");
        this.getLanguages().addDefault("de.service.value", "Der Wartungsmodus steht auf: %b");

        this.getLanguages().addDefault("de.ChatColor.default", "Deine Chat-Farben wurden auf den Standard zurückgesetzt");
        this.getLanguages().addDefault("de.ChatColor.prefixColor", "Die neue Farbe deines Namens ist: %s%s");
        this.getLanguages().addDefault("de.ChatColor.chatColor", "Die neue Farbe deiner Nachricht ist: %s%s");
        this.getLanguages().addDefault("de.ChatColor.misusage", "Du darfst nur %s oder %s nutzen!");

        this.getLanguages().addDefault("de.event.join", "&7%s ist jetzt &aonline");
        this.getLanguages().addDefault("de.event.leave", "&7%s ist jetzt &coffline");
        this.getLanguages().addDefault("de.event.sleep", "Die Sonne geht langsam auf...");
        this.getLanguages().addDefault("de.event.no-sleep", "Es schlafen nur %d von %d nötigen Spielern!");
    }

    /**
     * sets English language defaults
     */
    private void addEnglishLanguageDefaults() {
        this.getLanguages().addDefault("en.permission-missing", "You are not allowed to do this");
        this.getLanguages().addDefault("en.permission-join-missing", "You are not allowed to enter the server");

        this.getLanguages().addDefault("en.ChunkNotifier.Notify", "You will now receive notfications about slime chunks");
        this.getLanguages().addDefault("en.ChunkNotifier.No-Notify", "You won't receive notifications about slime chunks anymore");
        this.getLanguages().addDefault("en.ChunkNotifier.enter-chunk", "You just entered a slime chunk");
        this.getLanguages().addDefault("en.ChunkNotifier.leave-chunk", "You aren't in a slime chunk anymore");

        this.getLanguages().addDefault("en.home.message", "You are now at home");
        this.getLanguages().addDefault("en.home.usage", "Please use /home <home>");
        this.getLanguages().addDefault("en.home.usage-set", "Please use /sethome <home>");
        this.getLanguages().addDefault("en.home.set", "Your home: \"%s\" was set at X: %d Y: %d Z: %d in world: \"%s\"");
        this.getLanguages().addDefault("en.home.set-default", "Your home was set at X: %d Y: %d Z: %d in world: \"%s\"");
        this.getLanguages().addDefault("en.home.del", "Your home: \"%s\" at X: %d Y: %d Z: %d in world: \"%s\" was deleted");
        this.getLanguages().addDefault("en.home.del-default", "Your home at X: %d Y: %d Z: %d in world: \"%s\" was deleted");
        this.getLanguages().addDefault("en.home.not-set", "You do not have a default home yet!");
        this.getLanguages().addDefault("en.home.not-set-multiple", "You do not have a home with this name yet!");
        this.getLanguages().addDefault("en.home.perm-multiple-missing", "You are not allowed to have multiple homes!");

        this.getLanguages().addDefault("en.warp.message", "You have been warped to \"%s\"");
        this.getLanguages().addDefault("en.warp.usage", "Please use /warp <warp>");
        this.getLanguages().addDefault("en.warp.usage-set", "Please use /setwarp <warp>");
        this.getLanguages().addDefault("en.warp.usage-set", "Please use /delwarp <warp>");
        //this.getLanguages().addDefault("en.warp.usage-list", "Please use /warps to list all available warp points");
        this.getLanguages().addDefault("en.warp.set", "The warp point: \"%s\" was set at X: %d Y: %d Z: %d in world: \"%s\"");
        this.getLanguages().addDefault("en.warp.del", "The warp point: \"%s\" at X: %d Y: %d Z: %d in world: \"%s\" was deleted");
        this.getLanguages().addDefault("en.warp.not-set", "There is no warp point called \"%s\" yet!");
        //this.getLanguages().addDefault("en.warp.list", "List of all existing warp points:");

        this.getLanguages().addDefault("en.service.deactivated", "%s deactivated the service mode");
        this.getLanguages().addDefault("en.service.activated", "%s activated the service mode");
        this.getLanguages().addDefault("en.service.ping", "The server is in service mode at the moment");
        this.getLanguages().addDefault("en.service.value", "Service mode: %b");

        this.getLanguages().addDefault("en.ChatColor.default", "The chat format defaults were restored");
        this.getLanguages().addDefault("en.ChatColor.prefixColor", "Your new name format: %s%s");
        this.getLanguages().addDefault("en.ChatColor.chatColor", "Your new message format: %s%s");
        this.getLanguages().addDefault("en.ChatColor.misusage", "You are only allowed to use %s or %s!");

        this.getLanguages().addDefault("en.event.join", "&7%s is now &aonline");
        this.getLanguages().addDefault("en.event.leave", "&7%s is now &coffline");
        this.getLanguages().addDefault("en.event.sleep", "The sun is rising slowly..");
        this.getLanguages().addDefault("en.event.no-sleep", "There are only %d out of %d needed players sleeping!");
    }
}
