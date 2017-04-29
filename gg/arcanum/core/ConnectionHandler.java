package gg.arcanum.core;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import gg.arcanum.data.DataRow;
import gg.arcanum.data.DataTable;
import gg.arcanum.data.MySqlManager;

public class ConnectionHandler implements Listener {

	static Map<UUID, Ban> bans = new HashMap<UUID, Ban>();

	@EventHandler
	public void onJoinings(PlayerJoinEvent e) {
		if (bans.containsKey(e.getPlayer().getUniqueId())) {
			e.setJoinMessage(null);
			e.getPlayer()
					.kickPlayer("§cYou've been banned for \n" + bans.get(e.getPlayer().getUniqueId()).reason
							+ "\n§cAvailable: " + DateFormat.getDateTimeInstance()
									.format(bans.get(e.getPlayer().getUniqueId()).end));
			return;
		}
		if ((!e.getPlayer().hasPlayedBefore()) && Core.getPlugin().getConfig().getBoolean("joinLeave.firstJoinDisplay"))
			e.setJoinMessage(Core.getPlugin().getConfig().get("joinLeave.joinMessage").toString().replaceAll("&", "§")
					.replace("[PLAYER]", e.getPlayer().getName()));
		else if (Core.getPlugin().getConfig().getBoolean("joinLeave.joinDisplay"))
			e.setJoinMessage(Core.getPlugin().getConfig().get("joinLeave.joinMessage").toString().replaceAll("&", "§")
					.replace("[PLAYER]", e.getPlayer().getName()));
		else
			e.setJoinMessage(null);
	}

	@EventHandler
	public void onLeavings(PlayerQuitEvent e) {
		if (Core.getPlugin().getConfig().getBoolean("joinLeave.quitDisplay"))
			e.setQuitMessage(Core.getPlugin().getConfig().get("joinLeave.quitMessage").toString().replaceAll("&", "§")
					.replace("[PLAYER]", e.getPlayer().getName()));
		else
			e.setQuitMessage(null);
	}

	public static void getBans() {
		MySqlManager.doRunnable(new Runnable() {
			@Override
			public void run() {
				MySqlManager sql = new MySqlManager();
				try {
					sql.open();
					DataTable dt = sql.executeQuery("SELECT * FROM bans WHERE end > NOW();");
					sql.close();
					bans = new HashMap<UUID, Ban>();
					for (DataRow r : dt.getRows()) {
						Ban ban = new Ban();
						ban.uuid = UUID.fromString(r.getCell("uuid").toString());
						ban.start = (Date) r.getCell("start");
						ban.end = (Date) r.getCell("end");
						ban.reason = r.getCell("reason").toString();
						bans.put(ban.uuid, ban);
					}
				} catch (SQLException e) {
				} catch (Exception e) {
					// Core.getPlugin().getLogger().info("No bans");
				}
			}
		});
	}

}

class Ban {
	public UUID uuid;
	public Date start;
	public Date end;
	public String reason;
}
