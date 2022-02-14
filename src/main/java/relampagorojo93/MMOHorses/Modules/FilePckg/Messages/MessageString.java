package relampagorojo93.MMOHorses.Modules.FilePckg.Messages;

public enum MessageString {
	PREFIX("Prefix", "&6&lMMO&e&lHorses &a&l>>&r&7"),
	RELOAD("Message.Reload", "The plugin has been reloaded successfully."),
	ALREADYCASTRATED("Message.Already-castrated", "Your horse is already castrated."),
	ALREADYCLAIMED("Message.Already-claimed", "This horse is already claimed."),
	ALREADYTRUSTED("Message.Already-trusted", "This player is already a trusted player."),
	CANCELLED("Message.Cancelled", "Your action has been cancelled."),
	CLEAREDALL("Message.Cleared-all", "All the database has been cleared!"),
	CLEAREDHORSES("Message.Cleared-horses", "All horses on the database have been cleared!"),
	CLEAREDITEMS("Message.Cleared-items", "All items on the database have been cleared!"),
	CONSOLEDENIED("Message.Console-denied", "You can't use this command in console."),
	COOLDOWNACTIVE("Message.Cooldown-active",
			"Your horse have a cooldown of %remaining% second/s. Please wait until your horse gets ready."),
	DISABLEDLINK("Message.Disabled-link", "You can't link in this server."),
	DISABLEDUNLINK("Message.Disabled-unlink", "You can't unlink in this server."),
	EMPTYHAND("Message.Empty-hand", "Your hand is empty. You must have an item in your hand."),
	ENTITYNOTSUPPORTED("Message.Entity-not-supported", "This entity is not supported."),
	ENTITYNOTSUPPORTSFEATURE("Message.Entity-not-supports-feature", "This entity seems to not support this feature!"),
	GENDERSDISABLED("Message.Genders-disabled", "Genders are disabled, you can't use this feature."),
	HORSEBOUGHT("Message.Horse-bought", "You have bought this horse!"),
	HORSECASTRATED("Message.Horse-castrated", "Your horse has been castrated."),
	HORSECLAIMED("Message.Horse-claimed", "Horse claimed with name %name%."),
	HORSEDELETED("Message.Horse-deleted", "You have successfully deleted this horse."),
	HORSEEDITED("Message.Horse-edited", "You have successfully edited this horse."),
	HORSEERROR("Message.Horse-error",
			"Seems like something happened with that horse. (probably it become unclaimed, sold or deleted)"),
	HORSELEVELUP("Message.Horse-level-up", "You horse is now level %level%!"),
	HORSEMAXLEVEL("Message.Horse-max-level", "Your horse already reach its max level!"),
	HORSENOTSUMMONED("Message.Horse-not-summoned", "You haven't summon this horse yet."),
	HORSERELEASED("Message.Horse-released",
			"You released this horse. Use /horse claim [name] again if you wish to get it back."),
	HORSESOLD("Message.Horse-sold", "%buyer% bought your horse %name% for %price%!"),
	ITEMADDED("Message.Item-added", "A new item has been added."),
	ITEMALREADYEXISTS("Message.Item-already-exists", "There's already an item with this name."),
	ITEMMAXLEVELEXCEEDED("Message.Item-max-level-exceeded", "Your horse has a higher level than the allowed for this item."),
	ITEMMINLEVELNOTREACHED("Message.Item-min-level-not-reached", "Your horse doesn't have the required level to use this item."),
	LANDSNOTHOOKED("Message.Lands-not-hooked", "Lands is not hooked. You can't use this features."),
	MAXLEVELCHANGEINCHAT("Message.Max-level-change-in-chat",
			"Specify a new maximum level for the item. Write 'cancel' to cancel this action."),
	MAXLEVELCHANGED("Message.Max-level-changed", "You have changed the maximum level of the item."),
	MAXLEVELNOTSPECIFIED("Message.Max-level-not-specified", "You must specify a maxium level for the item."),
	MAXREACHED("Message.Max-reached", "You have reached your max allowed."),
	MINLEVELCHANGEINCHAT("Message.Min-level-change-in-chat",
			"Specify a new minimum level for the item. Write 'cancel' to cancel this action."),
	MINLEVELCHANGED("Message.Min-level-changed", "You have changed the minimum level of the item."),
	MINLEVELNOTSPECIFIED("Message.Min-level-not-specified", "You must specify a minium level for the item."),
	MISSINGENTITY("Message.Missing-entity",
			"This horse doesn't have an entity linked to it. You can't link this horse to the server."),
	MYSQLCONNECTIONERROR("Message.MySQL-connection-error", "There was an error trying to connect to the MySQL database (Check your MySQL configuration)."),
	MYSQLDISABLEDERROR("Message.MySQL-disabled-error", "You can't parse MySQL information to SQLite while you aren't using MySQL."),
	MYSQLENABLEDERROR("Message.MySQL-enabled-error", "You can't parse SQLite information to MySQL while you're using MySQL."),
	NAMECHANGEINCHAT("Message.Name-change-in-chat",
			"Specify a new name for your steed. Write 'cancel' to cancel this action."),
	NAMECHANGED("Message.Name-changed", "You have changed the name of your steed."),
	NAMEMAXLENGTHREACHED("Message.Name-max-length-reached", "The name of your steed exceeds the max of 32 characters."),
	NAMENOTSPECIFIED("Message.Name-not-specified", "You must specify a name for your steed."),
	NOBRED("Message.No-bred", "These horses can't be bred."),
	NOMOREONSALE("Message.No-more-on-sale", "This horse is no more on sale now"),
	NOPERMISSIONS("Message.No-permissions", "You don't have permissions for this."),
	NOTBUYYOURSELF("Message.Not-buy-yourself", "You can't buy your own horses."),
	NOTCLAIMED("Message.Not-claimed", "This horse is not claimed."),
	NOTENOUGHMONEY("Message.Not-enough-money", "You don't have enough money for this."),
	NOTENOUGHEXPERIENCE("Message.Not-enough-experience", "You don't have enough experience for this."),
	NOTENOUGHEXPERIENCELEVELS("Message.Not-enough-experience-levels", "You don't have enough experience levels for this."),
	NOTINALAND("Message.Not-in-a-land", "You're not inside a land."),
	NOTINANAREA("Message.Not-in-an-area", "You're not inside an area"),
	NOTLEASHED("Message.Not-leashed", "You aren't leashed to an owned horse"),
	NOTONSALE("Message.Not-on-sale", "Seems like this horse is no more on sale."),
	NOTOWNER("Message.Not-owner", "You aren't the owner of this horse."),
	NOTONLINE("Message.Not-online", "This player is not online."),
	NOTRIDEABLE("Message.Not-rideable", "You can't ride this horse."),
	NOTRIDING("Message.Not-riding", "You aren't riding a horse."),
	NOTTAMED("Message.Not-tamed", "This horse is not tamed."),
	NOTTRUSTED("Message.Not-trusted", "This player is not a trusted player on your horse."),
	NOTYOURSELF("Message.Not-yourself", "The player can't be yourself."),
	ONSALE("Message.On-sale", "This horse is now on sale."),
	ONLYNUMBERS("Message.Only-numbers", "You must specify a number."),
	ONLYNUMBERSLETTERSUNDERSCORE("Message.Only-numbers-letters-underscore", "You can only use numbers, letters and underscore here."),
	ONLYUUID("Message.Only-UUID", "You must specify a valid UUID!"),
	OWNERCHANGED("Message.Owner-changed", "Horse's owner has been changed."),
	PARSEDDATA("Message.Parsed-data", "Data has been parsed successfully."),
	PARSINGERROR("Message.Parsing-error", "There was an error trying to parse the database information (Contact with the developer)."),
	PRICETOOLOW("Message.Price-too-low", "You must set a price equal or higher than %minimum_price%"),
	PLAYERMAXREACHED("Message.Player-max-reached", "This player has reached the max amount of claims."),
	PLAYERMAXSUMMONREACHED("Message.Player-max-summon-reached", "This player has reached the max amount of summons he can have at the same time."),
	RESPAWNACTIVE("Message.Respawn-active",
			"Your horse is dead, you still have to wait %remaining% second/s to respawn it!"),
	RESTRICTIONSET("Message.Restriction-set", "The restriction has been set successfully."),
	RESTRICTIONREMOVED("Message.Restriction-removed", "The restriction has been removed successfully."),
	SAMEGENDER("Message.Same-gender", "The horse's gender is %gender%."),
	STATUPGRADED("Message.Stat-upgraded", "The stat has been upgraded successfully!"),
	SUMMONNOTALLOWED("Message.Summon-not-allowed", "You can't summon a horse here."),
	TOOMANYSUMMONEDHORSES("Message.Too-many-summoned-horses", "You reached the max amount of horses you can summon at the same time."),
	TRUSTADDED("Message.Trust-added", "Player added."), TRUSTREMOVED("Message.Trust-removed", "Player removed."),
	UNLINKEDHORSES("Message.Unlinked-horses", "All horses on the database have been unlinked!"),
	WORLDSWITCHBLOCKED("Message.World-switch-blocked", "You can't teleport a horse to another world."),
	HORSEINFO_NEVER("Message.Horse-info.Never", "&aNever"), HORSEINFO_YES("Message.Horse-info.Yes", "&aYes"),
	HORSEINFO_NO("Message.Horse-info.No", "&cNo"), HORSEINFO_ON("Message.Horse-info.On", "&aON"),
	HORSEINFO_OFF("Message.Horse-info.Off", "&cOFF"), HORSEINFO_PRICE("Message.Horse-info.Price", "&a%horse_price%$"),
	HORSEINFO_NOTINSALE("Message.Horse-info.Not-in-sale", "&cNot in sale"), GENDER_MARE("Message.Gender.Mare", "Mare"),
	GENDER_STALLION("Message.Gender.Stallion", "Stallion"), GENDER_GELDING("Message.Gender.Gelding", "Gelding"),
	SPEEDBAR_BARFORMAT("Message.Speed-bar.Bar-format",
			"%horse_name% &f| &e%horse_health_amount%&4\u2764 &f| &e%horse_walkmode_percent%\\%"),
	SPEEDBAR_NOSADDLE("Message.Speed-bar.No-saddle", " (&6No saddle!&8)"),
	SPEEDBAR_NOSADDLEUPGRADE("Message.Speed-bar.No-saddle-upgrade", " (&6No saddle upgrade!&8)"),
	SQLITECONNECTIONERROR("Message.SQLite-connection-error", "There was an error trying to connect to the SQLite database (Contact with the developer)."),
	VALUECHANGEINCHAT("Message.Value-change-in-chat",
			"Specify a new value for the item. Write 'cancel' to cancel this action."),
	VALUECHANGED("Message.Value-changed", "You have changed the value of the item."),
	VALUENOTSPECIFIED("Message.Value-not-specified", "You must specify a value for the item."),
	
