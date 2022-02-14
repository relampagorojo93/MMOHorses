package relampagorojo93.MMOHorses.Bukkit.Inventories.Wardrobe;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import relampagorojo93.MMOHorses.Enums.ItemType;
import relampagorojo93.MMOHorses.Enums.Type;
import relampagorojo93.MMOHorses.Modules.CraftPckg.Objects.CraftableItem;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.UserAccess;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Button;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Item;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Bukkit.Inventories.BaseInventory;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Abstracts.ChestInventoryWithUser;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Horses.HorseInventory;

public class WardrobeInventory extends ChestInventoryWithUser {
	
	private ClaimedData data;
	
	public WardrobeInventory(Player player, UserAccess user, ClaimedData data) {
		super(player, user);
		this.data = data;
		setName(MessageString.WARDROBEGUI_TITLE.toString());
		setSize(27);
		setBackground(BaseInventory.getBase());
	}
	
	@Override
	public Inventory getInventory() {
		ItemStack empty = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 14,
				"RED_STAINED_GLASS_PANE");
		ItemStack carmor = empty.clone();
		ItemStack csaddle = empty.clone();
		ItemStack caccessory = empty.clone();
		ItemMeta im;
		if (data.getCosmetics().getSaddle() != null) {
			csaddle = data.getCosmetics().getSaddle();
			setSlot(12, new Button(csaddle) {
				@Override
				public void onClick(InventoryClickEvent e) {
					e.getWhoClicked().getOpenInventory().setCursor(getItemStack()); data.getCosmetics().setSaddle(null); updateInventory(MMOHorsesAPI.getPlugin());
				}
			});
		} else {
			im = csaddle.getItemMeta();
			im.setDisplayName(MessageString.HORSEGUI_NOSADDLENAME.toString());
			im.setLore(MessageList.WARDROBEGUI_NOSADDLELORE.toList());
			csaddle.setItemMeta(im);
			setSlot(12, new Item(csaddle));
		}
		if (data.getCosmetics().getBody() != null) {
			carmor = data.getCosmetics().getBody();
			setSlot(13, new Button(carmor) {
				@Override
				public void onClick(InventoryClickEvent e) {
					e.getWhoClicked().getOpenInventory().setCursor(getItemStack()); data.getCosmetics().setBody(null); updateInventory(MMOHorsesAPI.getPlugin());
				}
			});
		} else {
			if (data.getData().getType() == Type.HORSE) {
				im = carmor.getItemMeta();
				im.setDisplayName(MessageString.HORSEGUI_NOARMORNAME.toString());
				im.setLore(MessageList.WARDROBEGUI_NOARMORLORE.toList());
				carmor.setItemMeta(im);
				setSlot(13, new Item(carmor));
			} else if (data.getData().getType() == Type.LLAMA || data.getData().getType() == Type.TRADERLLAMA) {
				im = carmor.getItemMeta();
				im.setDisplayName(MessageString.HORSEGUI_NOCARPETNAME.toString());
				im.setLore(MessageList.WARDROBEGUI_NOCARPETLORE.toList());
				carmor.setItemMeta(im);
				setSlot(13, new Item(carmor));
			}
		}
		if (data.getCosmetics().getAccessory() != null) {
			caccessory = data.getCosmetics().getAccessory();
			setSlot(14, new Button(caccessory) {
				@Override
				public void onClick(InventoryClickEvent e) {
					e.getWhoClicked().getOpenInventory().setCursor(getItemStack()); data.getCosmetics().setAccessory(null); updateInventory(MMOHorsesAPI.getPlugin());
				}
			});
		} else {
			im = caccessory.getItemMeta();
			im.setDisplayName(MessageString.HORSEGUI_NOACCESSORYNAME.toString());
			im.setLore(MessageList.WARDROBEGUI_NOACCESSORYLORE.toList());
			caccessory.setItemMeta(im);
			setSlot(14, new Item(caccessory));
		}
		ItemStack opane = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 1,
				"ORANGE_STAINED_GLASS_PANE");
		im = opane.getItemMeta();
		im.setDisplayName(MessageString.COMMONGUI_RETURNNAME.toString());
		opane.setItemMeta(im);
		setSlot(getSize() - 9, new Button(opane) {
			@Override
			public void onClick(InventoryClickEvent e) {
				new HorseInventory(getPlayer(), getUserAccess(), data)
						.openInventory(MMOHorsesAPI.getPlugin());
			}
		});
		return super.getInventory();
	}
	
	@Override
	public void onClick(InventoryClickEvent e) {
		if (e.getInventory() == e.getClickedInventory() || e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY)
			e.setCancelled(true);
		ItemStack c = getPlayer().getOpenInventory().getCursor();
		if (e.getInventory() == e.getClickedInventory()) {
			if (c != null && c.getType() != Material.AIR) {
				CraftableItem ci = MMOHorsesAPI.getCraftModule().getCraftableItem(c);
				if (SettingBoolean.ONLYREGISTEREDCOSMETICS.toBoolean() && (ci == null
						|| ci.getType() != ItemType.COSMETIC))
					return;
				if (((c.getType().name().contains("BARDING") || c.getType().name().contains("HORSE_ARMOR"))
						&& data.getData().getType() == Type.HORSE)
						|| (c.getType().name().contains("CARPET")
								&& (data.getData().getType() == Type.LLAMA
										|| data.getData().getType() == Type.TRADERLLAMA))) {
					if (data.getCosmetics().getBody() == null) {
						data.getCosmetics().setBody(c);
						getPlayer().getOpenInventory().setCursor(null);
						updateInventory(MMOHorsesAPI.getPlugin());
					}
				}
				else if (c.getType() == Material.SADDLE) {
					if (data.getCosmetics().getSaddle() == null) {
						data.getCosmetics().setSaddle(c);
						getPlayer().getOpenInventory().setCursor(null);
						updateInventory(MMOHorsesAPI.getPlugin());
					}
				}
				else if (data.getCosmetics().getAccessory() == null) {
					ItemStack f = c.clone();
					f.setAmount(1);
					data.getCosmetics().setAccessory(f);
					if (c.getAmount() != 1) {
						c.setAmount(c.getAmount() - 1);
					} else {
						getPlayer().getOpenInventory().setCursor(null);
					}
					updateInventory(MMOHorsesAPI.getPlugin());
				}
			}
			else super.onClick(e);
		}
	}
}
