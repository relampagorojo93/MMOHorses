package relampagorojo93.MMOHorses.Modules.FilePckg.Settings;

public enum SettingString {
	HOST("MySQL.Hostname", "localhost"), DATABASE("MySQL.Database", "server"), USERNAME("MySQL.Username", "admin"),
	PROTOCOL("MySQL.Protocol", "jdbc:mysql:"), PASSWORD("MySQL.Password", "admin"),
	TABLEPREFIX("MySQL.Table_prefix", ""),
	PARAMETERS("MySQL.Parameters", "sslMode=DISABLED&connectionTimeout=3000&socketTimeout=3000"),
	UPGRADE_COST_CURRENCY("Horse-settings.Stats.Upgrades.Upgrade-cost-currency", "EXPERIENCE"),
	HORSE_LEVEL_UPGRADE_COST("Horse-settings.Stats.Upgrades.Horse-level-upgrade-cost", "(2*%level%)^3"),
	SPEED_LEVEL_UPGRADE_COST("Horse-settings.Stats.Upgrades.Speed-level-upgrade-cost", "(2*%level%)^3"),
	JUMP_LEVEL_UPGRADE_COST("Horse-settings.Stats.Upgrades.Jump-level-upgrade-cost", "(2*%level%)^3"),
	HEALTH_LEVEL_UPGRADE_COST("Horse-settings.Stats.Upgrades.Health-level-upgrade-cost", "(2*%level%)^3"),
	ARMOR_LEVEL_UPGRADE_COST("Horse-settings.Stats.Upgrades.Armor-level-upgrade-cost", "(2*%level%)^3"),
	INVENTORY_LEVEL_UPGRADE_COST("Horse-settings.Stats.Upgrades.Inventory-level-upgrade-cost", "(2*%level%)^3"),
	
	// ---------------------------------------------------------------------//
	// Horse commands
	// ---------------------------------------------------------------------//
	
	HORSE_NAME("Commands.Horse.Name", "Horse"),
	HORSE_PERMISSION("Commands.Horse.Permission", ""),
	HORSE_DESCRIPTION("Commands.Horse.Description", "Get all MMOHorses commands"),
	HORSE_PARAMETERS("Commands.Horse.Parameters", "[command/help]"),
	
	HORSE_BUY_NAME("Commands.Horse.Subcommands.Buy.Name", "buy"),
	HORSE_BUY_PERMISSION("Commands.Horse.Subcommands.Buy.Permission", "Horse.Player.Buy"),
	HORSE_BUY_DESCRIPTION("Commands.Horse.Subcommands.Buy.Description", "Open the shop with all the horses on sell"),
	
	HORSE_CLAIM_NAME("Commands.Horse.Subcommands.Claim.Name", "claim"),
	HORSE_CLAIM_PERMISSION("Commands.Horse.Subcommands.Claim.Permission", "Horse.Player.Claim"),
	HORSE_CLAIM_DESCRIPTION("Commands.Horse.Subcommands.Claim.Description", "Claim a horse"),
	HORSE_CLAIM_PARAMETERS("Commands.Horse.Subcommands.Claim.Parameters", "[name]"),
	
	HORSE_DEBUG_NAME("Commands.Horse.Subcommands.Debug.Name", "debug"),
	HORSE_DEBUG_PERMISSION("Commands.Horse.Subcommands.Debug.Permission", "Horse.Admin.Debug"),
	HORSE_DEBUG_DESCRIPTION("Commands.Horse.Subcommands.Debug.Description", "Open a menu with all the debug messages"),
	
	HORSE_DELETE_NAME("Commands.Horse.Subcommands.Delete.Name", "delete"),
	HORSE_DELETE_PERMISSION("Commands.Horse.Subcommands.Delete.Permission", "Horse.Admin.Delete"),
	HORSE_DELETE_DESCRIPTION("Commands.Horse.Subcommands.Delete.Description", "Delete the horse permanently"),
	
	HORSE_GELD_NAME("Commands.Horse.Subcommands.Geld.Name", "geld"),
	HORSE_GELD_PERMISSION("Commands.Horse.Subcommands.Geld.Permission", "Horse.Player.Geld"),
	HORSE_GELD_DESCRIPTION("Commands.Horse.Subcommands.Geld.Description", "Geld your horse"),
	
