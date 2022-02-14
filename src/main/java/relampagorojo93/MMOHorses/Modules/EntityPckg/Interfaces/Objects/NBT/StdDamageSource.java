package relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT;


import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.Enums.DamageSource;

public interface StdDamageSource {
	public abstract DamageSource getEnumDamageSource();
	public abstract String getTranslationIndex();
}
