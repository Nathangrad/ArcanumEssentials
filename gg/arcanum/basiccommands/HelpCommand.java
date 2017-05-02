package gg.arcanum.basiccommands;

import gg.arcanum.core.Core;
import gg.arcanum.utils.PermissionCheck;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_8_R1.CraftServer;
import org.bukkit.entity.Player;

public class HelpCommand
  implements CommandExecutor
{
  Core essentials;
  String usage = "§cUsage: /help §o<Page>";
  
  public HelpCommand(Core e) {
    essentials = e;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String comandlbl, String[] args)
  {
    if (!(sender instanceof Player)) {
      essentials.getLogger().info(essentials.conserror);
      return true;
    }
    Player player = (Player)sender;
    CommandMap cmdmap = null;
    try {
      if ((Bukkit.getServer() instanceof CraftServer)) {
        Field f = CraftServer.class.getDeclaredField("commandMap");
        f.setAccessible(true);
        cmdmap = (CommandMap)f.get(Bukkit.getServer());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    int page = 1;
    if (args.length != 0) {
      try {
        page = Integer.parseInt(args[0].toString());
      } catch (NumberFormatException e) {
        player.sendMessage(essentials.prefix + "§cThis page doesn't exist");
        return true;
      }
    }
    if (cmdmap != null) {
      List<String> commands = new ArrayList();
      StringBuilder builder = new StringBuilder();
      for (Command command : ((SimpleCommandMap)cmdmap).getCommands()) {
        if ((!commands.contains("§a /" + command.getName() + "§7: " + command.getDescription())) && 
          (!command.getDescription().contains("A Mojang provided command")) && 
          (!command.getLabel().startsWith("man")) && (!command.getLabel().startsWith("tempa")) && 
          (!command.getLabel().startsWith("tempd")) && (!command.getLabel().startsWith("templ")) && 
          (!command.getLabel().startsWith("icanhas")) && (!command.getLabel().startsWith("vault")) && 
          (!command.getLabel().startsWith("timings")) && (!command.getLabel().startsWith("tps")) && 
          (!command.getLabel().startsWith("version")) && (!command.getLabel().startsWith("money")) && 
          (!command.getLabel().startsWith("listg")) && (!command.getLabel().startsWith("restart")) && 
          (!command.getDescription().contains("help menu")) && 
          (command.getPermission() != null) && 
          (PermissionCheck.check(player, command.getPermission()))) {
          commands.add("§a /" + command.getName() + "§7: " + command.getDescription());
        }
      }
      
      commands.sort(null);
      int start = page * 10 - 10;
      if (page == 1)
        start = 0;
      int end = page * 10 - 1;
      int pages = getMax(commands);
      try {
        for (String s : commands.subList(start, end)) {
          builder.append(s + "\n");
        }
      } catch (IndexOutOfBoundsException ioobe) {
        try {
          for (String s : commands.subList(start, commands.size())) {
            builder.append(s + "\n");
          }
        } catch (IndexOutOfBoundsException e) {
          builder = new StringBuilder();
        } catch (IllegalArgumentException e) {
          builder = new StringBuilder();
        }
      }
      player.sendMessage(essentials.prefix + "§eHelp page " + page + "/" + pages + "\n" + builder.toString());
    }
    return true;
  }
  
  private int getMax(List<String> commands) {
    StringBuilder builder = new StringBuilder();
    boolean complete = false;
    int total = 1;
    for (int page = 1; !complete; page++) {
      int start = page * 10 - 10;
      if (page == 1)
        start = 0;
      int end = page * 10 - 1;
      try {
        for (String s : commands.subList(start, end)) {
          builder.append(s + "\n");
        }
      } catch (IndexOutOfBoundsException ioobe) {
        try {
          for (String s : commands.subList(start, commands.size())) {
            builder.append(s + "\n");
          }
        } catch (IndexOutOfBoundsException e) {
          complete = true;
          total = page - 1;
        } catch (IllegalArgumentException e) {
          complete = true;
          total = page - 1;
        }
      }
    }
    return total;
  }
}
