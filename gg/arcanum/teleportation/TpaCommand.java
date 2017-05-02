package gg.arcanum.teleportation;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import gg.arcanum.core.Core;
import gg.arcanum.utils.PermissionCheck;
import gg.arcanum.utils.RequestType;
import gg.arcanum.utils.TptoggleUtil;

public class TpaCommand implements CommandExecutor {

	Core essentials;
	String usage = "�cUsage: /tpa <player>";

	public TpaCommand(Core e) {
		essentials = e;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String comandlbl, String[] args) {
		if (!(sender instanceof Player)) {
			essentials.getLogger().info(essentials.conserror);
			return true;
		}
		Player player = (Player) sender;
		if (!PermissionCheck.check(player, "core.tpa")) {
			player.sendMessage(essentials.prefix + essentials.permerror);
			return true;
		}
		if (args.length == 0) {
			player.sendMessage(essentials.prefix + usage);
		} else {
			Player target = Bukkit.getServer().getPlayer(args[0].toString());
			if (target != null) {
				if (!TptoggleUtil.isDenying(target)) {
					TeleportRequest request = new TeleportRequest(player, target, RequestType.TPA);
					request.getSender().sendMessage(essentials.prefix + "�aTeleport request successfully sent to �a"
							+ request.getReceiver().getName());
					request.getReceiver()
							.sendMessage(essentials.prefix + "�e" + request.getSender().getName()
									+ "�7 wishes to teleport to you\n" + essentials.prefix
									+ "�a/tpaccept�7 to accept the request\n" + essentials.prefix
									+ "�c/tpdeny�7 to deny the request");
				} else {
					player.sendMessage(essentials.prefix + "�e" + target.getName() + "�c has disabled teleportation");
				}
			} else {
				player.sendMessage(essentials.prefix + essentials.playerror);
			}
		}
		return true;
	}
}