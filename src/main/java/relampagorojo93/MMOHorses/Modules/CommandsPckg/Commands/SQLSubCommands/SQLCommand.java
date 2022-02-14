package relampagorojo93.MMOHorses.Modules.CommandsPckg.Commands.SQLSubCommands;

import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.MMOHorses.Modules.CommandsPckg.Base.HelpSubCommand;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingString;

public class SQLCommand extends Command {
	public SQLCommand(Command command) {
		super(command, "sql", SettingString.HORSESQL_NAME.toString(), SettingString.HORSESQL_PERMISSION.toString(),
				SettingString.HORSESQL_DESCRIPTION.toString(), SettingString.HORSESQL_PARAMETERS.toString(),
				SettingList.HORSESQL_ALIASES.toList());
		addCommand(new ParseFromSQLiteSubCommand(this));
		addCommand(new ParseFromMySQLSubCommand(this));
		addCommand(new ClearSubCommand(this));
		addCommand(new UnlinkSubCommand(this));
		sortCommands();
		addCommand(new HelpSubCommand(this), 0);
	}
}