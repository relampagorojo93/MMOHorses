package relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.Modules;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Location;

import relampagorojo93.LibsCollection.Utils.Bukkit.TasksUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Enums.ItemType;
import relampagorojo93.MMOHorses.Modules.CraftPckg.Objects.CraftableItem;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;

public class UpgradesObject {
	private ClaimedData data;
	private CraftableItem saddle_item, armor_item, speed_item, jump_item, health_item, chest_item;

	public UpgradesObject(ClaimedData data, ResultSet set) throws SQLException {
		this.data = data;
		this.saddle_item = MMOHorsesAPI.getCraftModule().getCraftableItem(set.getInt("saddle_item"));
		this.armor_item = MMOHorsesAPI.getCraftModule().getCraftableItem(set.getInt("armor_item"));
		this.speed_item = MMOHorsesAPI.getCraftModule().getCraftableItem(set.getInt("speed_item"));
		this.jump_item = MMOHorsesAPI.getCraftModule().getCraftableItem(set.getInt("jump_item"));
		this.health_item = MMOHorsesAPI.getCraftModule().getCraftableItem(set.getInt("health_item"));
		this.chest_item = MMOHorsesAPI.getCraftModule().getCraftableItem(set.getInt("chest_item"));
	}

	public CraftableItem getSaddleItem() {
		return saddle_item;
	}

	public CraftableItem getArmorItem() {
		return armor_item;
	}

	public CraftableItem getSpeedItem() {
		return speed_item;
	}

	public CraftableItem getJumpItem() {
		return jump_item;
	}

	public CraftableItem getHealthItem() {
		return health_item;
	}

	public CraftableItem getChestItem() {
		return chest_item;
	}
	
	public boolean applyItem(CraftableItem item, boolean force) {
		if (force || (item.getMinLevel() <= data.getStats().getHorseLevel() && item.getMaxLevel() >= data.getStats().getHorseLevel())) {
			if (item.getType() == ItemType.SADDLE && (force || saddle_item == null
					|| (!SettingBoolean.ALLOWDOWNGRADE.toBoolean() && saddle_item.getValue() < item.getValue()))) {
				saddle_item = item;
				TasksUtils.executeOnAsync(MMOHorsesAPI.getPlugin(),
						() -> MMOHorsesAPI.getSQLModule().applyUpgrade(data.getData().getId(), saddle_item));
				if (data.getMMOHorse() != null)
					data.getMMOHorse().getMMOHorseData().updateSaddle();
				return true;
			} else if (item.getType() == ItemType.ARMOR && (force || armor_item == null
					|| (!SettingBoolean.ALLOWDOWNGRADE.toBoolean() && armor_item.getValue() < item.getValue()))) {
				armor_item = item;
				TasksUtils.executeOnAsync(MMOHorsesAPI.getPlugin(),
						() -> MMOHorsesAPI.getSQLModule().applyUpgrade(data.getData().getId(), armor_item));
				if (data.getMMOHorse() != null)
					data.getMMOHorse().getMMOHorseData().updateBody();
				return true;
			} else if (item.getType() == ItemType.SPEED_UPGRADE && (force || speed_item == null
					|| (!SettingBoolean.ALLOWDOWNGRADE.toBoolean() && speed_item.getValue() < item.getValue()))) {
				speed_item = item;
				TasksUtils.executeOnAsync(MMOHorsesAPI.getPlugin(),
						() -> MMOHorsesAPI.getSQLModule().applyUpgrade(data.getData().getId(), speed_item));
				if (data.getMMOHorse() != null)
					data.getMMOHorse().getMMOHorseData().updateSpeed();
				return true;
			} else if (item.getType() == ItemType.JUMP_UPGRADE && (force || jump_item == null
					|| (!SettingBoolean.ALLOWDOWNGRADE.toBoolean() && jump_item.getValue() < item.getValue()))) {
				jump_item = item;
				TasksUtils.executeOnAsync(MMOHorsesAPI.getPlugin(),
						() -> MMOHorsesAPI.getSQLModule().applyUpgrade(data.getData().getId(), jump_item));
				if (data.getMMOHorse() != null)
					data.getMMOHorse().getMMOHorseData().updateJump();
				return true;
			} else if (item.getType() == ItemType.HEALTH_UPGRADE && (force || health_item == null
					|| (!SettingBoolean.ALLOWDOWNGRADE.toBoolean() && health_item.getValue() < item.getValue()))) {
				health_item = item;
				TasksUtils.executeOnAsync(MMOHorsesAPI.getPlugin(),
						() -> MMOHorsesAPI.getSQLModule().applyUpgrade(data.getData().getId(), health_item));
				if (data.getMMOHorse() != null)
					data.getMMOHorse().getMMOHorseData().updateMaxHealth();
				return true;
			} else if (item.getType() == ItemType.CHEST_UPGRADE && (force || chest_item == null
					|| (!SettingBoolean.ALLOWDOWNGRADE.toBoolean() && chest_item.getValue() < item.getValue()))) {
				chest_item = item;
				TasksUtils.executeOnAsync(MMOHorsesAPI.getPlugin(),
						() -> MMOHorsesAPI.getSQLModule().applyUpgrade(data.getData().getId(), chest_item));
				if (data.getMMOHorse() != null)
					data.getMMOHorse().getMMOHorseData().updateAccessory();
				return true;
			}
		}
		return false;
	}

