package gg.arcanum.teleportation;

import gg.arcanum.core.Core;
import gg.arcanum.utils.PermissionCheck;
import gg.arcanum.utils.TptoggleUtil;
import java.util.logging.Logger;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TphereCommand implements CommandExecutor
{
  Core essentials;
  String usage = "§cUsage: /tphere <player>";
  
  public TphereCommand(Core e) {
    essentials = e;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String comandlbl, String[] args)
  {
    if (!(sender instanceof Player)) {
      essentials.getLogger().info(essentials.conserror);
      return true;
    }
    Player player = (Player)sender;
    if (!PermissionCheck.check(player, "core.tphere")) {
      player.sendMessage(essentials.prefix + essentials.permerror);
      return true;
    }
    if (args.length == 0) {
      player.sendMessage(essentials.prefix + usage);
    } else {
      Player target = org.bukkit.Bukkit.getServer().getPlayer(args[0].toString());
      if (target != null) {
        if (!TptoggleUtil.isDenying(target)) {
          target.teleport(player.getLocation());
          player.sendMessage(
            essentials.prefix + "§aSuccessfully teleported §a" + target.getName() + "§a to you.");
          target.sendMessage(essentials.prefix + "§aYou have been teleported to §a" + player.getName());
        } else {
          player.sendMessage(essentials.prefix + "§c" + target.getName() + "§c has disabled teleportation");
        }
      } else {
        player.sendMessage(essentials.prefix + essentials.playerror);
      }
    }
    return true;
  }
}
