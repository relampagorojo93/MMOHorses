package relampagorojo93.MMOHorses.Modules.UtilsPckg.PHApplierPckg;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.SpigotMessages.Instances.TextReplacement;
import relampagorojo93.MMOHorses.Enums.WalkMode;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.MMOHorseData;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingDouble;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingInt;
import relampagorojo93.MMOHorses.Modules.UtilsPckg.PHApplierPckg.Instances.PHApplyRequest;
import relampagorojo93.MMOHorses.Modules.UtilsPckg.PHApplierPckg.Instances.PHDataApplyRequest;
import relampagorojo93.MMOHorses.Modules.UtilsPckg.PHApplierPckg.Instances.PHDeathApplyRequest;

public class PHReplacements {

	public static String horseFoodBar(PHApplyRequest request) {
		return MessagesUtils.generateBar(getMMOHorseData(request).getFood(), SettingInt.BARLENGTH.toInt(),
				ChatColor.GOLD);
	}

	public static String horseSpeedBar(PHApplyRequest request) {
		return MessagesUtils.generateBar((double) getMMOHorseData(request).getActiveWalkmode().ordinal()
				/ (double) (WalkMode.values().length - 1), SettingInt.BARLENGTH.toInt(), ChatColor.YELLOW);
	}

	public static String horseWalkMode(PHApplyRequest request) {
		return getMMOHorseData(request).getActiveWalkmode().toString();
	}

	public static String horseWalkModePercent(PHApplyRequest request) {
		return String
				.valueOf(getMMOHorseData(request).getActiveWalkmode().ordinal() * 100 / (WalkMode.values().length - 1));
	}

	public static String horseSpeedActual(PHApplyRequest request) {
		return MessagesUtils.round(getMMOHorseData(request).getEffectiveSpeed(), 2);
	}

	public static String horseJumpActual(PHApplyRequest request) {
		return MessagesUtils.round(getMMOHorseData(request).getEffectiveJump(), 2);
	}

	public static String horseHealthActual(PHApplyRequest request) {
		return MessagesUtils.round(request.getMMOHorse().getHealth(), 2);
	}

	public static String horseUUID(PHApplyRequest request) {
		return request.getMMOHorse().getUniqueID().toString();
	}

	public static String horseName(PHApplyRequest request) {
		return request.getClaimedData() != null ? request.getClaimedData().getData().getName()
				: (!request.getMMOHorse().getCustomEntityName().isEmpty() ? request.getMMOHorse().getCustomEntityName()
						: "???");
	}

	public static String horseSpeedAmount(PHApplyRequest request) {
		return MessagesUtils.round(request.getClaimedData() != null ? request.getClaimedData().getData().getSpeed()
				: getMMOHorseData(request).getSpeed(), 2);
	}

	public static String horseJumpAmount(PHApplyRequest request) {
		return MessagesUtils.round(request.getClaimedData() != null ? request.getClaimedData().getData().getJump()
				: getMMOHorseData(request).getJump(), 2);
	}

	public static String horseHealthAmount(PHApplyRequest request) {
		return MessagesUtils.round(request.getClaimedData() != null ? request.getClaimedData().getData().getHealth()
				: getMMOHorseData(request).getMaxHealth(), 2);
	}

	public static String horseGender(PHApplyRequest request) {
		return (request.getClaimedData() != null ? request.getClaimedData().getData().getGender()
				: getMMOHorseData(request).getGender()).toString();
	}

	public static String horseId(PHApplyRequest request) {
		return String.valueOf(request.getClaimedData().getData().getId());
	}

	public static String horseHealthUpgradeAmount(PHApplyRequest request) {
		return MessagesUtils.round(request.getClaimedData().getUpgrades().getUpgradedHealth(), 2);
	}

	public static String horseJumpUpgradeAmount(PHApplyRequest request) {
		return MessagesUtils.round(request.getClaimedData().getUpgrades().getUpgradedJump(), 2);
	}

	public static String horseSpeedUpgradeAmount(PHApplyRequest request) {
		return MessagesUtils.round(request.getClaimedData().getUpgrades().getUpgradedSpeed(), 2);
	}

	public static String horsePermanentHealthUpgradeAmount(PHApplyRequest request) {
		return MessagesUtils.round((request.getClaimedData().getStats().getHealthLevel() - 1)
				* SettingDouble.HEALTH_GIVEN_PER_LEVEL.toDouble(), 2);
	}

