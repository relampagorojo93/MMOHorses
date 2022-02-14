package relampagorojo93.MMOHorses.Modules.EntityPckg.v1_17_R1.NBTTags;

import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.player.EntityHuman;
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
			((EntityPlayer) getEntityHuman()).b.sendPacket(((NMSPacket) packet).getPacket());		
	}
}
