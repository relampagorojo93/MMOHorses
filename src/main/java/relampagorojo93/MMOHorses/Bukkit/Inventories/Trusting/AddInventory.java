package relampagorojo93.MMOHorses.Bukkit.Inventories.Trusting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
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
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.UserAccess;

public class AddInventory extends ChestInventoryWithUser {
	
	private ClaimedData data;
	private int page = 1;
	private List<Player> players;
	
	public AddInventory(Player player, UserAccess user, ClaimedData data) {
		super(player, user);
		this.data = data;
		setName(MessageString.ADDINGGUI_TITLE.toString());
		setSize(36);
		setBackground(BaseInventory.getBase());
	}
	
	@Override
	public Inventory getInventory() {
		if (players == null || players.isEmpty()) {
			players = new ArrayList<>();
			for (Player p : Bukkit.getOnlinePlayers())
				if (data.getData().getOwner().compareTo(p.getUniqueId()) != 0
						&& data.getData().getTrusted(p.getUniqueId()) == null)
					players.add(p);
		}
		int m = (int) ((((double) players.size()) / 14D) + 0.99D);
		if (m != 0 && page > m) page = m;
		HashMap<Integer, Player> slots = new HashMap<>();
		ItemStack l = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");
		SkullMeta lm = (SkullMeta) l.getItemMeta();
		for (int i = 0; i < 14; i++) {
			int slot = 10 + i + ((i / 7) * 2);
			int hl = (14 * (page - 1)) + i;
			if (hl >= 0 && hl < players.size()) {
				Player player = players.get(hl);
				lm.setDisplayName(player.getDisplayName());
				lm.setLore(MessageList.ADDINGGUI_PLAYERLORE.toList());
				l.setItemMeta(lm);
				slots.put(slot, player);
				this.setSlot(slot, new Button(l) {
					private final Player pl = player;
					@Override
					public void onClick(InventoryClickEvent e) {
						if (pl != null) {
							if (MMOHorsesAPI.getHorseModule().getHorseObject(data.getData().getId()) != null) {
								players.remove(pl);
								data.getData()
										.registerTrusted(new UserAccess(data.getData().getId(), pl.getUniqueId()));
								updateInventory(MMOHorsesAPI.getPlugin());
							} else
								MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.HORSEERROR)).sendMessage(getPlayer());
						}
					}
				});
				new BukkitRunnable() {
					private final int islot = slot;
					private final ItemStack i = getSlot(islot).getItemStack();
					private final OfflinePlayer pl = player;
					@Override
					public void run() {
						SkullMeta im = (SkullMeta) i.getItemMeta();
						try {
							SkullMeta.class.getMethod("setOwningPlayer", OfflinePlayer.class).invoke(im, pl);
						} catch (Exception e) {
							e.printStackTrace();
							try {
								SkullMeta.class.getMethod("setOwner", String.class).invoke(im, pl.getName());
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
						i.setItemMeta(im);
					}
				}.runTaskAsynchronously(MMOHorsesAPI.getPlugin());
			} else this.setSlot(slot, new Item(null));
		}
		if (page > 1) {
			lm.setDisplayName(MessageString.COMMONGUI_LEFTARROWNAME.toString());
			lm.setLore(null);
			l.setItemMeta(lm);
			l = ItemStacksUtils.setSkin(l,
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==");
			this.setSlot(this.getSize() - 6, new Button(l) {
				@Override
				public void onClick(InventoryClickEvent e) {page-=1;updateInventory(MMOHorsesAPI.getPlugin());}
			});
		}
		else removeSlot(getSize() - 6);
		if (page < m) {
			lm.setDisplayName(MessageString.COMMONGUI_RIGHTARROWNAME.toString());
			lm.setLore(null);
			l.setItemMeta(lm);
			l = ItemStacksUtils.setSkin(l,
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19");
			this.setSlot(this.getSize() - 4, new Button(l) {
				@Override
				public void onClick(InventoryClickEvent e) {page+=1;updateInventory(MMOHorsesAPI.getPlugin());}
			});
		}
		else removeSlot(getSize() - 4);
		ItemStack opane = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 1,
				"ORANGE_STAINED_GLASS_PANE");
		ItemMeta im = opane.getItemMeta();
		im.setDisplayName(MessageString.COMMONGUI_RETURNNAME.toString());
		opane.setItemMeta(im);
		this.setSlot(this.getSize() - 5, new Button(opane) {
			@Override
			public void onClick(InventoryClickEvent e) {
				new TrustingInventory(getPlayer(), getUserAccess(), data)
				.openInventory(MMOHorsesAPI.getPlugin());				
			}
		});
		return super.getInventory();
	}
}
