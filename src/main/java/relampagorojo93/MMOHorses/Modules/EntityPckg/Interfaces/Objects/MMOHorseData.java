package relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Horse;

import relampagorojo93.LibsCollection.SpigotDebug.Data.DebugAlertData;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Enums.Colour;
import relampagorojo93.MMOHorses.Enums.Gender;
import relampagorojo93.MMOHorses.Enums.Marking;
import relampagorojo93.MMOHorses.Enums.WalkMode;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdAttributeInstance;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingDouble;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;

public class MMOHorseData {

	private MMOHorse entity;

	public MMOHorseData(MMOHorse entity) {
		this.entity = entity;
	}

	// ---------------------------------------------------------------------//
	// Initialize
	// ---------------------------------------------------------------------//

	public void initialize() {
		if (speed == -1 || jump == -1 || maxhealth == -1)
			regenerateAttributes(false);
		if (colour == null)
			setColour(Colour.values()[(new Random()).nextInt((Colour.values()).length)], false);
		if (marking == null)
			setMarking(Marking.values()[(new Random()).nextInt((Marking.values()).length)], false);
		if (gender == null)
			setGender(Gender.values()[(new Random()).nextInt(2)], false);
		setInitialized(true);
		MMOHorsesAPI.getEntityModule().checkEntity(this.entity.getBukkitEntity());
	}

	// ---------------------------------------------------------------------//
	// Horse Object
	// ---------------------------------------------------------------------//
	private ClaimedData claimed_data;

	public void setClaimedData(ClaimedData claimed_data) {
		this.claimed_data = claimed_data;
		if (this.claimed_data != null)
			mmo();
		else
			vanilla();
	}

	public ClaimedData getClaimedData() {
		return claimed_data;
	}

	public boolean isClaimed() {
		return claimed_data != null;
	}

	public void mmo() {
		if (isClaimed()) {
			this.entity.setAdult();
			this.entity.setRemoveWhenFarAway(false);
			this.entity.setTamed(true);
			this.entity.setOwner((AnimalTamer) Bukkit.getOfflinePlayer(claimed_data.getData().getOwner()));
			this.entity.setAsCustom(true);
			setGender(getClaimedData().getData().getGender(), false);
			setColour(claimed_data.getData().getColour(), false);
			setMarking(claimed_data.getData().getMarking(), false);
			setSpeed(claimed_data.getData().getSpeed(), false);
			setJump(claimed_data.getData().getJump(), false);
			setMaxHealth(claimed_data.getData().getHealth(), false);
			updateSaddle();
			updateBody();
			updateAccessory();
			setInitialized(true);
			MMOHorseMethods.customTick(this.entity);
		}
	}

	public void vanilla() {
		updateAll();
		setWalkmode(WalkMode.GALLOP);
		this.entity.setCustomEntityName("");
		this.entity.setRemoveWhenFarAway(true);
		MMOHorseMethods.customTick(this.entity);
	}

	// ---------------------------------------------------------------------//
	// Gender
	// ---------------------------------------------------------------------//
	private Gender gender = null;

	public void setGender(Gender gender, boolean tosql) {
		this.gender = gender;
		if (tosql && isClaimed())
			getClaimedData().getData().setGender(gender);
	}

	public Gender getGender() {
		return gender;
	}

	// ---------------------------------------------------------------------//
	// Marking & Colour
	// ---------------------------------------------------------------------//

	private Marking marking = null;

	private Colour colour = null;

	public void setVariant(int variant, boolean tosql) {
		this.marking = Marking
				.getByBukkit(Horse.Style.values()[(((variant & 0xFF00) >> 8) % Horse.Color.values().length)]);
		this.colour = Colour.getByBukkit(Horse.Color.values()[((variant & 0xFF) % Horse.Color.values().length)]);
		this.entity.updateVariant();
		if (tosql && isClaimed()) {
			getClaimedData().getData().setMarking(marking);
			getClaimedData().getData().setColour(colour);
		}
	}

	public void setMarking(Marking marking, boolean tosql) {
		this.marking = marking;
		this.entity.updateVariant();
		if (tosql && isClaimed())
			getClaimedData().getData().setMarking(marking);
	}

