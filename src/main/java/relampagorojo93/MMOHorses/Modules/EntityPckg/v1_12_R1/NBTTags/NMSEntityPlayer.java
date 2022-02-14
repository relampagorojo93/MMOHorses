package relampagorojo93.MMOHorses.Modules.EntityPckg.v1_12_R1.NBTTags;

import net.minecraft.server.v1_12_R1.EntityPlayer;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdEntityPlayer;

public class NMSEntityPlayer extends NMSEntityHuman implements StdEntityPlayer {

	public NMSEntityPlayer(EntityPlayer ent) {
		super(ent);
	}

	public EntityPlayer getEntityPlayer() {
		return (EntityPlayer) getEntity();
	}
	
}
