package gg.arcanum.savedlocations;

import gg.arcanum.core.Core;
import gg.arcanum.utils.PermissionCheck;
import java.util.Iterator;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor
{
  Core essentials;
  String usage = "§cUsage: /warp";
  
  public WarpCommand(Core e) {
    essentials = e;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String comandlbl, String[] args)
  {
    if (!(sender instanceof Player)) {
      essentials.getLogger().info(essentials.conserror);
      return true;
    }
    Player player = (Player)sender;
    if (!PermissionCheck.check(player, "core.warp")) {
      player.sendMessage(essentials.prefix + essentials.permerror);
      return true;
    }
    if (args.length == 0) {
      StringBuilder builder = new StringBuilder();
      builder.append("(");
      for (String warp : essentials.getConfig().getStringList("warplist")) {
        builder.append(", " + warp);
      }
      player.sendMessage(
        essentials.prefix + "§eWarps:\n" + builder.toString().replace("(, ", "").replace("(", ""));
    } else {
      String warpname = args[0].toString().toUpperCase();
      try {
        player.teleport(new org.bukkit.Location(
          org.bukkit.Bukkit.getServer().getWorld((String)essentials.getConfig().get("warp." + warpname + ".w")), 
          Double.parseDouble(essentials.getConfig().get("warp." + warpname + ".x").toString()), 
          Double.parseDouble(essentials.getConfig().get("warp." + warpname + ".y").toString()), 
          Double.parseDouble(essentials.getConfig().get("warp." + warpname + ".z").toString()), 
          Float.parseFloat(essentials.getConfig().get("warp." + warpname + ".yaw").toString()), 
          Float.parseFloat(essentials.getConfig().get("warp." + warpname + ".pitch").toString())));
        player.sendMessage(essentials.prefix + "§aSuccessfully teleported to §eWarp " + warpname);
      } catch (NullPointerException e) {
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        for (String warp : essentials.getConfig().getStringList("warplist")) {
          builder.append(", " + warp);
        }
        player.sendMessage(
          essentials.prefix + "§eWarps:\n" + builder.toString().replace("(, ", "").replace("(", ""));
      } catch (NumberFormatException e) {
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        for (??? = essentials.getConfig().getStringList("warplist").iterator(); ???.hasNext();) { String warp = (String)???.next();
          builder.append(", " + warp);
        }
        player.sendMessage(
          essentials.prefix + "§eWarps:\n" + builder.toString().replace("(, ", "").replace("(", ""));
      } catch (IllegalArgumentException e) {
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        for (??? = essentials.getConfig().getStringList("warplist").iterator(); ???.hasNext();) { String warp = (String)???.next();
          builder.append(", " + warp);
        }
        player.sendMessage(
          essentials.prefix + "§eWarps:\n" + builder.toString().replace("(, ", "").replace("(", ""));
      }
    }
    return true;
  }
}