	HORSE_GIFT_NAME("Commands.Horse.Subcommands.Gift.Name", "gift"),
	HORSE_GIFT_PERMISSION("Commands.Horse.Subcommands.Gift.Permission", "Horse.Player.Gift"),
	HORSE_GIFT_DESCRIPTION("Commands.Horse.Subcommands.Gift.Description", "Gift the riden horse to a player"),
	HORSE_GIFT_PARAMETERS("Commands.Horse.Subcommands.Gift.Parameters", "[username]"),
	
	HORSE_GIVE_NAME("Commands.Horse.Subcommands.Give.Name", "give"),
	HORSE_GIVE_PERMISSION("Commands.Horse.Subcommands.Give.Permission", "Horse.Admin.Give"),
	HORSE_GIVE_DESCRIPTION("Commands.Horse.Subcommands.Give.Description",
			"Summon a custom entity (And give it to a player)"),
	HORSE_GIVE_PARAMETERS("Commands.Horse.Subcommands.Give.Parameters", "[custom horse id] <Player> <true/false>"),
	
	HORSE_GIVEPRESENT_NAME("Commands.Horse.Subcommands.GivePresent.Name", "givepresent"),
	HORSE_GIVEPRESENT_PERMISSION("Commands.Horse.Subcommands.GivePresent.Permission", "Horse.Admin.GivePresent"),
	HORSE_GIVEPRESENT_DESCRIPTION("Commands.Horse.Subcommands.GivePresent.Description",
			"Get a present with a custom horse (Or give it)"),
	HORSE_GIVEPRESENT_PARAMETERS("Commands.Horse.Subcommands.GivePresent.Parameters", "[custom horse id] <Player> <Item name>"),
	
	HORSE_HEAL_NAME("Commands.Horse.Subcommands.Heal.Name", "heal"),
	HORSE_HEAL_PERMISSION("Commands.Horse.Subcommands.Heal.Permission", "Horse.Admin.Heal"),
	HORSE_HEAL_DESCRIPTION("Commands.Horse.Subcommands.Heal.Description", "Heal the current riden horse"),
	
	HORSE_HELP_NAME("Commands.Horse.Subcommands.Help.Name", "help"),
	HORSE_HELP_PERMISSION("Commands.Horse.Subcommands.Help.Permission", ""),
	HORSE_HELP_DESCRIPTION("Commands.Horse.Subcommands.Help.Description", "Get all the commands"),
	HORSE_HELP_PARAMETERS("Commands.Horse.Subcommands.Help.Parameters", "<page>"),
	
	HORSE_INFO_NAME("Commands.Horse.Subcommands.Info.Name", "info"),
	HORSE_INFO_PERMISSION("Commands.Horse.Subcommands.Info.Permission", "Horse.Admin.Info"),
	HORSE_INFO_DESCRIPTION("Commands.Horse.Subcommands.Info.Description", "Get information about the plugin"),
	
	HORSE_LISTALL_NAME("Commands.Horse.Subcommands.ListAll.Name", "listall"),
	HORSE_LISTALL_PERMISSION("Commands.Horse.Subcommands.ListAll.Permission", "Horse.Admin.ListAll"),
	HORSE_LISTALL_DESCRIPTION("Commands.Horse.Subcommands.ListAll.Description",
			"List all the horses even if are linked or aren't yours"),
	
	HORSE_LIST_NAME("Commands.Horse.Subcommands.List.Name", "list"),
	HORSE_LIST_PERMISSION("Commands.Horse.Subcommands.List.Permission", "Horse.Player.List"),
	HORSE_LIST_DESCRIPTION("Commands.Horse.Subcommands.List.Description", "Open a menu with all your claimed horses"),
	HORSE_LIST_PARAMETERS("Commands.Horse.Subcommands.List.Parameters", "<player>"),
	
	HORSE_LUNGE_NAME("Commands.Horse.Subcommands.Lunge.Name", "lunge"),
	HORSE_LUNGE_PERMISSION("Commands.Horse.Subcommands.Lunge.Permission", "Horse.Player.Lunge"),
	HORSE_LUNGE_DESCRIPTION("Commands.Horse.Subcommands.Lunge.Description",
			"Start or stop the lunge event on all the leashed horses."),
	
