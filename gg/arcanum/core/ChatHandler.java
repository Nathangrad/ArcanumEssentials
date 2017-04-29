package gg.arcanum.core;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import gg.arcanum.data.DataRow;
import gg.arcanum.data.DataTable;
import gg.arcanum.data.MySqlManager;

public class ChatHandler implements Listener {

	static Map<UUID, Mute> mutes = new HashMap<UUID, Mute>();

	@EventHandler
	public void onPlayerChatting(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (mutes.containsKey(p.getUniqueId())) {
			e.setCancelled(true);
			p.sendMessage("§cYou're currently muted until "
					+ DateFormat.getDateTimeInstance().format((mutes.get(p.getUniqueId()).end)) + " for "
					+ mutes.get(p.getUniqueId()).reason);
			return;
		}
		String group = Core.getChat().getGroupPrefix(p.getWorld(), Core.getPermissions().getPrimaryGroup(p));
		if (p.hasPermission("arcanum.chat.colour")) {
			e.setMessage(e.getMessage().replaceAll("&", "§"));
		}
		e.setFormat(Core.getPlugin().getConfig().get("chat").toString().replace("[GROUP]", group)
				.replace("[PLAYER]", "%s").replace("[MESSAGE]", "%s").replaceAll("&", "§"));
	}

	public static void getMutes() {
		MySqlManager.doRunnable(new Runnable() {
			@Override
			public void run() {
				MySqlManager sql = new MySqlManager();
				try {
					sql.open();
					DataTable dt = sql.executeQuery("SELECT * FROM mutes WHERE end > NOW();");
					sql.close();
					mutes = new HashMap<UUID, Mute>();
					for (DataRow r : dt.getRows()) {
						Mute mute = new Mute();
						mute.uuid = UUID.fromString(r.getCell("uuid").toString());
						mute.start = (Date) r.getCell("start");
						mute.end = (Date) r.getCell("end");
						mute.reason = r.getCell("reason").toString();
						mutes.put(mute.uuid, mute);
					}
				} catch (SQLException e) {
				} catch (Exception e) {
					// Core.getPlugin().getLogger().info("No mutes");
				}
			}
		});
	}

}

class Mute {
	public UUID uuid;
	public Date start;
	public Date end;
	public String reason;
}