package gg.arcanum.basiccommands;

import gg.arcanum.core.Core;
import gg.arcanum.utils.PermissionCheck;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class ClearCommand implements CommandExecutor
{
  Core essentials;
  String usage = "§cUsage: /clear §o<Player>";
  
  public ClearCommand(Core e) {
    essentials = e;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String comandlbl, String[] args)
  {
    if (!(sender instanceof Player)) {
      essentials.getLogger().info(essentials.conserror);
      return true;
    }
    Player player = (Player)sender;
    if (args.length == 0) {
      if (!PermissionCheck.check(player, "core.clear")) {
        player.sendMessage(essentials.prefix + essentials.permerror);
        return true;
      }
      player.getInventory().clear();
      player.sendMessage(essentials.prefix + "§aYou've cleared your inventory");
    } else if (args.length == 1) {
      if (!PermissionCheck.check(player, "core.clearothers")) {
        player.sendMessage(essentials.prefix + essentials.permerror);
        return true;
      }
      Player target = org.bukkit.Bukkit.getServer().getPlayer(args[0]);
      if (target != null) {
        target.getInventory().clear();
        target.sendMessage(essentials.prefix + "§cYour inventory has been cleared");
        player.sendMessage(essentials.prefix + "§aYou've cleared the inventory of §e" + target.getName());
      } else {
        player.sendMessage(essentials.prefix + essentials.playerror);
        return true;
      }
    } else {
      player.sendMessage(essentials.prefix + usage);
      return true;
    }
    return true;
  }
}
