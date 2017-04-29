package gg.arcanum.core;

import gg.arcanum.admin.*;
import gg.arcanum.basiccommands.*;
import gg.arcanum.commands.StaffCommand;
import gg.arcanum.savedlocations.*;
import gg.arcanum.teleportation.*;

public class CommandHandler {

	Core essentials;

	public CommandHandler(Core e) {
		essentials = e;
		registerCommands();
	}

	public void registerCommands() {
		essentials.getCommand("invsee").setExecutor(new InvseeCommand(essentials));
		essentials.getCommand("whois").setExecutor(new WhoisCommand(essentials));
		essentials.getCommand("workbench").setExecutor(new WorkbenchCommand(essentials));
		essentials.getCommand("clear").setExecutor(new ClearCommand(essentials));
		essentials.getCommand("ci").setExecutor(new ClearCommand(essentials));
		essentials.getCommand("gm").setExecutor(new GamemodeCommand(essentials));
		essentials.getCommand("gamemode").setExecutor(new GamemodeCommand(essentials));
		essentials.getCommand("afk").setExecutor(new AFKCommand(essentials));
		essentials.getCommand("list").setExecutor(new ListCommand(essentials));
		essentials.getCommand("heal").setExecutor(new HealCommand(essentials));
		essentials.getCommand("feed").setExecutor(new FeedCommand(essentials));
		essentials.getCommand("hat").setExecutor(new HatCommand(essentials));
		essentials.getCommand("fly").setExecutor(new FlyCommand(essentials));
		essentials.getCommand("rules").setExecutor(new RulesCommand(essentials));
		essentials.getCommand("broadcast").setExecutor(new BroadcastCommand(essentials));
		essentials.getCommand("repair").setExecutor(new RepairCommand(essentials));
		essentials.getCommand("help").setExecutor(new HelpCommand(essentials));
		essentials.getCommand("back").setExecutor(new BackCommand(essentials));
		essentials.getCommand("tp").setExecutor(new TpCommand(essentials));
		essentials.getCommand("tpa").setExecutor(new TpaCommand(essentials));
		essentials.getCommand("tpahere").setExecutor(new TpahereCommand(essentials));
		essentials.getCommand("tpaccept").setExecutor(new TpacceptCommand(essentials));
		essentials.getCommand("tpdeny").setExecutor(new TpadenyCommand(essentials));
		essentials.getCommand("tphere").setExecutor(new TphereCommand(essentials));
		essentials.getCommand("tptoggle").setExecutor(new TptoggleCommand(essentials));
		essentials.getCommand("top").setExecutor(new TopCommand(essentials));
		essentials.getCommand("home").setExecutor(new HomeCommand(essentials));
		essentials.getCommand("sethome").setExecutor(new SethomeCommand(essentials));
		essentials.getCommand("warp").setExecutor(new WarpCommand(essentials));
		essentials.getCommand("setwarp").setExecutor(new SetwarpCommand(essentials));
		essentials.getCommand("delwarp").setExecutor(new DelwarpCommand(essentials));
		essentials.getCommand("spawn").setExecutor(new SpawnCommand(essentials));
		essentials.getCommand("setspawn").setExecutor(new SetspawnCommand(essentials));
		essentials.getCommand("kick").setExecutor(new KickCommand(essentials));
		essentials.getCommand("ban").setExecutor(new BanCommand(essentials));
		essentials.getCommand("unban").setExecutor(new PardonCommand(essentials));
		essentials.getCommand("mute").setExecutor(new MuteCommand(essentials));
		essentials.getCommand("unmute").setExecutor(new UnmuteCommand(essentials));
		StaffCommand sc = new StaffCommand();
		essentials.getCommand("punishfor").setExecutor(sc);
		essentials.getCommand("punish").setExecutor(sc);
		essentials.getCommand("kick").setExecutor(sc);
		essentials.getCommand("ban").setExecutor(sc);
		essentials.getCommand("mute").setExecutor(sc);
	}
}
