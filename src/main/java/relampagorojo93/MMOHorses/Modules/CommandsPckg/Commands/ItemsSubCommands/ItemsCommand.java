package relampagorojo93.MMOHorses.Modules.CommandsPckg.Commands.ItemsSubCommands;

import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.MMOHorses.Modules.CommandsPckg.Base.HelpSubCommand;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingString;

public class ItemsCommand extends Command {
	public ItemsCommand(Command command) {
		super(command, "items", SettingString.HORSEITEMS_NAME.toString(), SettingString.HORSEITEMS_PERMISSION.toString(),
				SettingString.HORSEITEMS_DESCRIPTION.toString(), SettingString.HORSEITEMS_PARAMETERS.toString(),
				SettingList.HORSEITEMS_ALIASES.toList());
		addCommand(new AddSubCommand(this));
		addCommand(new GetSubCommand(this));
		addCommand(new ListSubCommand(this));
		addCommand(new ExportSubCommand(this));
		addCommand(new ImportSubCommand(this));
		sortCommands();
		addCommand(new HelpSubCommand(this), 0);
	}
}