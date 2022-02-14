package relampagorojo93.MMOHorses.Bukkit.Inventories;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;

public class BaseInventory {
	
	private static ItemStack[] BASE;

	public static ItemStack[] getBase() {
		return BASE;
	}
	
	public static ItemStack getReturnItem() {
		ItemStack item = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 1,
				"ORANGE_STAINED_GLASS_PANE");
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(MessageString.COMMONGUI_RETURNNAME.toString());
		item.setItemMeta(im);
		return item;
	}
	public static ItemStack getLeftArrow() {
		ItemStack item = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(MessageString.COMMONGUI_LEFTARROWNAME.toString());
		im.setLore(null);
		item.setItemMeta(im);
		return ItemStacksUtils.setSkin(item,
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==");
	}
	public static ItemStack getRightArrow() {
		ItemStack item = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(MessageString.COMMONGUI_RIGHTARROWNAME.toString());
		im.setLore(null);
		item.setItemMeta(im);
		return ItemStacksUtils.setSkin(item,
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19");
	}
	
	static {
		BASE = new ItemStack[54];
		ItemStack gpane = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 7,
				"GRAY_STAINED_GLASS_PANE");
		ItemMeta im = gpane.getItemMeta();
		im.setDisplayName(ChatColor.BLACK.toString());
		gpane.setItemMeta(im);
		ItemStack rail = ItemStacksUtils.getItemStack("RAILS", "RAIL");
		im = rail.getItemMeta();
		im.setDisplayName(ChatColor.BLACK.toString());
		rail.setItemMeta(im);
		for (int i = 0; i < BASE.length; i++) {
			if (i % 9 == 0 || i % 9 % 8 == 0)
				BASE[i] = rail;
			else
				BASE[i] = gpane;
		}
	}
}
