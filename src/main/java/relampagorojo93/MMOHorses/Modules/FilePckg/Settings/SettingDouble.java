package relampagorojo93.MMOHorses.Modules.FilePckg.Settings;

public enum SettingDouble {
	MINIMUMPRICE("Horse-settings.Minimum-price", 100D), MINIMUMHEALTH("Horse-settings.Stats.Minimum-health", 15D),
	MAXIMUMHEALTH("Horse-settings.Stats.Maximum-health", 30D), MINIMUMSPEED("Horse-settings.Stats.Minimum-speed", 5D),
	MAXIMUMSPEED("Horse-settings.Stats.Maximum-speed", 11D), MINIMUMJUMP("Horse-settings.Stats.Minimum-jump", 1D),
	MAXIMUMJUMP("Horse-settings.Stats.Maximum-jump", 4D),
	SPEED_GIVEN_PER_LEVEL("Horse-settings.Stats.Upgrades.Speed-given-per-level", 10D),
	JUMP_GIVEN_PER_LEVEL("Horse-settings.Stats.Upgrades.Jump-given-per-level", 10D),
	HEALTH_GIVEN_PER_LEVEL("Horse-settings.Stats.Upgrades.Health-given-per-level", 2D),
	ARMOR_GIVEN_PER_LEVEL("Horse-settings.Stats.Upgrades.Armor-given-per-level", 1D),
	FOODCONSUMPERBLOCK("Horse-settings.Food-system.Food-consum-per-block", 0.0003D),
	FOODCONSUMPERJUMP("Horse-settings.Food-system.Food-consum-per-jump", 0.001D),
	FOODRECOVERYPERFOOD("Horse-settings.Food-system.Food-recovery-per-food", 0.25D);

	// Methods
	String oldpath, path;
	Double content, defaultcontent;

	SettingDouble(String path, Double defaultcontent) {
		this(path, path, defaultcontent);
	}

	SettingDouble(String path, String oldpath, Double defaultcontent) {
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

	public double getDefaultContent() {
		return defaultcontent;
	}

	public double toDouble() {
		return content != null ? content : defaultcontent;
	}

	public void setContent(Double content) {
		this.content = content;
	}
}