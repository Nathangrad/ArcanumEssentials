package gg.arcanum.basiccommands;

import gg.arcanum.core.Core;
import gg.arcanum.utils.PermissionCheck;
import java.util.logging.Logger;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvseeCommand implements CommandExecutor
{
  Core essentials;
  String usage = "§cUsage: /invsee <Player>";
  
  public InvseeCommand(Core e) {
    essentials = e;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String comandlbl, String[] args)
  {
    if (!(sender instanceof Player)) {
      essentials.getLogger().info(essentials.conserror);
      return true;
    }
    Player player = (Player)sender;
    if (!PermissionCheck.check(player, "core.invsee")) {
      player.sendMessage(essentials.prefix + essentials.permerror);
      return true;
    }
    if (args.length == 0) {
      player.sendMessage(essentials.prefix + usage);
      return true;
    }
    Player target = org.bukkit.Bukkit.getServer().getPlayer(args[0]);
    if (target != null) {
      player.openInventory(target.getInventory());
      player.sendMessage(essentials.prefix + "§aYou are viewing the inventory of §e" + target.getName());
    } else {
      player.sendMessage(essentials.prefix + essentials.playerror);
      return true;
    }
    return true;
  }
}
