package gg.arcanum.admin;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import gg.arcanum.core.Core;
import gg.arcanum.data.MySqlManager;
import gg.arcanum.utils.ChatFormatting;
import gg.arcanum.utils.PermissionCheck;

public class KickCommand implements CommandExecutor {

	Core essentials;
	String usage = "§cUsage: /kick <player> <message>";

	public KickCommand(Core e) {
		essentials = e;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String comandlbl, String[] args) {
		if (!(sender instanceof Player)) {
			essentials.getLogger().info(essentials.conserror);
			return true;
		}
		Player player = (Player) sender;
		if (!PermissionCheck.check(player, "core.kick")) {
			player.sendMessage(essentials.prefix + essentials.permerror);
			return true;
		}
		if (args.length != 0) {
			Player target = Bukkit.getServer().getPlayer(args[0].toString());
			if (target == null) {
				player.sendMessage(essentials.prefix + usage);
				return true;
			}
			StringBuilder builder = new StringBuilder();
			for (int i = 1; i < args.length; i++)
				builder.append(args[i].toString().replace("&", "§") + " ");
			target.kickPlayer(builder.toString());
			MySqlManager sql = new MySqlManager();
			try {
				sql.open();
				sql.executeNonQuery("INSERT INTO PunishmentHistory (Player, Staff, "
						+ "Type, Length, Reason, Date) VALUES ('" + target.getUniqueId() + "','" + player.getUniqueId()
						+ "','Kick',NULL,'" + ChatFormatting.apos(builder.toString().replace("§", "&")) + "', NOW())");
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