	public static String horsePermanentJumpUpgradeAmount(PHApplyRequest request) {
		return MessagesUtils.round(
				request.getClaimedData().getData().getJump() * (request.getClaimedData().getStats().getJumpLevel() - 1)
						* SettingDouble.JUMP_GIVEN_PER_LEVEL.toDouble() / 100D,
				2);
	}

	public static String horsePermanentSpeedUpgradeAmount(PHApplyRequest request) {
		return MessagesUtils.round(request.getClaimedData().getData().getSpeed()
				* (request.getClaimedData().getStats().getSpeedLevel() - 1)
				* SettingDouble.SPEED_GIVEN_PER_LEVEL.toDouble() / 100D, 2);
	}

	public static String horsePermanentArmorUpgradeAmount(PHApplyRequest request) {
		return MessagesUtils.round((request.getClaimedData().getStats().getArmorLevel() - 1)
				* SettingDouble.ARMOR_GIVEN_PER_LEVEL.toDouble(), 2);
	}

	public static String horseLevel(PHApplyRequest request) {
		return String.valueOf(request.getClaimedData().getStats().getHorseLevel());
	}

	public static String horseOwner(PHApplyRequest request) {
		OfflinePlayer owner = Bukkit.getOfflinePlayer(request.getClaimedData().getData().getOwner());
		return (owner != null && owner.getName() != null) ? owner.getName() : "???";
	}

	public static String horsePrice(PHApplyRequest request) {
		return (request.getClaimedData().getData().getPrice() != 0.0D)
				? MessageString.HORSEINFO_PRICE.toString().replace("%horse_price%",
						MessagesUtils.round(request.getClaimedData().getData().getPrice(), 2))
				: MessageString.HORSEINFO_NOTINSALE.toString();
	}

