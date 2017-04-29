package gg.arcanum.utils;

public class ChatFormatting {
	public static String amperSand(String replace) {
		return replace.replaceAll("&a", "§a").replaceAll("&b", "§b")
				.replaceAll("&c", "§c").replaceAll("&d", "§d")
				.replaceAll("&e", "§e").replaceAll("&f", "§f")
				.replaceAll("&0", "§0").replaceAll("&1", "§1")
				.replaceAll("&2", "§2").replaceAll("&3", "§3")
				.replaceAll("&4", "§4").replaceAll("&5", "§5")
				.replaceAll("&6", "§6").replaceAll("&7", "§7")
				.replaceAll("&8", "§8").replaceAll("&9", "§9")
				.replaceAll("&l", "§l").replaceAll("&m", "§m")
				.replaceAll("&o", "§o").replaceAll("&k", "§k");
	}

	public static String apos(String replace) {
		return replace.replaceAll("'", "|");
	}
}