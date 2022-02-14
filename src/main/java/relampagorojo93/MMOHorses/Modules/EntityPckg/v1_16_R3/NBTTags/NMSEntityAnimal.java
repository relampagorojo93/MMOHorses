package relampagorojo93.MMOHorses.Modules.EntityPckg.v1_16_R3.NBTTags;

import net.minecraft.server.v1_16_R3.EntityAnimal;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdEntityAnimal;

public class NMSEntityAnimal extends NMSEntity implements StdEntityAnimal {

	public NMSEntityAnimal(EntityAnimal ent) {
		super(ent);
	}

	public EntityAnimal getEntityAnimal() {
		return (EntityAnimal) getEntity();
	}

	@Override
	public void resetLove() {
		getEntityAnimal().resetLove();
	}
	
}
