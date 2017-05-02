package gg.arcanum.inventories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import gg.arcanum.enums.PunishmentType;

public class StaffInventory implements Listener {

	Inventory inv;
	Player player;
	public static Map<Player, String[]> reasons = new HashMap<Player, String[]>();
	static ArrayList<Player> players = new ArrayList<Player>();

	public StaffInventory(Player p, String[] reason) {
		if (p == null)
			return;
		inv = Bukkit.createInventory(null, 54, "§dStaff Manager");
		int[] border = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53 };
		ItemStack borderItem = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		ItemMeta borderItemMeta = borderItem.getItemMeta();
		borderItemMeta.setDisplayName("§0 ");
		borderItem.setItemMeta(borderItemMeta);
		for (int i : border)
			inv.setItem(i, borderItem);
		ItemStack ban = new ItemStack(Material.BARRIER, 1);
		ItemMeta banMeta = ban.getItemMeta();
		banMeta.setDisplayName("§cBan");
		ban.setItemMeta(banMeta);
		ItemStack mute = new ItemStack(Material.JUKEBOX, 1);
		ItemMeta muteMeta = mute.getItemMeta();
		muteMeta.setDisplayName("§cMute");
		mute.setItemMeta(muteMeta);
		inv.setItem(21, mute);
		inv.setItem(23, ban);
		reasons.put(p, reason);
		player = p;
		players.add(player);
	}

	public Inventory getInventory() {
		return inv;
	}

	@EventHandler
	public void onCloseStaff(InventoryCloseEvent e) {
		if (players.contains(e.getPlayer()))
			players.remove(e.getPlayer());
	}

	@EventHandler
	public void onInteractStaff(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getWhoClicked() != null && players.contains(p)) {
			e.setCancelled(true);
			if (e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.BARRIER) {
				e.getWhoClicked().closeInventory();
				e.getWhoClicked().openInventory(new PlayerSelectInventory(p, PunishmentType.BAN).getInventory());
			} else if (e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.JUKEBOX) {
				e.getWhoClicked().closeInventory();
				e.getWhoClicked().openInventory(new PlayerSelectInventory(p, PunishmentType.MUTE).getInventory());
			}
		}
	}

}
