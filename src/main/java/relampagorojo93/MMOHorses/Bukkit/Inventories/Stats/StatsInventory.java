package relampagorojo93.MMOHorses.Bukkit.Inventories.Stats;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.milkbowl.vault.economy.EconomyResponse;
import relampagorojo93.MMOHorses.Enums.Currency;
import relampagorojo93.MMOHorses.Enums.Stat;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingInt;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingString;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.UserAccess;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Button;
import relampagorojo93.LibsCollection.Utils.Shared.MathUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.API.Hooks.VaultAPI;
import relampagorojo93.MMOHorses.Bukkit.Inventories.BaseInventory;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Abstracts.ChestInventoryWithUser;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Horses.HorseInventory;

public class StatsInventory extends ChestInventoryWithUser {
	
	private ClaimedData data;
	private List<Stat> stats;
	private int firststat = 0;
	
	public StatsInventory(Player player, ClaimedData horse) {
		this(player,
				horse.getData().getTrusted(player.getUniqueId()) != null
						? horse.getData().getTrusted(player.getUniqueId())
						: new UserAccess(horse.getData().getId(), player.getUniqueId(),
								horse.getData().getOwner().compareTo(player.getUniqueId()) == 0,
								player.hasPermission("Horse.BypassOwnership")),
				horse);
	}

	public StatsInventory(Player player, boolean adminmode, ClaimedData horse) {
		this(player, new UserAccess(horse.getData().getId(), player.getUniqueId(),
				horse.getData().getOwner().compareTo(player.getUniqueId()) == 0, adminmode), horse);
	}

	public StatsInventory(Player player, UserAccess user, ClaimedData horse) {
		super(player, user);
		this.data = horse;
		setName(MessageString.STATSGUI_TITLE.toString());
		setSize(27);
		setBackground(BaseInventory.getBase());
	}
	
