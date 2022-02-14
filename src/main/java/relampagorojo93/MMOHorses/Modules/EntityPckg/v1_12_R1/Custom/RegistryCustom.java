package relampagorojo93.MMOHorses.Modules.EntityPckg.v1_12_R1.Custom;

import org.bukkit.Location;

import net.minecraft.server.v1_12_R1.EntityTypes;
import net.minecraft.server.v1_12_R1.MinecraftKey;
import relampagorojo93.MMOHorses.Enums.Type;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Managers.RegistryManager;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.MMOHorse;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_12_R1.Entities.DonkeyCustom;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_12_R1.Entities.HorseCustom;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_12_R1.Entities.LlamaCustom;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_12_R1.Entities.MuleCustom;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_12_R1.Entities.SkeletonHorseCustom;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_12_R1.Entities.ZombieHorseCustom;

public class RegistryCustom implements RegistryManager {
	
	@Override
	public void register() {
		EntityTypes.b.a(Type.DONKEY.getEntityId(), new MinecraftKey("donkey"), DonkeyCustom.class);
		EntityTypes.b.a(Type.HORSE.getEntityId(), new MinecraftKey("horse"), HorseCustom.class);
		EntityTypes.b.a(Type.LLAMA.getEntityId(), new MinecraftKey("llama"), LlamaCustom.class);
		EntityTypes.b.a(Type.MULE.getEntityId(), new MinecraftKey("mule"), MuleCustom.class);
		EntityTypes.b.a(Type.SKELETONHORSE.getEntityId(), new MinecraftKey("skeleton_horse"), SkeletonHorseCustom.class);
		EntityTypes.b.a(Type.ZOMBIEHORSE.getEntityId(), new MinecraftKey("zombie_horse"), ZombieHorseCustom.class);
	}

	@Override
	public MMOHorse spawnEntity(Type type, Location l) {
		if (type == Type.DONKEY)
			return new DonkeyCustom(l);
		else if (type == Type.LLAMA)
			return new LlamaCustom(l);
		else if (type == Type.MULE)
			return new MuleCustom(l);
		else if (type == Type.SKELETONHORSE)
			return new SkeletonHorseCustom(l);
		else if (type == Type.ZOMBIEHORSE)
			return new ZombieHorseCustom(l);
		else
			return new HorseCustom(l);
	}

}
