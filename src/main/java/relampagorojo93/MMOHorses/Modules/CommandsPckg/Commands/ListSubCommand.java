package relampagorojo93.MMOHorses.Modules.CommandsPckg.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.SubCommand;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Horses.ListInventory;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingString;

public class ListSubCommand extends SubCommand {
	public ListSubCommand(Command command) {
		super(command, "list", SettingString.HORSE_LIST_NAME.toString(), SettingString.HORSE_LIST_PERMISSION.toString(),
				SettingString.HORSE_LIST_DESCRIPTION.toString(), SettingString.HORSE_LIST_PARAMETERS.toString(),
				SettingList.HORSE_LIST_ALIASES.toList());
	}

	@Override
	public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
		List<String> list = new ArrayList<>();
		switch (args.length) {
			case 1: for (Player player:Bukkit.getOnlinePlayers()) list.add(player.getName()); break;
			default: break;
		
		}
		return list;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
		Player p = sender instanceof Player ? (Player) sender : null;
		if (p != null) {
			if (args.length > 1 && sender.hasPermission("Horse.Admin.List")) {
				OfflinePlayer other = Bukkit.getPlayer(args[1]);
				if (other == null)
					other = Bukkit.getOfflinePlayer(args[1]);
				if (other != null)
					new ListInventory(p, other, true).openInventory(MMOHorsesAPI.getPlugin());
				else
					new ListInventory(p, true).openInventory(MMOHorsesAPI.getPlugin());
			} else
				new ListInventory(p, false).openInventory(MMOHorsesAPI.getPlugin());
		} else
			MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.CONSOLEDENIED)).sendMessage(sender);
		return true;
	}
}