	BUYGUI_TITLE("Buy-GUI.Title", "Buy"),
	BUYGUI_HORSEITEMNAME("Buy-GUI.Horse-item-name", "%horse_name% &8(&7%horse_gender%&8) [&a%horse_price%&8]"),
	BUYGUI_CRATESITEMNAME("Buy-GUI.Crates-item-name", "&eCrates shop"),
	
	CHESTGUI_TITLE("Chest-GUI.Title", "Virtual Chest"), CRATESGUI_TITLE("Crates-GUI.Title", "Crates"),
	
	HORSEGUI_TITLE("Horse-GUI.Title", "%horse_name%"), HORSEGUI_INFONAME("Horse-GUI.Info-name", "&eInfo:"),
	HORSEGUI_NOSADDLENAME("Horse-GUI.No-saddle-name", "&cNo Saddle"),
	HORSEGUI_NOARMORNAME("Horse-GUI.No-armor-name", "&cNo Armor"),
	HORSEGUI_NOCARPETNAME("Horse-GUI.No-carpet-name", "&cNo Carpet"),
	HORSEGUI_NOACCESSORYNAME("Horse-GUI.No-accessory-name", "&cNo Accessory"),
	HORSEGUI_UPGRADESNAME("Horse-GUI.Upgrades-name", "&eUpgrades"),
	HORSEGUI_VIRTUALCHESTNAME("Horse-GUI.Virtual-chest-name", "&eVirtual Chest"),
	HORSEGUI_WARDROBENAME("Horse-GUI.Wardrobe-name", "&eWardrobe"),
	HORSEGUI_STATSNAME("Horse-GUI.Stats-name", "&eStats"),
	HORSEGUI_SETTINGSNAME("Horse-GUI.Settings-name", "&eSettings"),
	HORSEGUI_TRUSTINGNAME("Horse-GUI.Trusting-name", "&eTrusted players"),
	HORSEGUI_CHANGENAMENAME("Horse-GUI.Changename-name", "&eChange name"),
	HORSEGUI_ITEMUSAGENAME("Horse-GUI.Changename-name", "&eItem usage"),
	HORSEGUI_BUYNAME("Horse-GUI.Buy-name", "&7Buy &8(&a%horse_price%&8)"), LISTGUI_TITLE("List-GUI.Title", "List"),
	HORSEGUI_PREVIOUSARROWNAME("Horse-GUI.Previous-arrow-name", "&ePrevious option"),
	HORSEGUI_NEXTARROWNAME("Horse-GUI.Next-arrow-name", "&eNext option"),
	
