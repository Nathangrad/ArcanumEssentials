package gg.arcanum.utils;

import java.util.List;
import org.bukkit.entity.Player;

public class AFKUtil
{
  public AFKUtil() {}
  
  private static List<Player> AFKPlayers = new java.util.ArrayList();
  
  public static boolean isAFK(Player p) {
    return AFKPlayers.contains(p);
  }
  
  public static void setAFK(Player p) {
    if (!AFKPlayers.contains(p)) {
      AFKPlayers.add(p);
    }
  }
  
  public static void setNotAFK(Player p) {
    if (AFKPlayers.contains(p)) {
      AFKPlayers.remove(p);
    }
  }
}
