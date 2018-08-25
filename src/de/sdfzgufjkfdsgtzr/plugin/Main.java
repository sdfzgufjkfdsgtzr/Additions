package de.sdfzgufjkfdsgtzr.plugin;

import de.sdfzgufjkfdsgtzr.plugin.commands.ChunkNotifier;
import de.sdfzgufjkfdsgtzr.plugin.commands.Home;
import de.sdfzgufjkfdsgtzr.plugin.commands.Maintenance;
import de.sdfzgufjkfdsgtzr.plugin.commands.SetChatColor;
import de.sdfzgufjkfdsgtzr.plugin.economy.Economy;
import de.sdfzgufjkfdsgtzr.plugin.events.*;
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
    public File home_file = new File(this.getDataFolder() + "/homes.yml");
    private FileConfiguration languageFile = YamlConfiguration.loadConfiguration(lang_file);
    private FileConfiguration homeFile = YamlConfiguration.loadConfiguration(home_file);
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
        this.getCommand("balance").setExecutor(new Economy(this));
        this.getCommand("pay").setExecutor(new Economy(this));
        this.getCommand("slimecheck").setExecutor(new ChunkNotifier(this));

        maintenance = cfg.getBoolean("startup.maintenance");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + PLUGIN_NAME + " Successfully loaded!");
    }

    @Override
    public void onDisable(){
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + PLUGIN_NAME +" Successfully unloaded!");
    }


    private void loadConfig(){
        cfg.options().copyDefaults(true);
        saveConfig();
        getLanguageFile().options().copyDefaults(true);
        saveConfigFile(languageFile, lang_file);
        saveConfigFile(homeFile, home_file);
    }

    public FileConfiguration getLanguageFile() {
        return languageFile;
    }

    public FileConfiguration getHomeFile() {
        return homeFile;
    }

    public void saveConfigFile(FileConfiguration configFile, File file) {
        try {
            configFile.save(file);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + PLUGIN_NAME + " Error saving locales. Please check the existance of the file \"language.yml\"");
        }
    }

    private void addGermanLanguageDefaults() {
        this.getLanguageFile().addDefault("de.permission-missing", "Du hast keine Berechtigung das zu tun");
        this.getLanguageFile().addDefault("de.permission-join-missing", "Du hast keine Berechtigung den Server zu betreten");

        this.getLanguageFile().addDefault("de.ChunkNotifier.Notify", "Du erhälst ab sofort Benachrichtigungen über Slimechunks");
        this.getLanguageFile().addDefault("de.ChunkNotifier.No-Notify", "Du erhälst ab sofort keine Benachrichtigungen über Slimechunks mehr");
        this.getLanguageFile().addDefault("de.ChunkNotifier.enter-chunk", "Du befindest dich nun in einem Slimechunk");
        this.getLanguageFile().addDefault("de.ChunkNotifier.leave-chunk", "Du befindest dich nun nicht mehr in einem Slimechunk");

        this.getLanguageFile().addDefault("de.home.message", "Du bist nun Zuhause");
        this.getLanguageFile().addDefault("de.home.usage", "Bitte /home <home> nutzen");
        this.getLanguageFile().addDefault("de.home.set", "Dein Zuhause: \"%s\" wurde bei X: %d Y: %d Z: %d in der Welt: \"%s\" gesetzt");
        this.getLanguageFile().addDefault("de.home.set-default", "Dein Zuhause wurde bei X: %d Y: %d Z: %d in der Welt: \"%s\" gesetzt");
        this.getLanguageFile().addDefault("de.home.del", "Dein Zuhause: \"%s\" bei X: %d Y: %d Z: %d in der Welt: \"%s\" wurde gelöscht");
        this.getLanguageFile().addDefault("de.home.del-default", "Dein Zuhause bei X: %d Y: %d Z: %d in der Welt: \"%s\" wurde gelöscht");
        this.getLanguageFile().addDefault("de.home.not-set", "Du hast noch kein Zuhause!");
        this.getLanguageFile().addDefault("de.home.not-set-multiple", "Du hast noch kein Zuhause mit diesem Namen!");
        this.getLanguageFile().addDefault("de.home.perm-multiple-missing", "Du darfst nicht mehrere Homes haben!");

        this.getLanguageFile().addDefault("de.service.deactivated", "%s hat den Wartungsmodus deaktiviert");
        this.getLanguageFile().addDefault("de.service.activated", "%s hat den Wartungsmodus aktiviert");
        this.getLanguageFile().addDefault("de.service.ping", "Der Server ist gerade im Wartungsmodus");
        this.getLanguageFile().addDefault("de.service.value", "Der Wartungsmodus steht auf: %b");

        this.getLanguageFile().addDefault("de.ChatColor.default", "Deine Chat-Farben wurden auf den Standard zurückgesetzt");
        this.getLanguageFile().addDefault("de.ChatColor.prefixColor", "Die neue Farbe deines Namens ist: %s%s");
        this.getLanguageFile().addDefault("de.ChatColor.chatColor", "Die neue Farbe deiner Nachricht ist: %s%s");
        this.getLanguageFile().addDefault("de.ChatColor.misusage", "Du darfst nur %s oder %s nutzen!");

        this.getLanguageFile().addDefault("de.event.join", "&7%s ist jetzt &aonline");
        this.getLanguageFile().addDefault("de.event.leave", "&7%s ist jetzt &coffline");
        this.getLanguageFile().addDefault("de.event.sleep", "Die Sonne geht langsam auf...");
        this.getLanguageFile().addDefault("de.event.no-sleep", "Es schlafen nur %d von %d nötigen Spielern!");
    }

    private void addEnglishLanguageDefaults() {
        this.getLanguageFile().addDefault("en.permission-missing", "You are not allowed to do this");
        this.getLanguageFile().addDefault("en.permission-join-missing", "You are not allowed to enter the server");

        this.getLanguageFile().addDefault("en.ChunkNotifier.Notify", "You will now receive notfications about slime chunks");
        this.getLanguageFile().addDefault("en.ChunkNotifier.No-Notify", "You won't receive notifications about slime chunks anymore");
        this.getLanguageFile().addDefault("en.ChunkNotifier.enter-chunk", "You just entered a slime chunk");
        this.getLanguageFile().addDefault("en.ChunkNotifier.leave-chunk", "You aren't in a slime chunk anymore");

        this.getLanguageFile().addDefault("en.home.message", "You are now at home");
        this.getLanguageFile().addDefault("en.home.usage", "Please use /home <home>");
        this.getLanguageFile().addDefault("en.home.usage-set", "Please use /sethome <home>");
        this.getLanguageFile().addDefault("en.home.set", "Your home: \"%s\" was set at X: %d Y: %d Z: %d in world: \"%s\"");
        this.getLanguageFile().addDefault("en.home.set-default", "Your home was set at X: %d Y: %d Z: %d in world: \"%s\"");
        this.getLanguageFile().addDefault("en.home.del", "Your home: \"%s\" at X: %d Y: %d Z: %d in world: \"%s\" was deleted");
        this.getLanguageFile().addDefault("en.home.del-default", "Your home at X: %d Y: %d Z: %d in world: \"%s\" was deleted");
        this.getLanguageFile().addDefault("en.home.not-set", "You do not have a default home yet!");
        this.getLanguageFile().addDefault("en.home.not-set-multiple", "You do not have a home with this name yet!");
        this.getLanguageFile().addDefault("en.home.perm-multiple-missing", "You are not allowed to have multiple homes!");

        this.getLanguageFile().addDefault("en.service.deactivated", "%s deactivated the service mode");
        this.getLanguageFile().addDefault("en.service.activated", "%s activated the service mode");
        this.getLanguageFile().addDefault("en.service.ping", "The server is in service mode at the moment");
        this.getLanguageFile().addDefault("en.service.value", "Service mode: %b");

        this.getLanguageFile().addDefault("en.ChatColor.default", "The chat format defaults were restored");
        this.getLanguageFile().addDefault("en.ChatColor.prefixColor", "Your new name format: %s%s");
        this.getLanguageFile().addDefault("en.ChatColor.chatColor", "Your new message format: %s%s");
        this.getLanguageFile().addDefault("en.ChatColor.misusage", "You are only allowed to use %s or %s!");

        this.getLanguageFile().addDefault("en.event.join", "&7%s is now &aonline");
        this.getLanguageFile().addDefault("en.event.leave", "&7%s is now &coffline");
        this.getLanguageFile().addDefault("en.event.sleep", "The sun is rising slowly..");
        this.getLanguageFile().addDefault("en.event.no-sleep", "There are only %d out of %d needed players sleeping!");
    }
}
