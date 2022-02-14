package relampagorojo93.MMOHorses.Modules.CommandsPckg.Base;

import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.HelpCommand;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingString;

public class HelpSubCommand extends HelpCommand {
	
	public HelpSubCommand(Command command) {
		super(command, SettingString.HORSE_HELP_NAME.toString(), SettingString.HORSE_HELP_PERMISSION.toString(),
				SettingString.HORSE_HELP_DESCRIPTION.toString(), SettingString.HORSE_HELP_PARAMETERS.toString(),
				SettingList.HORSE_HELP_ALIASES.toList());
		this.setHeader(() -> MessageList.HELP_HEADER.toList()).setBody(() -> MessageList.HELP_BODY.toList())
				.setFooter(() -> MessageList.HELP_FOOT.toList())
				.setUnavailableLeftArrow(() -> MessageString.HELP_LEFTARROWUNAVAILABLE.toString())
				.setAvailableLeftArrow(() -> MessageString.HELP_LEFTARROWAVAILABLE.toString())
				.setUnavailableRightArrow(() -> MessageString.HELP_RIGHTARROWUNAVAILABLE.toString())
				.setAvailableRightArrow(() -> MessageString.HELP_RIGHTARROWAVAILABLE.toString());
	}

}