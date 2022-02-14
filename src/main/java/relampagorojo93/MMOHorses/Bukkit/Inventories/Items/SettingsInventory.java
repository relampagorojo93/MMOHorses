package relampagorojo93.MMOHorses.Bukkit.Inventories.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Button;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Item;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Bukkit.Events.ChatEvents;
import relampagorojo93.MMOHorses.Bukkit.Events.ChatEventsObjects.ChangeItemMaxLevelInputData;
import relampagorojo93.MMOHorses.Bukkit.Events.ChatEventsObjects.ChangeItemMinLevelInputData;
import relampagorojo93.MMOHorses.Bukkit.Events.ChatEventsObjects.ChangeItemValueInputData;
import relampagorojo93.MMOHorses.Bukkit.Inventories.BaseInventory;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Abstracts.ChestInventoryWithAdmin;
import relampagorojo93.MMOHorses.Enums.ItemType;
import relampagorojo93.MMOHorses.Modules.CraftPckg.Objects.CraftableItem;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;

public class SettingsInventory extends ChestInventoryWithAdmin {

	private CraftableItem item;
	private ItemType type;
	private float value;
	private int min_level, max_level;

	public SettingsInventory(Player player, boolean hasadminmode, CraftableItem item) {
		super(player, hasadminmode);
		this.item = item;
		this.type = item.getType();
		this.value = item.getValue();
		this.min_level = item.getMinLevel();
		this.max_level = item.getMaxLevel();
		setName(item.getName() + " recipe settings");
		setSize(45);
		setBackground(BaseInventory.getBase());
	}

	public float getValue() {
		return Math.round(value * 10) / 10F;
	}

	public void setValue(float value) {
		this.value = Math.round(value * 10) / 10F;
	}

	public int getMinLevel() {
		return min_level;
	}

	public void setMinLevel(int min_level) {
		this.min_level = min_level;
	}

	public int getMaxLevel() {
		return max_level;
	}

	public void setMaxLevel(int max_level) {
		this.max_level = max_level;
	}

