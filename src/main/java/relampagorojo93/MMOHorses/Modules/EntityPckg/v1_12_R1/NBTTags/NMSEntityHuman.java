package relampagorojo93.MMOHorses.Modules.EntityPckg.v1_12_R1.NBTTags;

import net.minecraft.server.v1_12_R1.EntityHuman;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdEntityHuman;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdPacket;

public class NMSEntityHuman extends NMSEntity implements StdEntityHuman {

	public NMSEntityHuman(EntityHuman ent) {
		super(ent);
	}

	public EntityHuman getEntityHuman() {
		return (EntityHuman) getEntity();
	}

	@Override
	public void sendPacket(StdPacket packet) {
		if (getEntity() instanceof EntityPlayer)
			((EntityPlayer) getEntityHuman()).playerConnection.sendPacket(((NMSPacket) packet).getPacket());		
	}
}
