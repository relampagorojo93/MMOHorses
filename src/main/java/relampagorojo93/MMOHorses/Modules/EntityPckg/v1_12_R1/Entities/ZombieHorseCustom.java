package relampagorojo93.MMOHorses.Modules.EntityPckg.v1_12_R1.Entities;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_12_R1.DamageSource;
import net.minecraft.server.v1_12_R1.DifficultyDamageScaler;
import net.minecraft.server.v1_12_R1.EntityAgeable;
import net.minecraft.server.v1_12_R1.EntityHorseAbstract;
import net.minecraft.server.v1_12_R1.EntityHorseZombie;
import net.minecraft.server.v1_12_R1.EntityHuman;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.GroupDataEntity;
import net.minecraft.server.v1_12_R1.InventorySubcontainer;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_12_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_12_R1.World;
import net.minecraft.server.v1_12_R1.EntityAnimal;
import relampagorojo93.MMOHorses.Enums.Type;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.MMOHorseData;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.MMOHorseMethods;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdDamageSource;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdEntity;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdEntityHuman;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdEntityPlayer;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdPacket;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_12_R1.NBTTags.NMSDamageSource;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_12_R1.NBTTags.NMSEntity;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_12_R1.NBTTags.NMSEntityAnimal;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_12_R1.NBTTags.NMSEntityHuman;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_12_R1.NBTTags.NMSEntityPlayer;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_12_R1.NBTTags.NMSPacket;

public class ZombieHorseCustom extends EntityHorseZombie implements NMSMMOHorse {

	// ---------------------------------------------------------------------//
	// Objects
	// ---------------------------------------------------------------------//

	private MMOHorseData data = new MMOHorseData(this);
	private boolean custom = false;

	// ---------------------------------------------------------------------//
	// Entity initializers
	// ---------------------------------------------------------------------//

	public ZombieHorseCustom(World w) {
		super(w);
		data.initialize();
	}

	public ZombieHorseCustom(Location l) {
		this(((CraftWorld) l.getWorld()).getHandle());
		this.setLocation(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
		((CraftWorld) l.getWorld()).getHandle().addEntity(this);
	}

	// ---------------------------------------------------------------------//
	// Class methods
	// ---------------------------------------------------------------------//

	public MMOHorseData getMMOHorseData() {
		return data;
	}
	
	public Type getType() {
		return Type.ZOMBIEHORSE;
	}

	@Override
	public ZombieHorseCustom createEntity(org.bukkit.World world) {
		return createEntity(((CraftWorld) world).getHandle());
	}

	public ZombieHorseCustom createEntity(World world) {
		return new ZombieHorseCustom(world);
	}

	@Override
	public void setAsCustom(boolean custom) {
		this.custom = custom;
	}

	@Override
	public boolean isCustom() {
		return custom;
	}

	// ---------------------------------------------------------------------//
	// Super methods
	// ---------------------------------------------------------------------//

	@Override
	public void superSaveData(NBTTagCompound nbtcompound) {
		super.b(nbtcompound);
	}

	@Override
	public void superLoadData(NBTTagCompound nbtcompound) {
		super.a(nbtcompound);
	}

	@Override
	public void superInitPathfinder() {
		super.r();
	}

	@Override
	public GroupDataEntity superPrepare(DifficultyDamageScaler difficultydamagescaler, GroupDataEntity groupdataentity) {
		return super.prepare(difficultydamagescaler, groupdataentity);
	}

	@Override
	public boolean superIsInvulnerable(StdDamageSource damagesource) {
		return super.isInvulnerable(((NMSDamageSource) damagesource).getDamageSource());
	}

	@Override
	public void superTick() {
		super.B_();
	}

	@Override
	public boolean superAddPassenger(StdEntity entity, boolean flag) {
		return super.a(((NMSEntity) entity).getEntity(), flag);
	}

	@Override
	public void superDie() {
		super.die();
	}

	public void superDie(StdDamageSource damagesource) {
		super.die(((NMSDamageSource) damagesource).getDamageSource());
	}

	@Override
	public boolean superIsDropExperience() {
		return super.isDropExperience();
	}

	@Override
	public void superJump(int jump) {
		super.b_(jump);
	}

	@Override
	public void superOpenInventory(StdEntityHuman entityhuman) {
		super.c(((NMSEntityHuman) entityhuman).getEntityHuman());
	}

	@Override
	public boolean superSetLeashHolder(StdEntityHuman entityhuman) {
		return super.a(((NMSEntityHuman) entityhuman).getEntityHuman());
	}

	@Override
	public void superEnterLoveMode(Player player) {
		super.f(((CraftPlayer) player).getHandle());
	}

	@Override
	public StdEntityPlayer superGetBreedCause() {
		return super.getBreedCause() != null ? new NMSEntityPlayer(super.getBreedCause()) : null;
	}

	// ---------------------------------------------------------------------//
	// NMS methods
	// ---------------------------------------------------------------------//

	@Override
	public StdPacket nmsLeashHolderPacket() {
		return new NMSPacket(new PacketPlayOutAttachEntity(this, getLeashHolder()));
	}

	// ---------------------------------------------------------------------//
	// Translated methods
	// ---------------------------------------------------------------------//

	@Override
	public void b(NBTTagCompound nbttagcompound) {
		customSaveData(nbttagcompound);
	}

	@Override
	public void a(NBTTagCompound nbttagcompound) {
		customLoadData(nbttagcompound);
	}

	@Override
	protected void r() {
		customInitPathfinder();
	}
	
	@Override
	public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, GroupDataEntity groupdataentity) {
		return customPrepare(difficultydamagescaler, groupdataentity);
	}

