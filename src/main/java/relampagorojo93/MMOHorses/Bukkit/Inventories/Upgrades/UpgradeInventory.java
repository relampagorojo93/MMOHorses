package relampagorojo93.MMOHorses.Bukkit.Inventories.Upgrades;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Button;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Item;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Bukkit.Inventories.BaseInventory;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Abstracts.ChestInventoryWithUser;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Horses.HorseInventory;
import relampagorojo93.MMOHorses.Modules.CraftPckg.Objects.CraftableItem;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.UserAccess;

public class UpgradeInventory extends ChestInventoryWithUser {
	
	private ClaimedData data;
	
	public UpgradeInventory(Player player, UserAccess user, ClaimedData data) {
		super(player, user);
		this.data = data;
		setName(MessageString.UPGRADEGUI_TITLE.toString());
		setSize(27);
		setBackground(BaseInventory.getBase());
	}

	public Inventory getInventory() {
		ItemStack empty = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 14,
				"RED_STAINED_GLASS_PANE");
		ItemStack item = empty.clone();
		if (data.getUpgrades().getJumpItem() != null) {
			item = data.getUpgrades().getJumpItem().getResult();
			setSlot(10, new Button(item) {
				@Override
				public void onClick(InventoryClickEvent e) {
					boolean result = data.getUpgrades().removeItem(data.getUpgrades().getJumpItem(), false);
					if (result) {
						getPlayer().getOpenInventory().setCursor(getItemStack());
						updateInventory(MMOHorsesAPI.getPlugin());
					}
				}
			});
		} else {
			ItemMeta itemMeta = item.getItemMeta();
			itemMeta.setDisplayName(MessageString.UPGRADEGUI_NOJUMPUPGRADENAME.toString());
			itemMeta.setLore(MessageList.UPGRADEGUI_NOJUMPUPGRADELORE.toList());
			item.setItemMeta(itemMeta);
			setSlot(10, new Item(item));
		}
		item = empty.clone();
		if (data.getUpgrades().getSpeedItem() != null) {
			item = data.getUpgrades().getSpeedItem().getResult();
			setSlot(11, new Button(item) {
				@Override
				public void onClick(InventoryClickEvent e) {
					boolean result = data.getUpgrades().removeItem(data.getUpgrades().getSpeedItem(), false);
					if (result) {
						getPlayer().getOpenInventory().setCursor(getItemStack());
						updateInventory(MMOHorsesAPI.getPlugin());
					}
				}
			});
		} else {
			ItemMeta itemMeta = item.getItemMeta();
			itemMeta.setDisplayName(MessageString.UPGRADEGUI_NOSPEEDUPGRADENAME.toString());
			itemMeta.setLore(MessageList.UPGRADEGUI_NOSPEEDUPGRADELORE.toList());
			item.setItemMeta(itemMeta);
			setSlot(11, new Item(item));
		}
		item = empty.clone();
		if (data.getUpgrades().getHealthItem() != null) {
			item = data.getUpgrades().getHealthItem().getResult();
			setSlot(12, new Button(item) {
				@Override
				public void onClick(InventoryClickEvent e) {
					boolean result = data.getUpgrades().removeItem(data.getUpgrades().getHealthItem(), false);
					if (result) {
						getPlayer().getOpenInventory().setCursor(getItemStack());
						updateInventory(MMOHorsesAPI.getPlugin());
					}
				}
			});
		} else {
			ItemMeta itemMeta = item.getItemMeta();
			itemMeta.setDisplayName(MessageString.UPGRADEGUI_NOHEALTHUPGRADENAME.toString());
			itemMeta.setLore(MessageList.UPGRADEGUI_NOHEALTHUPGRADELORE.toList());
			item.setItemMeta(itemMeta);
			setSlot(12, new Item(item));
		}
		item = empty.clone();
		if (data.getUpgrades().getSaddleItem() != null) {
			item = data.getUpgrades().getSaddleItem().getResult();
			setSlot(14, new Button(item) {
				@Override
				public void onClick(InventoryClickEvent e) {
					boolean result = data.getUpgrades().removeItem(data.getUpgrades().getSaddleItem(), false);
					if (result) {
						getPlayer().getOpenInventory().setCursor(getItemStack());
						updateInventory(MMOHorsesAPI.getPlugin());
					}
				}
			});
		} else {
			ItemMeta itemMeta = item.getItemMeta();
			itemMeta.setDisplayName(MessageString.UPGRADEGUI_NOSADDLEUPGRADENAME.toString());
			itemMeta.setLore(MessageList.UPGRADEGUI_NOSADDLEUPGRADELORE.toList());
			item.setItemMeta(itemMeta);
			setSlot(14, new Item(item));
		}
		item = empty.clone();
		if (data.getUpgrades().getArmorItem() != null) {
			item = data.getUpgrades().getArmorItem().getResult();
			setSlot(15, new Button(item) {
				@Override
				public void onClick(InventoryClickEvent e) {
					boolean result = data.getUpgrades().removeItem(data.getUpgrades().getArmorItem(), false);
					if (result) {
						getPlayer().getOpenInventory().setCursor(getItemStack()); updateInventory(MMOHorsesAPI.getPlugin());
					}
				}
			});
		} else {
			ItemMeta itemMeta = item.getItemMeta();
			itemMeta.setDisplayName(MessageString.UPGRADEGUI_NOARMORUPGRADENAME.toString());
			itemMeta.setLore(MessageList.UPGRADEGUI_NOARMORUPGRADELORE.toList());
			item.setItemMeta(itemMeta);
			setSlot(15, new Item(item));
		}
		item = empty.clone();
		if (data.getUpgrades().getChestItem() != null) {
			item = data.getUpgrades().getChestItem().getResult();
			setSlot(16, new Button(item) {
				@Override
				public void onClick(InventoryClickEvent e) {
					boolean result = data.getUpgrades().removeItem(data.getUpgrades().getChestItem(), false);
					if (result) {
						getPlayer().getOpenInventory().setCursor(getItemStack());
						updateInventory(MMOHorsesAPI.getPlugin());
					}
				}
			});
		} else {
			ItemMeta itemMeta = item.getItemMeta();
			itemMeta.setDisplayName(MessageString.UPGRADEGUI_NOCHESTUPGRADENAME.toString());
			itemMeta.setLore(MessageList.UPGRADEGUI_NOCHESTUPGRADELORE.toList());
			item.setItemMeta(itemMeta);
			setSlot(16, new Item(item));
		}
		ItemStack opane = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 1,
				"ORANGE_STAINED_GLASS_PANE");
		ItemMeta im = opane.getItemMeta();
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
	
	@Override
	public void onClick(InventoryClickEvent e) {
		if (e.getInventory() == e.getClickedInventory() || e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY)
			e.setCancelled(true);
		ItemStack c = getPlayer().getOpenInventory().getCursor();
		if (e.getInventory() == e.getClickedInventory()) {
			if (c != null && c.getType() != Material.AIR) {
				CraftableItem ci = MMOHorsesAPI.getCraftModule().getCraftableItem(c);
				if (ci != null) {
					if (data.getStats().getHorseLevel() >= ci.getMinLevel() && data.getStats().getHorseLevel() <= ci.getMaxLevel()) {
						boolean result = data.getUpgrades().applyItem(ci, false);
						if (result) {
							if (c.getAmount() != 1) {
								c.setAmount(c.getAmount() - 1);
							} else {
								getPlayer().getOpenInventory().setCursor(null);
							}
							updateInventory(MMOHorsesAPI.getPlugin());
						}
					}
					else if (data.getStats().getHorseLevel() < ci.getMinLevel()) MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.ITEMMINLEVELNOTREACHED)).sendMessage(getPlayer());
					else if (data.getStats().getHorseLevel() > ci.getMaxLevel()) MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.ITEMMAXLEVELEXCEEDED)).sendMessage(getPlayer());
				}
			} else super.onClick(e);
		}
	}
}