	LISTGUI_HORSEITEMNAME("List-GUI.Horse-item-name",
			"%horse_name% &bLvL %horse_level% &8(&7%horse_gender%&8) [&a%horse_owner%&8] <&a%horse_status%&8>"),
	LISTGUI_LEFTCLICKTEXT("List-GUI.Left-click-text", "&8- &6Left-Click: &aTeleport the horse to you"),
	LISTGUI_RIGHTCLICKTEXT("List-GUI.Right-click-text", "&8- &6Right-Click: &eTeleport yourself to the horse"),
	LISTGUI_QKEYTEXT("List-GUI.Q-key-text", "&8- &6Q key: &aUnsummon the horse"),
	LISTGUI_SHIFTLEFTCLICKTEXT("List-GUI.Shift-left-click-text", "&8- &6Shift + Left-Click: &bOpen horse's inventory"),
	LISTGUI_SHIFTRIGHTCLICKTEXT("List-GUI.Shift-right-click-text", "&8- &6Shift + Right-Click: &cDelete the horse"),
	
	ITEMSLISTGUI_TITLE("Items-list-GUI.Title", "Items"),
	
	SETTINGSGUI_TITLE("Settings-GUI.Title", "Settings"),
	SETTINGSGUI_NAMETAGNAME("Settings-GUI.Nametag-name", "&eShow name &8[&a%status%&8]"),
	SETTINGSGUI_HEALTHTAGNAME("Settings-GUI.Healthtag-name", "&eShow health &8[&a%status%&8]"),
	SETTINGSGUI_FOLLOWOWNERNAME("Settings-GUI.Follow-owner-name", "&eFollow owner &8[&a%status%&8]"),
	SETTINGSGUI_LINKNAME("Settings-GUI.Link-name", "&eLink &8[&a%status%&8]"),
	SETTINGSGUI_BLOCKSPEEDNAME("Settings-GUI.Block-speed-name", "&eBlock speed on unmount &8[&a%status%&8]"),
	SETTINGSGUI_PUBLIC("Settings-GUI.Public", "&ePublic &8[&a%status%&8]"),
	
