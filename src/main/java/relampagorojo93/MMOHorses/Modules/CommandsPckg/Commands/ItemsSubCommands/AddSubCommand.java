package relampagorojo93.MMOHorses.Modules.CommandsPckg.Commands.ItemsSubCommands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.SubCommand;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Enums.ItemType;
import relampagorojo93.MMOHorses.Modules.CraftPckg.Objects.CraftableItem;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingString;

public class AddSubCommand extends SubCommand {
	public AddSubCommand(Command command) {
		super(command, "add", SettingString.HORSEITEMS_ADD_NAME.toString(), SettingString.HORSEITEMS_ADD_PERMISSION.toString(),
				SettingString.HORSEITEMS_ADD_DESCRIPTION.toString(), SettingString.HORSEITEMS_ADD_PARAMETERS.toString(),
				SettingList.HORSEITEMS_ADD_ALIASES.toList());
	}

	@Override
	public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
		List<String> list = new ArrayList<>();
		switch (args.length) {
			case 2: for (ItemType type:ItemType.values()) list.add(type.name()); break;
			default: break;
		
		}
		return list;
	}

	@Override
	public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
		Player p = sender instanceof Player ? (Player) sender : null;
		if (p != null) {
			if (args.length > 1) {
				CraftableItem ci = MMOHorsesAPI.getCraftModule().getCraftableItem(args[1]);
				if (ci == null) {
					ItemStack i = ItemStacksUtils.getItemInMainHand(p);
					if (i != null && i.getType() != Material.AIR) {
						if (args[1].matches("^[0-9a-zA-Z_]{1,}$")) {
							try {
								String t = args.length > 2 ? args[2] : "";
								for (int arg = 3; arg < args.length; arg++) t += "_" + arg;
								ItemType type = !t.isEmpty() ? ItemType.valueOf(t.toUpperCase()) : ItemType.CUSTOM;
								MMOHorsesAPI.getCraftModule().addItem(args[1], i.clone(), type);
								MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.ITEMADDED)).sendMessage(sender);
							} catch (IllegalArgumentException e) {
								String msg = "Available item types:";
								for (ItemType type : ItemType.values())
									msg += (msg.endsWith(":") ? " " : ", ") + type.toString();
								MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(msg)).sendMessage(sender);
							}
						} else
							MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.ONLYNUMBERSLETTERSUNDERSCORE)).sendMessage(sender);
					} else
						MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.EMPTYHAND)).sendMessage(sender);
				} else
					MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.ITEMALREADYEXISTS)).sendMessage(sender);
			} else
				MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(getUsage())).sendMessage(sender);
		} else
			MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.CONSOLEDENIED)).sendMessage(sender);
		return true;
	}
}
