package relampagorojo93.MMOHorses;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.SpigotPlugin.MainClass;
import relampagorojo93.LibsCollection.Utils.Bukkit.TasksUtils;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.ConnectionData.MySQLConnectionData;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.ConnectionData.SQLiteConnectionData;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.API.Hooks.CitizensAPI;
import relampagorojo93.MMOHorses.API.Hooks.EIOpenerAPI;
import relampagorojo93.MMOHorses.API.Hooks.LandsAPI;
import relampagorojo93.MMOHorses.API.Hooks.MythicMobsAPI;
import relampagorojo93.MMOHorses.API.Hooks.PlaceholderAPI;
import relampagorojo93.MMOHorses.API.Hooks.VaultAPI;
import relampagorojo93.MMOHorses.API.Hooks.WorldGuardAPI;
import relampagorojo93.MMOHorses.Bukkit.Events.*;
import relampagorojo93.MMOHorses.Modules.CommandsPckg.CommandsModule;
import relampagorojo93.MMOHorses.Modules.CraftPckg.CraftModule;
import relampagorojo93.MMOHorses.Modules.CraftPckg.Objects.CraftableItem;
import relampagorojo93.MMOHorses.Modules.CustomPckg.CustomModule;
import relampagorojo93.MMOHorses.Modules.EntityPckg.EntityModule;
import relampagorojo93.MMOHorses.Modules.FilePckg.FileModule;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingInt;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingString;
import relampagorojo93.MMOHorses.Modules.HorsePckg.HorseModule;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;
import relampagorojo93.MMOHorses.Modules.LinksPckg.LinksModule;
import relampagorojo93.MMOHorses.Modules.SQLPckg.*;
import relampagorojo93.MMOHorses.Modules.UtilsPckg.UtilsModule;

public class MMOHorses extends MainClass {
	

	//---------------------------------------------------------------//
	//MainClass methods
	//---------------------------------------------------------------//
	
	public MMOHorses() {
		super(
				new UtilsModule(),
				new FileModule(),
				new SQLModule(),
				new CommandsModule(),
				new EntityModule(),
				new CraftModule(),
				new CustomModule(),
				new HorseModule(),
				new LinksModule()
				);
	}

	@Override
	public String getPrefix() {
		return MessageString.PREFIX.toString();
	}

