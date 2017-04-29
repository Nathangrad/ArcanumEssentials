package gg.arcanum.listeners;

import java.util.ArrayList;
import java.util.List;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import gg.arcanum.core.Core;
import gg.arcanum.utils.ChatFormatting;
import gg.arcanum.utils.MuteUtil;

public class ChatListener implements Listener {

	public static Permission perms = null;
	public static Chat chat = null;
	public static Core essentials = null;

	public ChatListener(Core es) {
		chat = Core.getChat();
		perms = Core.getPermissions();
		essentials = es;
	}

	@EventHandler
	public void playerTalkEvent(AsyncPlayerChatEvent chatEvent) {
		if (MuteUtil.isMuted(chatEvent.getPlayer())) {
			chatEvent.getPlayer().sendMessage(essentials.prefix + "§cSorry. You're currently muted");
			chatEvent.setCancelled(true);
		}
		if (chatEvent.getPlayer().hasPermission("core.chatformat")) {
			chatEvent.setMessage(ChatFormatting.amperSand(chatEvent.getMessage()));
		}
		chatEvent.setFormat(ChatFormatting.amperSand(
				chat.getGroupPrefix(chatEvent.getPlayer().getWorld(), perms.getPrimaryGroup(chatEvent.getPlayer())))
				+ "§r%s§7: %s");
		chatEvent.getRecipients().clear();
		chatEvent.getRecipients().addAll(Bukkit.getServer().getOnlinePlayers());
		List<Player> mentionedPlayers = new ArrayList<Player>();
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			if (chatEvent.getMessage().contains("@" + p.getName())) {
				chatEvent.getRecipients().remove(p);
				mentionedPlayers.add(p);
			}
		}
		for (Player p : mentionedPlayers) {
			p.sendMessage(ChatFormatting
					.amperSand(chat.getGroupPrefix(chatEvent.getPlayer().getWorld(),
							perms.getPrimaryGroup(chatEvent.getPlayer())))
					+ "§r" + chatEvent.getPlayer().getName() + "§7: §c"
					+ chatEvent.getMessage().replace("@" + p.getName(), "§6§l@" + p.getName() + "§c"));
		}
	}
}
