package gg.arcanum.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;

public class MuteUtil {

	private static List<String> mutedPlayers = new ArrayList<String>();

	public static boolean isMuted(OfflinePlayer p) {
		return mutedPlayers.contains(p.getUniqueId().toString());
	}

	public static void setMuted(OfflinePlayer p) {
		mutedPlayers.add(p.getUniqueId().toString());
	}

	public static void setUnMuted(OfflinePlayer p) {
		mutedPlayers.remove(p.getUniqueId().toString());
	}

}
