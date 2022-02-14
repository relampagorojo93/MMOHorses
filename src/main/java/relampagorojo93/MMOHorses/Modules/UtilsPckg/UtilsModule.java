package relampagorojo93.MMOHorses.Modules.UtilsPckg;

import java.util.UUID;

import relampagorojo93.LibsCollection.SpigotDebug.Data.DebugErrorData;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.SpigotPlugin.LoadOn;
import relampagorojo93.LibsCollection.SpigotPlugin.PluginModule;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.MMOHorse;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingInt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

public class UtilsModule extends PluginModule {

	@Override
	public boolean load() {
		return true;
	}

	@Override
	public boolean unload() {
		return true;
	}

	@Override
	public LoadOn loadOn() {
		return LoadOn.BEFORE_LOAD;
	}

	@Override
	public boolean optional() {
		return false;
	}

	@Override
	public boolean allowReload() {
		return false;
	}

	public final String VERSION = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
	
	public Particle getParticle(String oldname, String newname) {
		try {
			return Particle.valueOf(oldname);
		} catch (IllegalArgumentException e) {
			try {
				return Particle.valueOf(newname);
			} catch (IllegalArgumentException e2) {
				return null;
			}
		}
	}

	public boolean isSame(UUID n, UUID m) {
		return n.toString().equals(m.toString());
	}

	public String generateBar(double d) {
		return MessagesUtils.generateBar(d, SettingInt.BARLENGTH.toInt(), ChatColor.YELLOW);
	}

	public String generateFoodBar(double d) {
		return MessagesUtils.generateBar(d, SettingInt.BARLENGTH.toInt(), ChatColor.GOLD);
	}

	public int getPermissionValue(String prefix, Player pl) {
		int i = 0;
		for (PermissionAttachmentInfo perm : pl.getEffectivePermissions()) {
			String p = perm.getPermission().toLowerCase();
			if (perm.getValue() && p.startsWith(prefix))
				try {
					int q = Integer.parseInt(p.substring(prefix.length()));
					if (q > i)
						i = q;
				} catch (NumberFormatException e) {
					MMOHorsesAPI.getDebugController().addDebugData(new DebugErrorData("There's something wrong with "
									+ pl.getName() + "'s permission: " + perm.getPermission()));
				}
		}
		return i;
	}

	public String locParse(Location l) {
		return String.valueOf(l.getWorld().getName()) + ":" + l.getBlockX() + ":" + l.getBlockY() + ":" + l.getBlockZ();
	}

	public Location locParse(String l) {
		String[] loc = l.split(":");
		return new Location(Bukkit.getWorld(loc[0]), Integer.parseInt(loc[1]), Integer.parseInt(loc[2]),
				Integer.parseInt(loc[3]));
	}

	public double jumpToBlock(double d) {
		return Math.pow(d / 0.3846D, 1.7179178835251676D);
	}

	public double blockToJump(double d) {
		return 0.3846D * Math.pow(d, 0.5821D);
	}

	public double speedToBlock(double d) {
		return 43.178D * d - 0.02141D;
	}

	public double blockToSpeed(double d) {
		return (-d - 0.02141D) / -43.178D;
	}

	public void randomParticles(Particle p, Location l, int a, double rd) {
		for (int i = 0; i < a; i++)
			l.getWorld().spawnParticle(p, l, 1, rd / 2.0D, rd / 2.0D, rd / 2.0D, 0.0D);
	}

	public Sound getSound(String oldname, String newname) {
		try {
			return Sound.valueOf(oldname);
		} catch (IllegalArgumentException e) {
			try {
				return Sound.valueOf(newname);
			} catch (IllegalArgumentException e2) {
				return null;
			}
		}
	}

	public String applyPrefix(MessageString msg) {
		return MessageString.PREFIX + " " + msg;
	}

	public String applyPrefix(String msg) {
		return MessageString.PREFIX + " " + msg;
	}

	public MMOHorse toMMOHorse(Entity ent) {
		try {
			Object ce = Class.forName("org.bukkit.craftbukkit." + VERSION + ".entity.CraftEntity").cast(ent);
			return (MMOHorse) ce.getClass().getMethod("getHandle", new Class[0]).invoke(ce, new Object[0]);
		} catch (Exception e) {
			return null;
		}
	}
}