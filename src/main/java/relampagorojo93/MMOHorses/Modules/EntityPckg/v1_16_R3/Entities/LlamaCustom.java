package relampagorojo93.MMOHorses.Modules.EntityPckg.v1_16_R3.Entities;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_16_R3.DamageSource;
import net.minecraft.server.v1_16_R3.DifficultyDamageScaler;
import net.minecraft.server.v1_16_R3.EntityAgeable;
import net.minecraft.server.v1_16_R3.EntityAnimal;
import net.minecraft.server.v1_16_R3.EntityHorseAbstract;
import net.minecraft.server.v1_16_R3.EntityHuman;
import net.minecraft.server.v1_16_R3.EntityLlama;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.EnumMobSpawn;
import net.minecraft.server.v1_16_R3.GroupDataEntity;
import net.minecraft.server.v1_16_R3.InventorySubcontainer;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_16_R3.PathfinderGoalSelector;
import net.minecraft.server.v1_16_R3.World;
import net.minecraft.server.v1_16_R3.WorldAccess;
import net.minecraft.server.v1_16_R3.WorldServer;
import relampagorojo93.MMOHorses.Enums.Colour;
import relampagorojo93.MMOHorses.Enums.Type;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.MMOHorseData;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.MMOHorseMethods;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdDamageSource;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdEntity;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdEntityHuman;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdEntityPlayer;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdPacket;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_16_R3.NBTTags.NMSDamageSource;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_16_R3.NBTTags.NMSEntity;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_16_R3.NBTTags.NMSEntityAnimal;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_16_R3.NBTTags.NMSEntityHuman;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_16_R3.NBTTags.NMSEntityPlayer;
import relampagorojo93.MMOHorses.Modules.EntityPckg.v1_16_R3.NBTTags.NMSPacket;

public class LlamaCustom extends EntityLlama implements NMSMMOHorse {

	// ---------------------------------------------------------------------//
	// Objects
	// ---------------------------------------------------------------------//

	private MMOHorseData data = new MMOHorseData(this);
	private boolean custom = false;

	// ---------------------------------------------------------------------//
	// Entity initializers
	// ---------------------------------------------------------------------//
	
	public LlamaCustom(EntityTypes<? extends EntityLlama> entitytypes, World w) { // Required
		super(entitytypes, w);
		data.initialize();
	}

	public LlamaCustom(Location l) {
		this(EntityTypes.LLAMA, ((CraftWorld) l.getWorld()).getHandle());
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
		return Type.LLAMA;
	}

	@Override
	public LlamaCustom createEntity(org.bukkit.World world) {
		return createEntity(((CraftWorld) world).getHandle());
	}

	public LlamaCustom createEntity(World world) {
		return new LlamaCustom(EntityTypes.LLAMA, world);
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
		super.saveData(nbtcompound);
	}

	@Override
	public void superLoadData(NBTTagCompound nbtcompound) {
		super.loadData(nbtcompound);
	}

	@Override
	public void superInitPathfinder() {
		super.initPathfinder();
	}

	@Override
	public GroupDataEntity superPrepare(WorldAccess var0, DifficultyDamageScaler var1, EnumMobSpawn var2,
			GroupDataEntity var3, NBTTagCompound var4) {
		return super.prepare(var0, var1, var2, var3, var4);
	}

	@Override
	public boolean superIsInvulnerable(StdDamageSource damagesource) {
		return super.isInvulnerable(((NMSDamageSource) damagesource).getDamageSource());
	}

	@Override
	public void superTick() {
		super.tick();
	}

	@Override
	public boolean superAddPassenger(StdEntity entity, boolean flag) {
		return super.addPassenger(((NMSEntity) entity).getEntity());
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
		super.b(jump);
	}

	@Override
	public void superOpenInventory(StdEntityHuman entityhuman) {
		super.f(((NMSEntityHuman) entityhuman).getEntityHuman());
	}

