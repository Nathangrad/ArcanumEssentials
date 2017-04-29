package gg.arcanum.core;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;

public abstract class FireworkHandler {

	public static void spawnFirework(Player p) {
		Plugin core = Core.getPlugin();
		Firework firework = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
		String fireworkTypeStr = core.getConfig().get("firework.type").toString();
		Type fireworkType;
		switch (fireworkTypeStr) {
		case "BALL":
			fireworkType = Type.BALL;
			break;
		case "BALL_LARGE":
			fireworkType = Type.BALL_LARGE;
			break;
		case "BURST":
			fireworkType = Type.BURST;
			break;
		case "CREEPER":
			fireworkType = Type.CREEPER;
			break;
		case "STAR":
			fireworkType = Type.STAR;
			break;
		default:
			fireworkType = Type.BALL;
			break;
		}
		boolean fireworkFlicker = core.getConfig().getBoolean("firework.flicker");
		boolean fireworkTrail = core.getConfig().getBoolean("firework.trail");
		Color fireworkColour = Color.fromRGB(core.getConfig().getInt("firework.colour"));
		Color fireworkFade = Color.fromRGB(core.getConfig().getInt("firework.fade"));
		int fireworkPower = core.getConfig().getInt("firework.power");
		FireworkMeta fireworkMeta = firework.getFireworkMeta();
		FireworkEffect effect = FireworkEffect.builder().flicker(fireworkFlicker).withColor(fireworkColour)
				.withFade(fireworkFade).with(fireworkType).trail(fireworkTrail).build();
		fireworkMeta.addEffect(effect);
		fireworkMeta.setPower(fireworkPower);
		firework.setFireworkMeta(fireworkMeta);
	}
}
