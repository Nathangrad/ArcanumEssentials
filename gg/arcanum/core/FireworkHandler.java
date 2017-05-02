package gg.arcanum.core;

import org.bukkit.Color;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public abstract class FireworkHandler
{
  public FireworkHandler() {}
  
  public static void spawnFirework(org.bukkit.entity.Player p)
  {
    Plugin core = Core.getPlugin();
    org.bukkit.entity.Firework firework = (org.bukkit.entity.Firework)p.getWorld().spawnEntity(p.getLocation(), org.bukkit.entity.EntityType.FIREWORK);
    String fireworkTypeStr = core.getConfig().get("firework.type").toString();
    String str1;
    FireworkEffect.Type fireworkType; switch ((str1 = fireworkTypeStr).hashCode()) {case -773035813:  if (str1.equals("BALL_LARGE")) {} break; case 2031103:  if (str1.equals("BALL")) break; break; case 2555474:  if (str1.equals("STAR")) {} break; case 63566080:  if (str1.equals("BURST")) {} break; case 1746652494:  if (!str1.equals("CREEPER")) {
        break label205;
        FireworkEffect.Type fireworkType = FireworkEffect.Type.BALL;
        
        break label210;
        FireworkEffect.Type fireworkType = FireworkEffect.Type.BALL_LARGE;
        
        break label210;
        FireworkEffect.Type fireworkType = FireworkEffect.Type.BURST;
        break label210;
      } else {
        FireworkEffect.Type fireworkType = FireworkEffect.Type.CREEPER;
        
        break label210;
        fireworkType = FireworkEffect.Type.STAR; }
      break; }
    label205:
    FireworkEffect.Type fireworkType = FireworkEffect.Type.BALL;
    
    label210:
    boolean fireworkFlicker = core.getConfig().getBoolean("firework.flicker");
    boolean fireworkTrail = core.getConfig().getBoolean("firework.trail");
    Color fireworkColour = Color.fromRGB(core.getConfig().getInt("firework.colour"));
    Color fireworkFade = Color.fromRGB(core.getConfig().getInt("firework.fade"));
    int fireworkPower = core.getConfig().getInt("firework.power");
    org.bukkit.inventory.meta.FireworkMeta fireworkMeta = firework.getFireworkMeta();
    org.bukkit.FireworkEffect effect = org.bukkit.FireworkEffect.builder().flicker(fireworkFlicker).withColor(fireworkColour)
      .withFade(fireworkFade).with(fireworkType).trail(fireworkTrail).build();
    fireworkMeta.addEffect(effect);
    fireworkMeta.setPower(fireworkPower);
    firework.setFireworkMeta(fireworkMeta);
  }
}
