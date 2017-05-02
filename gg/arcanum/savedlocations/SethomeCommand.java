package gg.arcanum.savedlocations;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import gg.arcanum.core.Core;
import gg.arcanum.utils.PermissionCheck;

public class SethomeCommand implements CommandExecutor {

	Core essentials;
	String usage = "§cUsage: /sethome";

	public SethomeCommand(Core e) {
		essentials = e;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String comandlbl, String[] args) {
		if (!(sender instanceof Player)) {
			essentials.getLogger().info(essentials.conserror);
			return true;
		}
		Player player = (Player) sender;
		if (!PermissionCheck.check(player, "core.sethome")) {
			player.sendMessage(essentials.prefix + essentials.permerror);
			return true;
		}
		Location l = player.getLocation();
		String path = "homes." + player.getUniqueId() + ".";
		essentials.getConfig().set(path + "w", player.getWorld().getName());
		essentials.getConfig().set(path + "x", l.getX());
		essentials.getConfig().set(path + "y", l.getY());
		essentials.getConfig().set(path + "z", l.getZ());
		essentials.getConfig().set(path + "yaw", l.getYaw());
		essentials.getConfig().set(path + "pitch", l.getPitch());
		essentials.saveConfig();
		player.sendMessage(essentials.prefix + "§aSuccessfully set your home");
		return true;
	}
}