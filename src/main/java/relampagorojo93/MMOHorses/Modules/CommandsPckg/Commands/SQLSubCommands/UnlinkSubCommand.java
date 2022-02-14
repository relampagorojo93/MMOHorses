package relampagorojo93.MMOHorses.Modules.CommandsPckg.Commands.SQLSubCommands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.SubCommand;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.SpigotMessages.Instances.ClickEvent;
import relampagorojo93.LibsCollection.SpigotMessages.Instances.TextReplacement;
import relampagorojo93.LibsCollection.SpigotMessages.Instances.ClickEvent.Action;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingString;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;

public class UnlinkSubCommand extends SubCommand {
	public UnlinkSubCommand(Command command) {
		super(command, "unlink", SettingString.HORSESQL_UNLINK_NAME.toString(),
				SettingString.HORSESQL_UNLINK_PERMISSION.toString(),
				SettingString.HORSESQL_UNLINK_DESCRIPTION.toString(), SettingString.HORSESQL_UNLINK_PARAMETERS.toString(),
				SettingList.HORSESQL_UNLINK_ALIASES.toList());
	}

	@Override
	public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
		switch (args.length) {
		case 1:
			return Arrays.asList("Confirm");
		default:
			return new ArrayList<>();
		}
	}

	@Override
	public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
		Player p = sender instanceof Player ? (Player) sender : null;
		if (p != null) {
			if (args.length > 1 && args[1].equalsIgnoreCase("confirm")) {
				for (ClaimedData ho : MMOHorsesAPI.getHorseModule().getHorseObjects())
					ho.getSettings().setLink(false);
				MMOHorsesAPI.getSQLModule().unlinkHorses();
				for (Player pl : Bukkit.getOnlinePlayers())
					MMOHorsesAPI.getHorseModule().loadHorseObjects(pl.getUniqueId(), true);
				MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.UNLINKEDHORSES.toString())).sendMessage(sender);
			} else
				MessagesUtils.getMessageBuilder().createMessage(
						new TextReplacement[] { new TextReplacement("{button}", () -> "[✓]",
								new ClickEvent(Action.RUN_COMMAND,
										"/" + SettingString.HORSE_NAME.toString() + " " + SettingString.HORSESQL_NAME.toString() + " " + getCommand() + " confirm")) },
						true, "&eAre you sure? &a&l{button}").sendMessage(sender);
		} else
			MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.CONSOLEDENIED)).sendMessage(sender);
		return true;
	}
}
