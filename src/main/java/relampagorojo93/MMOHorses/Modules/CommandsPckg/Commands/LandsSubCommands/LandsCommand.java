package relampagorojo93.MMOHorses.Modules.CommandsPckg.Commands.LandsSubCommands;

import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.MMOHorses.Modules.CommandsPckg.Base.HelpSubCommand;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingString;

public class LandsCommand extends Command {
	public LandsCommand(Command command) {
		super(command, "lands", SettingString.HORSELANDS_NAME.toString(), SettingString.HORSELANDS_PERMISSION.toString(),
				SettingString.HORSELANDS_DESCRIPTION.toString(), SettingString.HORSELANDS_PARAMETERS.toString(),
				SettingList.HORSELANDS_ALIASES.toList());
		addCommand(new SetRestrictionSubCommand(this));
		sortCommands();
		addCommand(new HelpSubCommand(this), 0);
	}
}