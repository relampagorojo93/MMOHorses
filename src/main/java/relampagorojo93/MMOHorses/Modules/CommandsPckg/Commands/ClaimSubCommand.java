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

public class ClaimSubCommand extends SubCommand {
	public ClaimSubCommand(Command command) {
		super(command, "claim", SettingString.HORSE_CLAIM_NAME.toString(), SettingString.HORSE_CLAIM_PERMISSION.toString(),
				SettingString.HORSE_CLAIM_DESCRIPTION.toString(), SettingString.HORSE_CLAIM_PARAMETERS.toString(),
				SettingList.HORSE_CLAIM_ALIASES.toList());
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
					if (horse.isTamed()) {
						if (horse.getMMOHorseData().getClaimedData() == null) {
							if (!MMOHorsesAPI.getHorseModule().reachedMaxClaimed(p)) {
								if (!MMOHorsesAPI.getHorseModule().reachedMaxSummoned(p)) {
									String name = "";
									if (args.length >= 2)
										for (int t = 1; t < args.length; t++)
											name += (t != 1) ? " " + args[t] : args[t];
									else
										name = horse.getCustomEntityName() == null || horse.getCustomEntityName().isEmpty()
												? SettingList.DEFAULTCLAIMNAMES.toList()
														.get((int) (SettingList.DEFAULTCLAIMNAMES.toList().size()
																* Math.random()))
												: horse.getCustomEntityName();
									if (name.length() <= 32) {
										if (MMOHorsesAPI.getHorseModule().registerHorseObject(sender, true, horse,
												p.getUniqueId(), name) != null)
											MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(
													MessageString.HORSECLAIMED.toString().replace("%name%", name))).sendMessage(sender);
									} else
										MessagesUtils.getMessageBuilder().createMessage(
												MMOHorsesAPI.getUtils().applyPrefix(MessageString.NAMEMAXLENGTHREACHED)).sendMessage(sender);
								} else
									MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.TOOMANYSUMMONEDHORSES)).sendMessage(sender);
							} else
								MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.MAXREACHED)).sendMessage(sender);
						} else
							MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.ALREADYCLAIMED)).sendMessage(sender);
					} else
						MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.NOTTAMED)).sendMessage(sender);
				} else
					MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.ENTITYNOTSUPPORTSFEATURE)).sendMessage(sender);
			} else
				MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.NOTRIDING)).sendMessage(sender);
		} else
			MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.CONSOLEDENIED)).sendMessage(sender);
		return true;
	}
}
