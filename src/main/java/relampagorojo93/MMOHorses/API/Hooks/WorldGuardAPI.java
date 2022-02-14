package relampagorojo93.MMOHorses.API.Hooks;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import relampagorojo93.LibsCollection.SpigotDebug.Data.DebugAlertData;
import relampagorojo93.LibsCollection.SpigotDebug.Data.DebugLogData;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.API.Hooks.WorldGuardObjects.SpeedLimitFlag;
import relampagorojo93.MMOHorses.API.Hooks.WorldGuardObjects.SummoningFlag;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;

public class WorldGuardAPI {
	private static boolean hooked = false;
	private static WorldGuardPlugin guardplugin = null;
	private static SpeedLimitFlag speedlimitflag = null;
	private static SummoningFlag summonflag = null;
	private static Object regioncontainer = null;

	public WorldGuardAPI() {
		try {
			if (SettingBoolean.WORLDGUARD.toBoolean()) {
				MMOHorsesAPI.getDebugController()
						.addDebugData(new DebugLogData("<WorldGuard> Hook is enabled. Finding WorldGuard."));
				guardplugin = WorldGuardPlugin.inst();
				if (guardplugin != null) {
					MMOHorsesAPI.getDebugController()
							.addDebugData(new DebugLogData("<WorldGuard> WorldGuard found. Creating flags."));
					speedlimitflag = new SpeedLimitFlag();
					summonflag = new SummoningFlag();
					MMOHorsesAPI.getDebugController().addDebugData(new DebugLogData("<WorldGuard> Done!"));
					hooked = true;
				} else
					MMOHorsesAPI.getDebugController().addDebugData(
							new DebugAlertData("<WorldGuard> WorldGuard not found. Ignoring its implementation."));
			} else
				MMOHorsesAPI.getDebugController().addDebugData(
						new DebugAlertData("<WorldGuard> Hook is disabled. Ignoring its implementation."));
		} catch (NoClassDefFoundError e) {
			MMOHorsesAPI.getDebugController().addDebugData(new DebugAlertData(
					"<WorldGuard> WorldGuard has not been found. Excluding WorldGuard implementation"));
		}
	}

	public static boolean isHooked() {
		return hooked;
	}

	public static SpeedLimitFlag getSpeedLimitFlag() {
		return speedlimitflag;
	}

	public static SummoningFlag getSummoningFlag() {
		return summonflag;
	}

	public static WorldGuardPlugin getWorldGuardPlugin() {
		return guardplugin;
	}

	public static Object getRegionContainer() {
		if (regioncontainer == null) {
			try {
				Object inst = Class.forName("com.sk89q.worldguard.WorldGuard").getMethod("getInstance").invoke(null);
				Object platform = inst.getClass().getMethod("getPlatform").invoke(inst);
				regioncontainer = platform.getClass().getMethod("getRegionContainer").invoke(platform);
			} catch (Exception e) {
				try {
					regioncontainer = guardplugin.getClass().getMethod("getRegionContainer").invoke(guardplugin);
				} catch (Exception e2) {
					return regioncontainer;
				}
			}
		}
		return regioncontainer;
	}
}
