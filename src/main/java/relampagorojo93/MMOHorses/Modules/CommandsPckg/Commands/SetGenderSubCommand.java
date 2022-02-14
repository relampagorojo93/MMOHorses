package relampagorojo93.MMOHorses.Modules.CommandsPckg.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.SubCommand;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Enums.Gender;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.MMOHorse;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingString;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;

public class SetGenderSubCommand extends SubCommand {
	public SetGenderSubCommand(Command command) {
		super(command, "setgender", SettingString.HORSE_SETGENDER_NAME.toString(), SettingString.HORSE_SETGENDER_PERMISSION.toString(),
				SettingString.HORSE_SETGENDER_DESCRIPTION.toString(), SettingString.HORSE_SETGENDER_PARAMETERS.toString(),
				SettingList.HORSE_SETGENDER_ALIASES.toList());
	}

	@Override
	public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
		List<String> list = new ArrayList<>();
		switch (args.length) {
			case 1: for (Gender gender:Gender.values()) list.add(gender.name()); break;
			default: break;
		}
		return list;
	}

	@Override
	public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
		Player p = sender instanceof Player ? (Player) sender : null;
		if (p != null) {
			if (SettingBoolean.GENDERS.toBoolean()) {
				if (p.getVehicle() != null) {
					MMOHorse horse = MMOHorsesAPI.getUtils().toMMOHorse(p.getVehicle());
					if (horse != null && horse.isCustom()) {
						if (args.length >= 2) {
							try {
								Gender g = Gender.valueOf(args[1].toUpperCase());
								if (horse.getMMOHorseData().getGender() != g) {
									horse.getMMOHorseData().setJump(horse.getMMOHorseData().getJump()
											+ (horse.getMMOHorseData().getGender() == Gender.GELDING ? -0.5D : 0.5D), true);
									horse.getMMOHorseData().setGender(g, true);
									MessagesUtils.getMessageBuilder().createMessage(
											MMOHorsesAPI.getUtils().applyPrefix(MessageString.HORSEEDITED)).sendMessage(sender);
								} else
									MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.SAMEGENDER
											.toString().replace("%gender%", horse.getMMOHorseData().getGender().toString()))).sendMessage(sender);
							} catch (IllegalArgumentException e) {
								MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils()
										.applyPrefix("Available genders: Mare, Stallion, Gelding")).sendMessage(sender);
							}
						} else
							MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(getUsage())).sendMessage(sender);
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
