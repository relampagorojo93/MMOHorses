package relampagorojo93.MMOHorses.Modules.CommandsPckg.Commands.ItemsSubCommands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.SubCommand;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Modules.CraftPckg.Objects.CraftableItem;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingString;

public class GetSubCommand extends SubCommand {
	public GetSubCommand(Command command) {
		super(command, "get", SettingString.HORSEITEMS_GET_NAME.toString(), SettingString.HORSEITEMS_GET_PERMISSION.toString(),
				SettingString.HORSEITEMS_GET_DESCRIPTION.toString(), SettingString.HORSEITEMS_GET_PARAMETERS.toString(),
				SettingList.HORSEITEMS_GET_ALIASES.toList());
	}

	@Override
	public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
		List<String> list = new ArrayList<>();
		switch (args.length) {
			case 1: for (CraftableItem item:MMOHorsesAPI.getCraftModule().getCraftableItems()) list.add(item.getName()); break;
			case 2: for (Player player:Bukkit.getOnlinePlayers()) list.add(player.getName()); break;
			default: break;
		
		}
		return list;
	}

	@Override
	public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
		Player p = sender instanceof Player ? (Player) sender : null;
		if (args.length > 1) {
			CraftableItem ci = MMOHorsesAPI.getCraftModule().getCraftableItem(args[1]);
			if (ci != null) {
				if (args.length > 2) {
					Player pl = Bukkit.getPlayer(args[2]);
					if (pl != null)
						pl.getInventory().addItem(ci.getResult());
					else
						MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.NOTONLINE)).sendMessage(sender);
				} else if (p != null)
					p.getInventory().addItem(ci.getResult());
				else
					MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.CONSOLEDENIED)).sendMessage(sender);
			} else {
				String msg = "Available items:";
				for (CraftableItem item : MMOHorsesAPI.getCraftModule().getCraftableItems())
					msg += (msg.endsWith(":") ? " " : ", ") + item.getName().substring(0, 1)
							+ item.getName().substring(1).toLowerCase();
				MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(msg)).sendMessage(sender);
			}
		} else
			MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(getUsage())).sendMessage(sender);
		return true;
	}
}
