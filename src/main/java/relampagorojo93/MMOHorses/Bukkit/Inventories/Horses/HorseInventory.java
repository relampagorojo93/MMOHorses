package relampagorojo93.MMOHorses.Bukkit.Inventories.Horses;

import java.util.ArrayList;
import java.util.List;
import net.milkbowl.vault.economy.EconomyResponse;
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
import relampagorojo93.MMOHorses.Enums.Action;
import relampagorojo93.MMOHorses.Enums.Type;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Button;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Item;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.API.Hooks.VaultAPI;
import relampagorojo93.MMOHorses.Bukkit.Events.ChatEvents;
import relampagorojo93.MMOHorses.Bukkit.Events.ChatEventsObjects.ChangeNameInputData;
import relampagorojo93.MMOHorses.Bukkit.Inventories.BaseInventory;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Abstracts.ChestInventoryWithUser;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Settings.SettingsInventory;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Stats.StatsInventory;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Storage.ChestInventory;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Trusting.TrustingInventory;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Upgrades.UpgradeInventory;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Wardrobe.WardrobeInventory;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.UserAccess;
import relampagorojo93.MMOHorses.Modules.UtilsPckg.PHApplierPckg.Instances.PHDataApplyRequest;

public class HorseInventory extends ChestInventoryWithUser {

	private ClaimedData data;
	private List<Action> actions;
	private int firstaction = 0;

	public HorseInventory(Player player, ClaimedData data) {
		this(player,
				data.getData().getTrusted(player.getUniqueId()) != null
						? data.getData().getTrusted(player.getUniqueId())
						: new UserAccess(data.getData().getId(), player.getUniqueId(),
								data.getData().getOwner().compareTo(player.getUniqueId()) == 0,
								player.hasPermission("Horse.BypassOwnership")),
				data);
	}

	public HorseInventory(Player player, boolean adminmode, ClaimedData data) {
		this(player, new UserAccess(data.getData().getId(), player.getUniqueId(),
				data.getData().getOwner().compareTo(player.getUniqueId()) == 0, adminmode), data);
	}

	public HorseInventory(Player player, UserAccess user, ClaimedData data) {
		super(player, user);
		this.data = data;
		setName(MessagesUtils.color(data.getData().getName()));
		setBackground(BaseInventory.getBase());
	}

