package relampagorojo93.MMOHorses.Modules.UtilsPckg.PHApplierPckg.Instances;

import org.bukkit.entity.Player;

import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.SpigotMessages.Instances.TextResult;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.MMOHorse;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;
import relampagorojo93.MMOHorses.Modules.UtilsPckg.PHApplierPckg.PHReplacements;
import relampagorojo93.MMOHorses.Modules.UtilsPckg.PHApplierPckg.Enums.RequestType;

public class PHApplyRequest {
	
	private String message;
	private MMOHorse horse;
	private ClaimedData data;
	private RequestType type;

	public PHApplyRequest(MMOHorse horse, ClaimedData data, Player player, String message, RequestType type) {
		this.message = message;
		this.horse = horse;
		this.data = data;
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public MMOHorse getMMOHorse() {
		return horse;
	}

	public ClaimedData getClaimedData() {
		return data;
	}

	public RequestType getRequestType() {
		return type;
	}

	public TextResult generate() {
		return MessagesUtils.getMessageBuilder().createMessage(PHReplacements.generateReplacements(this), true, message);
	}
}
