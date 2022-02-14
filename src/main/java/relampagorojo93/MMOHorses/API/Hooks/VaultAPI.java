package relampagorojo93.MMOHorses.API.Hooks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;
import relampagorojo93.LibsCollection.SpigotDebug.Data.DebugAlertData;
import relampagorojo93.LibsCollection.SpigotDebug.Data.DebugLogData;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;

public class VaultAPI {
	private static boolean hooked = false;
	private static Economy econ = null;

	public VaultAPI() {
		if (SettingBoolean.VAULT.toBoolean()) {
			MMOHorsesAPI.getDebugController().addDebugData(new DebugLogData("<Vault> Hook is enabled. Finding Vault."));
			if (Bukkit.getServer().getPluginManager().getPlugin("Vault") != null) {
				MMOHorsesAPI.getDebugController().addDebugData(new DebugLogData("<Vault> Vault found. Trying to get the Economy API."));
				RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager()
						.getRegistration(Economy.class);
				if (rsp != null) {
					econ = rsp.getProvider();
					MMOHorsesAPI.getDebugController().addDebugData(new DebugLogData("<Vault> Done!"));
					hooked = true;
				} else
					MMOHorsesAPI.getDebugController().addDebugData(new DebugAlertData("<Vault> Economy API not initialized. Ignoring its implementation."));
			} else
				MMOHorsesAPI.getDebugController().addDebugData(new DebugAlertData("<Vault> Vault not found. Ignoring its implementation."));
		} else
			MMOHorsesAPI.getDebugController().addDebugData(new DebugAlertData("<Vault> Hook is disabled. Ignoring its implementation."));
	}

	public static boolean isHooked() {
		return hooked;
	}

	public static Economy getEconomy() {
		return econ;
	}
}
