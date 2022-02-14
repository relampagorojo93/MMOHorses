package relampagorojo93.MMOHorses.Modules.EntityPckg.v1_17_R1.NBTTags;

import net.minecraft.world.entity.animal.EntityAnimal;
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
