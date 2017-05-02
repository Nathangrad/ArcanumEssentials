package gg.arcanum.listeners;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import gg.arcanum.savedlocations.SpawnCommand;
import gg.arcanum.core.Core;
import gg.arcanum.utils.ChatFormatting;

public class SpawnListener implements Listener {

	public static Chat chat;
	public static Permission perms;
	public static Core economy;

	public SpawnListener(Core essentials) {
		chat = Core.getChat();
		perms = Core.getPermissions();
		economy = essentials;
	}

	@EventHandler
	public void onPlayerJoining(PlayerJoinEvent joinEvent) {
		joinEvent.setJoinMessage("§7[§a+§7] "
				+ ChatFormatting.amperSand(chat.getGroupPrefix(joinEvent.getPlayer().getWorld(),
						perms.getPrimaryGroup(joinEvent.getPlayer()))).replace("[", "").replace("]", "")
				+ "§7" + joinEvent.getPlayer().getName());
		if (!joinEvent.getPlayer().hasPlayedBefore()) {
			SpawnCommand.sendToSpawn(joinEvent.getPlayer());
		}
	}

	@EventHandler
	public void onPlayerLeaving(PlayerQuitEvent quitEvent) {
		quitEvent.setQuitMessage("§7[§c-§7] "
				+ ChatFormatting.amperSand(chat.getGroupPrefix(quitEvent.getPlayer().getWorld(),
						perms.getPrimaryGroup(quitEvent.getPlayer()))).replace("[", "").replace("]", "")
				+ "§7" + quitEvent.getPlayer().getName());
	}
}
