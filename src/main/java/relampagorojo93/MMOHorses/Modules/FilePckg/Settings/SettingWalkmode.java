package relampagorojo93.MMOHorses.Modules.FilePckg.Settings;

import relampagorojo93.MMOHorses.Enums.WalkMode;

public enum SettingWalkmode {
	WALKMODEFORMAXJUMP("Horse-settings.Walk-mode-for-max-jump", WalkMode.TROT);

	// Methods
	String oldpath, path;
	WalkMode content, defaultcontent;

	SettingWalkmode(String path, WalkMode defaultcontent) {
		this(path, path, defaultcontent);
	}

	SettingWalkmode(String path, String oldpath, WalkMode defaultcontent) {
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

	public WalkMode getDefaultContent() {
		return defaultcontent;
	}

	public WalkMode toWalkMode() {
		return content != null ? content : defaultcontent;
	}

	public void setContent(WalkMode content) {
		this.content = content;
	}
}