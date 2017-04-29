package gg.arcanum.inventories;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import gg.arcanum.core.ChatHandler;
import gg.arcanum.core.ConnectionHandler;
import gg.arcanum.data.MySqlManager;
import gg.arcanum.enums.PunishmentType;

public class DurationSelectInventory implements Listener {

	Inventory inv;
	Player player;
	OfflinePlayer selection;
	PunishmentType type;
	boolean ready = false;
	static HashMap<Player, DurationSelectInventory> players = new HashMap<Player, DurationSelectInventory>();

	public DurationSelectInventory(Player p, PunishmentType type, OfflinePlayer selection) {
		if (p == null)
			return;
		this.player = p;
		this.type = type;
		this.ready = false;
		players.put(p, this);
		this.selection = selection;
		this.inv = Bukkit.createInventory(null, 54,
				"§c" + ((type == PunishmentType.BAN) ? "Ban" : "Mute") + "§e " + selection.getName());
		int[] border = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53 };
		ItemStack borderItem = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		ItemMeta borderItemMeta = borderItem.getItemMeta();
		borderItemMeta.setDisplayName("§0 ");
		borderItem.setItemMeta(borderItemMeta);
		for (int i : border)
			inv.setItem(i, borderItem);
		Map<Integer, String> timeValues = new HashMap<Integer, String>();
		timeValues.put(19, "1 minute");
		timeValues.put(20, "10 minutes");
		timeValues.put(21, "30 minutes");
		timeValues.put(22, "1 hour");
		timeValues.put(23, "6 hours");
		timeValues.put(24, "12 hours");
		timeValues.put(25, "1 day");
		timeValues.put(29, "2 days");
		timeValues.put(30, "1 week");
		timeValues.put(31, "2 weeks");
		timeValues.put(32, "1 month");
		timeValues.put(33, "Forever");
		for (Map.Entry<Integer, String> timeValue : timeValues.entrySet()) {
			ItemStack time = new ItemStack(Material.WATCH, 1);
			ItemMeta timeMeta = time.getItemMeta();
			timeMeta.setDisplayName("§c" + timeValue.getValue());
			time.setItemMeta(timeMeta);
			inv.setItem(timeValue.getKey(), time);
		}
	}

	public Inventory getInventory() {
		return inv;
	}

	@EventHandler
	public void onCloseDuration(InventoryCloseEvent e) {
		if (players.containsKey(e.getPlayer())) {
			if (!players.get((Player) e.getPlayer()).ready) {
				players.get((Player) e.getPlayer()).ready = true;
				return;
			}
			players.remove(e.getPlayer());
		}
	}

	@EventHandler
	public void onInteractDuration(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getWhoClicked() != null && players.containsKey(p)) {
			e.setCancelled(true);
			if (e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.WATCH) {
				String length = e.getCurrentItem().getItemMeta().getDisplayName().replace("§c", "");
				String valType = "";
				int val = 0;
				switch (length) {
				case "1 minute":
					val = 1;
					valType = "MINUTE";
					break;
				case "10 minutes":
					val = 10;
					valType = "MINUTE";
					break;
				case "30 minutes":
					val = 30;
					valType = "MINUTE";
					break;
				case "1 hour":
					val = 1;
					valType = "HOUR";
					break;
				case "6 hours":
					val = 6;
					valType = "HOUR";
					break;
				case "12 hours":
					val = 12;
					valType = "HOUR";
					break;
				case "1 day":
					val = 1;
					valType = "DAY";
					break;
				case "2 days":
					val = 2;
					valType = "DAY";
					break;
				case "1 week":
					val = 1;
					valType = "WEEK";
					break;
				case "2 weeks":
					val = 2;
					valType = "WEEK";
					break;
				case "1 month":
					val = 1;
					valType = "MONTH";
					break;
				case "Forever":
					val = 20;
					valType = "YEAR";
					break;
				}
				final int acVal = val;
				final String acValType = valType;
				final String uuid = players.get(p).selection.getUniqueId().toString();
				final String type = ((players.get(p).type == PunishmentType.BAN) ? "bans" : "mutes");
				MySqlManager.doRunnable(new Runnable() {
					@Override
					public void run() {
						String reason = "";
						try {
							for (String s : StaffInventory.reasons.get(p)) {
								reason += s.replaceAll("'", "") + " ";
							}
						} catch (Exception e) {
							reason = "Misconduct";
						}
						MySqlManager sql = new MySqlManager();
						try {
							sql.open();
							sql.executeNonQuery("DELETE FROM " + type + " WHERE uuid='" + uuid + "';");
							sql.executeNonQuery(
									"INSERT INTO " + type + " VALUES ('" + uuid + "', NOW(), DATE_ADD(NOW(), INTERVAL "
											+ acVal + " " + acValType + "), '" + reason + "');");
							sql.close();
							ChatHandler.getMutes();
							ConnectionHandler.getBans();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				});
				try {
					String reason = "";
					try {
						for (String s : StaffInventory.reasons.get(p)) {
							reason += s.replaceAll("'", "") + " ";
						}
					} catch (Exception ete) {
						reason = "Misconduct";
					}
					if (players.get(p).selection.isOnline())
						players.get(p).selection.getPlayer()
								.kickPlayer("§cYou've been "
										+ (players.get(p).type == PunishmentType.BAN ? "banned" : "muted") + " for \n"
										+ reason + (players.get(p).type == PunishmentType.MUTE
												? "\nTake a moment before re-connecting to the server" : ""));
					p.sendMessage("§c" + ((players.get(p).type == PunishmentType.BAN) ? "Ban" : "Mute") + "§e "
							+ players.get(p).selection.getName() + "§c for "
							+ e.getCurrentItem().getItemMeta().getDisplayName() + " completed");
					p.closeInventory();
				} catch (Exception es) {
				}
			}
		}
	}
}
