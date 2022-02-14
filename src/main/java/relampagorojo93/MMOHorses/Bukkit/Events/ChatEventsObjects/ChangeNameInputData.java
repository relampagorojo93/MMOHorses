package relampagorojo93.MMOHorses.Bukkit.Events.ChatEventsObjects;

import org.bukkit.entity.Player;

import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Bukkit.Events.ChatEvents;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;

public class ChangeNameInputData extends InputData {
	private ClaimedData ho;
	public ChangeNameInputData(Player player, ClaimedData ho) {
		super(player, (data, result) -> {
			if (!result.isEmpty() && !result.equalsIgnoreCase("cancel")) {
				if (result.length() <= 32) {
					((ChangeNameInputData) data).getHorseObject().getData().setName(result);
					MessagesUtils.getMessageBuilder().createMessage(
							MMOHorsesAPI.getUtils().applyPrefix(MessageString.NAMECHANGED)).sendMessage(data.getPlayer());
				} else {
					MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils()
							.applyPrefix(MessageString.NAMEMAXLENGTHREACHED)).sendMessage(data.getPlayer());
					ChatEvents.register(data);
				}
			} else if (result.isEmpty()) {
				MessagesUtils.getMessageBuilder().createMessage(
						MMOHorsesAPI.getUtils().applyPrefix(MessageString.NAMENOTSPECIFIED)).sendMessage(data.getPlayer());
				ChatEvents.register(data);
			} else
				MessagesUtils.getMessageBuilder().createMessage(
						MMOHorsesAPI.getUtils().applyPrefix(MessageString.CANCELLED)).sendMessage(data.getPlayer());
		});
		this.ho = ho;
	}
	public ClaimedData getHorseObject() { return ho; }
}
