package gg.arcanum.admin;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import gg.arcanum.core.Core;
import gg.arcanum.data.MySqlManager;
import gg.arcanum.utils.ChatFormatting;
import gg.arcanum.utils.PermissionCheck;


public class BanCommand implements CommandExecutor {

	Core essentials;
	String usage = "§cUsage: /ban <player> <message>";

	public BanCommand(Core e) {
		essentials = e;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String comandlbl, String[] args) {
		if (!(sender instanceof Player)) {
			essentials.getLogger().info(essentials.conserror);
			return true;
		}
		Player player = (Player) sender;
		if (!PermissionCheck.check(player, "core.ban")) {
			player.sendMessage(essentials.prefix + essentials.permerror);
			return true;
		}
		if (args.length != 0) {
			OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[0].toString());
			if (target == null) {
				player.sendMessage(essentials.prefix + usage);
				return true;
			}
			StringBuilder builder = new StringBuilder();
			for (int i = 1; i < args.length; i++)
				builder.append(args[i].toString().replace("&", "§") + " ");
			try {
				((Player) target).kickPlayer(builder.toString());
			} catch (Exception castProblem) {
			}
			target.setBanned(true);
			player.sendMessage(
					essentials.prefix + "§e" + target.getName() + "§c has been banned for " + builder.toString());
			MySqlManager sql = new MySqlManager();
			try {
				sql.open();
				sql.executeNonQuery("INSERT INTO PunishmentHistory (Player, Staff, "
						+ "Type, Length, Reason, Date) VALUES ('" + target.getUniqueId() + "','" + player.getUniqueId()
						+ "','Ban',NULL,'" + ChatFormatting.apos(builder.toString()) + "', NOW())");
				sql.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return true;
		} else {
			player.sendMessage(essentials.prefix + usage);
		}
		return true;
	}
}