	public void setColour(Colour colour, boolean tosql) {
		this.colour = colour;
		this.entity.updateVariant();
		if (tosql && isClaimed())
			getClaimedData().getData().setColour(colour);
	}

	public Marking getMarking() {
		return marking;
	}

	public Colour getColour() {
		return colour;
	}

	// ---------------------------------------------------------------------//
	// Last activity
	// ---------------------------------------------------------------------//
	private long lastactivity = System.currentTimeMillis();

	public void setLastActivity(long lastactivity) {
		this.lastactivity = lastactivity;
	}

	public long getLastActivity() {
		return lastactivity;
	}

	// ---------------------------------------------------------------------//
	// Walkmode
	// ---------------------------------------------------------------------//
	private WalkMode walkmode = WalkMode.GALLOP, activewalkmode = WalkMode.GALLOP;

	public void setWalkmode(WalkMode walkmode) {
		this.walkmode = walkmode;
	}

	public boolean switchWalkMode(boolean next, boolean limit) {
		if (!limit || (next && walkmode.ordinal() < WalkMode.GALLOP.ordinal()) || (!next && walkmode.ordinal() > 0)) {
			int n = walkmode.ordinal() + (next ? 1 : -1);
			if (n > WalkMode.GALLOP.ordinal())
				n = 0;
			if (n < 0)
				n = WalkMode.GALLOP.ordinal();
			if (n > WalkMode.WALK.ordinal() && food < 0.3D)
				return false;
			this.walkmode = WalkMode.values()[n];
			return true;
		}
		return false;
	}

	public WalkMode getWalkmode() {
		return walkmode;
	}

	public void setActiveWalkmode(WalkMode activewalkmode) {
		this.activewalkmode = activewalkmode;
		updateSpeed();
		updateJump();
	}

	public WalkMode getActiveWalkmode() {
		return activewalkmode;
	}

	// ---------------------------------------------------------------------//
	// Initialize
	// ---------------------------------------------------------------------//
	private boolean initialized = false;

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	// ---------------------------------------------------------------------//
	// Lunging
	// ---------------------------------------------------------------------//
	private boolean lunging = false;

	public void startLunging() {
		lunging = true;
	}

	public boolean isLunging() {
		return lunging;
	}

	public void stopLunging() {
		lunging = false;
	}

	// ---------------------------------------------------------------------//
	// Food
	// ---------------------------------------------------------------------//
	private double food = 1D;

	public double getFood() {
		return food;
	}

	public void setFood(double food) {
		if (food < 0D)
			food = 0D;
		else if (food > 1D)
			food = 1D;
		this.food = food;
	}

	// ---------------------------------------------------------------------//
	// Starving cooldown
	// ---------------------------------------------------------------------//
	private int cooldown = 20;

	public boolean doStarveDamage() {
		if (--cooldown < 1)
			cooldown = 20;
		return cooldown == 20;
	}

	// ---------------------------------------------------------------------//
	// Attributes
	// ---------------------------------------------------------------------//

	private double speed = -1, jump = -1, maxhealth = -1;
	private double permaupgradedspeed, permaupgradedjump, permaupgradedmaxhealth, permaupgradedarmor;
	private double upgradedspeed, upgradedjump, upgradedmaxhealth, upgradedarmor;
	private double totalspeed, totaljump, totalmaxhealth, totalarmor;
	private double effectivespeed, effectivejump;
	private double nmsspeed, nmsjump;

	public double getSpeed() {
		return speed;
	}

	public double getJump() {
		return jump;
	}

	public double getMaxHealth() {
		return maxhealth;
	}

	public double getUpgradedSpeed() {
		return upgradedspeed;
	}

	public double getUpgradedJump() {
		return upgradedjump;
	}

	public double getUpgradedMaxHealth() {
		return upgradedmaxhealth;
	}

	public double getUpgradedArmor() {
		return upgradedarmor;
	}

	public double getPermanentUpgradedSpeed() {
		return permaupgradedspeed;
	}

	public double getPermanentUpgradedJump() {
		return permaupgradedjump;
	}

	public double getPermanentUpgradedMaxHealth() {
		return permaupgradedmaxhealth;
	}

