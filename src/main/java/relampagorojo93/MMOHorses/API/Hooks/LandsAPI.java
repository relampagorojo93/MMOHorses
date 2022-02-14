package relampagorojo93.MMOHorses.API.Hooks;

import java.util.HashMap;

import org.bukkit.entity.Player;

import me.angeschossen.lands.api.integration.LandsIntegration;
import me.angeschossen.lands.api.land.Land;
import me.angeschossen.lands.api.land.LandArea;
import relampagorojo93.LibsCollection.SpigotDebug.Data.DebugAlertData;
import relampagorojo93.LibsCollection.SpigotDebug.Data.DebugLogData;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Enums.WalkMode;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;

public class LandsAPI {
	private static boolean hooked = false;
	private static LandsIntegration addon = null;
	private static HashMap<LandArea, WalkMode> arearestrictions = null;
	private static HashMap<Land, WalkMode> landrestrictions = null;

	public LandsAPI() {
		try {
			if (SettingBoolean.LANDS.toBoolean()) {
				MMOHorsesAPI.getDebugController().addDebugData(new DebugLogData("<Lands> Hook is enabled. Trying to create a new addon."));
				addon = new LandsIntegration(MMOHorsesAPI.getPlugin(), false);
				if (addon != null) {
					MMOHorsesAPI.getDebugController().addDebugData(new DebugLogData("<Lands> Done!"));
					arearestrictions = new HashMap<>();
					landrestrictions = new HashMap<>();
					hooked = true;
				} else
					MMOHorsesAPI.getDebugController().addDebugData(new DebugAlertData("<Lands> Lands not found. Ignoring its implementation."));
			} else
				MMOHorsesAPI.getDebugController().addDebugData(new DebugAlertData("<Lands> Hook is disabled. Ignoring its implementation."));

		} catch (NoClassDefFoundError e) {
			MMOHorsesAPI.getDebugController().addDebugData(new DebugAlertData("<Lands> Lands has not been found. Excluding Lands implementation"));
		}
	}

	public static WalkMode checkRestriction(Player player) {
		LandArea area = addon.getArea(player.getLocation());
		if (area != null && arearestrictions.containsKey(area))
			return arearestrictions.get(area);
		Land land = addon.getLand(player.getLocation());
		if (land != null && landrestrictions.containsKey(land))
			return landrestrictions.get(land);
		return null;
	}

	public static void setRestriction(LandArea area, WalkMode walk) {
		if (walk != null)
			arearestrictions.put(area, walk);
		else
			arearestrictions.remove(area);
	}

	public static void setRestriction(Land land, WalkMode walk) {
		if (walk != null)
			landrestrictions.put(land, walk);
		else
			landrestrictions.remove(land);
	}

	public static LandsIntegration getAddon() {
		return addon;
	}

	public static boolean isHooked() {
		return hooked;
	}
}
