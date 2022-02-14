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
import relampagorojo93.MMOHorses.Modules.CustomPckg.Objects.CustomData;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.MMOHorse;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingString;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;

public class GiveSubCommand extends SubCommand {
	public GiveSubCommand(Command command) {
		super(command, "heal", SettingString.HORSE_GIVE_NAME.toString(), SettingString.HORSE_GIVE_PERMISSION.toString(),
				SettingString.HORSE_GIVE_DESCRIPTION.toString(), SettingString.HORSE_GIVE_PARAMETERS.toString(),
				SettingList.HORSE_GIVE_ALIASES.toList());
	}

	@Override
	public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
		List<String> list = new ArrayList<>();
		switch (args.length) {
			case 1: return MMOHorsesAPI.getCustomModule().getIDs();
			case 2: for (Player player:Bukkit.getOnlinePlayers()) list.add(player.getName()); break;
			default: break;
		
		}
		return list;
	}

	@Override
	public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
		Player p = sender instanceof Player ? (Player) sender : null;
		if (args.length > 1) {
			CustomData ced = MMOHorsesAPI.getCustomModule().getCustomEntityData(args[1]);
			if (ced != null) {
				Player owner = null;
				if (args.length > 2 && (owner = Bukkit.getPlayer(args[2])) == null) {
					MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.NOTONLINE)).sendMessage(sender);
					return true;
				} else if (owner == null)
					owner = p;
				if (owner != null) {
					boolean spawn = false;
					if (args.length > 3)
						try {
							spawn = Boolean.parseBoolean(args[3]);
						} catch (Exception e) {
						}
					ClaimedData h = MMOHorsesAPI.getHorseModule().registerHorseObject(sender, true, ced,
							owner.getUniqueId());
					if (spawn) {
						MMOHorse ec = MMOHorsesAPI.getEntityModule().getRegistry().spawnEntity(h.getData().getType(),
								owner.getLocation());
						h.setMMOHorse(ec);
						if (SettingBoolean.LINKONSPAWN.toBoolean() && !h.getSettings().isLinked())
							h.getSettings().setLink(true);
					}
				} else if (p == null)
					MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.CONSOLEDENIED)).sendMessage(sender);

			} else {
				String msg = "Available custom entities:";
				for (String id : MMOHorsesAPI.getCustomModule().getIDs())
					msg += (msg.endsWith(":") ? " " : ", ") + id.substring(0, 1) + id.substring(1).toLowerCase();
				MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(msg)).sendMessage(sender);
			}
		} else
			MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(getUsage())).sendMessage(sender);
		return true;
	}
}
