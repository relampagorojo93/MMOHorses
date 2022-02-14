package relampagorojo93.MMOHorses.Bukkit.Events.ChatEventsObjects;

import org.bukkit.entity.Player;

import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Bukkit.Events.ChatEvents;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Items.SettingsInventory;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;

public class ChangeItemValueInputData extends InputData {
	public ChangeItemValueInputData(Player player, SettingsInventory holder) {
		super(player, (data, result) -> {
			if (!result.isEmpty() && !result.equalsIgnoreCase("cancel")) {
				try {
					float value = Float.parseFloat(result);
					holder.setValue(value);
					MessagesUtils.getMessageBuilder().createMessage(
							MMOHorsesAPI.getUtils().applyPrefix(MessageString.VALUECHANGED)).sendMessage(data.getPlayer());
					holder.openInventory(MMOHorsesAPI.getPlugin());
				} catch (NumberFormatException e) {
					MessagesUtils.getMessageBuilder().createMessage(
							MMOHorsesAPI.getUtils().applyPrefix(MessageString.ONLYNUMBERS)).sendMessage(data.getPlayer());
					ChatEvents.register(data);
				}
			} else if (result.isEmpty()) {
				MessagesUtils.getMessageBuilder().createMessage(
						MMOHorsesAPI.getUtils().applyPrefix(MessageString.VALUENOTSPECIFIED)).sendMessage(data.getPlayer());
				ChatEvents.register(data);
			} else
				MessagesUtils.getMessageBuilder().createMessage(
						MMOHorsesAPI.getUtils().applyPrefix(MessageString.CANCELLED)).sendMessage(data.getPlayer());
		});
	}
}