	@Override
	public boolean isInvulnerable(DamageSource damagesource) {
		if (!custom)
			return super.isInvulnerable(damagesource);
		else
			return MMOHorseMethods.customIsInvulnerable(this, new NMSDamageSource(damagesource));
	}

	@Override
	public void B_() {
		if (custom)
			MMOHorseMethods.customTick(this);
		super.B_();
	}

	@Override
	public boolean a(net.minecraft.server.v1_12_R1.Entity entity, boolean flag) {
		if (!custom)
			return super.a(entity, flag);
		else
			return MMOHorseMethods.customAddPassenger(this,
					entity instanceof EntityPlayer ? new NMSEntityPlayer((EntityPlayer) entity) : new NMSEntity(entity),
					flag);
	}

	@Override
	public boolean a(EntityHuman entityhuman) {
		if (!custom)
			return super.a(entityhuman);
		else
			return MMOHorseMethods.customSetLeashHolder(this, new NMSEntityHuman(entityhuman));
	}

	@Override
	public void die() {
		if (!custom)
			super.die();
		else
			MMOHorseMethods.customDie(this);
	}

	@Override
	public void die(DamageSource damagesource) {
		if (!custom)
			super.die(damagesource);
		else
			MMOHorseMethods.customDie(this, new NMSDamageSource(damagesource));
	}

	@Override
	public boolean isDropExperience() {
		if (!custom)
			return super.isDropExperience();
		else
			return MMOHorseMethods.customIsDropExperience(this);
	}

	@Override
	public void b_(int jump) { // Required
		if (!custom)
			super.b_(jump);
		else
			MMOHorseMethods.customJump(this, jump);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityanimal) {
		if (!custom)
			return super.createChild(entityanimal);
		else
			return (EntityAgeable) MMOHorseMethods.customCreateChild(this, this.world.getWorld(),
					entityanimal instanceof EntityAnimal ? new NMSEntityAnimal((EntityAnimal) entityanimal) : null);
	}

	// ---------------------------------------------------------------------//
	// MMO abstract methods
	// ---------------------------------------------------------------------//
	
	@Override
	public World getWorld() {
		return super.world;
	}

	@Override
	public Entity getKiller() {
		return killer != null ? killer.getBukkitEntity() : null;
	}

	public EntityHorseAbstract getNMSEntity() {
		return this;
	}

	@Override
	public InventorySubcontainer getNMSInventory() {
		return inventoryChest;
	}

	@Override
	public List<net.minecraft.server.v1_12_R1.Entity> getPassengers() {
		return passengers;
	}

	@Override
	public Vector getMotion() {
		return new Vector(this.motX, this.motY, this.motZ);
	}

	@Override
	public void setRemoveWhenFarAway(boolean removewhenfaraway) {
		this.persistent = !removewhenfaraway;
	}

	@Override
	public boolean isValid() {
		return isAlive() && this.valid && getLocation().getChunk().isLoaded();
	}

	@Override
	public void setYawPitch(float yaw, float pitch) {
		super.setYawPitch(yaw, pitch);
	}
	
	@Override
	public void setPathfinder(PathfinderGoalSelector goalSelector) {
		this.goalSelector = goalSelector;
	}

}