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
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingString;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;
import relampagorojo93.MMOHorses.Enums.Gender;

public class GeldSubCommand extends SubCommand {
	public GeldSubCommand(Command command) {
		super(command, "geld", SettingString.HORSE_GELD_NAME.toString(), SettingString.HORSE_GELD_PERMISSION.toString(),
				SettingString.HORSE_GELD_DESCRIPTION.toString(), "",
				SettingList.HORSE_GELD_ALIASES.toList());
	}

	@Override
	public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
		return new ArrayList<>();
	}

	@Override
	public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
		Player p = sender instanceof Player ? (Player) sender : null;
		if (p != null) {
			if (SettingBoolean.GENDERS.toBoolean()) {
				if (p.getVehicle() != null) {
					MMOHorse horse = MMOHorsesAPI.getUtils().toMMOHorse(p.getVehicle());
					if (horse != null && horse.isCustom()) {
						ClaimedData hi = horse.getMMOHorseData().getClaimedData();
						if (hi != null && MMOHorsesAPI.getUtils().isSame(hi.getData().getOwner(), p.getUniqueId())) {
							if (hi.getData().getGender() != Gender.GELDING) {
								horse.getMMOHorseData().setGender(Gender.GELDING, true);
								horse.getMMOHorseData().setJump(hi.getData().getJump() + 0.5D, true);
								MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.HORSECASTRATED)).sendMessage(sender);
							} else
								MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.ALREADYCASTRATED)).sendMessage(sender);
						} else
							MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.NOTOWNER)).sendMessage(sender);
					} else
						MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.ENTITYNOTSUPPORTSFEATURE)).sendMessage(sender);
				} else
					MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.NOTRIDING)).sendMessage(sender);
			} else
				MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.GENDERSDISABLED)).sendMessage(sender);
		} else
			MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.CONSOLEDENIED)).sendMessage(sender);
		return true;
	}
}
