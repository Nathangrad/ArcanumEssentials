package gg.arcanum.teleportation;

import gg.arcanum.core.Core;
import gg.arcanum.utils.PermissionCheck;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TopCommand implements CommandExecutor
{
  Core essentials;
  String usage = "§cUsage: /top";
  
  public TopCommand(Core e) {
    essentials = e;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String comandlbl, String[] args)
  {
    if (!(sender instanceof Player)) {
      essentials.getLogger().info(essentials.conserror);
      return true;
    }
    Player player = (Player)sender;
    if (!PermissionCheck.check(player, "core.top")) {
      player.sendMessage(essentials.prefix + essentials.permerror);
      return true;
    }
    for (int y = 255; y > 0; y--) {
      if (new Location(player.getWorld(), player.getLocation().getBlockX(), y, player.getLocation().getBlockZ())
        .getBlock().getType() != null)
        if (new Location(player.getWorld(), player.getLocation().getBlockX(), y, 
          player.getLocation().getBlockZ()).getBlock().getType() != Material.AIR) {
          player.teleport(new Location(player.getWorld(), player.getLocation().getBlockX(), y + 1, 
            player.getLocation().getBlockZ()));
          player.sendMessage(essentials.prefix + "§aTeleported to the highest location");
          return true;
        }
    }
    return true;
  }
}