	HORSE_RELEASE_NAME("Commands.Horse.Subcommands.Release.Name", "release"),
	HORSE_RELEASE_PERMISSION("Commands.Horse.Subcommands.Release.Permission", "Horse.Player.Release"),
	HORSE_RELEASE_DESCRIPTION("Commands.Horse.Subcommands.Release.Description", "Release your horse"),
	
	HORSE_RELOAD_NAME("Commands.Horse.Subcommands.Reload.Name", "reload"),
	HORSE_RELOAD_PERMISSION("Commands.Horse.Subcommands.Reload.Permission", "Horse.Admin.Reload"),
	HORSE_RELOAD_DESCRIPTION("Commands.Horse.Subcommands.Reload.Description", "Reload the plugin configurations"),
	
	HORSE_RENAME_NAME("Commands.Horse.Subcommands.Rename.Name", "rename"),
	HORSE_RENAME_PERMISSION("Commands.Horse.Subcommands.Rename.Permission", "Horse.Player.Rename"),
	HORSE_RENAME_DESCRIPTION("Commands.Horse.Subcommands.Rename.Description", "Rename your horse"),
	HORSE_RENAME_PARAMETERS("Commands.Horse.Subcommands.Rename.Parameters", "[name]"),
	
	HORSE_SELL_NAME("Commands.Horse.Subcommands.Sell.Name", "sell"),
	HORSE_SELL_PERMISSION("Commands.Horse.Subcommands.Sell.Permission", "Horse.Player.Sell"),
	HORSE_SELL_DESCRIPTION("Commands.Horse.Subcommands.Sell.Description", "Set a price to the rided horse"),
	HORSE_SELL_PARAMETERS("Commands.Horse.Subcommands.Sell.Parameters", "[price]"),
	
	HORSE_SETCOLOR_NAME("Commands.Horse.Subcommands.SetColor.Name", "setcolor"),
	HORSE_SETCOLOR_PERMISSION("Commands.Horse.Subcommands.SetColor.Permission", "Horse.Admin.SetColor"),
	HORSE_SETCOLOR_DESCRIPTION("Commands.Horse.Subcommands.SetColor.Description", "Change horse's color"),
	HORSE_SETCOLOR_PARAMETERS("Commands.Horse.Subcommands.SetColor.Parameters", "[color]"),
	
	HORSE_SETGENDER_NAME("Commands.Horse.Subcommands.SetGender.Name", "setgender"),
	HORSE_SETGENDER_PERMISSION("Commands.Horse.Subcommands.SetGender.Permission", "Horse.Admin.SetGender"),
	HORSE_SETGENDER_DESCRIPTION("Commands.Horse.Subcommands.SetGender.Description", "Change horse's gender"),
	HORSE_SETGENDER_PARAMETERS("Commands.Horse.Subcommands.SetGender.Parameters", "[gender]"),
	
	HORSE_SETHEALTH_NAME("Commands.Horse.Subcommands.SetHealth.Name", "sethealth"),
	HORSE_SETHEALTH_PERMISSION("Commands.Horse.Subcommands.SetHealth.Permission", "Horse.Admin.SetHealth"),
	HORSE_SETHEALTH_DESCRIPTION("Commands.Horse.Subcommands.SetHealth.Description",
			"Set the amount of health the horse will have"),
	HORSE_SETHEALTH_PARAMETERS("Commands.Horse.Subcommands.SetHealth.Parameters", "[x.xx]"),
	
	HORSE_SETJUMP_NAME("Commands.Horse.Subcommands.SetJump.Name", "setjump"),
	HORSE_SETJUMP_PERMISSION("Commands.Horse.Subcommands.SetJump.Permission", "Horse.Admin.SetJump"),
	HORSE_SETJUMP_DESCRIPTION("Commands.Horse.Subcommands.SetJump.Description",
			"Set the amount of blocks the horse will jump"),
	HORSE_SETJUMP_PARAMETERS("Commands.Horse.Subcommands.SetJump.Parameters", "[x.xx]"),
	
