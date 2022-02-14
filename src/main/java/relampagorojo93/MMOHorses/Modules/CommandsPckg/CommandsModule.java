package relampagorojo93.MMOHorses.Modules.CommandsPckg;

import org.bukkit.command.Command;

import relampagorojo93.LibsCollection.SpigotCommands.CommandsUtils;
import relampagorojo93.LibsCollection.SpigotPlugin.LoadOn;
import relampagorojo93.LibsCollection.SpigotPlugin.PluginModule;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Modules.CommandsPckg.Commands.HorseCommand;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;

public class CommandsModule extends PluginModule {
	
	public boolean load() {
		if (SettingBoolean.COMMAND_HORSE_ENABLE.toBoolean())
			horse = CommandsUtils.registerCommand(MMOHorsesAPI.getPlugin(), chorse = new HorseCommand());
		return true;
	}

	@Override
	public boolean unload() {
		if (this.horse != null)
			CommandsUtils.unregisterCommand(MMOHorsesAPI.getPlugin(), this.horse);
		horse = null;
		chorse = null;
		return true;
	}

	@Override
	public LoadOn loadOn() {
		return LoadOn.ENABLE;
	}

	@Override
	public boolean optional() {
		return true;
	}

	@Override
	public boolean allowReload() {
		return true;
	}

	private HorseCommand chorse;
	private Command horse;
	
	public HorseCommand getHorseCommand() {
		return chorse;
	}
	
}
