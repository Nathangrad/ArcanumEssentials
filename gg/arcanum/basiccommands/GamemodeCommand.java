package gg.arcanum.basiccommands;

import gg.arcanum.core.Core;
import gg.arcanum.utils.PermissionCheck;
import java.util.logging.Logger;
import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor
{
  Core essentials;
  String usage = "§cUsage: /gamemode <0|1|2> <player>";
  
  public GamemodeCommand(Core e) {
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
    if ((args.length == 1) || (args.length == 2)) {
      int gamemode = 0;
      try {
        gamemode = Integer.parseInt(args[0].toString());
      } catch (NumberFormatException nfe) {
        player.sendMessage(essentials.prefix + usage);
        return true;
      }
      Player target = null;
      if (args.length == 2) {
        target = org.bukkit.Bukkit.getServer().getPlayer(args[1]);
        if (target == null) {
          player.sendMessage(essentials.prefix + essentials.playerror);
          return true;
        }
      }
      if (!PermissionCheck.check(player, "core.gamemode")) {
        player.sendMessage(essentials.prefix + essentials.permerror);
        return true;
      }
      player = target == null ? player : target;
      String gamemodeStr = "";
      switch (gamemode) {
      case 0: 
        player.setGameMode(GameMode.SURVIVAL);
        gamemodeStr = "Survival";
        break;
      case 1: 
        player.setGameMode(GameMode.CREATIVE);
        gamemodeStr = "Creative";
        break;
      case 2: 
        player.setGameMode(GameMode.ADVENTURE);
        gamemodeStr = "Adventure";
        break;
      default: 
        player.sendMessage(essentials.prefix + usage);
      }
      
      player.sendMessage(essentials.prefix + "§aYou've been set to §a" + gamemodeStr + " §amode.");
      if (target != null) {
        ((Player)sender).sendMessage(
          essentials.prefix + "§a" + player.getName() + " has been set to §e" + gamemodeStr + " §amode.");
      }
      return true;
    }
    return true;
  }
}
