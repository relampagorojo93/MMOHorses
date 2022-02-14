package relampagorojo93.MMOHorses.Bukkit.Events.ChatEventsObjects;

import org.bukkit.entity.Player;

import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Bukkit.Events.ChatEvents;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Items.SettingsInventory;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;

public class ChangeItemMaxLevelInputData extends InputData {
	public ChangeItemMaxLevelInputData(Player player, SettingsInventory holder) {
		super(player, (data, result) -> {
			if (!result.isEmpty() && !result.equalsIgnoreCase("cancel")) {
				try {
					int value = Integer.parseInt(result);
					holder.setMaxLevel(value);
					MessagesUtils.getMessageBuilder().createMessage(
							MMOHorsesAPI.getUtils().applyPrefix(MessageString.MAXLEVELCHANGED)).sendMessage(data.getPlayer());
					holder.openInventory(MMOHorsesAPI.getPlugin());
				} catch (NumberFormatException e) {
					MessagesUtils.getMessageBuilder().createMessage(
							MMOHorsesAPI.getUtils().applyPrefix(MessageString.ONLYNUMBERS)).sendMessage(data.getPlayer());
					ChatEvents.register(data);
				}
			} else if (result.isEmpty()) {
				MessagesUtils.getMessageBuilder().createMessage(
						MMOHorsesAPI.getUtils().applyPrefix(MessageString.MAXLEVELNOTSPECIFIED)).sendMessage(data.getPlayer());
				ChatEvents.register(data);
			} else
				MessagesUtils.getMessageBuilder().createMessage(
						MMOHorsesAPI.getUtils().applyPrefix(MessageString.CANCELLED)).sendMessage(data.getPlayer());
		});
	}
}