	public double getPermanentUpgradedArmor() {
		return permaupgradedarmor;
	}

	public double getTotalSpeed() {
		return totalspeed;
	}

	public double getTotalJump() {
		return totaljump;
	}

	public double getTotalMaxHealth() {
		return totalmaxhealth;
	}

	public double getTotalArmor() {
		return totalarmor;
	}

	public double getEffectiveSpeed() {
		return effectivespeed;
	}

	public double getEffectiveJump() {
		return effectivejump;
	}

	public double getNMSSpeed() {
		return nmsspeed;
	}

	public double getNMSJump() {
		return nmsjump;
	}

	public void setSpeed(double speedinblocks, boolean tosql) {
		this.speed = speedinblocks;
		updateSpeed();
		if (tosql && isClaimed())
			getClaimedData().getData().setSpeed(speedinblocks);
	}

	public void updateSpeed() {
		this.upgradedspeed = getClaimedData() != null && getClaimedData().getUpgrades().getSpeedItem() != null
				? (speed * ((double) getClaimedData().getUpgrades().getSpeedItem().getValue() / 100D))
				: 0D;
		this.permaupgradedspeed = getClaimedData() != null
				? ((getClaimedData().getStats().getSpeedLevel() - 1) * speed
						* SettingDouble.SPEED_GIVEN_PER_LEVEL.toDouble() / 100D)
				: 0D;
		this.totalspeed = speed + upgradedspeed + permaupgradedspeed;
		this.effectivespeed = totalspeed * activewalkmode.getSpeedMultiplier();
		this.nmsspeed = MMOHorsesAPI.getUtils().blockToSpeed(effectivespeed);
		this.entity.getAttribute(StdAttributeInstance.AttributeType.GENERIC_MOVEMENT_SPEED).setBaseValue(nmsspeed);
	}

	public void setJump(double jumpinblocks, boolean tosql) {
		this.jump = jumpinblocks;
		updateJump();
		if (tosql && isClaimed())
			getClaimedData().getData().setJump(jumpinblocks);
	}

	public void updateJump() {
		this.upgradedjump = isClaimed() && getClaimedData().getUpgrades().getJumpItem() != null
				? (jump * ((double) getClaimedData().getUpgrades().getJumpItem().getValue() / 100D))
				: 0D;
		this.permaupgradedjump = isClaimed()
				? ((getClaimedData().getStats().getJumpLevel() - 1) * jump
						* SettingDouble.JUMP_GIVEN_PER_LEVEL.toDouble() / 100D)
				: 0D;
		this.totaljump = jump + upgradedjump + permaupgradedjump;
		this.effectivejump = totaljump * activewalkmode.getJumpMultiplier();
		this.nmsjump = MMOHorsesAPI.getUtils().blockToJump(effectivejump);
		this.entity.getAttribute(StdAttributeInstance.AttributeType.HORSE_JUMP_STRENGTH).setBaseValue(nmsjump);
	}

	public void setMaxHealth(double healthinhalfhearts, boolean tosql) {
		this.maxhealth = healthinhalfhearts;
		updateMaxHealth();
		if (tosql && isClaimed())
			getClaimedData().getData().setHealth(healthinhalfhearts);
	}

	public void updateMaxHealth() {
		this.upgradedmaxhealth = isClaimed() && getClaimedData().getUpgrades().getHealthItem() != null
				? getClaimedData().getUpgrades().getHealthItem().getValue()
				: 0D;
		this.permaupgradedmaxhealth = isClaimed()
				? (getClaimedData().getStats().getHealthLevel() - 1) * SettingDouble.HEALTH_GIVEN_PER_LEVEL.toDouble()
				: 0D;
		this.totalmaxhealth = maxhealth + upgradedmaxhealth + permaupgradedmaxhealth;
		this.entity.getAttribute(StdAttributeInstance.AttributeType.GENERIC_MAX_HEALTH).setBaseValue(totalmaxhealth);
		if (this.entity.getHealth() > totalmaxhealth)
			this.entity.setHealth(totalmaxhealth);
	}

