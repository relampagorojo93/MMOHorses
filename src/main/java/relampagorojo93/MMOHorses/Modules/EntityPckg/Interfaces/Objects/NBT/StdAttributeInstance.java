package relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT;


public interface StdAttributeInstance {

	public abstract AttributeType getAttribute();

	public abstract double getBaseValue();

	public abstract void setBaseValue(double paramDouble);

	public abstract double getValue();

	public enum AttributeType {
		GENERIC_MAX_HEALTH, GENERIC_MOVEMENT_SPEED, GENERIC_ARMOR, HORSE_JUMP_STRENGTH
	}
}
