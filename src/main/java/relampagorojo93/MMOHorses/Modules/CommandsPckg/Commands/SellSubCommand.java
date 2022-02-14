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
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingDouble;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingString;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;

public class SellSubCommand extends SubCommand {
	public SellSubCommand(Command command) {
		super(command, "sell", SettingString.HORSE_SELL_NAME.toString(), SettingString.HORSE_SELL_PERMISSION.toString(),
				SettingString.HORSE_SELL_DESCRIPTION.toString(), SettingString.HORSE_SELL_PARAMETERS.toString(),
				SettingList.HORSE_SELL_ALIASES.toList());
	}

	@Override
	public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
		List<String> list = new ArrayList<>();
		switch (args.length) {
			case 1: list.add(String.valueOf(SettingDouble.MINIMUMPRICE.toDouble())); break;
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
					if (hi != null && MMOHorsesAPI.getUtils().isSame(hi.getData().getOwner(), p.getUniqueId())) {
						if (args.length >= 2) {
							try {
								double d = Double.parseDouble(args[1]);
								if (d != 0 && d < SettingDouble.MINIMUMPRICE.toDouble())
									MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils()
											.applyPrefix(MessageString.PRICETOOLOW.toString().replace("%minimum_price%",
													String.valueOf(SettingDouble.MINIMUMPRICE.toDouble())))).sendMessage(sender);
								else {
									hi.getData().setPrice(d);
									if (d != 0)
										MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.ONSALE)).sendMessage(sender);
									else
										MessagesUtils.getMessageBuilder().createMessage(
												MMOHorsesAPI.getUtils().applyPrefix(MessageString.NOMOREONSALE)).sendMessage(sender);
								}
							} catch (NumberFormatException e) {
								MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.ONLYNUMBERS)).sendMessage(sender);
							}
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
