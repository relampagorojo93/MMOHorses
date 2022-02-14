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

public class HealSubCommand extends SubCommand {
	public HealSubCommand(Command command) {
		super(command, "heal", SettingString.HORSE_HEAL_NAME.toString(), SettingString.HORSE_HEAL_PERMISSION.toString(),
				SettingString.HORSE_HEAL_DESCRIPTION.toString(), "",
				SettingList.HORSE_HEAL_ALIASES.toList());
	}

	@Override
	public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
		return new ArrayList<>();
	}

	@Override
	public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
		Player p = sender instanceof Player ? (Player) sender : null;
		if (p != null) {
			if (p.getVehicle() != null) {
				MMOHorse ec = MMOHorsesAPI.getUtils().toMMOHorse(p.getVehicle());
				if (ec != null)
					ec.setHealth(ec.getMMOHorseData().getTotalMaxHealth());
				else
					MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.ENTITYNOTSUPPORTSFEATURE)).sendMessage(sender);
			} else
				MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.NOTRIDING)).sendMessage(sender);
		} else
			MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.CONSOLEDENIED)).sendMessage(sender);
		return true;
	}
}
