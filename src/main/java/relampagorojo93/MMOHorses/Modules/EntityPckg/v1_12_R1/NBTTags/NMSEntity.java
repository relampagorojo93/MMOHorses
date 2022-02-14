package relampagorojo93.MMOHorses.Modules.EntityPckg.v1_12_R1.NBTTags;

import java.util.UUID;

import net.minecraft.server.v1_12_R1.Entity;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdEntity;

public class NMSEntity implements StdEntity {
	private Entity ent;

	public NMSEntity(Entity ent) {
		this.ent = ent;
	}

	public Entity getEntity() {
		return ent;
	}

	public UUID getUniqueID() {
		return ent.getUniqueID();
	}
	
	public org.bukkit.entity.Entity getBukkitEntity() {
		return ent.getBukkitEntity();
	}
}