	HORSE_SETMARKING_NAME("Commands.Horse.Subcommands.SetMarking.Name", "setmarking"),
	HORSE_SETMARKING_PERMISSION("Commands.Horse.Subcommands.SetMarking.Permission", "Horse.Admin.SetMarking"),
	HORSE_SETMARKING_DESCRIPTION("Commands.Horse.Subcommands.SetMarking.Description", "Change horse's marking"),
	HORSE_SETMARKING_PARAMETERS("Commands.Horse.Subcommands.SetMarking.Parameters", "[marking]"),
	
	HORSE_SETOWNER_NAME("Commands.Horse.Subcommands.SetOwner.Name", "setowner"),
	HORSE_SETOWNER_PERMISSION("Commands.Horse.Subcommands.SetOwner.Permission", "Horse.Admin.SetOwner"),
	HORSE_SETOWNER_DESCRIPTION("Commands.Horse.Subcommands.SetOwner.Description", "Change horse's owner"),
	HORSE_SETOWNER_PARAMETERS("Commands.Horse.Subcommands.SetOwner.Parameters", "[username]"),
	
	HORSE_SETSPEED_NAME("Commands.Horse.Subcommands.SetSpeed.Name", "setspeed"),
	HORSE_SETSPEED_PERMISSION("Commands.Horse.Subcommands.SetSpeed.Permission", "Horse.Admin.SetSpeed"),
	HORSE_SETSPEED_DESCRIPTION("Commands.Horse.Subcommands.SetSpeed.Description",
			"Set the blocks per second of the horse"),
	HORSE_SETSPEED_PARAMETERS("Commands.Horse.Subcommands.SetSpeed.Parameters", "[x.xx]"),
	
	HORSE_TRUST_NAME("Commands.Horse.Subcommands.Trust.Name", "trust"),
	HORSE_TRUST_PERMISSION("Commands.Horse.Subcommands.Trust.Permission", "Horse.Player.Trust"),
	HORSE_TRUST_DESCRIPTION("Commands.Horse.Subcommands.Trust.Description",
			"Add a player as trusted on the rided horse"),
	HORSE_TRUST_PARAMETERS("Commands.Horse.Subcommands.Trust.Parameters", "[username]"),
	
	HORSE_UNSUMMON_NAME("Commands.Horse.Subcommands.Unsummon.Name", "unsummon"),
	HORSE_UNSUMMON_PERMISSION("Commands.Horse.Subcommands.Unsummon.Permission", "Horse.Admin.Unsummon"),
	HORSE_UNSUMMON_DESCRIPTION("Commands.Horse.Subcommands.Unsummon.Description", "Unsummon all the horses"),
	HORSE_UNSUMMON_PARAMETERS("Commands.Horse.Subcommands.Unsummon.Parameters", "<confirm>"),
	
	HORSE_UNTRUST_NAME("Commands.Horse.Subcommands.Untrust.Name", "untrust"),
	HORSE_UNTRUST_PERMISSION("Commands.Horse.Subcommands.Untrust.Permission", "Horse.Player.Untrust"),
	HORSE_UNTRUST_DESCRIPTION("Commands.Horse.Subcommands.Untrust.Description",
			"Remove a player as trusted on the rided horse"),
	HORSE_UNTRUST_PARAMETERS("Commands.Horse.Subcommands.Untrust.Parameters", "[username]"),
	
	// ---------------------------------------------------------------------//
	// Horse Lands commands
	// ---------------------------------------------------------------------//
	
	HORSELANDS_NAME("Commands.Horse.Subcommands.Lands.Name", "Commands.HorseLands.Name", "lands"),
	HORSELANDS_PERMISSION("Commands.Horse.Subcommands.Lands.Permission", "Horse.Admin.Lands"),
	HORSELANDS_DESCRIPTION("Commands.Horse.Subcommands.Lands.Description", "Commands.HorseLands.Description",
			"Get all MMOHorses commands for Lands hook"),
	HORSELANDS_PARAMETERS("Commands.Horse.Subcommands.Lands.Parameters", "[command/help]"),
	
