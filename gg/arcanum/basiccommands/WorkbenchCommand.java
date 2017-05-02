package gg.arcanum.basiccommands;

import gg.arcanum.core.Core;
import gg.arcanum.utils.PermissionCheck;
import java.util.logging.Logger;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public class WorkbenchCommand implements CommandExecutor
{
  Core essentials;
  String usage = "§cUsage: /workbench";
  
  public WorkbenchCommand(Core e) {
    essentials = e;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String comandlbl, String[] args)
  {
    if (!(sender instanceof Player)) {
      essentials.getLogger().info(essentials.conserror);
      return true;
    }
    Player player = (Player)sender;
    if (!PermissionCheck.check(player, "core.workbench")) {
      player.sendMessage(essentials.prefix + essentials.permerror);
      return true;
    }
    player.openInventory(org.bukkit.Bukkit.getServer().createInventory(null, InventoryType.WORKBENCH));
    player.sendMessage(essentials.prefix + "§aYou have accessed a workbench");
    return true;
  }
}
