package relampagorojo93.MMOHorses.API.Hooks;

import org.bukkit.Bukkit;

import relampagorojo93.LibsCollection.SpigotDebug.Data.DebugAlertData;
import relampagorojo93.LibsCollection.SpigotDebug.Data.DebugLogData;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.API.Hooks.PlaceholderAPIObjects.PlaceHolder;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;

public class PlaceholderAPI {
	private static boolean hooked = false;

	public PlaceholderAPI() {
		hooked = false;
		if (SettingBoolean.PLACEHOLDERAPI.toBoolean()) {
			MMOHorsesAPI.getDebugController()
					.addDebugData(new DebugLogData("<PlaceholderAPI> Hook is enabled. Finding PlaceholderAPI."));
			if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
				MMOHorsesAPI.getDebugController().addDebugData(
						new DebugLogData("<PlaceholderAPI> PlaceholderAPI found. Creating pladeholders."));
				new PlaceHolder().register();
				MMOHorsesAPI.getDebugController().addDebugData(new DebugLogData("<PlaceholderAPI> Done!"));
				hooked = true;
			} else
				MMOHorsesAPI.getDebugController().addDebugData(
						new DebugAlertData("<PlaceholderAPI> PlaceholderAPI not found. Ignoring its implementation."));
		} else
			MMOHorsesAPI.getDebugController().addDebugData(
					new DebugAlertData("<PlaceholderAPI> Hook is disabled. Ignoring its implementation."));
	}

	public static boolean isHooked() {
		return hooked;
	}
}
