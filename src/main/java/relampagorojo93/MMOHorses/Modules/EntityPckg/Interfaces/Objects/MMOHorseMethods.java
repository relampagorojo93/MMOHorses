package relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

import relampagorojo93.MMOHorses.Enums.DropReason;
import relampagorojo93.MMOHorses.Enums.Gender;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdDamageSource;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdEntity;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdEntityAnimal;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdEntityHuman;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.NBT.StdEntityPlayer;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingDouble;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;
import relampagorojo93.MMOHorses.Modules.UtilsPckg.PHApplierPckg.Instances.PHDataApplyRequest;
import relampagorojo93.MMOHorses.Modules.UtilsPckg.PHApplierPckg.Instances.PHDeathApplyRequest;
import relampagorojo93.MMOHorses.Enums.WalkMode;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.API.Hooks.LandsAPI;
import relampagorojo93.MMOHorses.API.Hooks.WorldGuardAPI;

public class MMOHorseMethods {

	// ---------------------------------------------------------------------//
	// Custom methods
	// ---------------------------------------------------------------------//

	public static boolean customIsInvulnerable(MMOHorse horse, StdDamageSource damagesource) {
		List<String> block = new ArrayList<>();
		for (String str : horse.getMMOHorseData().isClaimed() ? SettingList.BLOCK_CLAIMED_HORSE_DAMAGE_TYPES.toList()
				: SettingList.BLOCK_UNCLAIMED_HORSE_DAMAGE_TYPES.toList())
			block.add(str.toUpperCase());
		return block.contains(damagesource.getTranslationIndex().toUpperCase())
				|| horse.superIsInvulnerable(damagesource);
	}

	public static void customTick(MMOHorse horse) {
		if (horse.isAlive()) {
			ClaimedData hi = horse.getMMOHorseData().getClaimedData();
			Player rider = horse.getEntityPassengers().size() > 0
					&& horse.getEntityPassengers().get(0) instanceof Player
							? (Player) horse.getEntityPassengers().get(0)
							: null;
			WalkMode limit = rider != null ? horse.getMMOHorseData().getWalkmode() : WalkMode.GALLOP;
			if (hi != null && ((rider == null && hi.getSettings().blockSpeedOnUnmount() && !horse.isInLove()
					&& !horse.getMMOHorseData().isLunging())
					|| (rider != null
							&& (hi.getUpgrades().getSaddleItem() == null && hi.getCosmetics().getSaddle() != null
									&& !SettingBoolean.MAKECOSMETICSFUNCTIONAL.toBoolean()))))
				limit = WalkMode.HALT;
			if (rider != null) {
				horse.getMMOHorseData().setLastActivity(System.currentTimeMillis());
				if (limit != WalkMode.HALT) {
					if (SettingBoolean.FOODSYSTEM.toBoolean() && horse.getMMOHorseData().getFood() <= 0.3D
							&& limit.ordinal() > WalkMode.WALK.ordinal())
						limit = WalkMode.WALK;
					else {
						for (Entity en : horse.getEntityPassengers()) {
							if (en instanceof Player) {
								Player pl = (Player) en;
								if (WorldGuardAPI.isHooked() && horse.getMMOHorseData().getWalkmode().ordinal() > 1
										&& !WorldGuardAPI.getSpeedLimitFlag().flagState(pl)) {
									limit = WalkMode.WALK;
									break;
								}
								if (LandsAPI.isHooked()) {
									WalkMode walk = LandsAPI.checkRestriction(pl);
									if (walk != null
											&& horse.getMMOHorseData().getWalkmode().ordinal() > walk.ordinal())
										limit = walk;
								}
								break;
							}
						}
					}
				}
				if (SettingBoolean.SPEEDACTIONBAR.toBoolean() || limit == WalkMode.HALT
						|| (!SettingBoolean.MAKECOSMETICSFUNCTIONAL.toBoolean() && hi != null
								&& hi.getUpgrades().getSaddleItem() == null))
					new PHDataApplyRequest(horse, rider,
							MessageString.SPEEDBAR_BARFORMAT.toString() + (SettingBoolean.MAKECOSMETICSFUNCTIONAL
									.toBoolean()
									&& (horse.getSaddle() == null || horse.getSaddle().getType() == Material.AIR)
											? MessageString.SPEEDBAR_NOSADDLE.toString()
											: (!SettingBoolean.MAKECOSMETICSFUNCTIONAL.toBoolean() && hi != null
													&& hi.getUpgrades().getSaddleItem() == null)
															? MessageString.SPEEDBAR_NOSADDLEUPGRADE.toString()
															: "")).generate().sendActionBar(rider);
			}
			if (hi != null) {
				int g = (int) (horse.getHealth() * 3.0D / horse.getMMOHorseData().getTotalMaxHealth());
				String tag = hi.getSettings().isNameTagVisible() ? MessagesUtils.color(hi.getData().getName()) : "";
				tag = String.valueOf(tag) + (tag.isEmpty() ? "" : " ") + (hi.getSettings().isHealthTagVisible()
						? (" " + ((g == 0) ? "&c" : ((g == 1) ? "&e" : "&a")) + (int) horse.getHealth() + "&4\u2764")
						: "");
				horse.setCustomEntityName(MessagesUtils.color(tag));
			}
			if (horse.getMMOHorseData().getActiveWalkmode() != limit)
				horse.getMMOHorseData().setActiveWalkmode(limit);
			if (SettingBoolean.FOODSYSTEM.toBoolean() && horse.getMMOHorseData().getFood() == 0D
					&& horse.getMMOHorseData().doStarveDamage())
				horse.damage(1D);
			if (horse.getHealth() <= 0D)
				return;
			if (horse.getMMOHorseData().isLunging() && horse.isValid()
					&& !horse.getLocation().getBlock().getType().name().contains("WATER")) {
				if (horse.getHolder() == null) {
					horse.getMMOHorseData().stopLunging();
					return;
				}
				int rad = 4;
				Location pll = horse.getHolder().getLocation();
				Location hl = horse.getLocation();
				Vector pth = new Vector(hl.getX() - pll.getX(), 0.0D, hl.getZ() - pll.getZ());
				Vector pp = (new Vector(-pth.getZ(), 0.0D, pth.getX())).normalize();
				Location ra = pll.clone().add(pth.normalize().multiply(rad));
				Vector r = new Vector(ra.getX() - hl.getX(), 0.0D, ra.getZ() - hl.getZ());
				Vector f = pp.add(r).setY(0).normalize()
						.multiply(horse.getMMOHorseData().getEffectiveSpeed() * 0.0125D);
				Location d = horse.getLocation().add(f);
				d.setY(pll.getY());
				if (!d.getBlock().isEmpty())
					d = horse.getLocation();
				setDirection(horse, f);
				horse.setMotion(f.getX(), horse.getMotion().getY(), f.getZ());
			}
		}
	}

