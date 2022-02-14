package relampagorojo93.MMOHorses.API.Hooks;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.citizensnpcs.api.event.NPCSpawnEvent;
import relampagorojo93.LibsCollection.SpigotDebug.Data.DebugAlertData;
import relampagorojo93.LibsCollection.SpigotDebug.Data.DebugLogData;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Enums.Type;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;

public class CitizensAPI implements Listener {
	private static boolean hooked = false;
	private static List<UUID> npcs;

	public CitizensAPI() {
		try {
			if (SettingBoolean.CITIZENS.toBoolean()) {
				MMOHorsesAPI.getDebugController()
						.addDebugData(new DebugLogData("<Citizens> Hook is enabled. Trying to get the API."));
				if (net.citizensnpcs.api.CitizensAPI.getPlugin() != null) {
					MMOHorsesAPI.getDebugController().addDebugData(new DebugLogData("<Citizens> Done!"));
					Bukkit.getPluginManager().registerEvents(this, MMOHorsesAPI.getPlugin());
					npcs = new ArrayList<>();
					hooked = true;
				} else
					MMOHorsesAPI.getDebugController().addDebugData(
							new DebugAlertData("<Citizens> Citizens not found. Ignoring its implementation."));
			} else
				MMOHorsesAPI.getDebugController()
						.addDebugData(new DebugAlertData("<Citizens> Hook is disabled. Ignoring its implementation."));

		} catch (NoClassDefFoundError e) {
			MMOHorsesAPI.getDebugController().addDebugData(
					new DebugAlertData("<Citizens> Citizens has not been found. Excluding Citizens implementation"));
		}
	}

	@EventHandler
	public void onSpawn(NPCSpawnEvent e) {
		if (Type.getByBukkit(e.getNPC().getEntity().getType()) != null)
			npcs.add(e.getNPC().getEntity().getUniqueId());
	}

	public static boolean isNPC(Entity ent) {
		if (npcs.contains(ent.getUniqueId()))
			return npcs.remove(ent.getUniqueId());
		else
			return ent.hasMetadata("NPC");
	}

	public static boolean isHooked() {
		return hooked;
	}
}
