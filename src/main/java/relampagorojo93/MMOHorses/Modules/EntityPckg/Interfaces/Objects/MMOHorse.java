package relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import relampagorojo93.MMOHorses.Enums.Type;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdAttributeInstance;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdDamageSource;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdEntity;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdEntityHuman;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdEntityPlayer;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdPacket;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingInt;

public interface MMOHorse {

	// ---------------------------------------------------------------------//
	// MMOHorses Abstract methods
	// ---------------------------------------------------------------------//
		
	abstract MMOHorseData getMMOHorseData();
	
	abstract Type getType();

	abstract MMOHorse createEntity(World world);

	abstract void setAsCustom(boolean custom);

	abstract boolean isCustom();

	// ---------------------------------------------------------------------//
	// Super methods
	// ---------------------------------------------------------------------//
	
	abstract void superInitPathfinder();
	
	abstract boolean superIsInvulnerable(StdDamageSource damagesource);
	
	abstract void superTick();
	
	abstract boolean superAddPassenger(StdEntity entity, boolean flag);
	
	abstract void superDie();
	
	abstract void superDie(StdDamageSource damagesource);
	
	abstract boolean superIsDropExperience();
	
	abstract void superJump(int jump);
	
	abstract void superOpenInventory(StdEntityHuman entityhuman);
	
	abstract boolean superSetLeashHolder(StdEntityHuman entityhuman);
	
	abstract void superEnterLoveMode(Player player);
	
	abstract StdEntityPlayer superGetBreedCause();
	
	abstract int superGetVariantRaw();

	// ---------------------------------------------------------------------//
	// NMS methods
	// ---------------------------------------------------------------------//
	
	abstract StdPacket nmsLeashHolderPacket();
	
	// ---------------------------------------------------------------------//
	// Abstract methods
	// ---------------------------------------------------------------------//

	abstract Entity getBukkitEntity();

	abstract Entity getKiller();

	abstract void ejectPassengers();

	abstract Location getLocation();

	abstract Inventory getInventory();

	abstract float getHealth();

	abstract void setDirection(float yaw, float pitch);

	abstract void setMotion(double x, double y, double z);

	abstract Vector getMotion();

	abstract boolean isAlive();

	abstract boolean isValid();

	abstract void setRemoveWhenFarAway(boolean removewhenfaraway);

	abstract void addEntityPassengers(Entity... entities);

	abstract void removeEntityPassengers(Entity... entities);

	abstract List<Entity> getEntityPassengers();

	abstract void setSaddle(ItemStack item);

	abstract ItemStack getSaddle();

	abstract void damage(double amount);

	abstract void damage(double amount, Entity source);

	abstract float getHeadHeight();

	abstract StdAttributeInstance getAttribute(StdAttributeInstance.AttributeType attribute);

	abstract void setCustomEntityName(String name);

	abstract String getCustomEntityName();

	abstract void setAge(int age);

	abstract int getAge();

	abstract void setTamed(boolean tamed);

	abstract void setOwnerUUID(UUID uuid);

	abstract void setHealth(float healthinhalfhearts);

	abstract boolean isTamed();

	abstract UUID getUniqueID();

	abstract boolean isInLove();

	abstract void setHolder(Entity holder);

	abstract Entity getHolder();

	abstract void teleport(Location l);
	
	abstract void resetLove();
	
	abstract void die();
	
	// ---------------------------------------------------------------------//
	// Default methods
	// ---------------------------------------------------------------------//

	default void setCarryingChest(boolean carrying) {
	}

	default boolean isCarryingChest() {
		return false;
	}

	default void removePassengers() {
		removeEntityPassengers(getEntityPassengers().toArray(new Entity[getEntityPassengers().size()]));
	}

	default void setBaby() {
		setAge(SettingBoolean.CUSTOMGROWUPSYSTEM.toBoolean() ? SettingInt.TIMEFORGROWUP.toInt() * 1200 : 24000);
	}

	default void setAdult() {
		setAge(0);
	}

	default void setOwner(AnimalTamer owner) {
		setOwnerUUID(owner != null ? owner.getUniqueId() : null);
	}

	default void setStrength(int strength) {
	}

	default int getStrength() {
		return 0;
	}

	default void setBody(ItemStack item) {
	}

	default ItemStack getBody() {
		return null;
	}

	default void updateVariant() {
	}

	default Location getEyeLocation() {
		Location l = getLocation();
		l.setY(l.getY() + getHeadHeight());
		return l;
	}

	default void setHealth(double health) {
		double maxhealth = getMMOHorseData().getTotalMaxHealth();
		setHealth((float) ((health >= maxhealth) ? maxhealth : health));
	}
	
}