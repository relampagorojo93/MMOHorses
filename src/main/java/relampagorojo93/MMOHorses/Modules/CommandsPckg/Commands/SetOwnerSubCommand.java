package relampagorojo93.MMOHorses.Modules.CommandsPckg.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.SubCommand;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.MMOHorse;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingString;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;

public class SetOwnerSubCommand extends SubCommand {
	public SetOwnerSubCommand(Command command) {
		super(command, "setowner", SettingString.HORSE_SETOWNER_NAME.toString(), SettingString.HORSE_SETOWNER_PERMISSION.toString(),
				SettingString.HORSE_SETOWNER_DESCRIPTION.toString(), SettingString.HORSE_SETOWNER_PARAMETERS.toString(),
				SettingList.HORSE_SETOWNER_ALIASES.toList());
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

	@Override
	public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
		Player p = sender instanceof Player ? (Player) sender : null;
		if (p != null) {
			if (p.getVehicle() != null) {
				MMOHorse horse = MMOHorsesAPI.getUtils().toMMOHorse(p.getVehicle());
				if (horse != null && horse.isCustom()) {
					ClaimedData hi = horse.getMMOHorseData().getClaimedData();
					if (hi != null) {
						if (args.length >= 2) {
							Player pl = Bukkit.getPlayer(args[1]);
							if (pl != null) {
								hi.getData().setOwner(pl.getUniqueId());
								MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.HORSEEDITED)).sendMessage(sender);
							} else
								MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.NOTONLINE)).sendMessage(sender);
						} else
							MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(getUsage())).sendMessage(sender);
					} else
						MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.NOTCLAIMED)).sendMessage(sender);
				} else
					MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.ENTITYNOTSUPPORTSFEATURE)).sendMessage(sender);
			} else
				MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.NOTRIDING)).sendMessage(sender);
		} else
			MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.CONSOLEDENIED)).sendMessage(sender);
		return true;
	}
}
