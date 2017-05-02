package gg.arcanum.admin;

import gg.arcanum.core.Core;
import gg.arcanum.data.MySqlManager;
import gg.arcanum.utils.MuteUtil;
import gg.arcanum.utils.PermissionCheck;
import java.sql.SQLException;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnmuteCommand implements CommandExecutor
{
  Core essentials;
  String usage = "§cUsage: /unmute <player>";
  
  public UnmuteCommand(Core e) {
    essentials = e;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String comandlbl, String[] args)
  {
    if (!(sender instanceof Player)) {
      essentials.getLogger().info(essentials.conserror);
      return true;
    }
    Player player = (Player)sender;
    if (!PermissionCheck.check(player, "core.unmute")) {
      player.sendMessage(essentials.prefix + essentials.permerror);
      return true;
    }
    if (args.length != 0) {
      Player target = Bukkit.getServer().getPlayer(args[0].toString());
      if (target == null) {
        player.sendMessage(essentials.prefix + usage);
        return true;
      }
      if (!MuteUtil.isMuted(target)) {
        player.sendMessage(essentials.prefix + "§e" + target.getName() + "§c is not currently muted");
      } else {
        MuteUtil.setUnMuted(target);
        player.sendMessage(essentials.prefix + "§e" + target.getName() + "§a is no longer muted");
        MySqlManager sql = new MySqlManager();
        try {
          sql.open();
          sql.executeNonQuery("INSERT INTO PunishmentHistory (Player, Staff, Type, Length, Reason, Date) VALUES ('" + 
            target.getUniqueId() + "','" + 
            player.getUniqueId() + "','Unmute',NULL,NULL, NOW())");
          sql.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      return true;
    }
    player.sendMessage(essentials.prefix + usage);
    
    return true;
  }
}
