package relampagorojo93.MMOHorses.Modules.FilePckg.Settings;

public enum SettingInt {
	PORT("MySQL.Port", 3306), AUTOSAVECOOLDOWN("Plugin.Autosave-cooldown", 2),
	DESPAWNAFTERINACTIVITY("Horse-settings.Despawn-after-inactivity-in-minutes", 0),
	MAXIMUM_HORSE_LEVEL("Horse-settings.Stats.Upgrades.Maximum-horse-level", "Horse-settings.Max-level", 100),
	MAXIMUM_SPEED_LEVEL("Horse-settings.Stats.Upgrades.Maximum-speed-level", 100),
	MAXIMUM_JUMP_LEVEL("Horse-settings.Stats.Upgrades.Maximum-jump-level", 100),
	MAXIMUM_HEALTH_LEVEL("Horse-settings.Stats.Upgrades.Maximum-heath-level", 100),
	MAXIMUM_ARMOR_LEVEL("Horse-settings.Stats.Upgrades.Maximum-armor-level", 100),
	MAXIMUM_INVENTORY_LEVEL("Horse-settings.Stats.Upgrades.Maximum-inventory-level", 100),
	INVENTORY_GIVEN_PER_LEVEL("Horse-settings.Stats.Upgrades.Inventory-given-per-level", 1),
	BARLENGTH("Horse-settings.Bar-length", 20),
	RESPAWNCOOLDOWN("Respawning.Respawn-cooldown", 60), TIMEFORGROWUP("Breeding.Time-for-grow-up", 20),
	TELEPORTCOOLDOWN("Player.Teleport-cooldown", 60);

	// Methods
	String oldpath, path;
	Integer content, defaultcontent;

	SettingInt(String path, Integer defaultcontent) {
		this(path, path, defaultcontent);
	}

	SettingInt(String path, String oldpath, Integer defaultcontent) {
		this.path = path;
		this.oldpath = oldpath;
		this.defaultcontent = defaultcontent;
	}

	public String getPath() {
		return path;
	}

	public String getOldPath() {
		return oldpath;
	}

	public int getDefaultContent() {
		return defaultcontent;
	}

	public int toInt() {
		return content != null ? content : defaultcontent;
	}

	public void setContent(Integer content) {
		this.content = content;
	}
}