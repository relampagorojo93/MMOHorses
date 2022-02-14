package relampagorojo93.MMOHorses.API;

import org.bukkit.plugin.java.JavaPlugin;

import relampagorojo93.LibsCollection.SpigotDebug.DebugController;
import relampagorojo93.MMOHorses.MMOHorses;
import relampagorojo93.MMOHorses.Modules.CraftPckg.CraftModule;
import relampagorojo93.MMOHorses.Modules.CustomPckg.CustomModule;
import relampagorojo93.MMOHorses.Modules.EntityPckg.EntityModule;
import relampagorojo93.MMOHorses.Modules.FilePckg.FileModule;
import relampagorojo93.MMOHorses.Modules.HorsePckg.HorseModule;
import relampagorojo93.MMOHorses.Modules.LinksPckg.LinksModule;
import relampagorojo93.MMOHorses.Modules.SQLPckg.SQLModule;
import relampagorojo93.MMOHorses.Modules.UtilsPckg.UtilsModule;

public class MMOHorsesAPI {
	private static MMOHorses plugin;

	public MMOHorsesAPI(MMOHorses pl) {
		plugin = pl;
	}

	public static JavaPlugin getPlugin() {
		return plugin;
	}

	public static UtilsModule getUtils() {
		return plugin.getUtilsModule();
	}

	public static SQLModule getSQLModule() {
		return plugin.getSQLModule();
	}

	public static FileModule getFileModule() {
		return plugin.getFileModule();
	}

	public static HorseModule getHorseModule() {
		return plugin.getHorseModule();
	}

	public static CraftModule getCraftModule() {
		return plugin.getCraftModule();
	}

	public static CustomModule getCustomModule() {
		return plugin.getCustomModule();
	}

	public static EntityModule getEntityModule() {
		return plugin.getEntityModule();
	}

	public static LinksModule getLinksModule() {
		return plugin.getLinksModule();
	}

	public static DebugController getDebugController() {
		return plugin.getDebugController();
	}

	public static void reloadPlugin() {
		plugin.reloadPlugin();
	}

	public static int getSQLVersion() {
		return 5;
	}

}