	@Override
	public Inventory getInventory() {
		if (stats == null) {
			stats = new ArrayList<>();
			for (String sts:SettingList.ENABLE_STATS_ON_MENU.toList()) {
				try {
					stats.add(Stat.valueOf(sts.toUpperCase()));
				} catch (IllegalArgumentException e) {
					Bukkit.getLogger().info("MMOHorses >> " + sts + " is not a registered stat");
				}
			}
		}
		if (stats.size() != 0) {
			ItemStack item = null;
			ItemMeta im;
			for (int i = -2; i < 3; i++) {
				int stat = firststat + i;
				while (stat < 0)
					stat += stats.size();
				while (stat >= stats.size())
					stat -= stats.size();
				ItemStack pane = null;
				Stat stt = stats.get(stat);
				int lvl = data.getStats().getLevel(stt);
				String cost = "";
				try {
					if (lvl < SettingInt.valueOf("MAXIMUM_" + stt.name()).toInt()) cost = MessagesUtils.round(MathUtils.formulaToResult(SettingString.valueOf(stt.name() + "_UPGRADE_COST").toString().replace("%level%", String.valueOf(lvl))), 2);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				List<String> lore;
				switch (stt) {
					case ARMOR_LEVEL:
						pane = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 10,
								"PURPLE_STAINED_GLASS_PANE");
						item = new ItemStack(Material.IRON_CHESTPLATE);
						im = item.getItemMeta();
						im.setDisplayName(MessageString.STATSGUI_ARMORLEVELNAME.toString().replaceAll("%cost%", !cost.isEmpty() ? cost : "MAX").replaceAll("%level%", String.valueOf(lvl)));
						lore = new ArrayList<>();
						for (String line:MessageList.STATSGUI_ARMORLEVELLORE.toList()) lore.add(line.replaceAll("%cost%", !cost.isEmpty() ? cost : "MAX").replaceAll("%level%", String.valueOf(lvl)));
						im.setLore(lore);
						im.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
						item.setItemMeta(im);
						break;
					case HEALTH_LEVEL:
						pane = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 11,
								"BLUE_STAINED_GLASS_PANE");
						item = new ItemStack(Material.APPLE);
						im = item.getItemMeta();
						im.setDisplayName(MessageString.STATSGUI_HEALTHLEVELNAME.toString().replaceAll("%cost%", !cost.isEmpty() ? cost : "MAX").replaceAll("%level%", String.valueOf(lvl)));
						lore = new ArrayList<>();
						for (String line:MessageList.STATSGUI_HEALTHLEVELLORE.toList()) lore.add(line.replaceAll("%cost%", !cost.isEmpty() ? cost : "MAX").replaceAll("%level%", String.valueOf(lvl)));
						im.setLore(lore);
						item.setItemMeta(im);
						break;
					case HORSE_LEVEL:
						pane = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 3,
								"LIGHT_BLUE_STAINED_GLASS_PANE");
						item = ItemStacksUtils.getItemStack("ENCHANTMENT_TABLE", "ENCHANTING_TABLE");
						im = item.getItemMeta();
						im.setDisplayName(MessageString.STATSGUI_HORSELEVELNAME.toString().replaceAll("%cost%", !cost.isEmpty() ? cost : "MAX").replaceAll("%level%", String.valueOf(lvl)));
						lore = new ArrayList<>();
						for (String line:MessageList.STATSGUI_HORSELEVELLORE.toList()) lore.add(line.replaceAll("%cost%", !cost.isEmpty() ? cost : "MAX").replaceAll("%level%", String.valueOf(lvl)));
						im.setLore(lore);
						item.setItemMeta(im);
						break;
					case INVENTORY_LEVEL:
						pane = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 9,
								"CYAN_STAINED_GLASS_PANE");
						item = new ItemStack(Material.CHEST);
						im = item.getItemMeta();
						im.setDisplayName(MessageString.STATSGUI_INVENTORYLEVELNAME.toString().replaceAll("%cost%", !cost.isEmpty() ? cost : "MAX").replaceAll("%level%", String.valueOf(lvl)));
						lore = new ArrayList<>();
						for (String line:MessageList.STATSGUI_INVENTORYLEVELLORE.toList()) lore.add(line.replaceAll("%cost%", !cost.isEmpty() ? cost : "MAX").replaceAll("%level%", String.valueOf(lvl)));
						im.setLore(lore);
						item.setItemMeta(im);
						break;
					case JUMP_LEVEL:
						pane = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 12,
								"BROWN_STAINED_GLASS_PANE");
						item = new ItemStack(Material.RABBIT_FOOT);
						im = item.getItemMeta();
						im.setDisplayName(MessageString.STATSGUI_JUMPLEVELNAME.toString().replaceAll("%cost%", !cost.isEmpty() ? cost : "MAX").replaceAll("%level%", String.valueOf(lvl)));
						lore = new ArrayList<>();
						for (String line:MessageList.STATSGUI_JUMPLEVELLORE.toList()) lore.add(line.replaceAll("%cost%", !cost.isEmpty() ? cost : "MAX").replaceAll("%level%", String.valueOf(lvl)));
						im.setLore(lore);
						item.setItemMeta(im);
						break;
					case SPEED_LEVEL:
						pane = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 13,
								"GREEN_STAINED_GLASS_PANE");
						item = new ItemStack(Material.BLAZE_POWDER);
						im = item.getItemMeta();
						im.setDisplayName(MessageString.STATSGUI_SPEEDLEVELNAME.toString().replaceAll("%cost%", !cost.isEmpty() ? cost : "MAX").replaceAll("%level%", String.valueOf(lvl)));
						lore = new ArrayList<>();
						for (String line:MessageList.STATSGUI_SPEEDLEVELLORE.toList()) lore.add(line.replaceAll("%cost%", !cost.isEmpty() ? cost : "MAX").replaceAll("%level%", String.valueOf(lvl)));
						im.setLore(lore);
						item.setItemMeta(im);
						break;
					default:
						break;
				}
				if (pane != null && item != null) {
					im = pane.getItemMeta();
					im.setDisplayName(ChatColor.BLACK.toString());
					pane.setItemMeta(im);
					for (int j = 0; j < 3; j++) {
						ItemStack it = pane;
						if (j == 1) it = item;
						setSlot(((4 + i) + (9 * j)), new Button(it) {
							private Stat stat = stt;
							@Override
							public void onClick(InventoryClickEvent e) {
								onStat(stat);
							}
						});
					}
				}
			}
			ItemStack head = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");
			im = head.getItemMeta();
			im.setDisplayName(MessageString.STATSGUI_PREVIOUSARROWNAME.toString());
			head.setItemMeta(im);
			Button button = new Button(ItemStacksUtils.setSkin(head,
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==")) {
				@Override
				public void onClick(InventoryClickEvent e) {
					if (--firststat < 0)
						firststat = stats.size() - 1;
					updateInventory(MMOHorsesAPI.getPlugin());
				}
			};
			setSlot(1, button);
			setSlot(19, button);
			im.setDisplayName(MessageString.STATSGUI_NEXTARROWNAME.toString());
			head.setItemMeta(im);
			button = new Button(ItemStacksUtils.setSkin(head,
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19")) {
				@Override
				public void onClick(InventoryClickEvent e) {
					if (++firststat >= stats.size())
						firststat = 0;
					updateInventory(MMOHorsesAPI.getPlugin());
				}
			};
			setSlot(7, button);
			setSlot(25, button);
			ItemStack opane = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 1,
					"ORANGE_STAINED_GLASS_PANE");
			im = opane.getItemMeta();
			im.setDisplayName(MessageString.COMMONGUI_RETURNNAME.toString());
			opane.setItemMeta(im);
			setSlot(getSize() - 9, new Button(opane) {
				
				@Override
				public void onClick(InventoryClickEvent e) {
					new HorseInventory(getPlayer(), getUserAccess().hasPrivileges(), data)
							.openInventory(MMOHorsesAPI.getPlugin());
					return;				
				}
			});
			return super.getInventory();
		}
		return null;
	}
	
	private void onStat(Stat stat) {
		int lvl = data.getStats().getLevel(stat);
		if (lvl >= SettingInt.valueOf("MAXIMUM_" + stat.name()).toInt()) return;
		if (stat != Stat.HORSE_LEVEL && SettingBoolean.LIMIT_LEVEL_UPGRADE_BY_HORSE_LEVEL.toBoolean() && lvl >= data.getStats().getHorseLevel()) return;
		Currency cur = Currency.EXPERIENCE;
		try {
			cur = Currency.valueOf(SettingString.UPGRADE_COST_CURRENCY.toString().toUpperCase());
		} catch (IllegalArgumentException ex) {
			Bukkit.getLogger().info("MMOHorses >> Invalid currency, using experience instead!");
		}
		try {
			double cost = MathUtils.round(MathUtils.formulaToResult(SettingString.valueOf(stat.name() + "_UPGRADE_COST").toString().replace("%level%", String.valueOf(lvl))), 2);
			switch (cur) {
				case EXPERIENCE:
					long xp = getPlayerExperience();
					if (xp >= cost) {
						setPlayerExperience(xp - (long) cost);
						data.getStats().addLevel(stat, 1);
						updateInventory(MMOHorsesAPI.getPlugin());
						MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.STATUPGRADED)).sendMessage(getPlayer());
					} else
						MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.NOTENOUGHEXPERIENCE)).sendMessage(getPlayer());
					break;
				case EXPERIENCE_LEVEL:
					int lvls = getPlayer().getLevel();
					if (lvls >= cost) {
						getPlayer().setLevel(lvls - (int) cost);
						data.getStats().addLevel(stat, 1);
						updateInventory(MMOHorsesAPI.getPlugin());
						MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.STATUPGRADED)).sendMessage(getPlayer());
					} else
						MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.NOTENOUGHEXPERIENCELEVELS)).sendMessage(getPlayer());
					break;
				case VAULT:
					if (VaultAPI.isHooked()) {
						EconomyResponse er = VaultAPI.getEconomy().withdrawPlayer((OfflinePlayer) getPlayer(), cost);
						if (er.transactionSuccess()) {
							data.getStats().addLevel(stat, 1);
							updateInventory(MMOHorsesAPI.getPlugin());
							MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.STATUPGRADED)).sendMessage(getPlayer());
						} else
							MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.NOTENOUGHMONEY)).sendMessage(getPlayer());
						break;
					}
				default: throw new Exception();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			MessagesUtils.getMessageBuilder().createMessage(MessageString.PREFIX.toString() + " Error!").sendMessage(getPlayer());
		}
	}
	
	private long getPlayerExperience() {
		int level = getPlayer().getLevel();
		return (long) (getExperience(level) + (getPlayer().getExp() * getRequiredExperience(level)));
	}
	
	private long getExperience(int level) {
		if (level < 17)
			return (long) (Math.pow(level, 2) + (6 * level));
		else if (level < 32)
			return (long) ((2.5 * Math.pow(level, 2)) - (40.5 * level) + 360);
		else
			return (long) ((4.5 * Math.pow(level, 2)) - (162.5 * level) + 2220);
	}
	
	private long getRequiredExperience(int level) {
		if (level < 16)
			return 2 * level + 7;
		else if (level < 31)
			return 5 * level - 38;
		else
			return 9 * level - 158;
	}
	
	private void setPlayerExperience(long exp) {
		int level = 0;
		if (exp < 394)
			level = (int) ((-6 + (Math.sqrt(36 + (4*exp))))/2);
		else if (exp < 1628)
			level = (int) ((40.5 + (Math.sqrt(1640.25 - (10*(360 - exp)))))/5);
		else
			level = (int) ((162.5 + (Math.sqrt(26406.25 - (18*(2220 - exp)))))/9);
		exp -= getExperience(level);
		long nextxp = getRequiredExperience(level);
		getPlayer().setLevel(level);
		getPlayer().setTotalExperience((int) exp);
		getPlayer().setExp((float) exp/(float) nextxp);
		
	}
}