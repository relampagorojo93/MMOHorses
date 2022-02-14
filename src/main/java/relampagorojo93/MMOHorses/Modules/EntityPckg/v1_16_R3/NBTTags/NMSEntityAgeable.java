package relampagorojo93.MMOHorses.Modules.EntityPckg.v1_16_R3.NBTTags;

import net.minecraft.server.v1_16_R3.EntityAgeable;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdEntityAgeable;

public class NMSEntityAgeable extends NMSEntity implements StdEntityAgeable {

	public NMSEntityAgeable(EntityAgeable ent) {
		super(ent);
	}

	public EntityAgeable getEntityAgeable() {
		return (EntityAgeable) getEntity();
	}
	
}