	HORSELANDS_SETRESTRICTION_NAME("Commands.Horse.Subcommands.Lands.Subcommands.SetRestriction.Name",
			"Commands.HorseLands.Subcommands.SetRestriction.Name", "setrestriction"),
	HORSELANDS_SETRESTRICTION_PERMISSION("Commands.Horse.Subcommands.Lands.Subcommands.SetRestriction.Permission",
			"Commands.HorseLands.Subcommands.SetRestriction.Permission", "HorseLands.Admin.SetRestriction"),
	HORSELANDS_SETRESTRICTION_DESCRIPTION("Commands.Horse.Subcommands.Lands.Subcommands.SetRestriction.Description",
			"Commands.HorseLands.Subcommands.SetRestriction.Description",
			"Set a speed restriction (No walk mode = Remove restriction)"),
	HORSELANDS_SETRESTRICTION_PARAMETERS("Commands.Horse.Subcommands.Lands.Subcommands.SetRestriction.Parameters",
			"Commands.HorseLands.Subcommands.SetRestriction.Usage",
			"[region type] <walk mode>"),
	
	// ---------------------------------------------------------------------//
	// Horse SQL commands
	// ---------------------------------------------------------------------//
	
	HORSESQL_NAME("Commands.Horse.Subcommands.SQL.Name", "sql"),
	HORSESQL_PERMISSION("Commands.Horse.Subcommands.SQL.Permission", "Horse.Admin.SQL"),
	HORSESQL_DESCRIPTION("Commands.Horse.Subcommands.SQL.Description", "Get all MMOHorses commands for the SQL management"),
	HORSESQL_PARAMETERS("Commands.Horse.Subcommands.SQL.Parameters", "[command/help]"),
	
	HORSESQL_CLEAR_NAME("Commands.Horse.Subcommands.SQL.Subcommands.Clear.Name", "clear"),
	HORSESQL_CLEAR_PERMISSION("Commands.Horse.Subcommands.SQL.Subcommands.Clear.Permission", "HorseSQL.Admin.Clear"),
	HORSESQL_CLEAR_DESCRIPTION("Commands.Horse.Subcommands.SQL.Subcommands.Clear.Description", "Clear the actual database"),
	HORSESQL_CLEAR_PARAMETERS("Commands.Horse.Subcommands.SQL.Subcommands.Clear.Parameters", "[horses/items/all] <confirm>"),
	
	HORSESQL_PARSEFROMSQLITE_NAME("Commands.Horse.Subcommands.SQL.Subcommands.ParseFromSQLite.Name", "parsefromsqlite"),
	HORSESQL_PARSEFROMSQLITE_PERMISSION("Commands.Horse.Subcommands.SQL.Subcommands.ParseFromSQLite.Permission",
			"HorseSQL.Admin.ParseFromSQLite"),
	HORSESQL_PARSEFROMSQLITE_DESCRIPTION("Commands.Horse.Subcommands.SQL.Subcommands.ParseFromSQLite.Description",
			"Parse all the content from the SQLite file to the configured MySQL database (Only works if MySQL is disabled)"),
	
	HORSESQL_PARSEFROMMYSQL_NAME("Commands.Horse.Subcommands.SQL.Subcommands.ParseFromMySQL.Name", "parsefrommysql"),
	HORSESQL_PARSEFROMMYSQL_PERMISSION("Commands.Horse.Subcommands.SQL.Subcommands.ParseFromMySQL.Permission",
			"HorseSQL.Admin.ParseFromMySQL"),
	HORSESQL_PARSEFROMMYSQL_DESCRIPTION("Commands.Horse.Subcommands.SQL.Subcommands.ParseFromMySQL.Description",
			"Parse all the content from the MySQL database to the configured SQLite file (Only works if MySQL is enabled)"),
	
	HORSESQL_UNLINK_NAME("Commands.Horse.Subcommands.SQL.Subcommands.Unlink.Name", "unlink"),
	HORSESQL_UNLINK_PERMISSION("Commands.Horse.Subcommands.SQL.Subcommands.Unlink.Permission", "HorseSQL.Admin.Unlink"),
	HORSESQL_UNLINK_DESCRIPTION("Commands.Horse.Subcommands.SQL.Subcommands.Unlink.Description", "Unlink all the horses"),
	HORSESQL_UNLINK_PARAMETERS("Commands.Horse.Subcommands.SQL.Subcommands.Unlink.Parameters", "<confirm>"),
	
	// ---------------------------------------------------------------------//
	// Horse Items commands
	// ---------------------------------------------------------------------//
	