	public static boolean customAddPassenger(MMOHorse horse, StdEntity entity, boolean flag) {
		ClaimedData ho = horse.getMMOHorseData().getClaimedData();
		if (entity instanceof StdEntityPlayer && ho != null
				&& !entity.getBukkitEntity().hasPermission("horse.bypassmount")
				&& !MMOHorsesAPI.getUtils().isSame(ho.getData().getOwner(), entity.getUniqueID())
				&& ho.getData().getTrusted(entity.getUniqueID()) == null) {
			horse.superOpenInventory((StdEntityPlayer) entity);
			return false;
		}
		if (entity instanceof StdEntityPlayer && SettingBoolean.OPENONMOUNT.toBoolean())
			horse.superOpenInventory((StdEntityPlayer) entity);
		return horse.superAddPassenger(entity, flag);
	}

	public static boolean customSetLeashHolder(MMOHorse horse, StdEntityHuman entityhuman) {
		boolean accept = horse.superSetLeashHolder(entityhuman)
				&& (horse.getMMOHorseData().getClaimedData() == null || horse.getMMOHorseData().getClaimedData()
						.getData().getOwner().compareTo(entityhuman.getUniqueID()) == 0);
		if (!accept)
			entityhuman.sendPacket(horse.nmsLeashHolderPacket());
		return accept;
	}

	public static void customDie(MMOHorse horse, StdDamageSource damagesource) {
		if (horse.getInventory() != null && (horse.superIsDropExperience() || horse.getMMOHorseData().isClaimed()))
			horse.getInventory().clear();
		horse.superDie(damagesource);
		horse.ejectPassengers();
		if (horse.getMMOHorseData().isClaimed()) {
			ClaimedData claimed = horse.getMMOHorseData().getClaimedData();
			claimed.getSettings().setLink(false);
			Entity killer = horse.getKiller();
			String msg = killer != null ? MessageString.BROADCASTDEATH_BYPLAYER.toString()
					: MessageString.BROADCASTDEATH_BYOTHER.toString();
			if (msg != null && !msg.isEmpty()) {
				Collection<? extends Player> collection = Bukkit.getOnlinePlayers();
				new PHDeathApplyRequest(claimed, killer, msg).generate().sendMessage(collection.toArray(new Player[collection.size()]));
			}
			if (SettingBoolean.RESPAWN.toBoolean()) {
				claimed.getData().setDeathTime();
				claimed.setMMOHorse(null);
			} else
				MMOHorsesAPI.getHorseModule().deleteHorseObject(claimed);
			if (!SettingBoolean.RESPAWN.toBoolean() || SettingBoolean.OVERRIDERESPAWN.toBoolean())
				claimed.drop(DropReason.DEATH, horse.getLocation());
		}
	}

