package relampagorojo93.MMOHorses.Modules.FilePckg.Settings;

public enum SettingBoolean {
	SQL("MySQL.Enable", false), SPEEDACTIONBAR("General-settings.Speed-ActionBar", true),
	GENDERS("General-settings.Enable-genders", true),
	ENABLE_HORSE_MENU("Menus.Enable-horse-menu", "General-settings.MMO-Menus", true),
	ENABLE_CRATES("Menus.Enable-crates", false), VAULT("Hooks.Vault-Integrity", true),
	EIOPENER("Hooks.EIOpener", false), WORLDGUARD("Hooks.WorldGuard-Integrity", true),
	LANDS("Hooks.Lands-Integrity", true),
	CITIZENS("Hooks.Citizens-Integrity", true),
	MYTHICMOBS("Hooks.MythicMobs-Integrity", true), PLACEHOLDERAPI("Hooks.PlaceholderAPI-Integrity", true),
	OPENONMOUNT("Menus.Open-on-mount", false), ALLOWDOWNGRADE("Upgrades.Allow-downgrade", true),
	DROPUPGRADESONDEATH("Horse-settings.Death.Drop-upgrades-on-death", "Upgrades.Drop-upgrade-on-death", true),
	DROPUPGRADESONRELEASE("Horse-settings.Death.Drop-upgrades-on-release", "Upgrades.Drop-upgrade-on-release", true),
	DROPUPGRADESONGIFT("Horse-settings.Death.Drop-upgrades-on-gift", false),
	DROPWARDROBEONDEATH("Horse-settings.Death.Drop-wardrobe-on-death", true),
	DROPWARDROBEONRELEASE("Horse-settings.Death.Drop-wardrobe-on-release", true),
	DROPWARDROBEONGIFT("Horse-settings.Death.Drop-wardrobe-on-gift", false),
	DROPINVENTORYONDEATH("Horse-settings.Death.Drop-inventory-on-death", true),
	DROPINVENTORYONRELEASE("Horse-settings.Death.Drop-inventory-on-release", true),
	DROPINVENTORYONGIFT("Horse-settings.Death.Drop-inventory-on-gift", false),
	MAKECOSMETICSPUBLIC("Cosmetics.Make-cosmetics-public", true),
	MAKECOSMETICSFUNCTIONAL("Cosmetics.Make-cosmetics-functional", false),
	ONLYREGISTEREDCOSMETICS("Cosmetics.Only-registered-cosmetics", false),
	SUPPORT_HORSE("Horse-settings.Entities.Support.Horse", "Horse-settings.Support.Horse", true),
	SUPPORT_SKELETONHORSE("Horse-settings.Entities.Support.Skeleton-horse", "Horse-settings.Support.Skeleton-horse",
			true),
	SUPPORT_ZOMBIEHORSE("Horse-settings.Entities.Support.Zombie-horse", "Horse-settings.Support.Zombie-horse", true),
	SUPPORT_DONKEY("Horse-settings.Entities.Support.Donkey", "Horse-settings.Support.Donkey", true),
	SUPPORT_MULE("Horse-settings.Entities.Support.Mule", "Horse-settings.Support.Mule", true),
	SUPPORT_LLAMA("Horse-settings.Entities.Support.Llama", "Horse-settings.Support.Llama", true),
	SUPPORT_TRADERLLAMA("Horse-settings.Entities.Support.Trader-llama", "Horse-settings.Support.Trader-llama", true),
	DISABLEEQUIPABLEITEMSUSAGE("Horse-settings.Disable-equipable-items-usage-on-non-claimed-horses", false),
	DISABLEVANILLAINVENTORY("Horse-settings.Disable-vanilla-inventory", true),
	LISTUNLINKED("Horse-settings.List-unlinked", true), ALLOWLINK("Horse-settings.Allow-link", true),
	ALLOWUNLINK("Horse-settings.Allow-unlink", true),
	SHOWNAMETAGBYDEFAULT("Horse-settings.Show-name-tag-by-default", true),
	SHOWHEALTHTAGBYDEFAULT("Horse-settings.Show-health-tag-by-default", true),
	FOLLOWOWNERBYDEFAULT("Horse-settings.Follow-owner-by-default", false),
	BLOCKUNMOUNTEDSPEEDBYDEFAULT("Horse-settings.Block-unmounted-speed-by-default", true),
	LINKONCLAIM("Horse-settings.Link-on-claim", true), LINKONSPAWN("Horse-settings.Link-on-spawn", true),
	HORSESCANSWITCHWORLD("Horse-settings.Horses-can-switch-world", true),
	DISABLEHORSEVANILLADROPS("Horse-settings.Disable-horse-vanilla-drops", true),
	DISABLENONREGISTEREDHORSEVANILLADROPS("Horse-settings.Disable-non-registered-horse-vanilla-drops", true),
	OVERRIDERESPAWN("Horse-settings.Death.Override-respawn", false), RESPAWN("Respawning.Enable-respawn", false),
	CUSTOMGROWUPSYSTEM("Breeding.Custom-grow-up-system", false), CLAIMONTAMING("Player.Claim-on-taming", true),
	UNSUMMONONUNMOUNT("Player.Unsummon-on-unmount", false), MOUNTONSPAWN("Player.Mount-on-spawn", false),
	FOODSYSTEM("Horse-settings.Food-system.Enable", false),
	LIMITCLAIMAMOUNT("Player.Limit-claim-amount-per-player", true),
	LIMITSUMMONAMOUNT("Player.Limit-summon-amount-per-player", false),
	USECUSTOMBREEDING("Breeding.Use-custom-breeding", true),
	LIMIT_LEVEL_UPGRADE_BY_HORSE_LEVEL("Horse-settings.Stats.Upgrades.Limit-level-upgrade-by-horse-level", false),
	PAIR_LEVEL_WITH_HORSE_LEVEL("Horse-settings.Stats.Upgrades.Pair-levels-with-horse-level", false),
	OPEN_CHEST_ON_SHIFT_RIGHT_CLICK("Menus.Open-chest-on-shift-right-click", false),
	CHECK_HORSES_STATS("Horse-settings.Stats.Check-horses-stats", true),
	COMMAND_HORSE_ENABLE("Commands.Horse.Enable", true),
	BLOCK_INTERACTIONS_WITH_REGISTERED_ITEMS("Items.Block-interactions-with-registered-items", true),
	DISCOVER_ALL_RECIPES("Player.Discover-all-recipes", false);

	// Methods
	String oldpath, path;
	Boolean content, defaultcontent;

	SettingBoolean(String path, Boolean defaultcontent) {
		this(path, path, defaultcontent);
	}

	SettingBoolean(String path, String oldpath, Boolean defaultcontent) {
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

	public boolean getDefaultContent() {
		return defaultcontent;
	}

	public boolean toBoolean() {
		return content != null ? content : defaultcontent;
	}

	public void setContent(Boolean content) {
		this.content = content;
	}
}