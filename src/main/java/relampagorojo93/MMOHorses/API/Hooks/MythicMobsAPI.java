package relampagorojo93.MMOHorses.API.Hooks;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;

import io.lumine.xikage.mythicmobs.MythicMobs;
import relampagorojo93.LibsCollection.SpigotDebug.Data.DebugAlertData;
import relampagorojo93.LibsCollection.SpigotDebug.Data.DebugLogData;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;

public class MythicMobsAPI implements Listener {
	private static boolean hooked = false;

	public MythicMobsAPI() {
		try {
			if (SettingBoolean.MYTHICMOBS.toBoolean()) {
				MMOHorsesAPI.getDebugController()
						.addDebugData(new DebugLogData("<MythicMobs> Hook is enabled. Trying to get the API."));
				if (Bukkit.getPluginManager().isPluginEnabled("MythicMobs")) {
					MMOHorsesAPI.getDebugController().addDebugData(new DebugLogData("<MythicMobs> Done!"));
					hooked = true;
				} else
					MMOHorsesAPI.getDebugController().addDebugData(
							new DebugAlertData("<MythicMobs> MythicMobs not found. Ignoring its implementation."));
			} else
				MMOHorsesAPI.getDebugController().addDebugData(
						new DebugAlertData("<MythicMobs> Hook is disabled. Ignoring its implementation."));
		} catch (NoClassDefFoundError e) {
			MMOHorsesAPI.getDebugController().addDebugData(new DebugAlertData(
					"<MythicMobs> MythicMobs has not been found. Excluding MythicMobs implementation"));
		}
	}

	public static boolean isMythicMob(Entity ent) {
		return MythicMobs.inst().getAPIHelper().isMythicMob(ent);
	}

	public static boolean isMythicMob(UUID uuid) {
		return MythicMobs.inst().getAPIHelper().isMythicMob(uuid);
	}

	public static boolean isHooked() {
		return hooked;
	}
}
