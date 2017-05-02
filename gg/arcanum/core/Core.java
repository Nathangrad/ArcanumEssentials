package gg.arcanum.core;

import gg.arcanum.inventories.PlayerSelectInventory;
import gg.arcanum.inventories.StaffInventory;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;

public class Core extends org.bukkit.plugin.java.JavaPlugin
{
  private static Core core;
  private static Permission perms = null;
  private static Chat chat = null;
  
  public String prefix = "";
  public static String apiPrefix = "";
  public String permerror = "§cYou don't have permission to execute this command";
  public String playerror = "§cThis player doesn't exist or isn't currently online";
  public String conserror = "This command cannot be executed from the console";
  
  public Core() {}
  
  public void onEnable() {
    core = this;
    saveDefaultConfig();
    setupPermissions();
    setupChat();
    Bukkit.getServer().getPluginManager().registerEvents(new Registration(), core);
    Bukkit.getServer().getPluginManager().registerEvents(new GodHandler(), core);
    Bukkit.getServer().getPluginManager().registerEvents(new ChatHandler(), core);
    Bukkit.getServer().getPluginManager().registerEvents(new ConnectionHandler(), core);
    Bukkit.getServer().getPluginManager().registerEvents(new StaffInventory(null, null), core);
    Bukkit.getServer().getPluginManager().registerEvents(new PlayerSelectInventory(null, null), core);
    Bukkit.getServer().getPluginManager().registerEvents(new gg.arcanum.inventories.DurationSelectInventory(null, null, null), core);
    try {
      prefix = (getConfig().get("configuration.prefix").toString().replace("&", "§") + " ");
    } catch (NullPointerException npe) {
      getConfig().set("configuration.prefix", "&7[&aAbove&7]");
      saveConfig();
      prefix = (getConfig().get("configuration.prefix").toString().replace("&", "§") + " ");
    }
    apiPrefix = getConfig().get("configuration.prefix").toString().replace("&", "§") + " ";
    Bukkit.getServer().getPluginManager().registerEvents(new gg.arcanum.listeners.ChatListener(this), this);
    Bukkit.getServer().getPluginManager().registerEvents(new gg.arcanum.listeners.AFKListener(this), this);
    Bukkit.getServer().getPluginManager().registerEvents(new gg.arcanum.listeners.SpawnListener(this), this);
    Bukkit.getServer().getPluginManager().registerEvents(new gg.arcanum.listeners.BackListener(), this);
    new CommandHandler(this);
    Bukkit.getScheduler().scheduleAsyncRepeatingTask(core, new Runnable()
    {
      public void run() {
        ChatHandler.getMutes();
        ConnectionHandler.getBans();
      }
    }, 0L, 1200L);
  }
  
  public static Plugin getPlugin() {
    return core;
  }
  
  public static Permission getPermissions() {
    return perms;
  }
  
  public static Chat getChat() {
    return chat;
  }
  
  private boolean setupChat() {
    RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
    chat = (Chat)rsp.getProvider();
    return chat != null;
  }
  
  private boolean setupPermissions() {
    RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
    perms = (Permission)rsp.getProvider();
    return perms != null;
  }
}
