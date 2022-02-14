package relampagorojo93.MMOHorses.Modules.CommandsPckg.Commands.SQLSubCommands;

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

public class ClearSubCommand extends SubCommand {
	public ClearSubCommand(Command command) {
		super(command, "clear", SettingString.HORSESQL_CLEAR_NAME.toString(),
				SettingString.HORSESQL_CLEAR_PERMISSION.toString(), SettingString.HORSESQL_CLEAR_DESCRIPTION.toString(),
				SettingString.HORSESQL_CLEAR_PARAMETERS.toString(), SettingList.HORSESQL_CLEAR_ALIASES.toList());
	}

	@Override
	public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
		switch (args.length) {
		case 1:
			return Arrays.asList("Horses", "Items", "All");
		case 2:
			return Arrays.asList("Confirm");
		default:
			return new ArrayList<>();
		}
	}

	@Override
	public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
		Player p = sender instanceof Player ? (Player) sender : null;
		if (p != null) {
			if (args.length > 1) {
				String choice = args[1].toLowerCase();
				if (!choice.equals("horses") && !choice.equals("items") && !choice.equals("all")) {
					MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(getUsage())).sendMessage(sender);
					return true;
				}
				if (args.length > 2 && args[2].equalsIgnoreCase("confirm")) {
					switch (args[1].toLowerCase()) {
					case "horses":
						for (ClaimedData h : MMOHorsesAPI.getHorseModule().getHorseObjects())
							MMOHorsesAPI.getHorseModule().deleteHorseObject(h);
						MMOHorsesAPI.getSQLModule().deleteHorses();
						MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.CLEAREDHORSES.toString())).sendMessage(sender);
						break;
					case "items":
						MMOHorsesAPI.getSQLModule().deleteItems();
						MMOHorsesAPI.getCraftModule().load();
						MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.CLEAREDITEMS.toString())).sendMessage(sender);
						break;
					case "all":
						for (ClaimedData h : MMOHorsesAPI.getHorseModule().getHorseObjects())
							MMOHorsesAPI.getHorseModule().deleteHorseObject(h);
						MMOHorsesAPI.getSQLModule().deleteHorses();
						MMOHorsesAPI.getSQLModule().deleteItems();
						MMOHorsesAPI.getCraftModule().load();
						MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.CLEAREDALL.toString())).sendMessage(sender);
						break;
					default:
						break;
					}
				} else
					MessagesUtils.getMessageBuilder().createMessage(new TextReplacement[] { new TextReplacement("{button}", () -> "[✓]",
									new ClickEvent(Action.RUN_COMMAND,
											"/" + SettingString.HORSE_NAME.toString() + " "
													+ SettingString.HORSESQL_NAME.toString() + " " + getCommand() + " "
													+ choice + " confirm")) },
							true, "&eAre you sure? &a&l{button}");
			} else
				MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(getUsage())).sendMessage(sender);
		} else
			MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.CONSOLEDENIED)).sendMessage(sender);
		return true;
	}
}
