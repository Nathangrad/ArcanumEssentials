package gg.arcanum.basiccommands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import gg.arcanum.core.Core;
import gg.arcanum.utils.PermissionCheck;

public class FeedCommand implements CommandExecutor {

	Core essentials;
	String usage = "§cUsage: /heal §o<Player>";

	public FeedCommand(Core e) {
		essentials = e;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String comandlbl, String[] args) {
		if (!(sender instanceof Player)) {
			essentials.getLogger().info(essentials.conserror);
			return true;
		}
		Player player = (Player) sender;
		if (args.length == 0) {
			if (!PermissionCheck.check(player, "core.feed")) {
				player.sendMessage(essentials.prefix + essentials.permerror);
				return true;
			}
			player.setFoodLevel(20);
			player.sendMessage(essentials.prefix + "§aYou have fed yourself");
		} else if (args.length == 1) {
			if (!PermissionCheck.check(player, "core.feedothers")) {
				player.sendMessage(essentials.prefix + essentials.permerror);
				return true;
			}
			Player target = Bukkit.getServer().getPlayer(args[0]);
			if (target != null) {
				target.setFoodLevel(20);
				target.sendMessage(essentials.prefix + "§aYou have been fed");
				player.sendMessage(essentials.prefix + "§aYou have fed §e" + target.getName());
			} else {
				player.sendMessage(essentials.prefix + essentials.playerror);
				return true;
			}
		} else {
			player.sendMessage(essentials.prefix + usage);
			return true;
		}
		return true;
	}
}