package relampagorojo93.MMOHorses.Modules.CommandsPckg.Commands;

import java.util.ArrayList;
import java.util.List;

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

public class RenameSubCommand extends SubCommand {
	public RenameSubCommand(Command command) {
		super(command, "rename", SettingString.HORSE_RENAME_NAME.toString(), SettingString.HORSE_RENAME_PERMISSION.toString(),
				SettingString.HORSE_RENAME_DESCRIPTION.toString(), SettingString.HORSE_RENAME_PARAMETERS.toString(),
				SettingList.HORSE_RENAME_ALIASES.toList());
	}

	@Override
	public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
		switch (args.length) {
			case 1: return SettingList.DEFAULTCLAIMNAMES.toList();
			default: return new ArrayList<>();
		}
	}

	@Override
	public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
		Player p = sender instanceof Player ? (Player) sender : null;
		if (p != null) {
			if (p.getVehicle() != null) {
				MMOHorse horse = MMOHorsesAPI.getUtils().toMMOHorse(p.getVehicle());
				if (horse != null && horse.isCustom()) {
					ClaimedData hi = horse.getMMOHorseData().getClaimedData();
					if (hi != null && MMOHorsesAPI.getUtils().isSame(hi.getData().getOwner(), p.getUniqueId())) {
						if (args.length >= 2) {
							String name = "";
							for (int t = 1; t < args.length; t++)
								name += (t != 1) ? " " + args[t] : args[t];
							if (name.length() <= 32) {
								hi.getData().setName(name);
								MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.NAMECHANGED)).sendMessage(sender);
							} else
								MessagesUtils.getMessageBuilder().createMessage(
										MMOHorsesAPI.getUtils().applyPrefix(MessageString.NAMEMAXLENGTHREACHED)).sendMessage(sender);
						} else
							MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(getUsage())).sendMessage(sender);
					} else
						MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.NOTOWNER)).sendMessage(sender);
				} else
					MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.ENTITYNOTSUPPORTSFEATURE)).sendMessage(sender);
			} else
				MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.NOTRIDING)).sendMessage(sender);
		} else
			MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.CONSOLEDENIED)).sendMessage(sender);
		return true;
	}
}
