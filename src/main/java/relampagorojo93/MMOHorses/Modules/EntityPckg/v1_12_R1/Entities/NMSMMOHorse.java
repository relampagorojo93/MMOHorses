package relampagorojo93.MMOHorses.Modules.EntityPckg.v1_12_R1.Entities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_12_R1.EnumMoveType;
import net.minecraft.server.v1_12_R1.IAttribute;
import net.minecraft.server.v1_12_R1.InventorySubcontainer;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagList;
import net.minecraft.server.v1_12_R1.PathfinderGoalArrowAttack;
import net.minecraft.server.v1_12_R1.PathfinderGoalBreed;
import net.minecraft.server.v1_12_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_12_R1.PathfinderGoalFollowParent;
import net.minecraft.server.v1_12_R1.PathfinderGoalLlamaFollow;
import net.minecraft.server.v1_12_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_12_R1.PathfinderGoalPanic;
import net.minecraft.server.v1_12_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_12_R1.PathfinderGoalRandomStrollLand;
import net.minecraft.server.v1_12_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_12_R1.World;
import net.minecraft.server.v1_12_R1.GenericAttributes;
import net.minecraft.server.v1_12_R1.GroupDataEntity;
import net.minecraft.server.v1_12_R1.AttributeInstance;
import net.minecraft.server.v1_12_R1.DifficultyDamageScaler;
import net.minecraft.server.v1_12_R1.EntityHorseAbstract;
import net.minecraft.server.v1_12_R1.EntityHuman;
import net.minecraft.server.v1_12_R1.EntityLlama;
import relampagorojo93.LibsCollection.SpigotDebug.Data.DebugErrorData;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Enums.Gender;
import relampagorojo93.MMOHorses.Enums.WalkMode;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.MMOHorse;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdAttributeInstance;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdAttributeInstance.AttributeType;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_12_R1.Entities.Pathfinders.MMOHorsePathfinderGoalFollowOwner;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_12_R1.Entities.Pathfinders.MMOHorsePathfinderGoalTame;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_12_R1.NBTTags.NMSAttributeInstance;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingDouble;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;

public interface NMSMMOHorse extends MMOHorse {

	// ---------------------------------------------------------------------//
	// Default methods
	// ---------------------------------------------------------------------//

