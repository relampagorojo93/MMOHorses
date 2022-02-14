package relampagorojo93.MMOHorses.Modules.CommandsPckg.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.SubCommand;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingString;

public class InfoSubCommand extends SubCommand {
	public InfoSubCommand(Command command) {
		super(command, "info", SettingString.HORSE_INFO_NAME.toString(), SettingString.HORSE_INFO_PERMISSION.toString(),
				SettingString.HORSE_INFO_DESCRIPTION.toString(), "",
				SettingList.HORSE_INFO_ALIASES.toList());
	}

	@Override
	public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
		return new ArrayList<>();
	}

	@Override
	public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
		MessagesUtils.getMessageBuilder().createMessage(MessagesUtils.color("")).sendMessage(sender);
		MessagesUtils.getMessageBuilder()
				.createMessage(MessagesUtils.color("&8&l>>>>>>-------------------------------<<<<<<"))
				.sendMessage(sender);
		MessagesUtils.getMessageBuilder().createMessage(MessagesUtils.color("")).sendMessage(sender);
		MessagesUtils.getMessageBuilder()
				.createMessage(MessagesUtils
						.color("&6  Plugin name:&7 " + MMOHorsesAPI.getPlugin().getDescription().getName()))
				.sendMessage(sender);
		MessagesUtils.getMessageBuilder()
				.createMessage(MessagesUtils
						.color("&6  Author:&7 " + MMOHorsesAPI.getPlugin().getDescription().getAuthors().get(0)))
				.sendMessage(sender);
		MessagesUtils.getMessageBuilder()
				.createMessage(
						MessagesUtils.color("&6  Version:&7 " + MMOHorsesAPI.getPlugin().getDescription().getVersion()))
				.sendMessage(sender);
		MessagesUtils.getMessageBuilder().createMessage(MessagesUtils.color("")).sendMessage(sender);
		MessagesUtils.getMessageBuilder()
				.createMessage(MessagesUtils.color("&8&l>>>>>>-------------------------------<<<<<<"))
				.sendMessage(sender);
		return true;
	}
}