	@Override
	public boolean canLoad() {
		if (!this.getUtilsModule().VERSION.matches("^v1_(8_R3|12_R1|16_R3|17_R1)$")) {
			MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix("This server version is not compatible with MMOHorses!")).sendMessage(Bukkit.getConsoleSender());;
			return false;
		}
		if (getSQLModule().isConnected() || getSQLModule().connect(SettingBoolean.SQL.toBoolean()
				? new MySQLConnectionData(SettingString.PROTOCOL.toString(), SettingString.HOST.toString(),
						SettingInt.PORT.toInt(), SettingString.DATABASE.toString(), SettingString.USERNAME.toString(),
						SettingString.PASSWORD.toString(), SettingString.PARAMETERS.toString().split("&"))
				: new SQLiteConnectionData(
						String.valueOf(getFileModule().PLUGIN_FOLDER.getPath()) + "/DB.sqlite")))
			return true;
		return false;
	}

	@Override
	public boolean canEnable() {
		return true;
	}

	@Override
	public boolean load() {
		if (isFirstTime()) {
			new WorldGuardAPI();
			new PlaceholderAPI();
		}
		return true;
	}

	@Override
	public boolean enable() {
		if (isFirstTime()) {
			Bukkit.getPluginManager().registerEvents(new HorseEvents(), this);
			Bukkit.getPluginManager().registerEvents(new InventoryEvents(), this);
			Bukkit.getPluginManager().registerEvents(new PlayerEvents(), this);
			Bukkit.getPluginManager().registerEvents(new ChatEvents(), this);
			TasksUtils.execute(this, () -> {
				for (String l : getLinksModule().getLocations())
					MMOHorsesAPI.getUtils().locParse(l).getChunk().load();
			});
			TasksUtils.execute(this, () -> {
				for (CraftableItem ci : getCraftModule().getCraftableItems()) {
					ci.unloadRecipe();
					ci.loadRecipe();
				}
			});
			TasksUtils.executeWithTimer(this, () -> getLinksModule().saveLinks(),
					SettingInt.AUTOSAVECOOLDOWN.toInt() * 60 * 20, SettingInt.AUTOSAVECOOLDOWN.toInt() * 60 * 20, true);
			TasksUtils.executeWithTimer(this, () -> {
				if (SettingInt.DESPAWNAFTERINACTIVITY.toInt() > 0)
					for (ClaimedData ho : getHorseModule().getHorseObjects()) {
						if (ho.getMMOHorse() != null
								&& (System.currentTimeMillis() - ho.getMMOHorse().getMMOHorseData().getLastActivity())
										/ 60000L >= SettingInt.DESPAWNAFTERINACTIVITY.toInt()) {
							ho.getSettings().setLink(false);
							if (ho.getMMOHorse().getLocation().getChunk().isLoaded())
								ho.getMMOHorse().getLocation().getChunk().load();
							ho.getMMOHorse().superDie();
							ho.setMMOHorse(null);
						}
					}
			}, 100L, 100L);
			new MetricsLite(this, 10083);
		}
		MessagesUtils.getMessageBuilder().createMessage(
				getPrefix() + "",
				getPrefix() + "                                     __                    ",
				getPrefix() + "                       _            |  |                   ",
				getPrefix() + "                     _| |___ ___ ___|  |                   ",
				getPrefix() + "                    | . | . |   | -_|__|                   ",
				getPrefix() + "                    |___|___|_|_|___|__|                   ",
				getPrefix() + ""
				).sendMessage(Bukkit.getConsoleSender());
		return true;
	}

	@Override
	public boolean disable() {
		for (Player pl : Bukkit.getOnlinePlayers())
			if (pl.isInsideVehicle() && getUtilsModule().toMMOHorse(pl.getVehicle()) != null)
				pl.leaveVehicle();
		MessagesUtils.getMessageBuilder().createMessage(
				getPrefix() + "",
				getPrefix() + "                                            __             ",
				getPrefix() + "                      _           _       _|  |            ",
				getPrefix() + "              _ _ ___| |___ ___ _| |___ _| |  |            ",
				getPrefix() + "             | | |   | | . | .'| . | -_| . |__|            ",
				getPrefix() + "             |___|_|_|_|___|__,|___|___|___|__|            ",
				getPrefix() + ""
				).sendMessage(Bukkit.getConsoleSender());
		return true;
	}

	@Override
	public boolean beforeLoad() {
		if (isFirstTime()) {
			new MMOHorsesAPI(this);
			this.utils = new UtilsModule();
		}
		return true;
	}

	@Override
	public boolean beforeEnable() {
		MessagesUtils.getMessageBuilder().createMessage(
				getPrefix() + "",
				getPrefix() + "        _____ _____ _____ _____                             ",
				getPrefix() + "       |     |     |     |  |  |___ ___ ___ ___ ___         ",
				getPrefix() + "       | | | | | | |  |  |     | . |  _|_ -| -_|_ -|        ",
				getPrefix() + "       |_|_|_|_|_|_|_____|__|__|___|_| |___|___|___|        ",
				getPrefix() + "                                                            ",
				getPrefix() + "               By relampagorojo93/DarkPanda73               ",
				getPrefix() + "                                                            ",
				getPrefix() + "                 _           _ _                            ",
				getPrefix() + "                | |___ ___ _| |_|___ ___                    ",
				getPrefix() + "                | | . | .'| . | |   | . |_ _ _              ",
				getPrefix() + "                |_|___|__,|___|_|_|_|_  |_|_|_|             ",
				getPrefix() + "                                    |___|                   ",
				getPrefix() + ""
				).sendMessage(Bukkit.getConsoleSender());
		if (isFirstTime()) {
			new CitizensAPI();
			new MythicMobsAPI();
			new LandsAPI();
			new VaultAPI();
			new EIOpenerAPI();
		}
		return true;
	}

	@Override
	public boolean beforeDisable() {
		return true;
	}

	//---------------------------------------------------------------//
	//Modules
	//---------------------------------------------------------------//
	
	private UtilsModule utils;
	
	public UtilsModule getUtilsModule() {
		return utils;
	}
	
	public FileModule getFileModule() {
		return (FileModule) getModule(FileModule.class);
	}
	
	public SQLModule getSQLModule() {
		return (SQLModule) getModule(SQLModule.class);
	}

	public CommandsModule getCommandsModule() {
		return (CommandsModule) getModule(CommandsModule.class);
	}

	public HorseModule getHorseModule() {
		return (HorseModule) getModule(HorseModule.class);
	}

	public CraftModule getCraftModule() {
		return (CraftModule) getModule(CraftModule.class);
	}

	public CustomModule getCustomModule() {
		return (CustomModule) getModule(CustomModule.class);
	}

	public LinksModule getLinksModule() {
		return (LinksModule) getModule(LinksModule.class);
	}

	public EntityModule getEntityModule() {
		return (EntityModule) getModule(EntityModule.class);
	}
	
}
