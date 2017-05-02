package gg.arcanum.savedlocations;

import gg.arcanum.core.Core;
import gg.arcanum.utils.PermissionCheck;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SethomeCommand implements CommandExecutor
{
  Core essentials;
  String usage = "§cUsage: /sethome";
  
  public SethomeCommand(Core e) {
    essentials = e;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String comandlbl, String[] args)
  {
    if (!(sender instanceof Player)) {
      essentials.getLogger().info(essentials.conserror);
      return true;
    }
    Player player = (Player)sender;
    if (!PermissionCheck.check(player, "core.sethome")) {
      player.sendMessage(essentials.prefix + essentials.permerror);
      return true;
    }
    Location l = player.getLocation();
    String path = "homes." + player.getUniqueId() + ".";
    essentials.getConfig().set(path + "w", player.getWorld().getName());
    essentials.getConfig().set(path + "x", Double.valueOf(l.getX()));
    essentials.getConfig().set(path + "y", Double.valueOf(l.getY()));
    essentials.getConfig().set(path + "z", Double.valueOf(l.getZ()));
    essentials.getConfig().set(path + "yaw", Float.valueOf(l.getYaw()));
    essentials.getConfig().set(path + "pitch", Float.valueOf(l.getPitch()));
    essentials.saveConfig();
    player.sendMessage(essentials.prefix + "§aSuccessfully set your home");
    return true;
  }
}
