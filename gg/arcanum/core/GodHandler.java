package gg.arcanum.core;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class GodHandler implements Listener {

	private static List<Player> flaggedPlayers = new ArrayList<Player>();

	public static boolean isFlagged(Player p) {
		return flaggedPlayers.contains(p);
	}

	public static void flagPlayer(Player p, boolean flagged) {
		if (isFlagged(p) && flagged == false) {
			flaggedPlayers.remove(p);
		} else if (flagged) {
			flaggedPlayers.add(p);
			p.setFoodLevel(20);
			p.setHealth(20);
		}
	}

	@EventHandler
	public void onHungerTake(FoodLevelChangeEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (isFlagged(p)) {
				e.setCancelled(true);
				p.setFoodLevel(20);
			}
		}
	}

	@EventHandler
	public void onDamageTake(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (isFlagged(p)) {
				e.setCancelled(true);
				p.setFoodLevel(20);
				p.setHealth(20);
			}
		}
	}

	/*
	 * public ArrayList<Player> torch = new ArrayList<Player>();
	 * 
	 * @EventHandler public void onPlayerItemHoldEvent(PlayerItemHeldEvent e) {
	 * if (e.getPlayer().getInventory().getItem(e.getNewSlot()) != null &&
	 * e.getPlayer().getInventory().getItem(e.getNewSlot()).getType() ==
	 * Material.TORCH) { torch.add(e.getPlayer()); } if
	 * (torch.contains(e.getPlayer())) { e.getPlayer().addPotionEffect(new
	 * PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 1)); } if
	 * (!(e.getPlayer().getInventory().getItem(e.getNewSlot()) != null &&
	 * e.getPlayer().getInventory().getItem(e.getNewSlot()).getType() ==
	 * Material.TORCH)) {
	 * e.getPlayer().removePotionEffect(PotionEffectType.NIGHT_VISION);
	 * torch.remove(e.getPlayer()); } }
	 */
}
