package gg.arcanum.teleportation;

import gg.arcanum.core.Core;
import gg.arcanum.utils.PermissionCheck;
import gg.arcanum.utils.TptoggleUtil;
import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TptoggleCommand implements CommandExecutor
{
  Core essentials;
  String usage = "§cUsage: /tptoggle";
  
  public TptoggleCommand(Core e) {
    essentials = e;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String comandlbl, String[] args)
  {
    if (!(sender instanceof Player)) {
      essentials.getLogger().info(essentials.conserror);
      return true;
    }
    Player player = (Player)sender;
    if (!PermissionCheck.check(player, "core.tptoggle")) {
      player.sendMessage(essentials.prefix + essentials.permerror);
      return true;
    }
    if (TptoggleUtil.isDenying(player)) {
      TptoggleUtil.setAllow(player);
      player.sendMessage(essentials.prefix + "§aTeleportation to you is now enabled.");
    } else {
      TptoggleUtil.setDeny(player);
      player.sendMessage(essentials.prefix + "§cTeleportation to you is now disabled.");
    }
    return true;
  }
}
