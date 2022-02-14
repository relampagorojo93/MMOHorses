package relampagorojo93.MMOHorses.Bukkit.Inventories.Economy;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
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
import relampagorojo93.MMOHorses.Bukkit.Inventories.Sorters;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Sorters.Sorter;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Abstracts.PluginChestInventory;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Horses.HorseInventory;
import relampagorojo93.MMOHorses.Enums.Type;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;
import relampagorojo93.MMOHorses.Modules.UtilsPckg.PHApplierPckg.Instances.PHDataApplyRequest;

public class BuyInventory extends PluginChestInventory {

	private int sorter = 0;
	private int page = 1;

	public BuyInventory(Player player) {
		super(player);
		setName(MessageString.BUYGUI_TITLE.toString());
		setSize(54);
		setBackground(BaseInventory.getBase());
	}

	@Override
	public Inventory getInventory() {
		List<ClaimedData> h = MMOHorsesAPI.getHorseModule().getHorseObjects();
		List<ClaimedData> horses = new ArrayList<>();
		for (ClaimedData hi : h)
			if (hi != null && hi.getData().getPrice() > 0D)
				horses.add(hi);
		String value = "";
		Sorter<ClaimedData> cdsorter = Sorters.HORSE_SORTERS.get(this.sorter);
		h.sort(cdsorter.getComparator());
		switch (cdsorter.getName()) {
			case "name":
				value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTZjYjY1NzM4MWVlOTZmNWVhZGU0YzczMGVlMWExYjE0NTUyNzY1ZjFkZWUyYmNmZGFlMTc1NzkyYjAxNmZiIn19fQ==";
				break;
			case "owner":
				value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjVlNTIyMzMxN2E4OTBhMzAzNTFmNmY3OGQwYWJmOGRkNzZjYmQwOGRmNmY5MTg4ODM5MzQ1NjRkMjhlNThlIn19fQ==";
				break;
			case "type":
				value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjBhMmRiMmYxZWI5M2U1OTc4ZDJkYzkxYTc0ZGY0M2Q3Yjc1ZDllYzBlNjk0ZmQ3ZjJhNjUyZmJkMTUifX19";
				break;
			case "summoned":
				value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWM2ZDVhYmJmNjhjY2IyMzg2YmYxNmFmMjVhYzM4ZDhiNzdiYjBlMDQzMTUyNDYxYmQ5N2YzZjYzMGRiYjhiYyJ9fX0=";
				break;
			default:
				value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTY5NTkwNThjMGMwNWE0MTdmZDc1N2NiODViNDQxNWQ5NjZmMjczM2QyZTdjYTU0ZjdiYTg2OGUzMjQ5MDllMiJ9fX0=";
				break;
		}
		int m = (int) ((((double) horses.size()) / 28D) + 0.99D);
		ItemStack l = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");
		ItemMeta lm = l.getItemMeta();
		for (int j = 0; j < 28; j++) {
			int slot = 10 + j + j / 7 * 2;
			int hl = 28 * (page - 1) + j;
			if (hl >= 0 && hl < horses.size()) {
				ClaimedData hi = horses.get(hl);
				lm.setDisplayName(new PHDataApplyRequest(hi, getPlayer(), MessageString.BUYGUI_HORSEITEMNAME.toString()).generate().getStrings().get(0));
				l.setItemMeta(lm);
				if (hi.getData().getType() == Type.HORSE)
					l = ItemStacksUtils.setSkin(l, hi.getData().getColour().getHorseSkin());
				else if (hi.getData().getType() == Type.LLAMA || hi.getData().getType() == Type.TRADERLLAMA)
					l = ItemStacksUtils.setSkin(l, hi.getData().getColour().getLlamaSkin());
				else
					l = ItemStacksUtils.setSkin(l, hi.getData().getType().getSkin());
				setSlot(slot, new Button(l) {
					private final ClaimedData data = hi;

					@Override
					public void onClick(InventoryClickEvent e) {
						ClaimedData cdata = MMOHorsesAPI.getHorseModule().getHorseObject(data.getData().getId());
						if (cdata != null && cdata.getData().getPrice() != 0)
							new HorseInventory(getPlayer(), cdata).setPreviousHolder(getHolder())
									.openInventory(MMOHorsesAPI.getPlugin());
						else
							MessagesUtils.getMessageBuilder()
									.createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.NOTONSALE))
									.sendMessage(getPlayer());
					}
				});
			} else
				setSlot(slot, new Item(null));
		}
		l = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");
		lm = l.getItemMeta();
		if (page > 1) {
			lm.setDisplayName(MessageString.COMMONGUI_LEFTARROWNAME.toString());
			l.setItemMeta(lm);
			l = ItemStacksUtils.setSkin(l,
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==");
			setSlot(48, new Button(l) {
				@Override
				public void onClick(InventoryClickEvent e) {
					page -= 1;
					updateInventory(MMOHorsesAPI.getPlugin());
				}
			});
		} else
			removeSlot(48);
		if (page < m) {
			lm.setDisplayName(MessageString.COMMONGUI_RIGHTARROWNAME.toString());
			l.setItemMeta(lm);
			l = ItemStacksUtils.setSkin(l,
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19");
			setSlot(50, new Button(l) {
				@Override
				public void onClick(InventoryClickEvent e) {
					page += 1;
					updateInventory(MMOHorsesAPI.getPlugin());
				}
			});
		} else
			removeSlot(50);
		lm.setDisplayName(MessageString.COMMONGUI_SORTINGNAME.toString().replace("%sorter%", cdsorter.getCapitalizedName()));
		lm.setLore(null);
		l.setItemMeta(lm);
		l = ItemStacksUtils.setSkin(l, value);
		setSlot(49, new Button(l) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (++sorter >= Sorters.HORSE_SORTERS.size())
					sorter = 0;
				updateInventory(MMOHorsesAPI.getPlugin());
			}
		});
		return super.getInventory();
	}

	public static Inventory getEzInventory(Player player) {
		return new BuyInventory(player).getInventory();
	}
}
