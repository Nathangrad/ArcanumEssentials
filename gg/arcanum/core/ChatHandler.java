package gg.arcanum.core;

import gg.arcanum.data.DataRow;
import gg.arcanum.data.DataTable;
import gg.arcanum.data.MySqlManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatHandler implements org.bukkit.event.Listener
{
  public ChatHandler() {}
  
  static Map<UUID, Mute> mutes = new HashMap();
  
  @EventHandler
  public void onPlayerChatting(AsyncPlayerChatEvent e) {
    Player p = e.getPlayer();
    if (mutes.containsKey(p.getUniqueId())) {
      e.setCancelled(true);
      p.sendMessage("§cYou're currently muted until " + 
        DateFormat.getDateTimeInstance().format(mutesgetgetUniqueIdend) + " for " + 
        mutesgetgetUniqueIdreason);
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
    MySqlManager.doRunnable(new Runnable()
    {
      public void run() {
        MySqlManager sql = new MySqlManager();
        try {
          sql.open();
          DataTable dt = sql.executeQuery("SELECT * FROM mutes WHERE end > NOW();");
          sql.close();
          ChatHandler.mutes = new HashMap();
          for (DataRow r : dt.getRows()) {
            Mute mute = new Mute();
            uuid = UUID.fromString(r.getCell("uuid").toString());
            start = ((Date)r.getCell("start"));
            end = ((Date)r.getCell("end"));
            reason = r.getCell("reason").toString();
            ChatHandler.mutes.put(uuid, mute);
          }
        }
        catch (SQLException localSQLException) {}catch (Exception localException) {}
      }
    });
  }
}