	public void updateArmor() {
		this.upgradedarmor = isClaimed() && claimed_data.getUpgrades().getArmorItem() != null
				? claimed_data.getUpgrades().getArmorItem().getValue()
				: 0D;
		this.permaupgradedarmor = isClaimed()
				? (getClaimedData().getStats().getArmorLevel() - 1) * SettingDouble.ARMOR_GIVEN_PER_LEVEL.toDouble()
				: 0D;
		this.totalarmor = upgradedarmor + permaupgradedarmor;
		this.entity.getAttribute(StdAttributeInstance.AttributeType.GENERIC_ARMOR).setBaseValue(totalarmor);
	}

	public void updateAll() {
		updateAccessory();
		updateBody();
		updateSaddle();
		updateJump();
		updateSpeed();
		updateMaxHealth();
		updateArmor();
	}

	public void regenerateAttributes(boolean warn) {
		if (warn)
			MMOHorsesAPI.getDebugController().addDebugData(new DebugAlertData("Restarting stats from entity " + this.entity.getUniqueID().toString()
							+ " due outbounded stats (Type " + this.entity.getType().name() + ", H->"
							+ (getMaxHealth() < SettingDouble.MINIMUMHEALTH.toDouble()
									|| getMaxHealth() > SettingDouble.MAXIMUMHEALTH.toDouble() ? "!" : "")
							+ getMaxHealth() + ", S->"
							+ (getSpeed() < SettingDouble.MINIMUMSPEED.toDouble()
									|| getSpeed() > SettingDouble.MAXIMUMSPEED.toDouble() ? "!" : "")
							+ getSpeed() + ", J->" + (getJump() < SettingDouble.MINIMUMJUMP.toDouble()
									|| getJump() > SettingDouble.MAXIMUMJUMP.toDouble() ? "!" : "")
							+ getJump() + ")."));
		Random r = new Random();
		int health = (int) (SettingDouble.MINIMUMHEALTH.toDouble() + r
				.nextInt((int) (SettingDouble.MAXIMUMHEALTH.toDouble() - SettingDouble.MINIMUMHEALTH.toDouble()) + 1));
		double speed = SettingDouble.MINIMUMSPEED.toDouble()
				+ (r.nextDouble() * (SettingDouble.MAXIMUMSPEED.toDouble() - SettingDouble.MINIMUMSPEED.toDouble()));
		double jump = SettingDouble.MINIMUMJUMP.toDouble()
				+ (r.nextDouble() * (SettingDouble.MAXIMUMJUMP.toDouble() - SettingDouble.MINIMUMJUMP.toDouble()));
		setMaxHealth(health, true);
		setSpeed(speed, true);
		setJump(jump, true);
	}

	// ---------------------------------------------------------------------//
	// Items
	// ---------------------------------------------------------------------//

	public void updateBody() {
		if (this.entity.isAlive()) {
			if (isClaimed()) {
				if (claimed_data.getCosmetics().getBody() != null)
					this.entity.setBody(
							SettingBoolean.MAKECOSMETICSFUNCTIONAL.toBoolean() ? claimed_data.getCosmetics().getBody()
									: ItemStacksUtils.removeAttributes(claimed_data.getCosmetics().getBody()));
				else if (claimed_data.getUpgrades().getArmorItem() != null)
					this.entity.setBody(
							ItemStacksUtils.removeAttributes(claimed_data.getUpgrades().getArmorItem().getResult()));
				else
					this.entity.setBody(null);
				updateArmor();
			}
		}
	}

	public void updateSaddle() {
		if (this.entity.isAlive() && isClaimed()) {
			if (claimed_data.getCosmetics().getSaddle() != null)
				this.entity.setSaddle(claimed_data.getCosmetics().getSaddle());
			else if (claimed_data.getUpgrades().getSaddleItem() != null)
				this.entity.setSaddle(claimed_data.getUpgrades().getSaddleItem().getResult());
			else
				this.entity.setSaddle(null);
		}
	}

	public void updateAccessory() {
		if (this.entity.isAlive() && isClaimed()) {
			if ((claimed_data.getCosmetics().getAccessory() != null
					&& claimed_data.getCosmetics().getAccessory().getType() == Material.CHEST)
					|| claimed_data.getUpgrades().getChestItem() != null)
				this.entity.setCarryingChest(true);
			else
				this.entity.setCarryingChest(false);
		}
	}
}