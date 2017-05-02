package gg.arcanum.listeners;

import gg.arcanum.core.Core;
import gg.arcanum.utils.AFKUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class AFKListener implements Listener
{
  Core essentials;
  
  public AFKListener(Core e)
  {
    essentials = e;
  }
  
  @EventHandler
  public void onAFKMove(PlayerMoveEvent moveEvent) {
    if ((moveEvent.getPlayer() != null) && 
      (AFKUtil.isAFK(moveEvent.getPlayer()))) {
      AFKUtil.setNotAFK(moveEvent.getPlayer());
      moveEvent.getPlayer().sendMessage(
        essentials.prefix + "§cYou are no longer AFK");
    }
  }
}
