package relampagorojo93.MMOHorses.Modules.FilePckg.Messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum MessageList {
	HELP_HEADER("Help-command.Header",
			Arrays.asList(" ", "&c・。・。・。・。・。・。・。%left_arrow%&r %current_page%/%max_page% %right_arrow%&c。・。・。・。・。・。・。・",
					" ")),
	HELP_BODY("Help-command.Body", Arrays.asList("&6%command_usage%", "  &8%command_description%")),
	HELP_FOOT("Help-command.Foot", Arrays.asList(" ", "&c・。・。・。・。・。・。・。・。・。・。・。・。・。・。・。・。・")),

	NONCLAIMEDHORSEINFO("Message.Non-claimed-horse-info",
			Arrays.asList(new String[] { " ", "&8&l>>>>>>-------------------------------<<<<<<", " ",
					"&e  Gender: &a%horse_gender%", " ", "&e  Jump: &a%horse_jump_amount%",
					"&e  Speed: &a%horse_speed_amount%", "&e  Health: &a%horse_health_amount%", " ",
					"&8&l>>>>>>-------------------------------<<<<<<" })),
	CLAIMEDHORSEINFO("Message.Claimed-horse-info", Arrays.asList(new String[] { " ",
			"&8&l>>>>>>-------------------------------<<<<<<", " ", "&e  Name: &a%horse_name%",
			"&e  Gender: &a%horse_gender%", "&e  Level: &a%horse_level%", " ",
			"&e  Jump: &a%horse_jump_amount% &7+ &a%horse_permanentjumpupgrade_amount% &8(&b+ %horse_jumpupgrade_amount%&8) <&7Actual: %horse_jump_actual%&8>",
			"&e  Speed: &a%horse_speed_amount% &7+ &a%horse_permanentspeedupgrade_amount% &8(&b+ %horse_speedupgrade_amount%&8) <&7Actual: %horse_speed_actual%&8>",
			"&e  Health: &a%horse_health_amount% &7+ &a%horse_permanenthealthupgrade_amount% &8(&b+ %horse_healthupgrade_amount%&8) <&7Actual: %horse_health_actual%&8>",
			"&e  Armor points: &a%horse_permanentarmorupgrade_amount% &8(&b+ %horse_armorupgrade_amount%&8)",
			"&e  Has saddle: &a%horse_saddle%", " ", "&e  Price: &a%horse_price%", " ",
			"&8&l>>>>>>-------------------------------<<<<<<" })),

	HORSEGUI_INFOLORE("Horse-GUI.Info-lore", Arrays.asList(new String[] { " ", "&e  Owner: &a%horse_owner%",
			"&e  Gender: &a%horse_gender%", "&e  Level: &a%horse_level%", " ",
			"&e  Jump: &a%horse_jump_amount% &7+ &a%horse_permanentjumpupgrade_amount% &8(&b+ %horse_jumpupgrade_amount%&8) <&7Actual: %horse_jump_actual%&8>",
			"&e  Speed: &a%horse_speed_amount% &7+ &a%horse_permanentspeedupgrade_amount% &8(&b+ %horse_speedupgrade_amount%&8) <&7Actual: %horse_speed_actual%&8>",
			"&e  Health: &a%horse_health_amount% &7+ &a%horse_permanenthealthupgrade_amount% &8(&b+ %horse_healthupgrade_amount%&8) <&7Actual: %horse_health_actual%&8>",
			"&e  Armor points: &a%horse_permanentarmorupgrade_amount% &8(&b+ %horse_armorupgrade_amount%&8)",
			"&e  Has saddle: &a%horse_saddle%", " ", "&e  Lifetime: &a%horse_lifetime% day/s",
			"&e  Claim date: &a%horse_lifetime_date%", "&e  Days since last death: &a%horse_deathtime% day/s",
			"&e  Last death: &a%horse_deathtime_date%", " " })),
	HORSEGUI_UPGRADESLORE("Horse-GUI.Upgrades-lore",
			Arrays.asList(new String[] { "&7Put any item here to", "&7use as accessory.", " ",
					"&7Click inside this inventory", "&7with an upgrade to equip", "&7it automatically." })),
	HORSEGUI_VIRTUALCHESTLORE("Horse-GUI.Virtual-chest-lore",
			Arrays.asList(new String[] { "&7Click on me to open", "&7the virtual chest." })),
	HORSEGUI_WARDROBELORE("Horse-GUI.Wardrobe-lore",
			Arrays.asList(new String[] { "&7Click on me to open", "&7the wardrobe." })),
	HORSEGUI_STATSLORE("Horse-GUI.Stats-lore",
			Arrays.asList(new String[] { "&7Click on me to check", "&7the horse stats", "&7and/or upgrade." })),
	HORSEGUI_SETTINGSLORE("Horse-GUI.Settings-lore",
			Arrays.asList(new String[] { "&7Click on me to edit the", "&7settings of your horse." })),
	HORSEGUI_TRUSTINGLORE("Horse-GUI.Trusting-lore",
			Arrays.asList(new String[] { "&7Click on me to see and", "&7edit horse's trusted players." })),

	SETTINGSGUI_NAMETAGLORE("Settings-GUI.Nametag-lore",
			Arrays.asList(new String[] { "&7Switch this name tag setting", "&7if you want to show", "&7it or not." })),
	SETTINGSGUI_HEALTHTAGLORE("Settings-GUI.Healthtag-lore",
			Arrays.asList(new String[] { "&7Switch this health setting", "&7if you want to show the",
					"&7health of your horse next", "&7to the name or not." })),
	SETTINGSGUI_FOLLOWOWNERLORE("Settings-GUI.Follow-owner-lore",
			Arrays.asList(new String[] { "&7Switch this follow setting", "&7if you want your horse to",
					"&7follow you when you", "&7are close." })),
	SETTINGSGUI_LINKLORE("Settings-GUI.Link-lore",
			Arrays.asList(new String[] { "&7Switch this link setting", "&7if you want to link the",
					"&7horse to this server or not." })),
	SETTINGSGUI_BLOCKSPEEDLORE("Settings-GUI.Block-speed-lore",
			Arrays.asList(new String[] { "&7Switch this speed setting", "&7if you want to block the speed",
					"&7of a horse when it's unmounted." })),
	SETTINGSGUI_PUBLICLORE("Settings-GUI.Public",
			Arrays.asList(new String[] { "&7Switch this setting", "&7if you want to allow other",
					"&7players to use your horse." })),

	TRUSTINGGUI_PLAYERLORE("Trusting-GUI.Player-lore", Arrays.asList(
			new String[] { "&8- &6Click: &cRemove player as trusted", "&8- &6Q key: &cEdit trusted permissions" })),

	ADDINGGUI_PLAYERLORE("Adding-GUI.Player-lore",
			Arrays.asList(new String[] { "&8- &6Click: &aAdd player as trusted" })),

	UPGRADEGUI_NOJUMPUPGRADELORE("Upgrade-GUI.No-jump-upgrade-lore",
			Arrays.asList(new String[] { "&7Put a jump upgrade here to", "&7get a jump boost for your horse." })),
	UPGRADEGUI_NOSPEEDUPGRADELORE("Upgrade-GUI.No-speed-upgrade-lore",
			Arrays.asList(new String[] { "&7Put a speed upgrade here to", "&7get a speed boost for your horse." })),
	UPGRADEGUI_NOHEALTHUPGRADELORE("Upgrade-GUI.No-health-upgrade-lore",
			Arrays.asList(new String[] { "&7Put a health upgrade here to", "&7give your horse additional hearts." })),
	UPGRADEGUI_NOSKILLUPGRADELORE("Upgrade-GUI.No-skill-upgrade-lore",
			Arrays.asList(new String[] { "&7Put a skill upgrade here to", "&7give your horse special abilities." })),
	UPGRADEGUI_NOSADDLEUPGRADELORE("Upgrade-GUI.No-saddle-upgrade-lore",
			Arrays.asList(new String[] { "&7Put a saddle upgrade here to", "&7be able to ride your horse." })),
	UPGRADEGUI_NOARMORUPGRADELORE("Upgrade-GUI.No-armor-upgrade-lore",
			Arrays.asList(new String[] { "&7Put an armor upgrade here to", "&7add some armor points." })),
	UPGRADEGUI_NOCHESTUPGRADELORE("Upgrade-GUI.No-chest-upgrade-lore",
			Arrays.asList(new String[] { "&7Put a chest upgrade here to", "&7add more slots to your inventory." })),

	ITEMSLISTGUI_ITEMLORE("Items-list-GUI.Item-lore",
			Arrays.asList(new String[] { "&e&oClick on me to check the recipe" })),
	ITEMSLISTGUI_INFOITEMLORE("Items-list-GUI.Info-item-lore",
			Arrays.asList(new String[] { "&0  &eItem Name &8→ &a%item_name%",
					"&0  &eItem Type &8→ &a%item_type%",
					"&0  &eValue &8→ &a%item_value%",
					"&0  &eMinimum level &8→ &a%item_min_level%",
					"&0  &eMaximum level &8→ &a%item_max_level%" })),
	ITEMSLISTGUI_ADMINITEMLORE("Items-list-GUI.Admin-item-lore",
			Arrays.asList(new String[] { "&8- &6Left-Click: &aOpen editor",
					"&8- &6Shift + Left-Click: &eGet a copy of the item", "&8- &6Q key: &bOpen settings editor",
					"&8- &6Right-Click: &cRemove recipe" })),

	STATSGUI_HORSELEVELLORE("Stats-GUI.Horse-level-lore",
			Arrays.asList(new String[] { "&7Click me to level", "&7up your horse" })),
	STATSGUI_SPEEDLEVELLORE("Stats-GUI.Speed-level-lore",
			Arrays.asList(new String[] { "&7Click me to level up", "&7your horse's speed" })),
	STATSGUI_JUMPLEVELLORE("Stats-GUI.Jump-level-lore",
			Arrays.asList(new String[] { "&7Click me to level up", "&7your horse's jump" })),
	STATSGUI_HEALTHLEVELLORE("Stats-GUI.Health-level-lore",
			Arrays.asList(new String[] { "&7Click me to level up", "&7your horse's health" })),
	STATSGUI_ARMORLEVELLORE("Stats-GUI.Armor-level-lore",
			Arrays.asList(new String[] { "&7Click me to level up", "&7your horse's armor" })),
	STATSGUI_INVENTORYLEVELLORE("Stats-GUI.Inventory-level-lore",
			Arrays.asList(new String[] { "&7Click me to add more", "&7slots to your virtual", "&7horse chest" })),

	WARDROBEGUI_NOSADDLELORE("Wardrobe-GUI.No-saddle-lore", "Horse-GUI.No-saddle-lore",
			Arrays.asList(new String[] { "&7Put any saddle here to", "&7use as your horse saddle." })),
	WARDROBEGUI_NOARMORLORE("Wardrobe-GUI.No-armor-lore", "Horse-GUI.No-armor-lore",
			Arrays.asList(new String[] { "&7Put any horse armor here to", "&7use as your horse armor." })),
	WARDROBEGUI_NOCARPETLORE("Wardrobe-GUI.No-carpet-lore", "Horse-GUI.No-carpet-lore",
			Arrays.asList(new String[] { "&7Put any carpet here to", "&7use as your llama carpet." })),
	WARDROBEGUI_NOACCESSORYLORE("Wardrobe-GUI.No-accessory-lore", "Horse-GUI.No-accessory-lore",
			Arrays.asList(new String[] { "&7Put any item here to", "&7use as accessory." }));

	String path, oldpath;

	List<String> defaultcontent, content;

	MessageList(String path, List<String> defaultcontent) {
		this(path, path, defaultcontent);
	}

	MessageList(String path, String oldpath, List<String> defaultcontent) {
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

	public List<String> getDefaultContent() {
		return this.defaultcontent;
	}

	public List<String> toList() {
		List<String> list = new ArrayList<>();
		for (String l : (this.content != null) ? this.content : this.defaultcontent)
			list.add(l.replace("&", "\u00A7"));
		return list;
	}

	public void setContent(List<String> content) {
		this.content = content;
	}
}
