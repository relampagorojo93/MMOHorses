package relampagorojo93.MMOHorses.Modules.CommandsPckg.Commands.LandsSubCommands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.angeschossen.lands.api.land.Land;
import me.angeschossen.lands.api.land.LandArea;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.SubCommand;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.API.Hooks.LandsAPI;
import relampagorojo93.MMOHorses.Enums.LandsRegionType;
import relampagorojo93.MMOHorses.Enums.WalkMode;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingString;

public class SetRestrictionSubCommand extends SubCommand {
	public SetRestrictionSubCommand(Command command) {
		super(command, "setrestriction", SettingString.HORSELANDS_SETRESTRICTION_NAME.toString(),
				SettingString.HORSELANDS_SETRESTRICTION_PERMISSION.toString(),
				SettingString.HORSELANDS_SETRESTRICTION_DESCRIPTION.toString(),
				SettingString.HORSELANDS_SETRESTRICTION_PARAMETERS.toString(),
				SettingList.HORSELANDS_SETRESTRICTION_ALIASES.toList());
	}

	@Override
	public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
		List<String> list = new ArrayList<>();
		switch (args.length) {
			case 1: list.add("Land"); list.add("Area"); break;
			case 2: for (WalkMode walk:WalkMode.values()) list.add(walk.name()); break;
			default: break;
		
		}
		return list;
	}

	@Override
	public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
		Player p = sender instanceof Player ? (Player) sender : null;
		if (p != null) {
			if (args.length >= 2) {
				LandsRegionType type = null;
				WalkMode walk = null;
				try {
					type = LandsRegionType.valueOf(args[1].toUpperCase());
				} catch (IllegalArgumentException e) {
					MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix("Available region types: Land, Area")).sendMessage(sender);
					return true;
				}
				if (args.length > 2) {
					try {
						walk = WalkMode.valueOf(args[2].toUpperCase());
					} catch (IllegalArgumentException e) {
						MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(
								"Available walk modes: Halt (0%), Walk (25%), Trot (50%), Canter (75%), Gallop (100%)")).sendMessage(sender);
						return true;
					}
				}
				switch (type) {
				case LAND:
					Land land = LandsAPI.getAddon().getLand(p.getLocation());
					if (land != null) {
						LandsAPI.setRestriction(land, walk);
						MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(
								walk != null ? MessageString.RESTRICTIONSET : MessageString.RESTRICTIONREMOVED)).sendMessage(sender);
					} else
						MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.NOTINALAND)).sendMessage(sender);
					break;
				default:
					LandArea area = LandsAPI.getAddon().getArea(p.getLocation());
					if (area != null) {
						LandsAPI.setRestriction(area, walk);
						MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(
								walk != null ? MessageString.RESTRICTIONSET : MessageString.RESTRICTIONREMOVED)).sendMessage(sender);
					} else
						MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.NOTINANAREA)).sendMessage(sender);
					break;
				}
			} else
				MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(getUsage())).sendMessage(sender);
		} else
			MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.CONSOLEDENIED)).sendMessage(sender);
		return true;
	}
}
