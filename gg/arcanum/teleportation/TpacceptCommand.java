package gg.arcanum.teleportation;

import gg.arcanum.core.Core;
import gg.arcanum.utils.PermissionCheck;
import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpacceptCommand implements CommandExecutor
{
  Core essentials;
  String usage = "§cUsage: /tpaccept";
  
  public TpacceptCommand(Core e) {
    essentials = e;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String comandlbl, String[] args)
  {
    if (!(sender instanceof Player)) {
      essentials.getLogger().info(essentials.conserror);
      return true;
    }
    Player player = (Player)sender;
    if (!PermissionCheck.check(player, "core.tpaccept")) {
      player.sendMessage(essentials.prefix + essentials.permerror);
      return true;
    }
    TeleportRequest request = TeleportRequest.getRequest(player);
    if (request != null) {
      request.getSender().sendMessage(
        essentials.prefix + "§a" + request.getReceiver().getName() + "§a has accepted your request.");
      request.getReceiver().sendMessage(
        essentials.prefix + "§aYou've accepted §a" + request.getSender().getName() + "§a's request.");
      request.acceptRequest();
    } else {
      player.sendMessage(essentials.prefix + "§cTeleportation request not found");
    }
    return true;
  }
}