	HORSEITEMS_NAME("Commands.Horse.Subcommands.Items.Name", "items"),
	HORSEITEMS_PERMISSION("Commands.Horse.Subcommands.Items.Permission", "Horse.Admin.Items"),
	HORSEITEMS_DESCRIPTION("Commands.Horse.Subcommands.Items.Description", "Get all MMOHorses commands for the items management"),
	HORSEITEMS_PARAMETERS("Commands.Horse.Subcommands.Items.Parameters", "[command/help]"),
	
	HORSEITEMS_ADD_NAME("Commands.Horse.Subcommands.Items.Subcommands.Add.Name", "add"),
	HORSEITEMS_ADD_PERMISSION("Commands.Horse.Subcommands.Items.Subcommands.Add.Permission", "HorseItems.Admin.Add"),
	HORSEITEMS_ADD_DESCRIPTION("Commands.Horse.Subcommands.Items.Subcommands.Add.Description", "Add a new MMOHorses' item"),
	HORSEITEMS_ADD_PARAMETERS("Commands.Horse.Subcommands.Items.Subcommands.Add.Parameters", "[item name] <item type>"),
	
	HORSEITEMS_GET_NAME("Commands.Horse.Subcommands.Items.Subcommands.Get.Name", "get"),
	HORSEITEMS_GET_PERMISSION("Commands.Horse.Subcommands.Items.Subcommands.Get.Permission", "HorseItems.Admin.Get"),
	HORSEITEMS_GET_DESCRIPTION("Commands.Horse.Subcommands.Items.Subcommands.Get.Description",
			"Get or give a MMOHorses' item without crafting it"),
	HORSEITEMS_GET_PARAMETERS("Commands.Horse.Subcommands.Items.Subcommands.Get.Parameters", "[item name] <Player>"),
	
	HORSEITEMS_LIST_NAME("Commands.Horse.Subcommands.Items.Subcommands.List.Name", "list"),
	HORSEITEMS_LIST_PERMISSION("Commands.Horse.Subcommands.Items.Subcommands.List.Permission", "HorseItems.Admin.List"),
	HORSEITEMS_LIST_DESCRIPTION("Commands.Horse.Subcommands.Items.Subcommands.List.Description",
			"Check all the recipes and manage them"),
	
	HORSEITEMS_EXPORT_NAME("Commands.Horse.Subcommands.Items.Subcommands.Export.Name", "export"),
	HORSEITEMS_EXPORT_PERMISSION("Commands.Horse.Subcommands.Items.Subcommands.Export.Permission", "HorseItems.Admin.Export"),
	HORSEITEMS_EXPORT_DESCRIPTION("Commands.Horse.Subcommands.Items.Subcommands.Export.Description", "Export your items into a YAML file"),
	HORSEITEMS_EXPORT_PARAMETERS("Commands.Horse.Subcommands.Items.Subcommands.Export.Parameters", "<file name>"),
	
	HORSEITEMS_IMPORT_NAME("Commands.Horse.Subcommands.Items.Subcommands.Import.Name", "import"),
	HORSEITEMS_IMPORT_PERMISSION("Commands.Horse.Subcommands.Items.Subcommands.Import.Permission", "HorseItems.Admin.Import"),
	HORSEITEMS_IMPORT_DESCRIPTION("Commands.Horse.Subcommands.Items.Subcommands.Import.Description", "Import items from a file"),
	HORSEITEMS_IMPORT_PARAMETERS("Commands.Horse.Subcommands.Items.Subcommands.Import.Parameters", "<file name>");

	String oldpath;

	String path;

	String content;

	String defaultcontent;

	SettingString(String path, String defaultcontent) {
		this.path = path;
		this.oldpath = path;
		this.defaultcontent = defaultcontent;
	}

	SettingString(String path, String oldpath, String defaultcontent) {
		this.path = path;
		this.oldpath = oldpath;
		this.defaultcontent = defaultcontent;
	}

	public String getPath() {
		return this.path;
	}

	public String getOldPath() {
		return this.oldpath;
	}

	public String getDefaultContent() {
		return this.defaultcontent;
	}

	public String toString() {
		return (this.content != null) ? this.content : this.defaultcontent;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
