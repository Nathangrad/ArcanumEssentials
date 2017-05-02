package gg.arcanum.core;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import gg.arcanum.inventories.DurationSelectInventory;
import gg.arcanum.inventories.PlayerSelectInventory;
import gg.arcanum.inventories.StaffInventory;
import gg.arcanum.listeners.*;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

public class Core extends JavaPlugin {

	private static Core core;
	private static Permission perms = null;
	private static Chat chat = null;

	public String prefix = "";
	public static String apiPrefix = "";
	public String permerror = "§cYou don't have permission to execute this command";
	public String playerror = "§cThis player doesn't exist or isn't currently online";
	public String conserror = "This command cannot be executed from the console";

	@SuppressWarnings("deprecation")
	@Override
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
		Bukkit.getServer().getPluginManager().registerEvents(new DurationSelectInventory(null, null, null), core);
				try {
			prefix = getConfig().get("configuration.prefix").toString().replace("&", "§") + " ";
		} catch (NullPointerException npe) {
			getConfig().set("configuration.prefix", "&7[&aAbove&7]");
			saveConfig();
			prefix = getConfig().get("configuration.prefix").toString().replace("&", "§") + " ";
		}
		Core.apiPrefix = getConfig().get("configuration.prefix").toString().replace("&", "§") + " ";
		Bukkit.getServer().getPluginManager().registerEvents(new ChatListener(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new AFKListener(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new SpawnListener(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new BackListener(), this);
		new CommandHandler(this);
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(core, new Runnable() {
			@Override
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
		chat = rsp.getProvider();
		return chat != null;
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		perms = rsp.getProvider();
		return perms != null;
	}

}