	public static boolean customIsDropExperience(MMOHorse horse) {
		if (SettingBoolean.RESPAWN.toBoolean() && !SettingBoolean.OVERRIDERESPAWN.toBoolean())
			return false;
		if (horse.getMMOHorseData().isClaimed() && SettingBoolean.DISABLEHORSEVANILLADROPS.toBoolean())
			return false;
		if (!horse.getMMOHorseData().isClaimed() && SettingBoolean.DISABLENONREGISTEREDHORSEVANILLADROPS.toBoolean())
			return false;
		return horse.superIsDropExperience();
	}

	public static void customDie(MMOHorse horse) {
		if (horse.getMMOHorseData().isClaimed() && horse.getHealth() > 0)
			return;
		else
			horse.superDie();
	}

	public static void customJump(MMOHorse horse, int jump) {
		if (SettingBoolean.FOODSYSTEM.toBoolean())
			horse.getMMOHorseData()
					.setFood(horse.getMMOHorseData().getFood() - SettingDouble.FOODCONSUMPERJUMP.toDouble());
		horse.superJump(jump);
	}

	// ---------------------------------------------------------------------//
	// Misc methods
	// ---------------------------------------------------------------------//

	public static void setDirection(MMOHorse horse, Vector v) {
		double _2PI = 6.283185307179586D;
		double x = v.getX();
		double z = v.getZ();
		if (x == 0.0D && z == 0.0D) {
			float f = ((v.getY() > 0.0D) ? -90 : 90);
			horse.setDirection(0.0F, f);
			return;
		}
		double theta = Math.atan2(-x, z);
		float yaw = (float) Math.toDegrees((theta + _2PI) % _2PI);
		double x2 = NumberConversions.square(x);
		double z2 = NumberConversions.square(z);
		double xz = Math.sqrt(x2 + z2);
		float pitch = (float) Math.toDegrees(Math.atan(-v.getY() / xz));
		horse.setDirection(yaw, pitch);
	}

	public static MMOHorse customCreateChild(MMOHorse m, World world, StdEntityAnimal entityanimal) {
		if (entityanimal == null || !canMakeLove(m, entityanimal))
			return null;
		MMOHorse f = MMOHorsesAPI.getUtils().toMMOHorse(entityanimal.getBukkitEntity());
		MMOHorse h = Math.random() * 2 == 0 ? m.createEntity(world) : f.createEntity(world);
		double r = Math.random();
		h.getMMOHorseData().setSpeed((f.getMMOHorseData().getSpeed() * r) + (m.getMMOHorseData().getSpeed() * (1 - r)),
				false);
		r = Math.random();
		h.getMMOHorseData().setJump((f.getMMOHorseData().getJump() * r) + (m.getMMOHorseData().getJump() * (1 - r)),
				false);
		r = Math.random();
		h.getMMOHorseData().setMaxHealth(
				(f.getMMOHorseData().getMaxHealth() * r) + (m.getMMOHorseData().getMaxHealth() * (1 - r)), false);
		h.getMMOHorseData().setMarking(
				Math.random() * 2 == 0 ? m.getMMOHorseData().getMarking() : f.getMMOHorseData().getMarking(), false);
		h.getMMOHorseData().setColour(
				Math.random() * 2 == 0 ? m.getMMOHorseData().getColour() : f.getMMOHorseData().getColour(), false);
		return h;
	}

	public static boolean canMakeLove(MMOHorse horse, StdEntityAnimal entityanimal) {
		StdEntityPlayer entityplayer = horse.superGetBreedCause();
		MMOHorse ent = MMOHorsesAPI.getUtils().toMMOHorse(entityanimal.getBukkitEntity());
		if (ent != null && ent.isCustom()) {
			if (SettingBoolean.GENDERS.toBoolean() && (horse.getMMOHorseData().getGender() == Gender.GELDING
					|| ent.getMMOHorseData().getGender() == Gender.GELDING
					|| horse.getMMOHorseData().getGender() == ent.getMMOHorseData().getGender())) {
				if (entityplayer != null)
					MessagesUtils.getMessageBuilder()
							.createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.NOBRED))
							.sendMessage(entityplayer.getBukkitEntity());
				horse.resetLove();
				entityanimal.resetLove();
				return false;
			}
		}
		return true;
	}
}