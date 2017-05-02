package gg.arcanum.savedlocations;

import gg.arcanum.core.Core;
import gg.arcanum.utils.PermissionCheck;
import java.util.List;
import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class DelwarpCommand implements CommandExecutor
{
  Core essentials;
  String usage = "§cUsage: /delwarp §o<Warp>";
  
  public DelwarpCommand(Core e) {
    essentials = e;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String comandlbl, String[] args)
  {
    if (!(sender instanceof Player)) {
      essentials.getLogger().info(essentials.conserror);
      return true;
    }
    Player player = (Player)sender;
    if (!PermissionCheck.check(player, "core.delwarp")) {
      player.sendMessage(essentials.prefix + essentials.permerror);
      return true;
    }
    if (args.length == 0) {
      player.sendMessage(essentials.prefix + usage);
      return true;
    }
    String path = "warp." + args[0].toString().toUpperCase();
    if (essentials.getConfig().get(path + ".x") != null) {
      List<String> warps = essentials.getConfig().getStringList("warplist");
      warps.remove(args[0].toString().toUpperCase());
      essentials.getConfig().set("warplist", warps);
      essentials.getConfig().set(path, null);
      essentials.saveConfig();
      player.sendMessage(
        essentials.prefix + "§aSuccessfully deleted §eWarp " + args[0].toString().toUpperCase());
    } else {
      player.sendMessage(essentials.prefix + "§cThe warp \"§e" + args[0].toString().toUpperCase() + 
        "§c\" does not exist");
    }
    
    return true;
  }
}