	default void customSaveData(NBTTagCompound nbttagcompound) {
		this.superSaveData(nbttagcompound);
		nbttagcompound.setString("Gender", this.getMMOHorseData().getGender().name());
		nbttagcompound.setString("WalkMode", this.getMMOHorseData().getWalkmode().name());
		nbttagcompound.setBoolean("Lunging", this.getMMOHorseData().isLunging());
		nbttagcompound.setLong("LastActivity", this.getMMOHorseData().getLastActivity());
		nbttagcompound.setDouble("Energy", this.getMMOHorseData().getFood());
		if (nbttagcompound.hasKeyOfType("Attributes", 9)) {
			NBTTagList list = nbttagcompound.getList("Attributes", 10);
			List<Integer> remove = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				NBTTagCompound compound = list.get(i);
				String name = compound.getString("Name");
				if (name.equals("minecraft:generic.movement_speed"))
					compound.setDouble("Base", MMOHorsesAPI.getUtils().blockToSpeed(this.getMMOHorseData().getSpeed()));
				else if (name.equals("minecraft:horse.jump_strength"))
					compound.setDouble("Base", MMOHorsesAPI.getUtils().blockToJump(this.getMMOHorseData().getJump()));
				else if (name.equals("minecraft:generic.max_health"))
					compound.setDouble("Base", this.getMMOHorseData().getMaxHealth());
				else if (name.equals("minecraft:generic.armor"))
					remove.add(i);
			}
			for (int i : remove)
				list.remove(i);
		}
		if (this.getMMOHorseData().getClaimedData() != null)
			nbttagcompound.setInt("HorseObject", this.getMMOHorseData().getClaimedData().getData().getId());
	}

	default void customLoadData(NBTTagCompound nbttagcompound) {
		this.superLoadData(nbttagcompound);
		if (nbttagcompound.hasKey("Gender"))
			this.getMMOHorseData().setGender(Gender.valueOf(nbttagcompound.getString("Gender")), false);
		if (nbttagcompound.hasKey("WalkMode"))
			this.getMMOHorseData().setWalkmode(WalkMode.valueOf(nbttagcompound.getString("WalkMode")));
		if (nbttagcompound.hasKey("Lunging")) {
			if (nbttagcompound.getBoolean("Lunging"))
				this.getMMOHorseData().startLunging();
			else
				this.getMMOHorseData().stopLunging();
		}
		if (nbttagcompound.hasKey("LastActivity"))
			this.getMMOHorseData().setLastActivity(nbttagcompound.getLong("LastActivity"));
		if (nbttagcompound.hasKey("Energy"))
			this.getMMOHorseData().setFood(nbttagcompound.getDouble("Energy"));
		if (nbttagcompound.hasKeyOfType("Attributes", 9)) {
			NBTTagList list = nbttagcompound.getList("Attributes", 10);
			for (int i = 0; i < list.size(); i++) {
				NBTTagCompound compound = list.get(i);
				String name = compound.getString("Name");
				if (name.equals("minecraft:generic.movement_speed"))
					this.getMMOHorseData().setSpeed(MMOHorsesAPI.getUtils().speedToBlock(compound.getDouble("Base")),
							false);
				else if (name.equals("minecraft:horse.jump_strength"))
					this.getMMOHorseData().setJump(MMOHorsesAPI.getUtils().jumpToBlock(compound.getDouble("Base")),
							false);
				else if (name.equals("minecraft:generic.max_health"))
					this.getMMOHorseData().setMaxHealth(compound.getDouble("Base"), false);
			}
		}
		if (nbttagcompound.hasKey("Variant")) {
			this.getMMOHorseData().setVariant(nbttagcompound.getInt("Variant"), false);
		}
		if (nbttagcompound.hasKey("HorseObject")) {
			ClaimedData h = MMOHorsesAPI.getHorseModule().getHorseObject(nbttagcompound.getInt("HorseObject"));
			if (h != null) {
				if (!MMOHorsesAPI.getHorseModule().isRegistered(h))
					MMOHorsesAPI.getHorseModule().registerHorseObject(h);
				if (h.getMMOHorse() != null && h.getMMOHorse().getUniqueID().compareTo(this.getUniqueID()) != 0) {
					h.getMMOHorse().superDie();
					Bukkit.getLogger()
							.info(MMOHorsesAPI.getUtils()
									.applyPrefix("Dup found with ID " + nbttagcompound.getInt("HorseObject")
											+ " on the horse with UUID " + h.getMMOHorse().getUniqueID()
											+ ". Removing entity!"));
				}
				h.setMMOHorse(this);
				this.setAsCustom(true);
			} else {
				this.superDie();
				MMOHorsesAPI.getDebugController()
						.addDebugData(new DebugErrorData("Error trying to get the data from the horse with ID "
								+ nbttagcompound.getInt("HorseObject") + " on the horse with UUID " + this.getUniqueID()
								+ ". Removing entity!"));
			}
		}
		if (nbttagcompound.hasKeyOfType("Health", 99))
			this.setHealth(nbttagcompound.getFloat("Health"));
		if (SettingBoolean.CHECK_HORSES_STATS.toBoolean()) {
			double health = this.getMMOHorseData().getMaxHealth(), speed = this.getMMOHorseData().getSpeed(),
					jump = this.getMMOHorseData().getJump();
			if (health < SettingDouble.MINIMUMHEALTH.toDouble() || health > SettingDouble.MAXIMUMHEALTH.toDouble()
					|| speed < SettingDouble.MINIMUMSPEED.toDouble() || speed > SettingDouble.MAXIMUMSPEED.toDouble()
					|| jump < SettingDouble.MINIMUMJUMP.toDouble() || jump > SettingDouble.MAXIMUMJUMP.toDouble())
				this.getMMOHorseData().regenerateAttributes(true);
		}
	}

	default GroupDataEntity customPrepare(DifficultyDamageScaler difficultydamagescaler,
			GroupDataEntity groupdataentity) {
		GroupDataEntity result = superPrepare(difficultydamagescaler, groupdataentity);
		if (!getMMOHorseData().isInitialized())
			getMMOHorseData().initialize();
		if (!getMMOHorseData().isClaimed())
			getMMOHorseData().setVariant(superGetVariantRaw(), false);
		getMMOHorseData().updateAll();
		setHealth(getMMOHorseData().getTotalMaxHealth());
		return result;
	}

	default void customInitPathfinder() {
		superInitPathfinder();
		PathfinderGoalSelector goalSelector = new PathfinderGoalSelector(
				(getWorld() != null && getWorld().methodProfiler != null) ? getWorld().methodProfiler : null);
		switch (getType()) {
		case LLAMA:
			goalSelector.a(0, new PathfinderGoalFloat(getNMSEntity()));
			goalSelector.a(1, new MMOHorsePathfinderGoalTame(this));
			goalSelector.a(2, new PathfinderGoalLlamaFollow((EntityLlama) getNMSEntity(), 2.0999999046325684D));
			goalSelector.a(3, new MMOHorsePathfinderGoalFollowOwner(this));
			goalSelector.a(3, new PathfinderGoalArrowAttack((EntityLlama) getNMSEntity(), 1.25D, 40, 20.0F));
			goalSelector.a(3, new PathfinderGoalPanic(getNMSEntity(), 1.2D));
			goalSelector.a(4, new PathfinderGoalBreed(getNMSEntity(), 1.0D));
			goalSelector.a(5, new PathfinderGoalFollowParent(getNMSEntity(), 1.0D));
			goalSelector.a(6, new PathfinderGoalRandomStrollLand(getNMSEntity(), 0.7D));
			goalSelector.a(7, new PathfinderGoalLookAtPlayer(getNMSEntity(), EntityHuman.class, 6.0F));
			goalSelector.a(8, new PathfinderGoalRandomLookaround(getNMSEntity()));
			setPathfinder(goalSelector);
			break;
		default:
			goalSelector.a(1, new PathfinderGoalPanic(getNMSEntity(), 1.2D));
			goalSelector.a(1, new MMOHorsePathfinderGoalTame(this));
			goalSelector.a(2, new PathfinderGoalBreed(getNMSEntity(), 1.0D, EntityHorseAbstract.class));
			goalSelector.a(3, new MMOHorsePathfinderGoalFollowOwner(this));
			goalSelector.a(4, new PathfinderGoalFollowParent(getNMSEntity(), 1.0D));
			goalSelector.a(6, new PathfinderGoalRandomStrollLand(getNMSEntity(), 0.7D));
			goalSelector.a(7, new PathfinderGoalLookAtPlayer(getNMSEntity(), EntityHuman.class, 6.0F));
			goalSelector.a(8, new PathfinderGoalRandomLookaround(getNMSEntity()));
			setPathfinder(goalSelector);
			break;
		}
	}

	default void setSaddle(ItemStack item) {
		if ((item == null || item.getType() != Material.SADDLE) && getSaddle() != null
				&& getSaddle().getType() == Material.SADDLE)
			removePassengers();
		this.getNMSInventory().setItem(0, CraftItemStack.asNMSCopy(item));
	}

	default ItemStack getSaddle() {
		return CraftItemStack.asBukkitCopy(this.getNMSInventory().getItem(0));
	}

	default void removePassengers() {
		removeEntityPassengers(getEntityPassengers().toArray(new Entity[getEntityPassengers().size()]));
	}

	default void removeEntityPassengers(Entity... entities) {
		for (Entity ent : entities)
			((CraftEntity) ent).getHandle().stopRiding();
	}

	default void addEntityPassengers(Entity... entities) {
		for (Entity ent : entities)
			((CraftEntity) ent).getHandle().startRiding(getNMSEntity());
	}

	default List<Entity> getEntityPassengers() {
		List<Entity> passengers = new ArrayList<>();
		for (net.minecraft.server.v1_12_R1.Entity ent : getPassengers())
			passengers.add(ent.getBukkitEntity());
		return passengers;
	}

	default Location getLocation() {
		return getBukkitEntity().getLocation();
	}

	default Inventory getInventory() {
		return ((InventoryHolder) getBukkitEntity()).getInventory();
	}

	default Entity getHolder() {
		return this.getLeashHolder() != null ? this.getLeashHolder().getBukkitEntity() : null;
	}

	default void setDirection(float yaw, float pitch) {
		this.setYawPitch(yaw, pitch);
	}

	default void setMotion(double x, double y, double z) {
		this.move(EnumMoveType.SELF, x, y, z);
	}

	default void setHolder(Entity holder) {
		this.setLeashHolder(((CraftEntity) holder).getHandle(), true);
	}

	default void setCustomEntityName(String name) {
		setCustomName(name == null ? "" : name);
		setCustomNameVisible(name != null && !name.isEmpty());
	}

	default String getCustomEntityName() {
		return getCustomName();
	}

	default void teleport(Location l) {
		this.getBukkitEntity().teleport(l, TeleportCause.PLUGIN);
	}

	default void damage(double amount) {
		damage(amount, null);
	}

	default void damage(double amount, Entity source) {
		((Damageable) this.getBukkitEntity()).damage(amount, source);
	}

	default StdAttributeInstance getAttribute(AttributeType type) {
		switch (type) {
		case GENERIC_ARMOR:
			return new NMSAttributeInstance(this.getAttributeInstance(GenericAttributes.h));
		case GENERIC_MAX_HEALTH:
			return new NMSAttributeInstance(this.getAttributeInstance(GenericAttributes.maxHealth));
		case GENERIC_MOVEMENT_SPEED:
			return new NMSAttributeInstance(this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED));
		default:
			return new NMSAttributeInstance(this.getAttributeInstance(EntityHorseAbstract.attributeJumpStrength));
		}
	}

	default int superGetVariantRaw() {
		return 0;
	}

	// ---------------------------------------------------------------------//
	// Abstract methods
	// ---------------------------------------------------------------------//

	abstract void superSaveData(NBTTagCompound nbtcompound);

	abstract void superLoadData(NBTTagCompound nbtcompound);

	abstract GroupDataEntity superPrepare(DifficultyDamageScaler difficultydamagescaler,
			GroupDataEntity groupdataentity);

	abstract void setPathfinder(PathfinderGoalSelector pathfinder);

	abstract World getWorld();

	abstract AttributeInstance getAttributeInstance(IAttribute attribute);

	abstract boolean hasLineOfSight(net.minecraft.server.v1_12_R1.Entity entity);

	abstract List<net.minecraft.server.v1_12_R1.Entity> getPassengers();

	abstract net.minecraft.server.v1_12_R1.Entity teleportTo(Location exit, boolean portal);

	abstract void setLeashHolder(net.minecraft.server.v1_12_R1.Entity holder, boolean flag);

	abstract net.minecraft.server.v1_12_R1.Entity getLeashHolder();

	abstract void setYawPitch(float yaw, float pitch);

	abstract void move(EnumMoveType type, double x, double y, double z);

	abstract void setHealth(float health);

	abstract net.minecraft.server.v1_12_R1.EntityHorseAbstract getNMSEntity();

	abstract InventorySubcontainer getNMSInventory();

	abstract void setCustomName(String name);

	abstract void setCustomNameVisible(boolean visible);

	abstract String getCustomName();

}