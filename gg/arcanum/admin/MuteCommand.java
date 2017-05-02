package gg.arcanum.admin;

import gg.arcanum.core.Core;
import gg.arcanum.data.MySqlManager;
import gg.arcanum.utils.ChatFormatting;
import gg.arcanum.utils.MuteUtil;
import gg.arcanum.utils.PermissionCheck;
import java.sql.SQLException;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteCommand implements CommandExecutor
{
  Core essentials;
  String usage = "§cUsage: /mute <Player> §o<Minutes> §o<Message>";
  
  public MuteCommand(Core e) {
    essentials = e;
  }
  

  public boolean onCommand(CommandSender sender, Command cmd, String comandlbl, String[] args)
  {
    if (!(sender instanceof Player)) {
      essentials.getLogger().info(essentials.conserror);
      return true;
    }
    Player player = (Player)sender;
    if (!PermissionCheck.check(player, "core.mute")) {
      player.sendMessage(essentials.prefix + essentials.permerror);
      return true;
    }
    if (args.length != 0) {
      final OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[0].toString());
      if (target == null) {
        player.sendMessage(essentials.prefix + usage);
        return true;
      }
      if (args.length >= 2) {
        long amount = 0L;
        try {
          amount = Long.parseLong(Integer.parseInt(args[1].toString()).toString());
        }
        catch (NumberFormatException localNumberFormatException) {}
        StringBuilder builder = new StringBuilder();
        if (args.length >= 3) {
          for (int i = 2; i < args.length; i++)
            builder.append(args[i].toString().replace("&", "§") + " ");
        } else {
          builder.append("Misconduct");
        }
        try {
          ((Player)target).sendMessage(essentials.prefix + "§cYou have been muted for " + amount + 
            " minutes for " + builder.toString());
        }
        catch (Exception localException) {}
        player.sendMessage(essentials.prefix + "§e" + target.getName() + "§a has been muted for " + amount + 
          " minutes for " + builder.toString());
        MySqlManager sql = new MySqlManager();
        try {
          sql.open();
          sql.executeNonQuery(
            "INSERT INTO PunishmentHistory (Player, Staff, Type, Length, Reason, Date) VALUES ('" + 
            target.getUniqueId() + "','" + player.getUniqueId() + "','Mute'," + amount + ",'" + 
            ChatFormatting.apos(builder.toString()) + "', NOW())");
          sql.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
        amount *= 1200L;
        MuteUtil.setMuted(target);
        Bukkit.getScheduler().scheduleSyncDelayedTask(essentials, new Runnable()
        {
          public void run() {
            if (MuteUtil.isMuted(target)) {
              MuteUtil.setUnMuted(target);
              try {
                ((Player)target).sendMessage(essentials.prefix + "§aYou are no longer muted");
              }
              catch (Exception localException) {}
            }
          }
        }, amount);
      } else {
        MuteUtil.setMuted(target);
        MySqlManager sql = new MySqlManager();
        try {
          sql.open();
          sql.executeNonQuery("INSERT INTO PunishmentHistory (Player, Staff, Type, Length, Reason, Date) VALUES ('" + 
            target.getUniqueId() + "','" + 
            player.getUniqueId() + "','Mute',NULL,NULL, NOW())");
          sql.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
        try {
          ((Player)target).sendMessage(essentials.prefix + "§cYou have been muted");
        }
        catch (Exception localException1) {}
        player.sendMessage(essentials.prefix + "§e" + target.getName() + "§a has been muted");
      }
      return true;
    }
    player.sendMessage(essentials.prefix + usage);
    
    return true;
  }
}
