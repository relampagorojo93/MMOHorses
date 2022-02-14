package relampagorojo93.MMOHorses.API.Hooks;

import org.bukkit.Bukkit;

import relampagorojo93.EzInvOpener.API.EIOAPI;
import relampagorojo93.EzInvOpener.API.Objects.EzInventory;
import relampagorojo93.LibsCollection.SpigotDebug.Data.DebugAlertData;
import relampagorojo93.LibsCollection.SpigotDebug.Data.DebugLogData;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Economy.BuyInventory;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Horses.ListInventory;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;

public class EIOpenerAPI {
	private static boolean hooked = false;

	public EIOpenerAPI() {
		hooked = false;
		if (SettingBoolean.EIOPENER.toBoolean()) {
			MMOHorsesAPI.getDebugController().addDebugData(new DebugLogData("<EIOpener> Hook is enabled. Finding EzInvOpener."));
			if (Bukkit.getPluginManager().getPlugin("EzInvOpener") != null) {
				MMOHorsesAPI.getDebugController().addDebugData(new DebugLogData("<EIOpener> EzInvOpener found. Creating ez inventories."));
				EIOAPI.getInvAPI().registerInventory(new EzInventory(MMOHorsesAPI.getPlugin(), "list-menu", (player) -> ListInventory.getEzInventory(player)));
				EIOAPI.getInvAPI().registerInventory(new EzInventory(MMOHorsesAPI.getPlugin(), "shop-menu", (player) -> BuyInventory.getEzInventory(player)));
				MMOHorsesAPI.getDebugController().addDebugData(new DebugLogData("<EIOpener> Done!"));
				hooked = true;
			} else
				MMOHorsesAPI.getDebugController().addDebugData(new DebugAlertData("<EIOpener> EzInvOpener not found. Ignoring its implementation."));
		} else
			MMOHorsesAPI.getDebugController().addDebugData(new DebugAlertData("<EIOpener> Hook is disabled. Ignoring its implementation."));
	}

	public static boolean isHooked() {
		return hooked;
	}
}
