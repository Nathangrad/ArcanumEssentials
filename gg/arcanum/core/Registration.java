package gg.arcanum.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import gg.arcanum.data.MySqlManager;
import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutPlayerListHeaderFooter;

public class Registration implements Listener {

	private Map<String, Integer> ids;

	public Registration() {
		ids = new HashMap<>();
		loadPrefixes();
		new BukkitRunnable() {
			public void run() {
				updateAll();
			}
		}.runTaskTimer(Core.getPlugin(), 20L, 300L);
	}

	@EventHandler
	public void onPlayerFirstJoin(PlayerJoinEvent e) {
		String group = Core.getChat().getGroupPrefix(e.getPlayer().getWorld(),
				Core.getPermissions().getPrimaryGroup(e.getPlayer()));
		e.getPlayer().setPlayerListName(group.replaceAll("&", "§") + e.getPlayer().getName());
		setHeaderandFooter(e.getPlayer());
		updateAll();
		if (!e.getPlayer().hasPlayedBefore()) {
			Player p = e.getPlayer();
			MySqlManager.doRunnable(new Runnable() {
				@Override
				public void run() {
					MySqlManager sql = new MySqlManager();
					try {
						sql.open();
						sql.executeNonQuery("INSERT INTO players (UUID, balance, online, joined, karma) values ('"
								+ p.getUniqueId().toString() + "', 0, NOW(), NOW(), 0);");
						sql.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			});
			FireworkHandler.spawnFirework(p);
		} else {
			Player p = e.getPlayer();
			MySqlManager.doRunnable(new Runnable() {
				@Override
				public void run() {
					MySqlManager sql = new MySqlManager();
					try {
						sql.open();
						sql.executeNonQuery(
								"UPDATE players SET online=NOW() WHERE UUID='" + p.getUniqueId().toString() + "'");
						sql.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	@EventHandler
	public void onPlayerLeaving(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		p.setPlayerListName(null);
		setHeaderandFooter(p);
		updateAll();
		MySqlManager.doRunnable(new Runnable() {
			@Override
			public void run() {
				MySqlManager sql = new MySqlManager();
				try {
					sql.open();
					sql.executeNonQuery(
							"UPDATE players SET online=NOW() WHERE UUID='" + p.getUniqueId().toString() + "'");
					sql.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void updateAll() {
		sendTabPackets(1);
		sendTabPackets(0);
	}

	private void setHeaderandFooter(Player p) {
		IChatBaseComponent header = ChatSerializer
				.a("{\"text\":\"" + ChatColor.translateAlternateColorCodes('&', getList("Header")) + "\"}");
		IChatBaseComponent footer = ChatSerializer
				.a("{\"text\":\"" + ChatColor.translateAlternateColorCodes('&', getList("Footer")) + "\"}");
		PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
		try {
			Field headerField = packet.getClass().getDeclaredField("a");
			headerField.setAccessible(true);
			headerField.set(packet, header);
			headerField.setAccessible(!headerField.isAccessible());
			Field footerField = packet.getClass().getDeclaredField("b");
			footerField.setAccessible(true);
			footerField.set(packet, footer);
			footerField.setAccessible(!footerField.isAccessible());
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Player t : Bukkit.getOnlinePlayers()) {
			sendPacket(t, packet);
		}
	}

	private void sendTabPackets(int state) {
		for (String rankName : Core.getPlugin().getConfig().getStringList("Ranks")) {
			String prefix = Core.getChat().getGroupPrefix(Bukkit.getWorld("lobby"), rankName);
			String id = String.valueOf(ids.get(rankName));
			Collection<String> entrys = new ArrayList<String>();
			for (Player p : Bukkit.getOnlinePlayers()) {
				String targetRank = Core.getPermissions().getPrimaryGroup(p);
				if (targetRank.equals(rankName)) {
					entrys.add(p.getName());
				}
			}
			if (rankName.length() > 14) {
				rankName = rankName.substring(0, 14);
			}
			if (prefix.length() > 14) {
				prefix = prefix.substring(0, 14);
			}
			try {
				String teamName = id + rankName;
				Constructor<?> constructor = getNMSClass("PacketPlayOutScoreboardTeam").getConstructor(new Class[0]);
				Object packet = constructor.newInstance(new Object[0]);
				try {
					setField(packet, "a", teamName);
					setField(packet, "b", teamName);
					setField(packet, "c", prefix.replaceAll("&", "§"));
					setField(packet, "d", "");
					setField(packet, "e", "ALWAYS");
					setField(packet, "h", state);
					setField(packet, "g", entrys);
				} catch (Exception ex) {
					setField(packet, "a", teamName);
					setField(packet, "b", teamName);
					setField(packet, "c", prefix.replaceAll("&", "§"));
					setField(packet, "d", "");
					setField(packet, "e", "ALWAYS");
					setField(packet, "i", state);
					setField(packet, "h", entrys);
				}
				for (Player p : Bukkit.getOnlinePlayers()) {
					sendPacket(p, packet);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void sendPacket(Player to, Object packet) {
		try {
			Object playerHandle = to.getClass().getMethod("getHandle", new Class[0]).invoke(to, new Object[0]);
			Object playerConnection = playerHandle.getClass().getField("playerConnection").get(playerHandle);
			playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setField(Object change, String name, Object to) throws Exception {
		Field field = change.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(change, to);
		field.setAccessible(false);
	}

	private Class<?> getNMSClass(String name) {
		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		;
		try {
			return Class.forName("net.minecraft.server." + version + "." + name);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getList(String path) {
		String output = "";
		for (String s : Core.getPlugin().getConfig().getStringList("Tab." + path)) {
			output = String.valueOf(output) + s.replace("&", "§") + "\n";
		}
		if (output.length() > 0) {
			output = output.substring(0, output.length() - 1);
		}
		return output
				.replace("%online%", new StringBuilder(String.valueOf(Bukkit.getOnlinePlayers().size())).toString())
				.replace("%max%", new StringBuilder(String.valueOf(Bukkit.getMaxPlayers())).toString());
	}

	private void loadPrefixes() {
		int id = 1;
		for (String rank : Core.getPlugin().getConfig().getStringList("Ranks")) {
			ids.put(rank, id);
			++id;
		}
		updateAll();
	}

}
