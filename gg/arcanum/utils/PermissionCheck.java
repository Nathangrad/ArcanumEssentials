package gg.arcanum.utils;

import org.bukkit.entity.Player;

public class PermissionCheck {
	public static boolean check(Player player, String perm) {
		return player.hasPermission(perm);
	}
}