	public boolean removeItem(CraftableItem item, boolean force) {
		if (item != null && (force || SettingBoolean.ALLOWDOWNGRADE.toBoolean())) {
			if (item.getType() == ItemType.ARMOR && (armor_item == null || armor_item.getId() != item.getId()))
				return false;
			else if (item.getType() == ItemType.SADDLE && (saddle_item == null || saddle_item.getId() != item.getId()))
				return false;
			else if (item.getType() == ItemType.SPEED_UPGRADE
					&& (speed_item == null || speed_item.getId() != item.getId()))
				return false;
			else if (item.getType() == ItemType.JUMP_UPGRADE
					&& (jump_item == null || jump_item.getId() != item.getId()))
				return false;
			else if (item.getType() == ItemType.HEALTH_UPGRADE
					&& (health_item == null || health_item.getId() != item.getId()))
				return false;
			else if (item.getType() == ItemType.CHEST_UPGRADE
					&& (chest_item == null || chest_item.getId() != item.getId()))
				return false;
			return removeItem(item.getType());
		}
		return false;
	}

	public boolean removeItem(ItemType type) {
		if (type == ItemType.ARMOR) {
			armor_item = null;
			if (data.getMMOHorse() != null)
				data.getMMOHorse().getMMOHorseData().updateBody();
		} else if (type == ItemType.SADDLE) {
			saddle_item = null;
			if (data.getMMOHorse() != null)
				data.getMMOHorse().getMMOHorseData().updateSaddle();
		} else if (type == ItemType.SPEED_UPGRADE) {
			speed_item = null;
			if (data.getMMOHorse() != null)
				data.getMMOHorse().getMMOHorseData().updateSpeed();
		}
		else if (type == ItemType.JUMP_UPGRADE) {
			jump_item = null;
			if (data.getMMOHorse() != null)
				data.getMMOHorse().getMMOHorseData().updateJump();
		}
		else if (type == ItemType.HEALTH_UPGRADE) {
			health_item = null;
			if (data.getMMOHorse() != null)
				data.getMMOHorse().getMMOHorseData().updateMaxHealth();
		}
		else if (type == ItemType.CHEST_UPGRADE) {
			chest_item = null;
			if (data.getMMOHorse() != null)
				data.getMMOHorse().getMMOHorseData().updateAccessory();
		}
		TasksUtils.executeOnAsync(MMOHorsesAPI.getPlugin(),
				() -> MMOHorsesAPI.getSQLModule().removeUpgrade(data.getData().getId(), type));
		return true;
	}

	public double getUpgradedSpeed() {
		return speed_item != null ? (data.getData().getSpeed() * speed_item.getValue()) / 100D : 0D;
	}

	public double getUpgradedJump() {
		return jump_item != null ? (data.getData().getJump() * jump_item.getValue()) / 100D : 0D;
	}

	public double getUpgradedHealth() {
		return health_item != null ? health_item.getValue() : 0D;
	}

	public void checkLevel() {
		int level = data.getStats().getHorseLevel();
		Location l = data.getMMOHorse() != null ? data.getMMOHorse().getLocation() : null;
		for (CraftableItem item: new CraftableItem[] { saddle_item, armor_item, speed_item, jump_item, health_item, chest_item }) {
			if (item != null && (item.getMinLevel() > level || item.getMaxLevel() < level)) {
				if (l != null) l.getWorld().dropItem(l, item.getResult());
				removeItem(item.getType());
			}
		}
	}
}
