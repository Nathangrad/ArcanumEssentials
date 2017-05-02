package gg.arcanum.commands;

import gg.arcanum.enums.PunishmentType;
import gg.arcanum.inventories.PlayerSelectInventory;
import gg.arcanum.inventories.StaffInventory;
import java.util.Map;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffCommand implements org.bukkit.command.CommandExecutor
{
  public StaffCommand() {}
  
  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
  {
    if (((cmd.getName().equalsIgnoreCase("punishfor")) || (cmd.getName().equalsIgnoreCase("punish"))) && 
      ((sender instanceof Player))) {
      Player p = (Player)sender;
      if (!p.hasPermission("arcanum.cmd.punish"))
        return true;
      p.openInventory(new StaffInventory(p, args.length == 0 ? null : args).getInventory());
      return true; }
    if ((cmd.getName().equalsIgnoreCase("mute")) && ((sender instanceof Player))) {
      Player p = (Player)sender;
      if (!p.hasPermission("arcanum.cmd.punish"))
        return true;
      StaffInventory.reasons.put(p, args.length == 0 ? null : args);
      PlayerSelectInventory psi = new PlayerSelectInventory(p, PunishmentType.MUTE);
      p.openInventory(psi.getInventory());
      initial = false;
      return true; }
    if ((cmd.getName().equalsIgnoreCase("ban")) && ((sender instanceof Player))) {
      Player p = (Player)sender;
      if (!p.hasPermission("arcanum.cmd.punish"))
        return true;
      StaffInventory.reasons.put(p, args.length == 0 ? null : args);
      PlayerSelectInventory psi = new PlayerSelectInventory(p, PunishmentType.BAN);
      p.openInventory(psi.getInventory());
      initial = false;
      return true; }
    if ((cmd.getName().equalsIgnoreCase("kick")) && ((sender instanceof Player))) {
      if (args.length == 0) {
        sender.sendMessage("§cUsage: /kick <Player> <Reason>");
        return true;
      }
      if (org.bukkit.Bukkit.getPlayer(args[0]) != null) {
        Player kicked = org.bukkit.Bukkit.getPlayer(args[0]);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < args.length; i++)
          sb.append(args[i]);
        kicked.kickPlayer("§cYou have been kicked by §e" + ((Player)sender).getName() + "\n§cFor " + (
          args.length == 1 ? "misconduct" : sb.toString()));
      } else {
        sender.sendMessage("§cInvalid player specified");
      }
      return true;
    }
    return false;
  }
}