	public static String horseLifeTimeDate(PHApplyRequest request) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(request.getClaimedData().getData().getLifeTime());
	}

	public static String horseLifeTime(PHApplyRequest request) {
		return String.valueOf(
				(System.currentTimeMillis() - request.getClaimedData().getData().getLifeTime().getTime()) / 86400000L);
	}

	public static String horseDeathTimeDate(PHApplyRequest request) {
		return (request.getClaimedData().getData().getDeathTime().getTime() > System.currentTimeMillis() - 63072000000L)
				? (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(
						request.getClaimedData().getData().getDeathTime())
				: MessageString.HORSEINFO_NEVER.toString();
	}

	public static String horseDeathTime(PHApplyRequest request) {
		return (request.getClaimedData().getData().getDeathTime().getTime() > System.currentTimeMillis() - 63072000000L)
				? String.valueOf(
						(System.currentTimeMillis() - request.getClaimedData().getData().getDeathTime().getTime())
								/ 86400000L)
				: "---";
	}

	public static String horseSaddle(PHApplyRequest request) {
		return (request.getClaimedData().getUpgrades().getSaddleItem() != null
				|| (SettingBoolean.MAKECOSMETICSFUNCTIONAL.toBoolean()
						&& request.getClaimedData().getCosmetics().getSaddle() != null))
								? MessageString.HORSEINFO_YES.toString()
								: MessageString.HORSEINFO_NO.toString();
	}

	public static String horseStatus(PHApplyRequest request) {
		return (request.getClaimedData().getMMOHorse() != null && request.getClaimedData().getMMOHorse().isAlive())
				? MessageString.COMMONGUI_SUMMONEDTEXT.toString()
				: MessageString.COMMONGUI_UNSUMMONEDTEXT.toString();
	}

	public static String horseArmorUpgradeAmount(PHApplyRequest request) {
		return horseArmor(request);
	}

	public static String horseArmor(PHApplyRequest request) {
		return MessagesUtils.round(request.getClaimedData().getUpgrades().getArmorItem() != null
				? request.getClaimedData().getUpgrades().getArmorItem().getValue()
				: 0D, 2);
	}

	public static String horseIsDead(PHApplyRequest request) {
		return ((int) ((System.currentTimeMillis() - request.getClaimedData().getData().getDeathTime().getTime())
				/ 1000L) < SettingInt.RESPAWNCOOLDOWN.toInt())
				&& (request.getMMOHorse() == null || !request.getMMOHorse().isAlive())
						? MessageString.COMMONGUI_DEADTEXT.toString()
						: MessageString.COMMONGUI_ALIVETEXT.toString();
	}

	public static String horseKiller(PHDeathApplyRequest request) {
		return request.getKiller() != null
				? ((request.getKiller().getCustomName() != null && !request.getKiller().getCustomName().isEmpty())
						? request.getKiller().getCustomName()
						: (request.getKiller().getName() != null && !request.getKiller().getName().isEmpty()
								? request.getKiller().getName()
								: "???"))
				: "???";
	}

	private static MMOHorseData getMMOHorseData(PHApplyRequest request) {
		return request.getMMOHorse().getMMOHorseData();
	}

	public static TextReplacement[] generateReplacements(PHApplyRequest request) {
		List<TextReplacement> replacements = new ArrayList<>();
		switch (request.getRequestType()) {
		case DATA:
			PHDataApplyRequest datarequest = (PHDataApplyRequest) request;
			if (request.getMMOHorse() != null) {
				replacements.add(new TextReplacement("%horse_food_bar%", () -> horseFoodBar(datarequest)));
				replacements.add(new TextReplacement("%horse_speed_bar%", () -> horseSpeedBar(datarequest)));
				replacements.add(new TextReplacement("%horse_walkmode%", () -> horseWalkMode(datarequest)));
				replacements
						.add(new TextReplacement("%horse_walkmode_percent%", () -> horseWalkModePercent(datarequest)));
				replacements.add(new TextReplacement("%horse_speed_actual%", () -> horseSpeedActual(datarequest)));
				replacements.add(new TextReplacement("%horse_jump_actual%", () -> horseJumpActual(datarequest)));
				replacements.add(new TextReplacement("%horse_health_actual%", () -> horseHealthActual(datarequest)));
				replacements.add(new TextReplacement("%horse_uuid%", () -> horseUUID(datarequest)));
			} else
				for (String placeholder : new String[] { "%horse_food_bar%", "%horse_speed_bar%", "%horse_walkmode%",
						"%horse_walkmode_percent%", "%horse_speed_actual%", "%horse_jump_actual%",
						"%horse_health_actual%", "%horse_uuid%" })
					replacements.add(new TextReplacement(placeholder, () -> "???"));
			replacements.add(new TextReplacement("%horse_name%", () -> horseName(datarequest)));
			replacements.add(new TextReplacement("%horse_speed_amount%", () -> horseSpeedAmount(datarequest)));
			replacements.add(new TextReplacement("%horse_jump_amount%", () -> horseJumpAmount(datarequest)));
			replacements.add(new TextReplacement("%horse_health_amount%", () -> horseHealthAmount(datarequest)));
			replacements.add(new TextReplacement("%horse_gender%", () -> horseGender(datarequest)));
			if (request.getClaimedData() != null) {
				replacements.add(new TextReplacement("%horse_id%", () -> horseId(request)));
				replacements.add(new TextReplacement("%horse_healthupgrade_amount%",
						() -> horseHealthUpgradeAmount(datarequest)));
				replacements.add(
						new TextReplacement("%horse_jumpupgrade_amount%", () -> horseJumpUpgradeAmount(datarequest)));
				replacements.add(
						new TextReplacement("%horse_speedupgrade_amount%", () -> horseSpeedUpgradeAmount(datarequest)));
				replacements.add(new TextReplacement("%horse_permanenthealthupgrade_amount%",
						() -> horsePermanentHealthUpgradeAmount(datarequest)));
				replacements.add(new TextReplacement("%horse_permanentjumpupgrade_amount%",
						() -> horsePermanentJumpUpgradeAmount(datarequest)));
				replacements.add(new TextReplacement("%horse_permanentspeedupgrade_amount%",
						() -> horsePermanentSpeedUpgradeAmount(datarequest)));
				replacements.add(new TextReplacement("%horse_permanentarmorupgrade_amount%",
						() -> horsePermanentArmorUpgradeAmount(datarequest)));
				replacements.add(new TextReplacement("%horse_level%", () -> horseLevel(datarequest)));
				replacements.add(new TextReplacement("%horse_owner%", () -> horseOwner(datarequest)));
				replacements.add(new TextReplacement("%horse_price%", () -> horsePrice(datarequest)));
				replacements.add(new TextReplacement("%horse_lifetime_date%", () -> horseLifeTimeDate(datarequest)));
				replacements.add(new TextReplacement("%horse_lifetime%", () -> horseLifeTime(datarequest)));
				replacements.add(new TextReplacement("%horse_deathtime_date%", () -> horseDeathTimeDate(datarequest)));
				replacements.add(new TextReplacement("%horse_deathtime%", () -> horseDeathTime(datarequest)));
				replacements.add(new TextReplacement("%horse_saddle%", () -> horseSaddle(datarequest)));
				replacements.add(new TextReplacement("%horse_is_dead%", () -> horseIsDead(datarequest)));
				replacements.add(new TextReplacement("%horse_status%", () -> horseStatus(datarequest)));
				replacements.add(
						new TextReplacement("%horse_armorupgrade_amount%", () -> horseArmorUpgradeAmount(datarequest)));
				replacements.add(new TextReplacement("%horse_armor%", () -> horseArmor(datarequest)));
			}
			break;
		case DEATH:
			PHDeathApplyRequest deathrequest = (PHDeathApplyRequest) request;
			replacements.add(new TextReplacement("%horse_name%", () -> horseName(deathrequest)));
			replacements.add(new TextReplacement("%horse_speed_amount%", () -> horseSpeedAmount(deathrequest)));
			replacements.add(new TextReplacement("%horse_jump_amount%", () -> horseJumpAmount(deathrequest)));
			replacements.add(new TextReplacement("%horse_health_amount%", () -> horseHealthAmount(deathrequest)));
			replacements.add(new TextReplacement("%horse_gender%", () -> horseGender(deathrequest)));
			replacements.add(new TextReplacement("%horse_id%", () -> horseId(deathrequest)));
			replacements.add(
					new TextReplacement("%horse_healthupgrade_amount%", () -> horseHealthUpgradeAmount(deathrequest)));
			replacements
					.add(new TextReplacement("%horse_jumpupgrade_amount%", () -> horseJumpUpgradeAmount(deathrequest)));
			replacements.add(
					new TextReplacement("%horse_speedupgrade_amount%", () -> horseSpeedUpgradeAmount(deathrequest)));
			replacements.add(new TextReplacement("%horse_permanenthealthupgrade_amount%",
					() -> horsePermanentHealthUpgradeAmount(deathrequest)));
			replacements.add(new TextReplacement("%horse_permanentjumpupgrade_amount%",
					() -> horsePermanentJumpUpgradeAmount(deathrequest)));
			replacements.add(new TextReplacement("%horse_permanentspeedupgrade_amount%",
					() -> horsePermanentSpeedUpgradeAmount(deathrequest)));
			replacements.add(new TextReplacement("%horse_permanentarmorupgrade_amount%",
					() -> horsePermanentArmorUpgradeAmount(deathrequest)));
			replacements.add(new TextReplacement("%horse_level%", () -> horseLevel(deathrequest)));
			replacements.add(new TextReplacement("%horse_owner%", () -> horseOwner(deathrequest)));
			replacements.add(new TextReplacement("%horse_price%", () -> horsePrice(deathrequest)));
			replacements.add(new TextReplacement("%horse_lifetime_date%", () -> horseLifeTimeDate(deathrequest)));
			replacements.add(new TextReplacement("%horse_lifetime%", () -> horseLifeTime(deathrequest)));
			replacements.add(new TextReplacement("%horse_deathtime_date%", () -> horseDeathTimeDate(deathrequest)));
			replacements.add(new TextReplacement("%horse_deathtime%", () -> horseDeathTime(deathrequest)));
			replacements.add(new TextReplacement("%horse_saddle%", () -> horseSaddle(deathrequest)));
			replacements.add(new TextReplacement("%horse_is_dead%", () -> horseIsDead(deathrequest)));
			replacements.add(new TextReplacement("%horse_status%", () -> horseStatus(deathrequest)));
			replacements.add(
					new TextReplacement("%horse_armorupgrade_amount%", () -> horseArmorUpgradeAmount(deathrequest)));
			replacements.add(new TextReplacement("%horse_armor%", () -> horseArmor(deathrequest)));
			replacements.add(new TextReplacement("%horse_killer%", () -> horseKiller(deathrequest)));
			break;
		default:
			return new TextReplacement[0];
		}
		return replacements.toArray(new TextReplacement[replacements.size()]);
	}

}
