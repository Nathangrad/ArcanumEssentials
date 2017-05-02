package gg.arcanum.teleportation;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import gg.arcanum.listeners.BackListener;
import gg.arcanum.core.Core;
import gg.arcanum.utils.PermissionCheck;

public class BackCommand implements CommandExecutor {

	Core essentials;
	String usage = "§cUsage: /back";

	public BackCommand(Core e) {
		essentials = e;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String comandlbl, String[] args) {
		if (!(sender instanceof Player)) {
			essentials.getLogger().info(essentials.conserror);
			return true;
		}
		Player player = (Player) sender;
		if (!PermissionCheck.check(player, "core.back")) {
			player.sendMessage(essentials.prefix + essentials.permerror);
			return true;
		}
		if (BackListener.getBackLocation(player) != null) {
			player.teleport(BackListener.getBackLocation(player));
			player.sendMessage(essentials.prefix + "§aTeleported to your previous location");
		} else {
			player.sendMessage(essentials.prefix + "§cYou don't have a previous location set");
		}
		return true;
	}
}