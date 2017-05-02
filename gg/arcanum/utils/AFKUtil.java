package gg.arcanum.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class AFKUtil {

	private static List<Player> AFKPlayers = new ArrayList<Player>();

	public static boolean isAFK(Player p) {
		return AFKPlayers.contains(p);
	}

	public static void setAFK(Player p) {
		if (!(AFKPlayers.contains(p))) {
			AFKPlayers.add(p);
		}
	}

	public static void setNotAFK(Player p) {
		if (AFKPlayers.contains(p)) {
			AFKPlayers.remove(p);
		}
	}
}