	UPGRADEGUI_TITLE("Upgrade-GUI.Title", "Upgrades"),
	UPGRADEGUI_NOJUMPUPGRADENAME("Upgrade-GUI.No-jump-upgrade-name", "&cJump Upgrade LvL 0"),
	UPGRADEGUI_NOSPEEDUPGRADENAME("Upgrade-GUI.No-speed-upgrade-name", "&cSpeed Upgrade LvL 0"),
	UPGRADEGUI_NOHEALTHUPGRADENAME("Upgrade-GUI.No-health-upgrade-name", "&cHealth Upgrade LvL 0"),
	UPGRADEGUI_NOSKILLUPGRADENAME("Upgrade-GUI.No-skill-upgrade-name", "&cNo Skill"),
	UPGRADEGUI_NOSADDLEUPGRADENAME("Upgrade-GUI.No-saddle-upgrade-name", "&cNo Saddle"),
	UPGRADEGUI_NOARMORUPGRADENAME("Upgrade-GUI.No-armor-upgrade-name", "&cNo Armor"),
	UPGRADEGUI_NOCHESTUPGRADENAME("Upgrade-GUI.No-chest-upgrade-name", "&cChest Upgrade LvL 0"),
	
	TRUSTINGGUI_TITLE("Trusting-GUI.Title", "Trusted players"),
	TRUSTINGGUI_ADDTRUSTEDNAME("Trusting-GUI.Add-trusted-name", "&a&lAdd a new trusted player"),
	
