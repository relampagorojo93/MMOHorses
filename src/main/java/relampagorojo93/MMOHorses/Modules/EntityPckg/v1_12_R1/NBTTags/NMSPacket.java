package relampagorojo93.MMOHorses.Modules.EntityPckg.v1_12_R1.NBTTags;

import net.minecraft.server.v1_12_R1.Packet;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdPacket;

public class NMSPacket implements StdPacket {
	private Packet<?> packet;

	public NMSPacket(Packet<?> packet) {
		this.packet = packet;
	}

	public Packet<?> getPacket() {
		return packet;
	}
}
