package gg.arcanum.inventories;

import gg.arcanum.core.ChatHandler;
import gg.arcanum.core.ConnectionHandler;
import gg.arcanum.data.MySqlManager;
import gg.arcanum.enums.PunishmentType;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
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

public class DurationSelectInventory implements Listener
{
  Inventory inv;
  Player player;
  OfflinePlayer selection;
  PunishmentType type;
  boolean ready = false;
  static HashMap<Player, DurationSelectInventory> players = new HashMap();
  
  public DurationSelectInventory(Player p, PunishmentType type, OfflinePlayer selection) {
    if (p == null)
      return;
    player = p;
    this.type = type;
    ready = false;
    players.put(p, this);
    this.selection = selection;
    inv = Bukkit.createInventory(null, 54, 
      "§c" + (type == PunishmentType.BAN ? "Ban" : "Mute") + "§e " + selection.getName());
    int[] border = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53 };
    ItemStack borderItem = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)15);
    ItemMeta borderItemMeta = borderItem.getItemMeta();
    borderItemMeta.setDisplayName("§0 ");
    borderItem.setItemMeta(borderItemMeta);
    for (int i : border)
      inv.setItem(i, borderItem);
    Map<Integer, String> timeValues = new HashMap();
    timeValues.put(Integer.valueOf(19), "1 minute");
    timeValues.put(Integer.valueOf(20), "10 minutes");
    timeValues.put(Integer.valueOf(21), "30 minutes");
    timeValues.put(Integer.valueOf(22), "1 hour");
    timeValues.put(Integer.valueOf(23), "6 hours");
    timeValues.put(Integer.valueOf(24), "12 hours");
    timeValues.put(Integer.valueOf(25), "1 day");
    timeValues.put(Integer.valueOf(29), "2 days");
    timeValues.put(Integer.valueOf(30), "1 week");
    timeValues.put(Integer.valueOf(31), "2 weeks");
    timeValues.put(Integer.valueOf(32), "1 month");
    timeValues.put(Integer.valueOf(33), "Forever");
    for (Object timeValue : timeValues.entrySet()) {
      ItemStack time = new ItemStack(Material.WATCH, 1);
      ItemMeta timeMeta = time.getItemMeta();
      timeMeta.setDisplayName("§c" + (String)((Map.Entry)timeValue).getValue());
      time.setItemMeta(timeMeta);
      inv.setItem(((Integer)((Map.Entry)timeValue).getKey()).intValue(), time);
    }
  }
  
  public Inventory getInventory() {
    return inv;
  }
  
  @EventHandler
  public void onCloseDuration(InventoryCloseEvent e) {
    if (players.containsKey(e.getPlayer())) {
      if (!playersgetgetPlayerready) {
        playersgetgetPlayerready = true;
        return;
      }
      players.remove(e.getPlayer());
    }
  }
  
  @EventHandler
  public void onInteractDuration(InventoryClickEvent e) {
    final Player p = (Player)e.getWhoClicked();
    if ((e.getWhoClicked() != null) && (players.containsKey(p))) {
      e.setCancelled(true);
      if ((e.getCurrentItem() != null) && (e.getCurrentItem().getType() == Material.WATCH)) {
        String length = e.getCurrentItem().getItemMeta().getDisplayName().replace("§c", "");
        String valType = "";
        int val = 0;
        String str1; switch ((str1 = length).hashCode()) {case -1840314991:  if (str1.equals("2 weeks")) {} break; case -1290464740:  if (str1.equals("30 minutes")) {} break; case -897437730:  if (str1.equals("10 minutes")) {} break; case 46305069:  if (str1.equals("1 day")) {} break; case 986990919:  if (str1.equals("Forever")) {} break; case 1054647523:  if (str1.equals("1 minute")) break; break; case 1435589747:  if (str1.equals("1 hour")) {} break; case 1436026499:  if (str1.equals("1 week")) {} break; case 1464086405:  if (str1.equals("2 days")) {} break; case 1558220241:  if (str1.equals("1 month")) {} break; case 1696160421:  if (str1.equals("6 hours")) {} break; case 1939473488:  if (!str1.equals("12 hours")) {
            break label482;
            val = 1;
            valType = "MINUTE";
            
            break label482;
            val = 10;
            valType = "MINUTE";
            
            break label482;
            val = 30;
            valType = "MINUTE";
            
            break label482;
            val = 1;
            valType = "HOUR";
            
            break label482;
            val = 6;
            valType = "HOUR";
          }
          else {
            val = 12;
            valType = "HOUR";
            
            break label482;
            val = 1;
            valType = "DAY";
            
            break label482;
            val = 2;
            valType = "DAY";
            
            break label482;
            val = 1;
            valType = "WEEK";
            
            break label482;
            val = 2;
            valType = "WEEK";
            
            break label482;
            val = 1;
            valType = "MONTH";
            
            break label482;
            val = 20;
            valType = "YEAR"; }
          break; }
        label482:
        final int acVal = val;
        final String acValType = valType;
        final String uuid = playersgetselection.getUniqueId().toString();
        final String type = playersgettype == PunishmentType.BAN ? "bans" : "mutes";
        MySqlManager.doRunnable(new Runnable()
        {
          public void run() {
            String reason = "";
            try {
              for (String s : (String[])StaffInventory.reasons.get(p)) {
                reason = reason + s.replaceAll("'", "") + " ";
              }
            } catch (Exception e) {
              reason = "Misconduct";
            }
            MySqlManager sql = new MySqlManager();
            try {
              sql.open();
              sql.executeNonQuery("DELETE FROM " + type + " WHERE uuid='" + uuid + "';");
              sql.executeNonQuery(
                "INSERT INTO " + type + " VALUES ('" + uuid + "', NOW(), DATE_ADD(NOW(), INTERVAL " + 
                acVal + " " + acValType + "), '" + reason + "');");
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
            for (String s : (String[])StaffInventory.reasons.get(p)) {
              reason = reason + s.replaceAll("'", "") + " ";
            }
          } catch (Exception ete) {
            reason = "Misconduct";
          }
          if (playersgetselection.isOnline())
          {
            playersgetselection.getPlayer().kickPlayer("§cYou've been " + (
              playersgettype == PunishmentType.BAN ? "banned" : "muted") + " for \n" + 
              reason + (playersgettype == PunishmentType.MUTE ? 
              "\nTake a moment before re-connecting to the server" : "")); }
          p.sendMessage("§c" + (playersgettype == PunishmentType.BAN ? "Ban" : "Mute") + "§e " + 
            playersgetselection.getName() + "§c for " + 
            e.getCurrentItem().getItemMeta().getDisplayName() + " completed");
          p.closeInventory();
        }
        catch (Exception localException1) {}
      }
    }
  }
}
