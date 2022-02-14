package relampagorojo93.MMOHorses.Modules.UtilsPckg.PHApplierPckg.Instances;

import org.bukkit.entity.Entity;

import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;
import relampagorojo93.MMOHorses.Modules.UtilsPckg.PHApplierPckg.Enums.RequestType;

public class PHDeathApplyRequest extends PHApplyRequest {

	private Entity killer;

	public PHDeathApplyRequest(ClaimedData data, Entity killer, String message) {
		super(null, data, null, message, RequestType.DEATH);
		this.killer = killer;
	}

	public Entity getKiller() {
		return killer;
	}

}
