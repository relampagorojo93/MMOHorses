package relampagorojo93.MMOHorses.Modules.EntityPckg.v1_17_R1.NBTTags;

import net.minecraft.world.damagesource.DamageSource;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdDamageSource;

public class NMSDamageSource implements StdDamageSource {
	private DamageSource damagesource;
	private relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.Enums.DamageSource enumdamagesource;

	public NMSDamageSource(DamageSource damagesource) {
		this.damagesource = damagesource;
		this.enumdamagesource = relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.Enums.DamageSource
				.valueOf(damagesource.y.toUpperCase().replaceAll("\\.", "_"));
	}

	public DamageSource getDamageSource() {
		return damagesource;
	}

	@Override
	public relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.Enums.DamageSource getEnumDamageSource() {
		return enumdamagesource;
	}

	@Override
	public String getTranslationIndex() {
		return damagesource.y;
	}
}
