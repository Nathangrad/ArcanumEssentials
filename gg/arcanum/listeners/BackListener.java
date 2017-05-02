package gg.arcanum.listeners;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class BackListener implements Listener
{
  public BackListener() {}
  
  public static Map<Player, Location> backLocations = new HashMap();
  
  @org.bukkit.event.EventHandler
  public void onPlayersTeleport(PlayerTeleportEvent e) {
    backLocations.put(e.getPlayer(), e.getFrom());
  }
  
  public static Location getBackLocation(Player player) {
    if (backLocations.containsKey(player)) {
      return (Location)backLocations.get(player);
    }
    return null;
  }
}
