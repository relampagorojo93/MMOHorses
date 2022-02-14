package relampagorojo93.MMOHorses.Modules.CommandsPckg.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.SubCommand;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingString;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;

public class LungeSubCommand extends SubCommand {
	public LungeSubCommand(Command command) {
		super(command, "lunge", SettingString.HORSE_LUNGE_NAME.toString(), SettingString.HORSE_LUNGE_PERMISSION.toString(),
				SettingString.HORSE_LUNGE_DESCRIPTION.toString(), "",
				SettingList.HORSE_LUNGE_ALIASES.toList());
	}

	@Override
	public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
		return new ArrayList<>();
	}

	@Override
	public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
		Player p = sender instanceof Player ? (Player) sender : null;
		if (p != null) {
			boolean found = false;
			for (ClaimedData hi : MMOHorsesAPI.getHorseModule().getOwned(p.getUniqueId())) {
				if (hi.getMMOHorse() != null && hi.getMMOHorse().getHolder() != null) {
					if (hi.getMMOHorse().getMMOHorseData().isLunging())
						hi.getMMOHorse().getMMOHorseData().stopLunging();
					else
						hi.getMMOHorse().getMMOHorseData().startLunging();
					if (!found)
						found = true;
				}
			}
			if (found)
				return true;
			MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.NOTLEASHED)).sendMessage(sender);
		} else
			MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.CONSOLEDENIED)).sendMessage(sender);
		return true;
	}
}
