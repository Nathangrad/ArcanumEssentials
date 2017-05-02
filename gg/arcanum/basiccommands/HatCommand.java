package gg.arcanum.basiccommands;

import gg.arcanum.core.Core;
import gg.arcanum.utils.PermissionCheck;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class HatCommand implements CommandExecutor
{
  Core essentials;
  String usage = "§cUsage: /hat";
  
  public HatCommand(Core e) {
    essentials = e;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String comandlbl, String[] args)
  {
    if (!(sender instanceof Player)) {
      essentials.getLogger().info(essentials.conserror);
      return true;
    }
    Player player = (Player)sender;
    if (!PermissionCheck.check(player, "core.hat")) {
      player.sendMessage(essentials.prefix + essentials.permerror);
      return true;
    }
    ItemStack itemHand = player.getInventory().getHelmet();
    ItemStack itemHelm = player.getInventory().getItemInHand();
    player.getInventory().setItemInHand(itemHand);
    player.getInventory().setHelmet(itemHelm);
    player.sendMessage(essentials.prefix + "§aYour hat has been changed");
    return true;
  }
}
