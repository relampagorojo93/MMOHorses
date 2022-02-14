package relampagorojo93.MMOHorses.Modules.CommandsPckg.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.SubCommand;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingString;

public class GivePresentSubCommand extends SubCommand {
	private String[] skins = {
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTNlNThlYTdmMzExM2NhZWNkMmIzYTZmMjdhZjUzYjljYzljZmVkN2IwNDNiYTMzNGI1MTY4ZjEzOTFkOSJ9fX0=",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWUzYThmZDA4NTI5Nzc0NDRkOWZkNzc5N2NhYzA3YjhkMzk0OGFkZGM0M2YwYmI1Y2UyNWFlNzJkOTVkYyJ9fX0=",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzU0MTlmY2U1MDZhNDk1MzQzYTFkMzY4YTcxZDIyNDEzZjA4YzZkNjdjYjk1MWQ2NTZjZDAzZjgwYjRkM2QzIn19fQ==",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTBjNzVhMDViMzQ0ZWEwNDM4NjM5NzRjMTgwYmE4MTdhZWE2ODY3OGNiZWE1ZTRiYTM5NWY3NGQ0ODAzZDFkIn19fQ==",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWMzODIxZDRmNjFiMTdmODJmMGQ3YThlNTMxMjYwOGZmNTBlZGUyOWIxYjRkYzg5ODQ3YmU5NDI3ZDM2In19fQ==",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTlmMDc0MzU3NmJiYTRhMjYyMjQ4MDU0ODk3MGI3MjE1NDNkMmM0NTc5NTVlOGRkNWM0ZjlkZGI2YTU2Yjk1YyJ9fX0="
	};
	public GivePresentSubCommand(Command command) {
		super(command, "givepresent", SettingString.HORSE_GIVEPRESENT_NAME.toString(), SettingString.HORSE_GIVEPRESENT_PERMISSION.toString(),
				SettingString.HORSE_GIVEPRESENT_DESCRIPTION.toString(), SettingString.HORSE_GIVEPRESENT_PARAMETERS.toString(),
				SettingList.HORSE_GIVEPRESENT_ALIASES.toList());
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
			if (MMOHorsesAPI.getCustomModule().getCustomEntityData(args[1]) != null) {
				Player owner = null;
				if (args.length > 2 && (owner = Bukkit.getPlayer(args[2])) == null) {
					MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.NOTONLINE)).sendMessage(sender);
					return true;
				} else if (owner == null) owner = p;
				if (owner != null) {
					ItemStack i = 
							MMOHorsesAPI.getEntityModule().getCatchersManager().insertData(
									ItemStacksUtils.setSkin(
											ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD"),
											skins[(int) (Math.random() * skins.length)]), args[1]);
					ItemMeta im = i.getItemMeta();
					String name = "";
					if (args.length > 3) for (int j = 3; j < args.length; j++) name += (name.isEmpty() ? "" : " ") + args[j];
					else name = "&eYour new present";
					im.setDisplayName(MessagesUtils.color(name));
					i.setItemMeta(im);
					owner.getInventory().addItem(i);
				} else if (p == null) MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.CONSOLEDENIED)).sendMessage(sender);
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
