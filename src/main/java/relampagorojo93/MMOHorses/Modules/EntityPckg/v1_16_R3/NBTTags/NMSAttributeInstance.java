package relampagorojo93.MMOHorses.Modules.EntityPckg.v1_16_R3.NBTTags;

import net.minecraft.server.v1_16_R3.AttributeModifiable;
import net.minecraft.server.v1_16_R3.GenericAttributes;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdAttributeInstance;

public class NMSAttributeInstance implements StdAttributeInstance {
	
	private AttributeModifiable attribute;
	
	public NMSAttributeInstance(AttributeModifiable attribute) {
		this.attribute = attribute;
	}

	@Override
	public AttributeType getAttribute() {
		if (attribute.getAttribute() == GenericAttributes.ARMOR)
			return AttributeType.GENERIC_ARMOR;
		else if (attribute.getAttribute() == GenericAttributes.JUMP_STRENGTH)
			return AttributeType.HORSE_JUMP_STRENGTH;
		else if (attribute.getAttribute() == GenericAttributes.MAX_HEALTH)
			return AttributeType.GENERIC_MAX_HEALTH;
		else
			return AttributeType.GENERIC_MOVEMENT_SPEED;
	}

	@Override
	public double getBaseValue() {
		return attribute.getBaseValue();
	}

	@Override
	public void setBaseValue(double value) {
		attribute.setValue(value);
	}

	@Override
	public double getValue() {
		return attribute.getValue();
	}

}
