package relampagorojo93.MMOHorses.Modules.HorsePckg.Objects;

import java.util.HashMap;
import java.util.UUID;

import relampagorojo93.LibsCollection.Utils.Bukkit.TasksUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Enums.Action;

public class UserAccess {
	private int horse_id;
	private UUID uuid;
	private boolean isowner, privileges;
	private HashMap<Action, Boolean> map = new HashMap<>();

	public UserAccess(int horse_id, UUID uuid) {
		this(horse_id, uuid, false, false);
	}

	public UserAccess(int horse_id, UUID uuid, boolean open_upgrades, boolean open_chest, boolean open_settings,
			boolean open_trusting, boolean open_wardrobe, boolean change_name, boolean item_usage) {
		this(horse_id, uuid, false, false);
		map.put(Action.OPEN_UPGRADES, (Boolean) open_upgrades);
		map.put(Action.OPEN_VIRTUALCHEST, (Boolean) open_chest);
		map.put(Action.OPEN_SETTINGS, (Boolean) open_settings);
		map.put(Action.OPEN_TRUSTING, (Boolean) open_trusting);
		map.put(Action.OPEN_WARDROBE, (Boolean) open_wardrobe);
		map.put(Action.CHANGE_NAME, (Boolean) change_name);
		map.put(Action.ITEM_USAGE, (Boolean) item_usage);
	}

	public UserAccess(int horse_id, UUID uuid, boolean isowner) {
		this(horse_id, uuid, isowner, isowner);
	}

	public UserAccess(int horse_id, UUID uuid, boolean isowner, boolean privileges) {
		this.horse_id = horse_id;
		this.uuid = uuid;
		this.isowner = isowner;
		this.privileges = privileges;
	}

	public int getHorseID() {
		return horse_id;
	}

	public UUID getUniqueID() {
		return uuid;
	}

	public boolean isOwner() {
		return isowner;
	}

	public boolean hasPrivileges() {
		return privileges;
	}

	public boolean hasAccess(Action action) {
		return privileges || map.getOrDefault(action, (Boolean) false).booleanValue();
	}

	public void switchAccess(Action action) {
		map.put(action, !map.getOrDefault(action, false).booleanValue());
		TasksUtils.executeOnAsync(MMOHorsesAPI.getPlugin(),
				() -> MMOHorsesAPI.getSQLModule().setAccess(horse_id, uuid, action, map.get(action).booleanValue()));
	}
}
