package relampagorojo93.MMOHorses.Bukkit.Inventories.Items;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Button;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Item;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Bukkit.Inventories.BaseInventory;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Sorters;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Sorters.Sorter;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Abstracts.ChestInventoryWithAdmin;
import relampagorojo93.MMOHorses.Modules.CraftPckg.Objects.CraftableItem;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;

public class ListInventory extends ChestInventoryWithAdmin {

	private int sorter = 0;
	private int page = 1;

	public ListInventory(Player player, boolean hasadminmode) {
		super(player, hasadminmode);
		setName(MessageString.ITEMSLISTGUI_TITLE.toString());
		setSize(45);
		setBackground(BaseInventory.getBase());
	}

	public Inventory getInventory() {
		List<CraftableItem> items = new ArrayList<>(MMOHorsesAPI.getCraftModule().getCraftableItems());
		String value = "";
		Sorter<CraftableItem> cisorter = Sorters.ITEM_SORTERS.get(this.sorter);
		items.sort(cisorter.getComparator());
		switch (cisorter.getName()) {
			case "name":
				value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTZjYjY1NzM4MWVlOTZmNWVhZGU0YzczMGVlMWExYjE0NTUyNzY1ZjFkZWUyYmNmZGFlMTc1NzkyYjAxNmZiIn19fQ==";
				break;
			case "type":
				value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODEyMmE1MDNkN2E2ZjU3ODAyYjAzYWY3NjI0MTk0YTRjNGY1MDc3YTk5YWUyMWRkMjc2Y2U3ZGI4OGJjMzhhZSJ9fX0=";
				break;
			default:
				value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTY5NTkwNThjMGMwNWE0MTdmZDc1N2NiODViNDQxNWQ5NjZmMjczM2QyZTdjYTU0ZjdiYTg2OGUzMjQ5MDllMiJ9fX0=";
				break;
		}
		int m = (int) (items.size() / 21.0D + 0.99D);
		for (int j = 0; j < 21; j++) {
			int slot = 10 + j + j / 7 * 2;
			int hl = 21 * (page - 1) + j;
			if (hl >= 0 && hl < items.size()) {
				CraftableItem item = items.get(hl);
				ItemStack it = item.getResult();
				ItemMeta im = it.getItemMeta();
				List<String> lore = (im != null && im.hasLore()) ? im.getLore() : new ArrayList<>();
				if (hasAdminMode()) {
					if (!MessageList.ITEMSLISTGUI_INFOITEMLORE.toList().isEmpty()) {
						lore.add(" ");
						for (String line : MessageList.ITEMSLISTGUI_INFOITEMLORE.toList())
							lore.add(line.replaceAll("%item_type%", item.getType().toString())
									.replaceAll("%item_value%", String.valueOf(item.getValue()))
									.replaceAll("%item_min_level%", String.valueOf(item.getMinLevel()))
									.replaceAll("%item_max_level%", String.valueOf(item.getMaxLevel()))
									.replaceAll("%item_name%", item.getName()));
					}
					if (!MessageList.ITEMSLISTGUI_ADMINITEMLORE.toList().isEmpty()) {
						lore.add(" ");
						lore.addAll(MessageList.ITEMSLISTGUI_ADMINITEMLORE.toList());
					}
				} else
					lore.addAll(MessageList.ITEMSLISTGUI_ITEMLORE.toList());
				im.setLore(lore);
				it.setItemMeta(im);
				setSlot(slot, new Button(it) {
					private CraftableItem craftableitem = item;

					@Override
					public void onClick(InventoryClickEvent e) {
						if (hasAdminMode()) {
							if (e.isLeftClick()) {
								if (e.isShiftClick()) {
									e.getWhoClicked().getInventory()
											.addItem(new ItemStack[] { craftableitem.getResult() });
								} else {
									new RecipeInventory(getPlayer(), hasAdminMode(), craftableitem)
											.openInventory(MMOHorsesAPI.getPlugin());
								}
							} else if (e.isRightClick()) {
								MMOHorsesAPI.getCraftModule().removeItem(craftableitem);
								updateInventory(MMOHorsesAPI.getPlugin());
							} else if (e.getAction() == InventoryAction.DROP_ONE_SLOT
									|| e.getAction() == InventoryAction.DROP_ALL_SLOT) {
								new SettingsInventory(getPlayer(), hasAdminMode(), craftableitem)
										.openInventory(MMOHorsesAPI.getPlugin());
							}
						} else
							new RecipeInventory(getPlayer(), hasAdminMode(), craftableitem)
									.openInventory(MMOHorsesAPI.getPlugin());
					}
				});
			} else
				setSlot(slot, new Item(null));
		}
		ItemStack l = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");
		ItemMeta lm = l.getItemMeta();
		if (page > 1) {
			lm.setDisplayName(MessageString.COMMONGUI_LEFTARROWNAME.toString());
			l.setItemMeta(lm);
			l = ItemStacksUtils.setSkin(l,
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==");
			setSlot(39, new Button(l) {
				@Override
				public void onClick(InventoryClickEvent e) {
					page -= 1;
					updateInventory(MMOHorsesAPI.getPlugin());
				}
			});
		} else
			removeSlot(39);
		if (page < m) {
			lm.setDisplayName(MessageString.COMMONGUI_RIGHTARROWNAME.toString());
			l.setItemMeta(lm);
			l = ItemStacksUtils.setSkin(l,
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19");
			setSlot(41, new Button(l) {
				@Override
				public void onClick(InventoryClickEvent e) {
					page += 1;
					updateInventory(MMOHorsesAPI.getPlugin());
				}
			});
		} else
			removeSlot(41);
		lm.setDisplayName(MessageString.COMMONGUI_SORTINGNAME.toString().replace("%sorter%", cisorter.getCapitalizedName()));
		lm.setLore(null);
		l.setItemMeta(lm);
		l = ItemStacksUtils.setSkin(l, value);
		setSlot(40, new Button(l) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (++sorter >= Sorters.ITEM_SORTERS.size())
					sorter = 0;
				updateInventory(MMOHorsesAPI.getPlugin());
			}
		});
		return super.getInventory();
	}
}
