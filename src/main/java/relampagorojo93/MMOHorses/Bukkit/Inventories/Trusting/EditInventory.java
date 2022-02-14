package relampagorojo93.MMOHorses.Bukkit.Inventories.Trusting;

import java.util.Arrays;
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
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import relampagorojo93.MMOHorses.Enums.Action;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.UserAccess;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Button;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Item;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Bukkit.Inventories.BaseInventory;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Abstracts.ChestInventoryWithUser;

public class EditInventory extends ChestInventoryWithUser {

	private UserAccess edited;
	private List<Action> actions;
	private int firstaction = 0;

	public EditInventory(Player player, UserAccess user, UserAccess edited) {
		super(player, user);
		this.edited = edited;
		setBackground(BaseInventory.getBase());
	}
	
	@Override
	public Inventory getInventory() {
		if (actions == null) {
			actions = Arrays.asList(Action.OPEN_UPGRADES, Action.OPEN_VIRTUALCHEST, Action.OPEN_SETTINGS,
					Action.OPEN_TRUSTING, Action.OPEN_WARDROBE, Action.CHANGE_NAME, Action.ITEM_USAGE);
		}
		OfflinePlayer op = Bukkit.getOfflinePlayer(edited.getUniqueID());
		if (op != null) {
			setSize(actions.size() == 0 ? 27 : 54);
			setName(MessageString.TRUSTEDITIONGUI_TITLE.toString().replace("%username%", op.getName()));
			ItemStack info = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");
			ItemMeta im = info.getItemMeta();
			im.setDisplayName(op != null && op.getName() != null ? op.getName() : "???");
			info.setItemMeta(im);
			setSlot(13, new Item(info));
			new BukkitRunnable() {
				private final ItemStack i = getSlot(13).getItemStack();
				private final OfflinePlayer pl = op;
				@Override
				public void run() {
					SkullMeta im = (SkullMeta) i.getItemMeta();
					try {
						SkullMeta.class.getMethod("setOwningPlayer", OfflinePlayer.class).invoke(im, pl);
					} catch (Exception e) {
						try {
							SkullMeta.class.getMethod("setOwner", String.class).invoke(im, pl.getName());
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
					i.setItemMeta(im);
				}
			}.runTaskAsynchronously(MMOHorsesAPI.getPlugin());
			ItemStack greenpane = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 5,
					"LIME_STAINED_GLASS_PANE");
			im = greenpane.getItemMeta();
			im.setDisplayName(ChatColor.BLACK.toString());
			greenpane.setItemMeta(im);
			ItemStack redpane = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 14,
					"RED_STAINED_GLASS_PANE");
			im = redpane.getItemMeta();
			im.setDisplayName(ChatColor.BLACK.toString());
			redpane.setItemMeta(im);
			if (actions.size() != 0) {
				for (int i = -2; i < 3; i++) {
					int action = firstaction + i;
					while (action < 0)
						action += actions.size();
					while (action >= actions.size())
						action -= actions.size();
					Action a = actions.get(action);
					ItemStack pane = edited.hasAccess(a) ? greenpane.clone() : redpane.clone(), item = null;
					switch (a) {
						case OPEN_SETTINGS:
							item = new ItemStack(Material.STONE_PICKAXE);
							im = item.getItemMeta();
							im.setDisplayName(MessageString.TRUSTEDITIONGUI_SETTINGSNAME.toString());
							im.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
							item.setItemMeta(im);
							break;
						case OPEN_TRUSTING:
							item = ItemStacksUtils.getItemStack("BOOK_AND_QUILL", "WRITABLE_BOOK");
							im = item.getItemMeta();
							im.setDisplayName(MessageString.TRUSTEDITIONGUI_TRUSTINGNAME.toString());
							item.setItemMeta(im);
							break;
						case OPEN_UPGRADES:
							item = new ItemStack(Material.ANVIL);
							im = item.getItemMeta();
							im.setDisplayName(MessageString.TRUSTEDITIONGUI_UPGRADESNAME.toString());
							item.setItemMeta(im);
							break;
						case OPEN_VIRTUALCHEST:
							item = new ItemStack(Material.ENDER_CHEST);
							im = item.getItemMeta();
							im.setDisplayName(MessageString.TRUSTEDITIONGUI_VIRTUALCHESTNAME.toString());
							item.setItemMeta(im);
							break;
						case OPEN_WARDROBE:
							item = new ItemStack(Material.LEATHER_CHESTPLATE);
							im = item.getItemMeta();
							im.setDisplayName(MessageString.TRUSTEDITIONGUI_WARDROBENAME.toString());
							im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
							item.setItemMeta(im);
							break;
						case CHANGE_NAME:
							item = new ItemStack(Material.NAME_TAG);
							im = item.getItemMeta();
							im.setDisplayName(MessageString.HORSEGUI_CHANGENAMENAME.toString());
							item.setItemMeta(im);
							break;
						case ITEM_USAGE:
							item = new ItemStack(Material.STICK);
							im = item.getItemMeta();
							im.setDisplayName(MessageString.HORSEGUI_CHANGENAMENAME.toString());
							item.setItemMeta(im);
							break;
						default:
							break;
					}
					if (pane != null && item != null) {
						for (int j = 0; j < 3; j++) setSlot(((31 + i) + (9 * j)), new Button(pane) {
							private final Action action = a;
							@Override
							public void onClick(InventoryClickEvent e) {
								edited.switchAccess(action);
								updateInventory(MMOHorsesAPI.getPlugin());
							}
						});
						setSlot(40 + i, new Button(item) {
							private final Action action = a;
							@Override
							public void onClick(InventoryClickEvent e) {
								edited.switchAccess(action);
								updateInventory(MMOHorsesAPI.getPlugin());
							}
						});
					}
				}
			}
			Button button;
			ItemStack head = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");
			im = head.getItemMeta();
			im.setDisplayName(MessageString.TRUSTEDITIONGUI_PREVIOUSARROWNAME.toString());
			head.setItemMeta(im);
			head = ItemStacksUtils.setSkin(head,
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==");
			button = new Button(head) {
				@Override
				public void onClick(InventoryClickEvent e) {
					if (--firstaction < 0)
						firstaction = actions.size() - 1;
					updateInventory(MMOHorsesAPI.getPlugin());
				}
			};
			setSlot(28, button);
			setSlot(46, button);
			im.setDisplayName(MessageString.TRUSTEDITIONGUI_NEXTARROWNAME.toString());
			head.setItemMeta(im);
			head = ItemStacksUtils.setSkin(head,
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19");
			button = new Button(head) {
				@Override
				public void onClick(InventoryClickEvent e) {
					if (++firstaction >= actions.size())
						firstaction = 0;
					updateInventory(MMOHorsesAPI.getPlugin());
				}
			};
			setSlot(34, button);
			setSlot(52, button);
			return super.getInventory();
		}
		return null;
	}
}