package relampagorojo93.MMOHorses.Modules.UtilsPckg.PHApplierPckg.Instances;

import org.bukkit.entity.Player;

import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.MMOHorse;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;
import relampagorojo93.MMOHorses.Modules.UtilsPckg.PHApplierPckg.Enums.RequestType;

public class PHDataApplyRequest extends PHApplyRequest {

	private Player player;

	public PHDataApplyRequest(MMOHorse horse, Player player, String message) {
		this(horse, horse.getMMOHorseData().getClaimedData(), player, message);
		this.player = player;
	}

	public PHDataApplyRequest(ClaimedData data, Player player, String message) {
		this(data.getMMOHorse(), data, player, message);
		this.player = player;
	}

	public PHDataApplyRequest(MMOHorse horse, ClaimedData data, Player player, String message) {
		super(horse, data, player, message, RequestType.DATA);
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

}