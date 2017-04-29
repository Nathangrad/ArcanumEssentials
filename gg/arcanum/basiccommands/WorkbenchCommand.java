package gg.arcanum.basiccommands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import gg.arcanum.core.Core;
import gg.arcanum.utils.PermissionCheck;

public class WorkbenchCommand implements CommandExecutor {

	Core essentials;
	String usage = "§cUsage: /workbench";

	public WorkbenchCommand(Core e) {
		essentials = e;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String comandlbl, String[] args) {
		if (!(sender instanceof Player)) {
			essentials.getLogger().info(essentials.conserror);
			return true;
		}
		Player player = (Player) sender;
		if (!PermissionCheck.check(player, "core.workbench")) {
			player.sendMessage(essentials.prefix + essentials.permerror);
			return true;
		}
		player.openInventory(Bukkit.getServer().createInventory(null, InventoryType.WORKBENCH));
		player.sendMessage(essentials.prefix + "§aYou have accessed a workbench");
		return true;
	}
}