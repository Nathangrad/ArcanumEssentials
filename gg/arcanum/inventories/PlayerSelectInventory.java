package gg.arcanum.inventories;

import gg.arcanum.enums.PunishmentType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerSelectInventory
  implements Listener
{
  ArrayList<Inventory> invs = new ArrayList();
  int current = 0;
  PunishmentType type;
  static HashMap<Player, PlayerSelectInventory> players = new HashMap();
  public boolean initial = true;
  
  public PlayerSelectInventory(Player p, PunishmentType type) {
    if (p == null)
      return;
    Collection<? extends Player> onlinePlayersCol = Bukkit.getServer().getOnlinePlayers();
    Player[] onlinePlayers = new Player[onlinePlayersCol.size()];
    for (int i = 0; i < onlinePlayersCol.size(); i++)
      onlinePlayers[i] = ((Player)onlinePlayersCol.toArray()[i]);
    int start = 0;int end = 27;
    Player[] playerPage = new Player[28];
    try {
      for (;;) {
        for (int i = start; i <= end; i++) {
          playerPage[i] = onlinePlayers[i];
        }
        createPage(playerPage);
        playerPage = new Player[28];
        start += 28;
        end += 28;
      }
      return; } catch (Exception e) { if (playerPage.length != 0) {
        createPage(playerPage);
      }
      
      players.put(p, this);
      current = 0;
      this.type = type;
    }
  }
  
  void createPage(Player[] playerList) { Inventory inv = Bukkit.createInventory(null, 54, "§cPlayer Selection");
    int[] border = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 46, 47, 48, 49, 50, 51, 52 };
    ItemStack borderItem = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)15);
    ItemMeta borderItemMeta = borderItem.getItemMeta();
    borderItemMeta.setDisplayName("§0 ");
    borderItem.setItemMeta(borderItemMeta);
    for (int i : border)
      inv.setItem(i, borderItem);
    ItemStack borderItemN = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)3);
    ItemMeta borderItemNMeta = borderItemN.getItemMeta();
    borderItemNMeta.setDisplayName("§d->");
    borderItemN.setItemMeta(borderItemNMeta);
    ItemStack borderItemP = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)3);
    ItemMeta borderItemPMeta = borderItemP.getItemMeta();
    borderItemPMeta.setDisplayName("§d<-");
    borderItemP.setItemMeta(borderItemPMeta);
    inv.setItem(53, borderItemN);
    inv.setItem(45, borderItemP);
    for (Player play : playerList) {
      try {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        SkullMeta meta = (SkullMeta)skull.getItemMeta();
        meta.setOwner(play.getName());
        meta.setDisplayName("§d" + play.getName());
        List<String> lore = new ArrayList();
        lore.add("§c" + play.getUniqueId().toString());
        meta.setLore(lore);
        skull.setItemMeta(meta);
        inv.addItem(new ItemStack[] { skull });
      }
      catch (NullPointerException localNullPointerException) {}
    }
    invs.add(inv);
  }
  
  public Inventory getInventory() {
    return (Inventory)invs.get(0);
  }
  
  void remove(Player p) {
    players.remove(p);
  }
  
  @EventHandler
  public void onMovingBadly(PlayerMoveEvent e) {
    if (players.containsKey(e.getPlayer()))
      remove(e.getPlayer());
  }
  
  @EventHandler
  public void onUserClick(InventoryClickEvent e) {
    if ((e.getCurrentItem() != null) && (players.containsKey(e.getWhoClicked()))) {
      Player p = (Player)e.getWhoClicked();
      if (playersgetinitial) {
        playersgetinitial = false;
        return;
      }
      e.setCancelled(true);
      if ((e.getCurrentItem().getType() != Material.STAINED_GLASS_PANE) && (e.getCurrentItem().hasItemMeta()))
      {




        p.openInventory(new DurationSelectInventory(p, playersgettype, 
          Bukkit.getOfflinePlayer(
          UUID.fromString(((String)e.getCurrentItem().getItemMeta().getLore().get(0)).replaceAll("§c", ""))))
          .getInventory());
        remove(p);
      }
      else if ((e.getCurrentItem().getItemMeta() != null) && 
        (e.getCurrentItem().getItemMeta().getDisplayName() != null) && 
        (e.getCurrentItem().getItemMeta().getDisplayName().contains("->"))) {
        try {
          p.openInventory((Inventory)playersgetinvs.get(playersgetcurrent + 1));
          playersgetcurrent += 1;
        }
        catch (Exception localException) {}
      } else if ((e.getCurrentItem().getItemMeta() != null) && 
        (e.getCurrentItem().getItemMeta().getDisplayName() != null) && 
        (e.getCurrentItem().getItemMeta().getDisplayName().contains("<-"))) {
        try {
          p.openInventory((Inventory)playersgetinvs.get(playersgetcurrent - 1));
          playersgetcurrent -= 1;
        }
        catch (Exception localException1) {}
      }
    }
  }
}
