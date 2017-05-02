package gg.arcanum.core;

import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class GodHandler implements Listener
{
  public GodHandler() {}
  
  private static List<Player> flaggedPlayers = new java.util.ArrayList();
  
  public static boolean isFlagged(Player p) {
    return flaggedPlayers.contains(p);
  }
  
  public static void flagPlayer(Player p, boolean flagged) {
    if ((isFlagged(p)) && (!flagged)) {
      flaggedPlayers.remove(p);
    } else if (flagged) {
      flaggedPlayers.add(p);
      p.setFoodLevel(20);
      p.setHealth(20.0D);
    }
  }
  
  @EventHandler
  public void onHungerTake(FoodLevelChangeEvent e) {
    if ((e.getEntity() instanceof Player)) {
      Player p = (Player)e.getEntity();
      if (isFlagged(p)) {
        e.setCancelled(true);
        p.setFoodLevel(20);
      }
    }
  }
  
  @EventHandler
  public void onDamageTake(EntityDamageEvent e) {
    if ((e.getEntity() instanceof Player)) {
      Player p = (Player)e.getEntity();
      if (isFlagged(p)) {
        e.setCancelled(true);
        p.setFoodLevel(20);
        p.setHealth(20.0D);
      }
    }
  }
}