	TRUSTEDITIONGUI_TITLE("Trust-edition-GUI.Title", "Edit %username% access"),
	TRUSTEDITIONGUI_UPGRADESNAME("Trust-edition-GUI.Upgrades-name", "&eUpgrades"),
	TRUSTEDITIONGUI_VIRTUALCHESTNAME("Trust-edition-GUI.Virtual-chest-name", "&eVirtual Chest"),
	TRUSTEDITIONGUI_WARDROBENAME("Trust-edition-GUI.Wardrobe-name", "&eWardrobe"),
	TRUSTEDITIONGUI_SETTINGSNAME("Trust-edition-GUI.Settings-name", "&eSettings"),
	TRUSTEDITIONGUI_TRUSTINGNAME("Trust-edition-GUI.Trusting-name", "&eTrusted players"),
	TRUSTEDITIONGUI_CHANGENAMENAME("Trust-edition-GUI.Changename-name", "&eChange name"),
	TRUSTEDITIONGUI_PREVIOUSARROWNAME("Trust-edition-GUI.Previous-arrow-name", "&ePrevious option"),
	TRUSTEDITIONGUI_NEXTARROWNAME("Trust-edition-GUI.Next-arrow-name", "&eNext option"),
	
	ADDINGGUI_TITLE("Adding-GUI.Title", "Adding trusted players"),
	
	WARDROBEGUI_TITLE("Wardrobe-GUI.Title", "Wardrobe"),
	
	CHANGENAMEGUI_TITLE("Change-name-GUI.Title", "Change name"),
	
	STATSGUI_TITLE("Stats-GUI.Title", "Stats"),
	STATSGUI_HORSELEVELNAME("Stats-GUI.Horse-level-name", "&eHorse level &b%level% &8(&7%cost% $&8)"),
	STATSGUI_SPEEDLEVELNAME("Stats-GUI.Speed-level-name", "&eSpeed level &b%level% &8(&7%cost% $&8)"),
	STATSGUI_JUMPLEVELNAME("Stats-GUI.Jump-level-name", "&eJump level &b%level% &8(&7%cost% $&8)"),
	STATSGUI_HEALTHLEVELNAME("Stats-GUI.Health-level-name", "&eHealth level &b%level% &8(&7%cost% $&8)"),
	STATSGUI_ARMORLEVELNAME("Stats-GUI.Armor-level-name", "&eArmor level &b%level% &8(&7%cost% $&8)"),
	STATSGUI_INVENTORYLEVELNAME("Stats-GUI.Inventory-level-name", "&eInventory level &b%level% &8(&7%cost% $&8)"),
	STATSGUI_PREVIOUSARROWNAME("Stats-GUI.Previous-arrow-name", "&ePrevious stat"),
	STATSGUI_NEXTARROWNAME("Stats-GUI.Next-arrow-name", "&eNext stat"),
	
	COMMONGUI_SUMMONEDTEXT("Common-GUI.Summoned-text", "&aSummoned"),
	COMMONGUI_UNSUMMONEDTEXT("Common-GUI.Unsummoned-text", "&cUnsummoned"),
	COMMONGUI_ALIVETEXT("Common-GUI.Alive-text", "&aAlive"), COMMONGUI_DEADTEXT("Common-GUI.Dead-text", "&cDead"),
	COMMONGUI_LEFTARROWNAME("Common-GUI.Left-arrow-name", "&7&l<- &7Previous"),
	COMMONGUI_RIGHTARROWNAME("Common-GUI.Right-arrow-name", "&7Next &7&l->"),
	COMMONGUI_RETURNNAME("Common-GUI.Return-name", "&6Return"),
	COMMONGUI_SORTINGNAME("Common-GUI.Sorting-name", "&a&lSorting by: &7%sorter%"),
	
	HELP_LEFTARROWAVAILABLE("Help-command.Left-arrow-available", "&e«"),
	HELP_LEFTARROWUNAVAILABLE("Help-command.Left-arrow-unavailable", "&r«"),
	HELP_RIGHTARROWAVAILABLE("Help-command.Right-arrow-available", "&e»"),
	HELP_RIGHTARROWUNAVAILABLE("Help-command.Right-arrow-unavailable", "&r»"),
	
	BROADCASTDEATH_BYPLAYER("Broadcast-death.By-player", ""),
	BROADCASTDEATH_BYOTHER("Broadcast-death.By-other", "");

	String path, oldpath, defaultcontent, content;

	MessageString(String path, String defaultcontent) {
		this(path, path, defaultcontent);
	}

	MessageString(String path, String oldpath, String defaultcontent) {
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
		return ((this.content != null) ? this.content : this.defaultcontent).replace("&", "\u00A7");
	}

	public void setContent(String content) {
		this.content = content;
	}
}
