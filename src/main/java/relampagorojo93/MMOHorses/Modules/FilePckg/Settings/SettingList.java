package relampagorojo93.MMOHorses.Modules.FilePckg.Settings;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public enum SettingList {
	DEFAULTCLAIMNAMES("Player.Default-claim-names",
			Arrays.asList("Buddy", "Lilly", "Alexia", "Scout", "Daisy", "Bella", "Flash", "Tara")),
	FIRSTJOINHORSES("Player.First-join-horses", Arrays.asList("Name_of_custom_horse")),
	IGNORE_WORLDS_FOR_REPLACE("Horse-settings.Entities.Ignore-worlds-for-replace", Arrays.asList("WorldToIgnore")),
	SELECTED_WORLDS_FOR_REPLACE("Horse-settings.Entities.Selected-worlds-for-replace", new ArrayList<>()),
	ENABLE_HORSE_MENU_OPTIONS("Menus.Enable-horse-menu-options",
			Arrays.asList("OPEN_UPGRADES", "OPEN_VIRTUALCHEST", "OPEN_SETTINGS", "OPEN_TRUSTING", "OPEN_WARDROBE", "OPEN_STATS", "CHANGE_NAME", "BUY_HORSE")),
	ENABLE_STATS_ON_MENU("Menus.Stats-menu.Enable-stats-on-menu",
			Arrays.asList("HORSE_LEVEL", "SPEED_LEVEL", "JUMP_LEVEL", "HEALTH_LEVEL", "ARMOR_LEVEL", "INVENTORY_LEVEL")),
	BLOCK_UNCLAIMED_HORSE_DAMAGE_TYPES("Horse-settings.Block-unclaimed-horse-damage-types",
			Arrays.asList("INFIRE", "LIGHTNINGBOLT", "ONFIRE", "LAVA", "HOTFLOOR", "INWALL", "CRAMMING", "DROWN", "STARVE",
					"CACTUS", "FALL", "FLYINTOWALL", "OUTOFWORLD", "GENERIC", "MAGIC", "WITHER", "ANVIL",
					"FALLINGBLOCK", "DRAGONBREATH", "DRYOUT", "SWEETBERRYBUSH", "STING", "MOB", "PLAYER", "ARROW",
					"TRIDENT", "FIREWORKS", "WITHERSKULL", "THROWN", "INDIRECTMAGIC", "THORNS",
					"EXPLOSION.PLAYER")),
	BLOCK_CLAIMED_HORSE_DAMAGE_TYPES("Horse-settings.Block-claimed-horse-damage-types",
			Arrays.asList("INFIRE", "LIGHTNINGBOLT", "ONFIRE", "LAVA", "HOTFLOOR", "INWALL", "CRAMMING", "DROWN", "STARVE",
					"CACTUS", "FALL", "FLYINTOWALL", "OUTOFWORLD", "GENERIC", "MAGIC", "WITHER", "ANVIL",
					"FALLINGBLOCK", "DRAGONBREATH", "DRYOUT", "SWEETBERRYBUSH", "STING", "MOB", "PLAYER", "ARROW",
					"TRIDENT", "FIREWORKS", "WITHERSKULL", "THROWN", "INDIRECTMAGIC", "THORNS",
					"EXPLOSION.PLAYER")),
	HORSE_ALIASES("Commands.Horse.Aliases", Arrays.asList("h")),
	HORSE_BUY_ALIASES("Commands.Horse.Subcommands.Buy.Aliases", Arrays.asList("b")),
	HORSE_CLAIM_ALIASES("Commands.Horse.Subcommands.Claim.Aliases", Arrays.asList("c")),
	HORSE_DEBUG_ALIASES("Commands.Horse.Subcommands.Debug.Aliases", Arrays.asList("dg")),
	HORSE_DELETE_ALIASES("Commands.Horse.Subcommands.Delete.Aliases", Arrays.asList("d")),
	HORSE_GELD_ALIASES("Commands.Horse.Subcommands.Geld.Aliases", Arrays.asList("g")),
	HORSE_GIFT_ALIASES("Commands.Horse.Subcommands.Gift.Aliases", Arrays.asList("gf")),
	HORSE_GIVE_ALIASES("Commands.Horse.Subcommands.Give.Aliases", Arrays.asList("gv")),
	HORSE_GIVEPRESENT_ALIASES("Commands.Horse.Subcommands.GivePresent.Aliases", Arrays.asList("gp")),
	HORSE_HEAL_ALIASES("Commands.Horse.Subcommands.Heal.Aliases", Arrays.asList("hl")),
	HORSE_HELP_ALIASES("Commands.Horse.Subcommands.Help.Aliases", Arrays.asList("h")),
	HORSE_INFO_ALIASES("Commands.Horse.Subcommands.Info.Aliases", Arrays.asList("i")),
	HORSE_LISTALL_ALIASES("Commands.Horse.Subcommands.ListAll.Aliases", Arrays.asList("la")),
	HORSE_LIST_ALIASES("Commands.Horse.Subcommands.List.Aliases", Arrays.asList("l")),
	HORSE_LUNGE_ALIASES("Commands.Horse.Subcommands.Lunge.Aliases", Arrays.asList("ln")),
	HORSE_RELEASE_ALIASES("Commands.Horse.Subcommands.Release.Aliases", Arrays.asList("r")),
	HORSE_RELOAD_ALIASES("Commands.Horse.Subcommands.Reload.Aliases", Arrays.asList("rl")),
	HORSE_RENAME_ALIASES("Commands.Horse.Subcommands.Rename.Aliases", Arrays.asList("rn")),
	HORSE_SELL_ALIASES("Commands.Horse.Subcommands.Sell.Aliases", Arrays.asList("s")),
	HORSE_SETCOLOR_ALIASES("Commands.Horse.Subcommands.SetColor.Aliases", Arrays.asList("sc")),
	HORSE_SETGENDER_ALIASES("Commands.Horse.Subcommands.SetGender.Aliases", Arrays.asList("sg")),
	HORSE_SETHEALTH_ALIASES("Commands.Horse.Subcommands.SetHealth.Aliases", Arrays.asList("sh")),
	HORSE_SETJUMP_ALIASES("Commands.Horse.Subcommands.SetJump.Aliases", Arrays.asList("sj")),
	HORSE_SETMARKING_ALIASES("Commands.Horse.Subcommands.SetMarking.Aliases", Arrays.asList("sm")),
	HORSE_SETOWNER_ALIASES("Commands.Horse.Subcommands.SetOwner.Aliases", Arrays.asList("so")),
	HORSE_SETSPEED_ALIASES("Commands.Horse.Subcommands.SetSpeed.Aliases", Arrays.asList("ss")),
	HORSE_TRUST_ALIASES("Commands.Horse.Subcommands.Trust.Aliases", Arrays.asList("t")),
	HORSE_UNSUMMON_ALIASES("Commands.Horse.Subcommands.Unsummon.Aliases", Arrays.asList("us")),
	HORSE_UNTRUST_ALIASES("Commands.Horse.Subcommands.Untrust.Aliases", Arrays.asList("ut")),
	
	// ---------------------------------------------------------------------//
	// Horse lands commands
	// ---------------------------------------------------------------------//
	
	HORSELANDS_ALIASES("Commands.Horse.Subcommands.Lands.Aliases", "Commands.HorseLands.Aliases", Arrays.asList("hl")),
	HORSELANDS_HELP_ALIASES("Commands.Horse.Subcommands.Lands.Subcommands.Help.Aliases", Arrays.asList("h")),
	HORSELANDS_SETRESTRICTION_ALIASES("Commands.Horse.Subcommands.Lands.Subcommands.SetRestriction.Aliases",
			Arrays.asList("sr")),
	
	// ---------------------------------------------------------------------//
	// Horse sql commands
	// ---------------------------------------------------------------------//
	
	HORSESQL_ALIASES("Commands.Horse.Subcommands.SQL.Aliases", Arrays.asList("")),
	HORSESQL_HELP_ALIASES("Commands.Horse.Subcommands.SQL.Subcommands.Help.Aliases", Arrays.asList("h")),
	HORSESQL_CLEAR_ALIASES("Commands.Horse.Subcommands.SQL.Subcommands.Clear.Aliases", Arrays.asList("cl")),
	HORSESQL_PARSEFROMSQLITE_ALIASES("Commands.Horse.Subcommands.SQL.Subcommands.ParseFromSQLite.Aliases",
			Arrays.asList("pfs")),
	HORSESQL_PARSEFROMMYSQL_ALIASES("Commands.Horse.Subcommands.SQL.Subcommands.ParseFromMySQL.Aliases",
			Arrays.asList("pfm")),
	HORSESQL_UNLINK_ALIASES("Commands.Horse.Subcommands.SQL.Subcommands.Unlink.Aliases", Arrays.asList("ul")),
	
	// ---------------------------------------------------------------------//
	// Horse items commands
	// ---------------------------------------------------------------------//
	
	HORSEITEMS_ALIASES("Commands.Horse.Subcommands.Items.Aliases", Arrays.asList("it")),
	HORSEITEMS_HELP_ALIASES("Commands.Horse.Subcommands.Items.Subcommands.Help.Aliases", Arrays.asList("h")),
	HORSEITEMS_ADD_ALIASES("Commands.Horse.Subcommands.Items.Subcommands.Add.Aliases", Arrays.asList("a")),
	HORSEITEMS_GET_ALIASES("Commands.Horse.Subcommands.Items.Subcommands.Get.Aliases", Arrays.asList("g")),
	HORSEITEMS_LIST_ALIASES("Commands.Horse.Subcommands.Items.Subcommands.List.Aliases", Arrays.asList("l")),
	HORSEITEMS_EXPORT_ALIASES("Commands.Horse.Subcommands.Items.Subcommands.Export.Aliases", Arrays.asList("e")),
	HORSEITEMS_IMPORT_ALIASES("Commands.Horse.Subcommands.Items.Subcommands.Import.Aliases", Arrays.asList("i"));

	// Methods
	String oldpath, path;
	List<String> content, defaultcontent;

	SettingList(String path, List<String> defaultcontent) {
		this(path, path, defaultcontent);
	}

	SettingList(String path, String oldpath, List<String> defaultcontent) {
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

	public List<String> getDefaultContent() {
		return defaultcontent;
	}

	public List<String> toList() {
		return content != null ? content : defaultcontent;
	}

	public void setContent(List<String> content) {
		this.content = content;
	}
}