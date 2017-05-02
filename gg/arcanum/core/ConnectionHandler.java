package gg.arcanum.core;

import gg.arcanum.data.DataRow;
import gg.arcanum.data.MySqlManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class ConnectionHandler implements org.bukkit.event.Listener
{
  public ConnectionHandler() {}
  
  static Map<java.util.UUID, Ban> bans = new HashMap();
  
  @EventHandler
  public void onJoinings(PlayerJoinEvent e) {
    if (bans.containsKey(e.getPlayer().getUniqueId())) {
      e.setJoinMessage(null);
      e.getPlayer()
        .kickPlayer("§cYou've been banned for \n" + bansgetgetPlayergetUniqueIdreason + 
        "\n§cAvailable: " + DateFormat.getDateTimeInstance()
        .format(bansgetgetPlayergetUniqueIdend));
      return;
    }
    if ((!e.getPlayer().hasPlayedBefore()) && (Core.getPlugin().getConfig().getBoolean("joinLeave.firstJoinDisplay"))) {
      e.setJoinMessage(Core.getPlugin().getConfig().get("joinLeave.joinMessage").toString().replaceAll("&", "§")
        .replace("[PLAYER]", e.getPlayer().getName()));
    } else if (Core.getPlugin().getConfig().getBoolean("joinLeave.joinDisplay")) {
      e.setJoinMessage(Core.getPlugin().getConfig().get("joinLeave.joinMessage").toString().replaceAll("&", "§")
        .replace("[PLAYER]", e.getPlayer().getName()));
    } else
      e.setJoinMessage(null);
  }
  
  @EventHandler
  public void onLeavings(PlayerQuitEvent e) {
    if (Core.getPlugin().getConfig().getBoolean("joinLeave.quitDisplay")) {
      e.setQuitMessage(Core.getPlugin().getConfig().get("joinLeave.quitMessage").toString().replaceAll("&", "§")
        .replace("[PLAYER]", e.getPlayer().getName()));
    } else
      e.setQuitMessage(null);
  }
  
  public static void getBans() {
    MySqlManager.doRunnable(new Runnable()
    {
      public void run() {
        MySqlManager sql = new MySqlManager();
        try {
          sql.open();
          gg.arcanum.data.DataTable dt = sql.executeQuery("SELECT * FROM bans WHERE end > NOW();");
          sql.close();
          ConnectionHandler.bans = new HashMap();
          for (DataRow r : dt.getRows()) {
            Ban ban = new Ban();
            uuid = java.util.UUID.fromString(r.getCell("uuid").toString());
            start = ((Date)r.getCell("start"));
            end = ((Date)r.getCell("end"));
            reason = r.getCell("reason").toString();
            ConnectionHandler.bans.put(uuid, ban);
          }
        }
        catch (SQLException localSQLException) {}catch (Exception localException) {}
      }
    });
  }
}