	@Override
	public boolean superSetLeashHolder(StdEntityHuman entityhuman) {
		return super.a(((NMSEntityHuman) entityhuman).getEntityHuman());
	}

	@Override
	public void superEnterLoveMode(Player player) {
		super.g(((CraftPlayer) player).getHandle());
	}

	@Override
	public StdEntityPlayer superGetBreedCause() {
		return super.getBreedCause() != null ? new NMSEntityPlayer(super.getBreedCause()) : null;
	}
	
	@Override
	public int superGetVariantRaw() {
		return super.getVariant();
	}

	// ---------------------------------------------------------------------//
	// NMS methods
	// ---------------------------------------------------------------------//

	@Override
	public StdPacket nmsLeashHolderPacket() {
		return new NMSPacket(new PacketPlayOutAttachEntity(this, getLeashHolder()));
	}

	@Override
	public boolean cW() {
		return isDropExperience();
	}

	@Override
	public void dropInventory() {
		if (!getMMOHorseData().isClaimed())
			super.dropInventory();
	}

	// ---------------------------------------------------------------------//
	// Translated methods
	// ---------------------------------------------------------------------//

	@Override
	public void saveData(NBTTagCompound nbttagcompound) {
		customSaveData(nbttagcompound);
	}

	@Override
	public void loadData(NBTTagCompound nbttagcompound) {
		customLoadData(nbttagcompound);
	}

	@Override
	protected void initPathfinder() {
		customInitPathfinder();
	}

	@Override
	public GroupDataEntity prepare(WorldAccess var0, DifficultyDamageScaler var1, EnumMobSpawn var2, GroupDataEntity var3, NBTTagCompound var4) {
		return customPrepare(var0, var1, var2, var3, var4);
	}

	@Override
	public boolean isInvulnerable(DamageSource damagesource) {
		if (!custom)
			return super.isInvulnerable(damagesource);
		else
			return MMOHorseMethods.customIsInvulnerable(this, new NMSDamageSource(damagesource));
	}

	@Override
	public void tick() {
		if (custom)
			MMOHorseMethods.customTick(this);
		super.tick();
	}

	@Override
	protected boolean addPassenger(net.minecraft.server.v1_16_R3.Entity entity) {
		if (!custom)
			return super.addPassenger(entity);
		else
			return MMOHorseMethods.customAddPassenger(this,
					entity instanceof EntityPlayer ? new NMSEntityPlayer((EntityPlayer) entity) : new NMSEntity(entity),
					false);
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
	public void b(int jump) { // Required
		if (!custom)
			super.b(jump);
		else
			MMOHorseMethods.customJump(this, jump);
	}

	@Override
	public EntityLlama createChild(WorldServer worldserver, EntityAgeable entityanimal) {
		if (!custom)
			return super.createChild(worldserver, entityanimal);
		else
			return (EntityLlama) MMOHorseMethods.customCreateChild(this, this.world.getWorld(),
					entityanimal instanceof EntityAnimal ? new NMSEntityAnimal((EntityAnimal) entityanimal) : null);
	}

	// ---------------------------------------------------------------------//
	// MMO abstract methods
	// ---------------------------------------------------------------------//

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
	
	// ---------------------------------------------------------------------//
	// Entity specific methods
	// ---------------------------------------------------------------------//

	@Override
	public void updateVariant() {
		setVariant(getVariant());
	}

	@Override
	public void setBody(ItemStack item) {
		this.inventoryChest.setItem(1, CraftItemStack.asNMSCopy(item));
	}

	@Override
	public ItemStack getBody() {
		return CraftItemStack.asBukkitCopy(this.inventoryChest.getItem(1));
	}
	
	@Override
	public int getVariant() {
		Colour colour = getMMOHorseData().getColour();
		return colour != null && colour.toBukkitLlama() != null ? colour.toBukkitLlama().ordinal() : Colour.values()[0].toBukkitLlama().ordinal();
	}
	
}