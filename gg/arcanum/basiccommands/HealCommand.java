package gg.arcanum.basiccommands;

import gg.arcanum.core.Core;
import gg.arcanum.utils.PermissionCheck;
import java.util.logging.Logger;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor
{
  Core essentials;
  String usage = "§cUsage: /heal §o<Player>";
  
  public HealCommand(Core e) {
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
      if (!PermissionCheck.check(player, "core.heal")) {
        player.sendMessage(essentials.prefix + essentials.permerror);
        return true;
      }
      player.setHealth(20.0D);
      player.setFoodLevel(20);
      player.sendMessage(essentials.prefix + "§aYou have healed yourself");
    } else if (args.length == 1) {
      if (!PermissionCheck.check(player, "core.healothers")) {
        player.sendMessage(essentials.prefix + essentials.permerror);
        return true;
      }
      Player target = org.bukkit.Bukkit.getServer().getPlayer(args[0]);
      if (target != null) {
        target.setHealth(20.0D);
        target.setFoodLevel(20);
        target.sendMessage(essentials.prefix + "§aYou have been healed");
        player.sendMessage(essentials.prefix + "§aYou have healed §e" + target.getName());
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
