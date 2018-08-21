package de.sdfzgufjkfdsgtzr.plugin;

import de.sdfzgufjkfdsgtzr.plugin.SQL.SQLGetterSetter;
import de.sdfzgufjkfdsgtzr.plugin.commands.ChunkNotifier;
import de.sdfzgufjkfdsgtzr.plugin.commands.Home;
import de.sdfzgufjkfdsgtzr.plugin.commands.Maintenance;
import de.sdfzgufjkfdsgtzr.plugin.commands.SetChatColor;
import de.sdfzgufjkfdsgtzr.plugin.economy.EconomyMain;
import de.sdfzgufjkfdsgtzr.plugin.events.*;
import de.sdfzgufjkfdsgtzr.plugin.SQL.MySQLCon;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


public class Main extends JavaPlugin {

    public FileConfiguration cfg;
    public boolean maintenance;
    public final SQLGetterSetter sqlgetset = new SQLGetterSetter(this);
    public MySQLCon con;
    public boolean conActive = true;
    public final String PLUGIN_NAME = "[PluginTools]";
    private File file = new File(this.getDataFolder() + "/language.yml");
    private FileConfiguration languageFile = YamlConfiguration.loadConfiguration(file);
    private String lang;

    @Override
    public void onEnable(){
        PluginManager pm;
        cfg = getConfig();
        addLanguageDefaults();
        loadConfig();
        establishConnection();
        this.lang = cfg.getString("startup.language");


        pm = getServer().getPluginManager();
        pm.registerEvents(new JoinLeave(this), this);
        pm.registerEvents(new ServerPing(this), this);
        pm.registerEvents(new Sleep(), this);
        pm.registerEvents(new Chat(this), this);
        pm.registerEvents(new EnterSlimeChunk(this), this);

        this.getCommand("color").setExecutor(new SetChatColor(this));
        this.getCommand("home").setExecutor(new Home(this));
        this.getCommand("service").setExecutor(new Maintenance(this));
        this.getCommand("balance").setExecutor(new EconomyMain(this));
        this.getCommand("pay").setExecutor(new EconomyMain(this));
        this.getCommand("slimecheck").setExecutor(new ChunkNotifier(this));

        maintenance = cfg.getBoolean("startup.maintenance");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + PLUGIN_NAME + " Successfully loaded!");
    }

    @Override
    public void onDisable(){
        try {
            con.getConnection().close();
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + PLUGIN_NAME + " SQL connection successfully terminated!");
        }catch(SQLException e){
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + PLUGIN_NAME + " SQL connection could not be terminated!");
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + PLUGIN_NAME +" Successfully unloaded!");
    }

    private void establishConnection(){
        try{
            con = new MySQLCon(cfg.getString("database.host"),
                    cfg.getString("database.database"), cfg.getString("database.user"),
                    cfg.getString("database.password"), cfg.getString("database.table"),
                    cfg.getInt("database.port"), this);
        }catch(Exception e){
            conActive = false;
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + PLUGIN_NAME +" For full functionality please consider establishing a MySQL connection!");
        }
        if(conActive){
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + PLUGIN_NAME + " SQL connection successfully established");
        }else{
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + PLUGIN_NAME +" SQL connection could not be established -- May cause issues!");
        }
    }

    private void loadConfig(){
        cfg.options().copyDefaults(true);
        saveConfig();
        getLanguageFile().options().copyDefaults(true);
        saveLanguageFile(languageFile, file);
    }

    public FileConfiguration getLanguageFile() {
        return languageFile;
    }

    private void saveLanguageFile(FileConfiguration languageFile, File file) {
        try {
            languageFile.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String format(String format){
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', format);
    }

    private void addLanguageDefaults(){
        this.getLanguageFile().addDefault("de.permission-missing", "Du hast keine Berechtigung das zu tun");
        this.getLanguageFile().addDefault("de.ChunkNotifier.Notify", "Du erhälst ab sofort Benachrichtigungen über Slime Chunks");
        this.getLanguageFile().addDefault("de.ChunkNotifier.No-Notify", "Du erhälst ab sofort keine Benachrichtigungen über Slime Chunks mehr");
        this.getLanguageFile().addDefault("de.Home.teleport-message", "Du bist nun Zuhause");
        this.getLanguageFile().addDefault("de.Home.teleport-usage", "Bitte /home <set> nutzen");
        this.saveLanguageFile(this.languageFile, this.file);
        this.cfg.addDefault("startup.language", "de");
    }
}