	@Override
	public Inventory getInventory() {
		/////////////////////////////////////////////////////////////
		//
		// Item Type section
		//
		/////////////////////////////////////////////////////////////
		final ItemType ptype = type.ordinal() == 0 ? ItemType.values()[ItemType.values().length - 1]
				: ItemType.values()[type.ordinal() - 1];
		ItemStack i = null;
		ItemMeta im;
		i = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");
		i = ItemStacksUtils.setSkin(i,
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==");
		im = i.getItemMeta();
		im.setDisplayName("§aSwitch type");
		im.setLore(Arrays.asList(MessagesUtils.color("§6Previous type:§7 " + ptype)));
		i.setItemMeta(im);
		setSlot(11, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				type = ptype;
				updateInventory(MMOHorsesAPI.getPlugin());
			}
		});
		final ItemType ntype = type.ordinal() == ItemType.values().length - 1 ? ItemType.values()[0]
				: ItemType.values()[type.ordinal() + 1];
		i = ItemStacksUtils.setSkin(i,
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19");
		im = i.getItemMeta();
		im.setDisplayName("§aSwitch type");

		im.setLore(Arrays.asList("§6Next type:§7 " + ntype));
		i.setItemMeta(im);
		setSlot(15, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				type = ntype;
				updateInventory(MMOHorsesAPI.getPlugin());
			}
		});
		/////////////////////////////////////////////////////////////
		//
		// Value section
		//
		/////////////////////////////////////////////////////////////
		switch (type) {
		case CUSTOM:
			i = new ItemStack(Material.ENDER_PEARL);
			break;
		case COSMETIC:
			i = new ItemStack(Material.PAINTING);
			break;
		case HAIRBRUSH:
			i = new ItemStack(Material.WHEAT);
			break;
		case SADDLE:
			i = new ItemStack(Material.SADDLE);
			break;
		case ARMOR:
			i = new ItemStack(Material.IRON_CHESTPLATE);
			break;
		case SPEED_UPGRADE:
			i = new ItemStack(Material.BLAZE_POWDER);
			break;
		case JUMP_UPGRADE:
			i = new ItemStack(Material.RABBIT_FOOT);
			break;
		case HEALTH_UPGRADE:
			i = new ItemStack(Material.APPLE);
			break;
		case CHEST_UPGRADE:
			i = new ItemStack(Material.CHEST);
			break;
		case FOOD:
			i = new ItemStack(Material.CARROT);
			break;
		case BREEDING_FOOD:
			i = new ItemStack(Material.GOLDEN_APPLE);
			break;
		case LEVEL_BOOSTER:
			i = ItemStacksUtils.getItemStack("ENCHANTMENT_TABLE", "ENCHANTING_TABLE");
			break;
		case CATCHER:
			i = new ItemStack(Material.EGG);
			break;
		case NAMETAG:
			i = new ItemStack(Material.NAME_TAG);
			break;
		default:
			break;
		}
		im = i.getItemMeta();
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		im.setDisplayName("§a" + type);
		i.setItemMeta(im);
		setSlot(13, new Item(i));
		i = new ItemStack(Material.EMERALD);
		im = i.getItemMeta();
		im.setDisplayName("§6Value: §a" + value);
		List<String> lore = new ArrayList<>();
		lore.add("§8Function:");
		switch (type) {
		case HAIRBRUSH:
			lore.add("§7Healing per brush");
			break;
		case ARMOR:
			lore.add("§7Armor points");
			break;
		case SPEED_UPGRADE:
			lore.add("§7% of additional speed");
			break;
		case JUMP_UPGRADE:
			lore.add("§7% of additional jump");
			break;
		case HEALTH_UPGRADE:
			lore.add("§7Amount of hearts");
			break;
		case CHEST_UPGRADE:
			lore.add("§7Additional lines on Virtual Chest");
			break;
		case FOOD:
			lore.add("§7Healing");
			break;
		case LEVEL_BOOSTER:
			lore.add("§7Amount of levels");
			break;
		default:
			lore.add("§7None");
			break;
		}
		lore.add("§0");
		lore.add("§e§oClick to edit the value");
		im.setLore(lore);
		i.setItemMeta(im);
		setSlot(29, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				closeInventory(MMOHorsesAPI.getPlugin());
				MessagesUtils.getMessageBuilder()
						.createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.VALUECHANGEINCHAT))
						.sendMessage(getPlayer());
				if (!ChatEvents.isRegistered(getPlayer().getUniqueId()))
					ChatEvents.register(new ChangeItemValueInputData(getPlayer(), (SettingsInventory) getHolder()));
			}
		});
		i = new ItemStack(Material.GLASS_BOTTLE);
		im = i.getItemMeta();
		im.setDisplayName("§6Minimum level: §a" + min_level);
		lore = new ArrayList<>();
		lore.add("§0");
		lore.add("§e§oClick to edit the minimum level");
		im.setLore(lore);
		i.setItemMeta(im);
		setSlot(31, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				closeInventory(MMOHorsesAPI.getPlugin());
				MessagesUtils.getMessageBuilder()
						.createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.MINLEVELCHANGEINCHAT))
						.sendMessage(getPlayer());
				if (!ChatEvents.isRegistered(getPlayer().getUniqueId()))
					ChatEvents.register(new ChangeItemMinLevelInputData(getPlayer(), (SettingsInventory) getHolder()));
			}
		});
		i = ItemStacksUtils.getItemStack("EXP_BOTTLE", "EXPERIENCE_BOTTLE");
		im = i.getItemMeta();
		im.setDisplayName("§6Maximum level: §a" + max_level);
		lore = new ArrayList<>();
		lore.add("§0");
		lore.add("§e§oClick to edit the maximum level");
		im.setLore(lore);
		i.setItemMeta(im);
		setSlot(33, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				closeInventory(MMOHorsesAPI.getPlugin());
				MessagesUtils.getMessageBuilder()
						.createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.MAXLEVELCHANGEINCHAT))
						.sendMessage(getPlayer());
				if (!ChatEvents.isRegistered(getPlayer().getUniqueId()))
					ChatEvents.register(new ChangeItemMaxLevelInputData(getPlayer(), (SettingsInventory) getHolder()));
			}
		});
		ItemStack pane = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 14,
				"RED_STAINED_GLASS_PANE");
		im = pane.getItemMeta();
		im.setDisplayName("§cCancel");
		pane.setItemMeta(im);
		setSlot(36, new Button(pane) {
			@Override
			public void onClick(InventoryClickEvent e) {
				new ListInventory(getPlayer(), hasAdminMode()).openInventory(MMOHorsesAPI.getPlugin());
			}
		});
		pane = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 13, "GREEN_STAINED_GLASS_PANE");
		im = pane.getItemMeta();
		im.setDisplayName("§aConfirm");
		pane.setItemMeta(im);
		setSlot(44, new Button(pane) {
			@Override
			public void onClick(InventoryClickEvent e) {
				item.setItemType(type);
				item.setMinLevel(min_level);
				item.setMaxLevel(max_level);
				item.setValue(value);
				MMOHorsesAPI.getCraftModule().updateItem(item);
				new ListInventory(getPlayer(), hasAdminMode()).openInventory(MMOHorsesAPI.getPlugin());
			}
		});
		return super.getInventory();
	}
}