	@Override
	public Inventory getInventory() {
		if (actions == null) {
			actions = new ArrayList<>();
			for (String action : SettingList.ENABLE_HORSE_MENU_OPTIONS.toList()) {
				try {
					Action ma = Action.valueOf(action.toUpperCase());
					if (ma == Action.ITEM_USAGE)
						throw new IllegalArgumentException();
					if ((ma == Action.OPEN_WARDROBE && SettingBoolean.MAKECOSMETICSPUBLIC.toBoolean())
							|| (ma == Action.BUY_HORSE && data.getData().getPrice() > 0D && VaultAPI.isHooked()
									&& !getUserAccess().isOwner())
							|| (ma != Action.OPEN_WARDROBE && ma != Action.BUY_HORSE
									&& getUserAccess().hasAccess(ma)))
						actions.add(ma);
				} catch (IllegalArgumentException e) {
					Bukkit.getLogger().info("MMOHorses >> " + action + " is not a registered menu option");
				}
			}
		}
		setSize(actions.size() == 0 ? 27 : 54);
		ItemStack info = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");
		ItemMeta im = info.getItemMeta();
		im.setDisplayName(MessageString.HORSEGUI_INFONAME.toString());
		List<String> lore = new ArrayList<>();
		for (String l : MessageList.HORSEGUI_INFOLORE.toList())
			lore.addAll(new PHDataApplyRequest(data, getPlayer(), l).generate().getStrings());
		im.setLore(lore);
		info.setItemMeta(im);
		if (data.getData().getType() == Type.HORSE)
			info = ItemStacksUtils.setSkin(info, data.getData().getColour().getHorseSkin());
		else if (data.getData().getType() == Type.LLAMA || data.getData().getType() == Type.TRADERLLAMA)
			info = ItemStacksUtils.setSkin(info, data.getData().getColour().getLlamaSkin());
		else
			info = ItemStacksUtils.setSkin(info, data.getData().getType().getSkin());
		setSlot(13, new Item(info));
		if (actions.size() != 0) {
			for (int i = -2; i < 3; i++) {
				int action = firstaction + i;
				while (action < 0)
					action += actions.size();
				while (action >= actions.size())
					action -= actions.size();
				ItemStack pane = null, item = null;
				switch (actions.get(action)) {
				case OPEN_SETTINGS:
					pane = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 10, "PURPLE_STAINED_GLASS_PANE");
					item = new ItemStack(Material.STONE_PICKAXE);
					im = item.getItemMeta();
					im.setDisplayName(MessageString.HORSEGUI_SETTINGSNAME.toString());
					im.setLore(MessageList.HORSEGUI_SETTINGSLORE.toList());
					im.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
					item.setItemMeta(im);
					break;
				case OPEN_TRUSTING:
					pane = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 11, "BLUE_STAINED_GLASS_PANE");
					item = ItemStacksUtils.getItemStack("BOOK_AND_QUILL", "WRITABLE_BOOK");
					im = item.getItemMeta();
					im.setDisplayName(MessageString.HORSEGUI_TRUSTINGNAME.toString());
					im.setLore(MessageList.HORSEGUI_TRUSTINGLORE.toList());
					item.setItemMeta(im);
					break;
				case OPEN_UPGRADES:
					pane = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 3,
							"LIGHT_BLUE_STAINED_GLASS_PANE");
					item = new ItemStack(Material.ANVIL);
					im = item.getItemMeta();
					im.setDisplayName(MessageString.HORSEGUI_UPGRADESNAME.toString());
					im.setLore(MessageList.HORSEGUI_UPGRADESLORE.toList());
					item.setItemMeta(im);
					break;
				case OPEN_VIRTUALCHEST:
					pane = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 9, "CYAN_STAINED_GLASS_PANE");
					item = new ItemStack(Material.ENDER_CHEST);
					im = item.getItemMeta();
					im.setDisplayName(MessageString.HORSEGUI_VIRTUALCHESTNAME.toString());
					im.setLore(MessageList.HORSEGUI_VIRTUALCHESTLORE.toList());
					item.setItemMeta(im);
					break;
				case OPEN_WARDROBE:
					pane = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 12, "BROWN_STAINED_GLASS_PANE");
					item = new ItemStack(Material.LEATHER_CHESTPLATE);
					im = item.getItemMeta();
					im.setDisplayName(MessageString.HORSEGUI_WARDROBENAME.toString());
					im.setLore(MessageList.HORSEGUI_WARDROBELORE.toList());
					im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
					item.setItemMeta(im);
					break;
				case OPEN_STATS:
					pane = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 5, "LIME_STAINED_GLASS_PANE");
					item = new ItemStack(Material.BOOK);
					im = item.getItemMeta();
					im.setDisplayName(MessageString.HORSEGUI_STATSNAME.toString());
					im.setLore(MessageList.HORSEGUI_STATSLORE.toList());
					im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
					item.setItemMeta(im);
					break;
				case CHANGE_NAME:
					pane = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 13, "GREEN_STAINED_GLASS_PANE");
					item = new ItemStack(Material.NAME_TAG);
					im = item.getItemMeta();
					im.setDisplayName(MessageString.HORSEGUI_CHANGENAMENAME.toString());
					item.setItemMeta(im);
					break;
				case BUY_HORSE:
					pane = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 5, "LIME_STAINED_GLASS_PANE");
					item = new ItemStack(Material.EMERALD);
					im = item.getItemMeta();
					im.setDisplayName(
							new PHDataApplyRequest(data, getPlayer(), MessageString.HORSEGUI_BUYNAME.toString())
									.generate().getStrings().get(0));
					item.setItemMeta(im);
					break;
				default:
					break;
				}
				if (pane != null && item != null) {
					Action act = actions.get(action);
					im = pane.getItemMeta();
					im.setDisplayName(ChatColor.BLACK.toString());
					pane.setItemMeta(im);
					for (int j = 0; j < 3; j++) {
						ItemStack it = pane;
						if (j == 1)
							it = item;
						setSlot(((31 + i) + (9 * j)), new Button(it) {
							private final Action menuaction = act;

							@Override
							public void onClick(InventoryClickEvent e) {
								onAction(menuaction);
							}
						});
					}
				}
			}
		}
		if (getSize() == 54) {
			ItemStack head = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");
			im = head.getItemMeta();
			im.setDisplayName(MessageString.HORSEGUI_PREVIOUSARROWNAME.toString());
			head.setItemMeta(im);
			Button button = new Button(ItemStacksUtils.setSkin(head,
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==")) {
				@Override
				public void onClick(InventoryClickEvent e) {
					if (--firstaction < 0)
						firstaction = actions.size() - 1;
					updateInventory(MMOHorsesAPI.getPlugin());
				}
			};
			setSlot(28, button);
			setSlot(46, button);
			im.setDisplayName(MessageString.HORSEGUI_NEXTARROWNAME.toString());
			head.setItemMeta(im);
			button = new Button(ItemStacksUtils.setSkin(head,
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19")) {
				@Override
				public void onClick(InventoryClickEvent e) {
					if (++firstaction >= actions.size())
						firstaction = 0;
					updateInventory(MMOHorsesAPI.getPlugin());
				}
			};
			setSlot(34, button);
			setSlot(52, button);
		}
		return super.getInventory();
	}

	private void onAction(Action action) {
		switch (action) {
		case BUY_HORSE:
			EconomyResponse er = VaultAPI.getEconomy().withdrawPlayer((OfflinePlayer) getPlayer(),
					data.getData().getPrice());
			if (er.transactionSuccess()) {
				VaultAPI.getEconomy().depositPlayer(Bukkit.getOfflinePlayer(data.getData().getOwner()),
						data.getData().getPrice());
				Player sllr = Bukkit.getPlayer(data.getData().getOwner());
				if (sllr != null)
					MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils()
							.applyPrefix(MessageString.HORSESOLD.toString().replace("%buyer%", getPlayer().getName())
									.replace("%name%", data.getData().getName())
									.replace("%price%", String.valueOf(data.getData().getPrice()))))
							.sendMessage(sllr);
				data.getData().setOwner(getPlayer().getUniqueId());
				data.getData().setPrice(0.0D);
				data.getData().unregisterTrusteds();
				getPlayer().closeInventory();
				MessagesUtils.getMessageBuilder()
						.createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.HORSEBOUGHT))
						.sendMessage(getPlayer());
			} else
				MessagesUtils.getMessageBuilder()
						.createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.NOTENOUGHMONEY))
						.sendMessage(getPlayer());
			break;
		case CHANGE_NAME:
			closeInventory(MMOHorsesAPI.getPlugin());
			MessagesUtils.getMessageBuilder()
					.createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.NAMECHANGEINCHAT))
					.sendMessage(getPlayer());
			if (!ChatEvents.isRegistered(getPlayer().getUniqueId()))
				ChatEvents.register(new ChangeNameInputData(getPlayer(), data));
			break;
		case OPEN_SETTINGS:
			new SettingsInventory(getPlayer(), getUserAccess(), data).openInventory(MMOHorsesAPI.getPlugin());
			break;
		case OPEN_TRUSTING:
			new TrustingInventory(getPlayer(), getUserAccess(), data).openInventory(MMOHorsesAPI.getPlugin());
			break;
		case OPEN_STATS:
			new StatsInventory(getPlayer(), getUserAccess(), data).openInventory(MMOHorsesAPI.getPlugin());
			break;
		case OPEN_UPGRADES:
			new UpgradeInventory(getPlayer(), getUserAccess(), data).openInventory(MMOHorsesAPI.getPlugin());
			break;
		case OPEN_VIRTUALCHEST:
			new ChestInventory(getPlayer(), getUserAccess(), data).openInventory(MMOHorsesAPI.getPlugin());
			break;
		case OPEN_WARDROBE:
			new WardrobeInventory(getPlayer(), getUserAccess(), data).openInventory(MMOHorsesAPI.getPlugin());
			break;
		default:
			break;
		}
	}
}