package gg.arcanum.core;

import gg.arcanum.basiccommands.ClearCommand;
import gg.arcanum.basiccommands.GamemodeCommand;
import gg.arcanum.commands.StaffCommand;
import org.bukkit.command.PluginCommand;

public class CommandHandler
{
  Core essentials;
  
  public CommandHandler(Core e)
  {
    essentials = e;
    registerCommands();
  }
  
  public void registerCommands() {
    essentials.getCommand("invsee").setExecutor(new gg.arcanum.basiccommands.InvseeCommand(essentials));
    essentials.getCommand("whois").setExecutor(new gg.arcanum.basiccommands.WhoisCommand(essentials));
    essentials.getCommand("workbench").setExecutor(new gg.arcanum.basiccommands.WorkbenchCommand(essentials));
    essentials.getCommand("clear").setExecutor(new ClearCommand(essentials));
    essentials.getCommand("ci").setExecutor(new ClearCommand(essentials));
    essentials.getCommand("gm").setExecutor(new GamemodeCommand(essentials));
    essentials.getCommand("gamemode").setExecutor(new GamemodeCommand(essentials));
    essentials.getCommand("afk").setExecutor(new gg.arcanum.basiccommands.AFKCommand(essentials));
    essentials.getCommand("list").setExecutor(new gg.arcanum.basiccommands.ListCommand(essentials));
    essentials.getCommand("heal").setExecutor(new gg.arcanum.basiccommands.HealCommand(essentials));
    essentials.getCommand("feed").setExecutor(new gg.arcanum.basiccommands.FeedCommand(essentials));
    essentials.getCommand("hat").setExecutor(new gg.arcanum.basiccommands.HatCommand(essentials));
    essentials.getCommand("fly").setExecutor(new gg.arcanum.basiccommands.FlyCommand(essentials));
    essentials.getCommand("rules").setExecutor(new gg.arcanum.basiccommands.RulesCommand(essentials));
    essentials.getCommand("broadcast").setExecutor(new gg.arcanum.basiccommands.BroadcastCommand(essentials));
    essentials.getCommand("repair").setExecutor(new gg.arcanum.basiccommands.RepairCommand(essentials));
    essentials.getCommand("help").setExecutor(new gg.arcanum.basiccommands.HelpCommand(essentials));
    essentials.getCommand("back").setExecutor(new gg.arcanum.teleportation.BackCommand(essentials));
    essentials.getCommand("tp").setExecutor(new gg.arcanum.teleportation.TpCommand(essentials));
    essentials.getCommand("tpa").setExecutor(new gg.arcanum.teleportation.TpaCommand(essentials));
    essentials.getCommand("tpahere").setExecutor(new gg.arcanum.teleportation.TpahereCommand(essentials));
    essentials.getCommand("tpaccept").setExecutor(new gg.arcanum.teleportation.TpacceptCommand(essentials));
    essentials.getCommand("tpdeny").setExecutor(new gg.arcanum.teleportation.TpadenyCommand(essentials));
    essentials.getCommand("tphere").setExecutor(new gg.arcanum.teleportation.TphereCommand(essentials));
    essentials.getCommand("tptoggle").setExecutor(new gg.arcanum.teleportation.TptoggleCommand(essentials));
    essentials.getCommand("top").setExecutor(new gg.arcanum.teleportation.TopCommand(essentials));
    essentials.getCommand("home").setExecutor(new gg.arcanum.savedlocations.HomeCommand(essentials));
    essentials.getCommand("sethome").setExecutor(new gg.arcanum.savedlocations.SethomeCommand(essentials));
    essentials.getCommand("warp").setExecutor(new gg.arcanum.savedlocations.WarpCommand(essentials));
    essentials.getCommand("setwarp").setExecutor(new gg.arcanum.savedlocations.SetwarpCommand(essentials));
    essentials.getCommand("delwarp").setExecutor(new gg.arcanum.savedlocations.DelwarpCommand(essentials));
    essentials.getCommand("spawn").setExecutor(new gg.arcanum.savedlocations.SpawnCommand(essentials));
    essentials.getCommand("setspawn").setExecutor(new gg.arcanum.savedlocations.SetspawnCommand(essentials));
    essentials.getCommand("kick").setExecutor(new gg.arcanum.admin.KickCommand(essentials));
    essentials.getCommand("ban").setExecutor(new gg.arcanum.admin.BanCommand(essentials));
    essentials.getCommand("unban").setExecutor(new gg.arcanum.admin.PardonCommand(essentials));
    essentials.getCommand("mute").setExecutor(new gg.arcanum.admin.MuteCommand(essentials));
    essentials.getCommand("unmute").setExecutor(new gg.arcanum.admin.UnmuteCommand(essentials));
    StaffCommand sc = new StaffCommand();
    essentials.getCommand("punishfor").setExecutor(sc);
    essentials.getCommand("punish").setExecutor(sc);
    essentials.getCommand("kick").setExecutor(sc);
    essentials.getCommand("ban").setExecutor(sc);
    essentials.getCommand("mute").setExecutor(sc);
  }
}
