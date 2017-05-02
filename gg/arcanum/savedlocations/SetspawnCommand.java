package gg.arcanum.savedlocations;

import gg.arcanum.core.Core;
import gg.arcanum.utils.PermissionCheck;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetspawnCommand implements CommandExecutor
{
  Core essentials;
  String usage = "§cUsage: /setspawn";
  
  public SetspawnCommand(Core e) {
    essentials = e;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String comandlbl, String[] args)
  {
    if (!(sender instanceof Player)) {
      essentials.getLogger().info(essentials.conserror);
      return true;
    }
    Player player = (Player)sender;
    if (!PermissionCheck.check(player, "core.setspawn")) {
      player.sendMessage(essentials.prefix + essentials.permerror);
      return true;
    }
    Location l = player.getLocation();
    essentials.getConfig().set("spawn.w", player.getWorld().getName());
    essentials.getConfig().set("spawn.x", Double.valueOf(l.getX()));
    essentials.getConfig().set("spawn.y", Double.valueOf(l.getY()));
    essentials.getConfig().set("spawn.z", Double.valueOf(l.getZ()));
    essentials.getConfig().set("spawn.yaw", Float.valueOf(l.getYaw()));
    essentials.getConfig().set("spawn.pitch", Float.valueOf(l.getPitch()));
    essentials.saveConfig();
    player.sendMessage(essentials.prefix + "§aSuccessfully set spawn");
    return true;
  }
}
