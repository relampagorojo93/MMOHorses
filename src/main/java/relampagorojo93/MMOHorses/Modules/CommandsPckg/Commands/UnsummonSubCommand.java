package relampagorojo93.MMOHorses.Modules.CommandsPckg.Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.SubCommand;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.SpigotMessages.Instances.ClickEvent;
import relampagorojo93.LibsCollection.SpigotMessages.Instances.ClickEvent.Action;
import relampagorojo93.LibsCollection.SpigotMessages.Instances.TextReplacement;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingString;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;

public class UnsummonSubCommand extends SubCommand {
	public UnsummonSubCommand(Command command) {
		super(command, "unsummon", SettingString.HORSE_UNSUMMON_NAME.toString(), SettingString.HORSE_UNSUMMON_PERMISSION.toString(),
				SettingString.HORSE_UNSUMMON_DESCRIPTION.toString(), SettingString.HORSE_UNSUMMON_PARAMETERS.toString(),
				SettingList.HORSE_UNSUMMON_ALIASES.toList());
	}

	@Override
	public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
		switch (args.length) {
			case 1: return Arrays.asList("Confirm");
			default: return new ArrayList<>();
		}
	}

	@Override
	public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
		Player p = sender instanceof Player ? (Player) sender : null;
		if (p != null) {
			if (args.length > 1 && args[1].equalsIgnoreCase("confirm")) {
				for (ClaimedData horse : MMOHorsesAPI.getHorseModule().getHorseObjects()) {
					if (horse.getMMOHorse() != null) {
						if (horse.getSettings().isLinked())
							horse.getSettings().setLink(false);
						horse.getMMOHorse().superDie();
						horse.setMMOHorse(null);
					}
				}
				MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.UNLINKEDHORSES.toString())).sendMessage(sender);
			} else
				MessagesUtils.getMessageBuilder().createMessage(
						new TextReplacement[] { new TextReplacement("{button}", () -> "[✓]",
								new ClickEvent(Action.RUN_COMMAND,
										"/horse " + SettingString.HORSE_UNSUMMON_NAME.toString() + " confirm")) },
						true, "&eAre you sure? &a&l{button}").sendMessage(sender);
		} else
			MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.CONSOLEDENIED)).sendMessage(sender);
		return true;
	}
}
