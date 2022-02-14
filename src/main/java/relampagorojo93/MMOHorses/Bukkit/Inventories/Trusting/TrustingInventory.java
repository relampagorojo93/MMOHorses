package relampagorojo93.MMOHorses.Bukkit.Inventories.Trusting;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Button;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Item;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Bukkit.Inventories.BaseInventory;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Abstracts.ChestInventoryWithUser;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Horses.HorseInventory;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.UserAccess;

public class TrustingInventory extends ChestInventoryWithUser {
	
	private ClaimedData data;
	private int page = 1;
	
	public TrustingInventory(Player player, UserAccess user, ClaimedData data) {
		super(player, user);
		this.data = data;
		setName(MessageString.TRUSTINGGUI_TITLE.toString());
		setSize(36);
		setBackground(BaseInventory.getBase());
	}
	
	@Override
	public Inventory getInventory() {
		List<UserAccess> ul = new ArrayList<>(data.getData().getTrusteds());
		int m = (int) ((((double) ul.size()) / 14D) + 0.99D);
		if (page > m) page = m;
		ItemStack l = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");
		SkullMeta lm = (SkullMeta) l.getItemMeta();
		for (int i = 0; i < 14; i++) {
			int slot = 10 + i + ((i / 7) * 2);
			int hl = (14 * (page - 1)) + i;
			if (hl >= 0 && hl < ul.size()) {
				UserAccess user = ul.get(hl);
				OfflinePlayer op = Bukkit.getOfflinePlayer(user.getUniqueID());
				lm.setDisplayName(op != null && op.getName() != null ? op.getName() : "???");
				lm.setLore(MessageList.TRUSTINGGUI_PLAYERLORE.toList());
				l.setItemMeta(lm);
				setSlot(slot, new Button(l) {
					private final UserAccess iuser = user;
					@Override
					public void onClick(InventoryClickEvent e) {
						if (MMOHorsesAPI.getHorseModule().getHorseObject(data.getData().getId()) != null) {
							if (e.getAction() == InventoryAction.DROP_ONE_SLOT
									|| e.getAction() == InventoryAction.DROP_ALL_SLOT) {
								new EditInventory(getPlayer(), getUserAccess(), iuser).openInventory(MMOHorsesAPI.getPlugin());
							} else {
								data.getData().unregisterTrusted(user);
								updateInventory(MMOHorsesAPI.getPlugin());
							}
						} else {
							MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.HORSEERROR)).sendMessage(getPlayer());
							closeInventory(MMOHorsesAPI.getPlugin());
						}
					}
				});
				new BukkitRunnable() {
					private final int islot = slot;
					private final ItemStack i = getSlot(islot).getItemStack();
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
			} else
				setSlot(slot, new Item(null));
		}
		if (page > 1) {
			lm.setDisplayName(MessageString.COMMONGUI_LEFTARROWNAME.toString());
			lm.setLore(null);
			l.setItemMeta(lm);
			l = ItemStacksUtils.setSkin(l,
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==");
			setSlot(getSize() - 6, new Button(l) {
				@Override
				public void onClick(InventoryClickEvent e) {
					page -= 1; updateInventory(MMOHorsesAPI.getPlugin());
				}
			});
		}
		else removeSlot(getSize() - 6);
		if (page < m) {
			lm.setDisplayName(MessageString.COMMONGUI_RIGHTARROWNAME.toString());
			lm.setLore(null);
			l.setItemMeta(lm);
			l = ItemStacksUtils.setSkin(l,
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19");
			setSlot(getSize() - 4, new Button(l) {
				@Override
				public void onClick(InventoryClickEvent e) {
					page += 1; updateInventory(MMOHorsesAPI.getPlugin());
				}
			});
		}
		else removeSlot(getSize() - 4);
		lm.setDisplayName(MessageString.TRUSTINGGUI_ADDTRUSTEDNAME.toString());
		lm.setLore(null);
		l.setItemMeta(lm);
		l = ItemStacksUtils.setSkin(l,
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDM2MmFjNmQ5ZmY4YmZiYzliYWRhYzM1ZjYyN2NiZTkxN2FjMjVhZjhlNmU5ZDM4MmNlM2RkMzE3YWMwMzk4In19fQ==");
		setSlot(31, new Button(l) {
			@Override
			public void onClick(InventoryClickEvent e) {
				new AddInventory(getPlayer(), getUserAccess(), data).openInventory(MMOHorsesAPI.getPlugin());
			}
		});
		ItemStack opane = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 1,
				"ORANGE_STAINED_GLASS_PANE");
		ItemMeta im = opane.getItemMeta();
		im.setDisplayName(MessageString.COMMONGUI_RETURNNAME.toString());
		opane.setItemMeta(im);
		setSlot(getSize() - 9, new Button(opane) {
			@Override
			public void onClick(InventoryClickEvent e) {
				new HorseInventory(getPlayer(), getUserAccess(), data).openInventory(MMOHorsesAPI.getPlugin());				
			}
		});
		return super.getInventory();
	}
}