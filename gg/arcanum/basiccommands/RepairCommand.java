package gg.arcanum.basiccommands;

import gg.arcanum.core.Core;
import gg.arcanum.utils.PermissionCheck;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class RepairCommand implements CommandExecutor
{
  Core essentials;
  String usage = "§cUsage: /repair <hand|all>";
  
  public RepairCommand(Core e) {
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
      player.sendMessage(essentials.prefix + usage);
      return true; }
    if (args.length == 1) {
      if (!PermissionCheck.check(player, "core.repair")) {
        player.sendMessage(essentials.prefix + essentials.permerror);
        return true;
      }
      if (args[0].toString().equalsIgnoreCase("HAND")) {
        try {
          player.getInventory().getItemInHand().setDurability((short)0);
        }
        catch (NullPointerException localNullPointerException) {}
        player.sendMessage(essentials.prefix + "§aYour hand item has been repaired");
      } else if (args[0].toString().equalsIgnoreCase("ALL")) {
        for (ItemStack i : player.getInventory().getContents()) {
          try {
            i.setDurability((short)0);
          }
          catch (NullPointerException localNullPointerException1) {}
        }
        player.sendMessage(essentials.prefix + "§aAll your items have been repaired");
      } else {
        player.sendMessage(essentials.prefix + usage);
      }
      return true;
    }
    return true;
  }
}
