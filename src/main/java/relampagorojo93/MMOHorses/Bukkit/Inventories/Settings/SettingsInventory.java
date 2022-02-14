package relampagorojo93.MMOHorses.Bukkit.Inventories.Settings;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.UserAccess;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Button;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Bukkit.Inventories.BaseInventory;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Abstracts.ChestInventoryWithUser;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Horses.HorseInventory;

public class SettingsInventory extends ChestInventoryWithUser {
	
	private ClaimedData data;
	
	public SettingsInventory(Player player, UserAccess user, ClaimedData data) {
		super(player, user);
		this.data = data;
		setName(MessageString.SETTINGSGUI_TITLE.toString());
		setSize(27);
		setBackground(BaseInventory.getBase());
	}

	@Override
	public Inventory getInventory() {
		ItemStack tag = new ItemStack(Material.NAME_TAG);
		ItemStack health = new ItemStack(Material.APPLE);
		ItemStack follow = ItemStacksUtils.getItemStack("LEASH", "LEAD");
		ItemStack link = new ItemStack(Material.TRIPWIRE_HOOK);
		ItemStack blockspeed = new ItemStack(Material.BLAZE_POWDER);
		ItemStack _public = new ItemStack(Material.BOOK);
		ItemMeta im = tag.getItemMeta();
		im.setDisplayName(MessageString.SETTINGSGUI_NAMETAGNAME.toString().replace("%status%",
				data.getSettings().isNameTagVisible() ? MessageString.HORSEINFO_ON.toString()
						: MessageString.HORSEINFO_OFF.toString()));
		im.setLore(MessageList.SETTINGSGUI_NAMETAGLORE.toList());
		tag.setItemMeta(im);
		setSlot(10, new Button(tag) {
			@Override
			public void onClick(InventoryClickEvent e) {
				data.getSettings().setNameTagVisible(!data.getSettings().isNameTagVisible());
				updateInventory(MMOHorsesAPI.getPlugin());
			}
		});
		im = health.getItemMeta();
		im.setDisplayName(MessageString.SETTINGSGUI_HEALTHTAGNAME.toString().replace("%status%",
				data.getSettings().isHealthTagVisible() ? MessageString.HORSEINFO_ON.toString()
						: MessageString.HORSEINFO_OFF.toString()));
		im.setLore(MessageList.SETTINGSGUI_HEALTHTAGLORE.toList());
		health.setItemMeta(im);
		setSlot(11, new Button(health) {
			@Override
			public void onClick(InventoryClickEvent e) {
				data.getSettings().setHealthTagVisible(!data.getSettings().isHealthTagVisible());
				updateInventory(MMOHorsesAPI.getPlugin());
			}
		});
		im = follow.getItemMeta();
		im.setDisplayName(MessageString.SETTINGSGUI_FOLLOWOWNERNAME.toString().replace("%status%",
				data.getSettings().isFollowOwner() ? MessageString.HORSEINFO_ON.toString()
						: MessageString.HORSEINFO_OFF.toString()));
		im.setLore(MessageList.SETTINGSGUI_FOLLOWOWNERLORE.toList());
		follow.setItemMeta(im);
		setSlot(12, new Button(follow) {
			@Override
			public void onClick(InventoryClickEvent e) {
				data.getSettings().setFollowOwner(!data.getSettings().isFollowOwner());
				updateInventory(MMOHorsesAPI.getPlugin());
			}
		});
		im = link.getItemMeta();
		im.setDisplayName(MessageString.SETTINGSGUI_LINKNAME.toString().replace("%status%",
				data.getSettings().isLinked() ? MessageString.HORSEINFO_ON.toString()
						: MessageString.HORSEINFO_OFF.toString()));
		im.setLore(MessageList.SETTINGSGUI_LINKLORE.toList());
		link.setItemMeta(im);
		setSlot(14, new Button(link) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (data.getSettings().isLinked() && !SettingBoolean.ALLOWUNLINK.toBoolean()) {
					MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.DISABLEDUNLINK)).sendMessage(getPlayer());
					return;
				}
				if (!data.getSettings().isLinked()) {
					if (!SettingBoolean.ALLOWLINK.toBoolean()) {
						MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.DISABLEDLINK)).sendMessage(getPlayer());
						return;
					}
					if (data.getMMOHorse() == null) {
						MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.MISSINGENTITY)).sendMessage(getPlayer());
						return;
					}
				}
				data.getSettings().setLink(!data.getSettings().isLinked());
				if (Bukkit.getPlayer(data.getData().getOwner()) != null && !MMOHorsesAPI.getHorseModule().isRegistered(data))
					MMOHorsesAPI.getHorseModule().registerHorseObject(data);
				updateInventory(MMOHorsesAPI.getPlugin());
			}
		});
		im = blockspeed.getItemMeta();
		im.setDisplayName(MessageString.SETTINGSGUI_BLOCKSPEEDNAME.toString().replace("%status%",
				data.getSettings().blockSpeedOnUnmount() ? MessageString.HORSEINFO_ON.toString()
						: MessageString.HORSEINFO_OFF.toString()));
		im.setLore(MessageList.SETTINGSGUI_BLOCKSPEEDLORE.toList());
		blockspeed.setItemMeta(im);
		setSlot(15, new Button(blockspeed) {
			@Override
			public void onClick(InventoryClickEvent e) {
				data.getSettings().blockSpeedOnUnmount(!data.getSettings().blockSpeedOnUnmount());
				updateInventory(MMOHorsesAPI.getPlugin());
			}
		});
		im = _public.getItemMeta();
		im.setDisplayName(MessageString.SETTINGSGUI_PUBLIC.toString().replace("%status%",
				data.getSettings().isPublic() ? MessageString.HORSEINFO_ON.toString()
						: MessageString.HORSEINFO_OFF.toString()));
		im.setLore(MessageList.SETTINGSGUI_PUBLICLORE.toList());
		_public.setItemMeta(im);
		setSlot(16, new Button(_public) {
			@Override
			public void onClick(InventoryClickEvent e) {
				data.getSettings().setPublic(!data.getSettings().isPublic());
				updateInventory(MMOHorsesAPI.getPlugin());
			}
		});
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
}
