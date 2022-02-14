package relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.Modules;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import relampagorojo93.LibsCollection.Utils.Bukkit.TasksUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Enums.Stat;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;

public class StatsObject {
	private ClaimedData data;
	private int id;
	private HashMap<Stat, Integer> levels = new HashMap<>();
	
	public StatsObject(ClaimedData data, ResultSet set) throws SQLException {
		this.data = data;
		this.id = set.getInt("id");
		ResultSet statsset = MMOHorsesAPI.getSQLModule().getStats(id);
		while (statsset.next()) levels.put(Stat.values()[statsset.getInt("stat")], statsset.getInt("level"));
	}

	////////////////////////////////////////////////////////////////////////
	//
	// Final data
	//
	////////////////////////////////////////////////////////////////////////
	
	public int getId() {
		return this.id;
	}

	////////////////////////////////////////////////////////////////////////
	//
	// Variable data
	//
	////////////////////////////////////////////////////////////////////////
	
	public void addLevel(Stat stat, int amount) {
		setLevel(stat, getLevel(stat) + amount);
	}

	public void setLevel(Stat stat, int level) {
		levels.put(stat, level);
		if (stat == Stat.HORSE_LEVEL) data.getUpgrades().checkLevel();
		else if (data.getMMOHorse() != null) {
			if (stat == Stat.SPEED_LEVEL) data.getMMOHorse().getMMOHorseData().updateSpeed();
			else if (stat == Stat.JUMP_LEVEL) data.getMMOHorse().getMMOHorseData().updateJump();
			else if (stat == Stat.HEALTH_LEVEL) data.getMMOHorse().getMMOHorseData().updateMaxHealth();
			else if (stat == Stat.ARMOR_LEVEL) data.getMMOHorse().getMMOHorseData().updateArmor();
		}
		TasksUtils.execute(MMOHorsesAPI.getPlugin(), () -> MMOHorsesAPI.getSQLModule().setStat(this.id, stat, level), true);
	}
	
	public int getLevel(Stat stat) {
		int horse_level = levels.getOrDefault(Stat.HORSE_LEVEL, 1);
		int stat_level = levels.getOrDefault(stat, 1);
		return SettingBoolean.PAIR_LEVEL_WITH_HORSE_LEVEL.toBoolean() || (SettingBoolean.LIMIT_LEVEL_UPGRADE_BY_HORSE_LEVEL.toBoolean() && stat != Stat.HORSE_LEVEL && stat_level >= horse_level) ? horse_level : stat_level;
	}
	
	public int getHorseLevel() {
		return getLevel(Stat.HORSE_LEVEL);
	}
	
	public int getSpeedLevel() {
		return getLevel(Stat.SPEED_LEVEL);
	}
	
	public int getJumpLevel() {
		return getLevel(Stat.JUMP_LEVEL);
	}
	
	public int getHealthLevel() {
		return getLevel(Stat.HEALTH_LEVEL);
	}
	
	public int getArmorLevel() {
		return getLevel(Stat.ARMOR_LEVEL);
	}
	
	public int getInvLevel() {
		return getLevel(Stat.INVENTORY_LEVEL);
	}
}