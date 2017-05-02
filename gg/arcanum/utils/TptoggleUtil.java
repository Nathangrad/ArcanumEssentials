package gg.arcanum.utils;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;


public class TptoggleUtil
{
  private static List<Player> tpDenying = new ArrayList();
  
  public TptoggleUtil() {}
  
  public static boolean isDenying(Player p)
  {
    return tpDenying.contains(p);
  }
  
  public static void setDeny(Player p) {
    tpDenying.add(p);
  }
  
  public static void setAllow(Player p) {
    tpDenying.remove(p);
  }
}
