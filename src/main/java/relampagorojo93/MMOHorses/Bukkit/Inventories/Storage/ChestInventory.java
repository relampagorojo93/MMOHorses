package relampagorojo93.MMOHorses.Bukkit.Inventories.Storage;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Button;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Item;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Modifiable;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Bukkit.Inventories.BaseInventory;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Abstracts.ChestInventoryWithUser;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Horses.HorseInventory;
import relampagorojo93.MMOHorses.Enums.Action;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingInt;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.UserAccess;

public class ChestInventory extends ChestInventoryWithUser {
	
	private ClaimedData data;
	private ItemStack[] content;
	private int page = 1;
	private int lastpage = 1;
	
	public ChestInventory(Player player, UserAccess user, ClaimedData data) {
		super(player, user);
		this.data = data;
		this.content = data.getInventory().getContents();
		setName(MessageString.CHESTGUI_TITLE.toString());
		setSize(45);
		setBackground(BaseInventory.getBase());
		setAllowStorageExchange(true);
	}
	
	@Override
	public Inventory getInventory() {
		int items = (data.getStats().getInvLevel() - 1) * SettingInt.INVENTORY_GIVEN_PER_LEVEL.toInt();
		if (data.getUpgrades().getChestItem() != null)
			items += (int) data.getUpgrades().getChestItem().getValue() * 7;
		int itemsinpage = items - ((page - 1) * 28);
		if (itemsinpage > 28) itemsinpage = 28;
		int m = (int) (items / 28.0D + 0.99D);
		ItemStack locked = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 3,
				"LIGHT_BLUE_STAINED_GLASS_PANE");
		ItemMeta im = locked.getItemMeta();
		im.setDisplayName(ChatColor.BLACK.toString());
		locked.setItemMeta(im);
		for (int i = 0; i < 28; i++) {
			int slot = 1 + i + ((i / 7) * 2);
			if (i < itemsinpage) {
				int item = ((page - 1) * 28) + i;
				if (content != null && item < content.length && content[item] != null && content[item].getType() != Material.AIR) {
					setSlot(slot, new Modifiable(content[item]) {
						@Override
						public void onModify(InventoryClickEvent e) {}
					});
				}
				else setSlot(slot, new Modifiable(null) {
					@Override
					public void onModify(InventoryClickEvent e) {}
				});
			}
			else setSlot(slot, new Item(locked));
		}
		ItemStack l = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");
		ItemMeta lm = l.getItemMeta();
		if (page > 1) {
			lm.setDisplayName(MessageString.COMMONGUI_LEFTARROWNAME.toString());
			l.setItemMeta(lm);
			l = ItemStacksUtils.setSkin(l,
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==");
			setSlot(getSize() - 6, new Button(l) {
				@Override
				public void onClick(InventoryClickEvent e) {
					page -= 1; savePageContent(e.getInventory()); updateInventory(MMOHorsesAPI.getPlugin());
				}
			});
		}
		else removeSlot(getSize() - 6);
		if (page < m) {
			lm.setDisplayName(MessageString.COMMONGUI_RIGHTARROWNAME.toString());
			l.setItemMeta(lm);
			l = ItemStacksUtils.setSkin(l,
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19");
			setSlot(getSize() - 4, new Button(l) {
				@Override
				public void onClick(InventoryClickEvent e) {
					page += 1; savePageContent(e.getInventory()); updateInventory(MMOHorsesAPI.getPlugin());
				}
			});
		}
		else removeSlot(getSize() - 4);
		ItemStack opane = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 1,
				"ORANGE_STAINED_GLASS_PANE");
		im = opane.getItemMeta();
		im.setDisplayName(MessageString.COMMONGUI_RETURNNAME.toString());
		opane.setItemMeta(im);
		setSlot(getSize() - 5, new Button(opane) {
			@Override
			public void onClick(InventoryClickEvent e) {
				new HorseInventory(getPlayer(), getUserAccess(), data).openInventory(MMOHorsesAPI.getPlugin());
			}
		});
		return super.getInventory();
	}
	
	private void savePageContent(Inventory inv) {
		int items = (data.getStats().getInvLevel() - 1) * SettingInt.INVENTORY_GIVEN_PER_LEVEL.toInt();
		if (data.getUpgrades().getChestItem() != null)
			items += (int) data.getUpgrades().getChestItem().getValue() * 7;
		int itemsinpage = items - ((page - 1) * 28);
		if (itemsinpage > 28) itemsinpage = 28;
		int total = ((lastpage - 1) * 28) + itemsinpage;
		if (content.length < total) {
			ItemStack[] ncontent = new ItemStack[total];
			for (int i = 0; i < content.length; i++) ncontent[i] = content[i];
			content = ncontent;
		}
		for (int i = 0; i < itemsinpage; i++) {
			ItemStack it = inv.getItem(1 + i + ((i / 7) * 2));
			content[((lastpage - 1) * 28) + i] = it != null && it.getType() != Material.AIR ? it.clone() : null;
		}
		lastpage = page;
	}
	
	@Override
	public void onClick(InventoryClickEvent e) {
		if (!getUserAccess().hasAccess(Action.OPEN_VIRTUALCHEST)) {
			new HorseInventory(getPlayer(), data).openInventory(MMOHorsesAPI.getPlugin());
			e.setCancelled(true);
			MessagesUtils.getMessageBuilder().createMessage(MessageString.HORSEERROR.toString()).sendMessage(getPlayer());
			return;
		}
		super.onClick(e);
	}

	@Override
	public void onClose(InventoryCloseEvent e) {
		savePageContent(e.getInventory());
		this.data.getInventory().setContents(content);
	}
}
