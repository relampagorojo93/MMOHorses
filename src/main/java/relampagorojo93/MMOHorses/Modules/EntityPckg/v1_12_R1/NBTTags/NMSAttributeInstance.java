package relampagorojo93.MMOHorses.Modules.EntityPckg.v1_12_R1.NBTTags;

import net.minecraft.server.v1_12_R1.AttributeInstance;
import net.minecraft.server.v1_12_R1.EntityHorseAbstract;
import net.minecraft.server.v1_12_R1.GenericAttributes;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdAttributeInstance;

public class NMSAttributeInstance implements StdAttributeInstance {
	
	private AttributeInstance attribute;
	
	public NMSAttributeInstance(AttributeInstance attribute) {
		this.attribute = attribute;
	}

	@Override
	public AttributeType getAttribute() {
		if (attribute.getAttribute() == GenericAttributes.h)
			return AttributeType.GENERIC_ARMOR;
		else if (attribute.getAttribute() == EntityHorseAbstract.attributeJumpStrength)
			return AttributeType.HORSE_JUMP_STRENGTH;
		else if (attribute.getAttribute() == GenericAttributes.maxHealth)
			return AttributeType.GENERIC_MAX_HEALTH;
		else
			return AttributeType.GENERIC_MOVEMENT_SPEED;
	}

	@Override
	public double getBaseValue() {
		return attribute.b();
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
