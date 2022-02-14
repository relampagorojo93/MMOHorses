package relampagorojo93.MMOHorses.Bukkit.Inventories.Items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Button;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Item;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Modifiable;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Bukkit.Inventories.BaseInventory;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Abstracts.ChestInventoryWithAdmin;
import relampagorojo93.MMOHorses.Modules.CraftPckg.Objects.CraftableItem;

public class RecipeInventory extends ChestInventoryWithAdmin {
	
	private CraftableItem item;
	
	public RecipeInventory(Player pl, boolean hasadminmode, CraftableItem item) {
		super(pl, hasadminmode);
		this.item = item;
		setName(String.valueOf(item.getName()) + " recipe");
		setSize(45);
		setBackground(BaseInventory.getBase());
	}

	@Override
	public Inventory getInventory() {
		for (int i = 0; i < 9; i++) {
			ItemStack it = null;
			if (item.getCompleteRecipe() != null) it = item.getCompleteRecipe()[i];
			setSlot(11 + i + i / 3 * 6, !hasAdminMode() ? new Item(it) : new Modifiable(it) {
				@Override
				public void onModify(InventoryClickEvent e) {}
			});
		}
		setSlot(24, !hasAdminMode() ? new Item(item.getResult()) : new Modifiable(item.getResult()) {
			@Override
			public void onModify(InventoryClickEvent e) {}
		});
		ItemStack pane = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 14,
				"RED_STAINED_GLASS_PANE");
		ItemMeta im = pane.getItemMeta();
		im.setDisplayName(MessagesUtils.color("&cCancel"));
		pane.setItemMeta(im);
		setSlot(36, new Button(pane) {
			@Override
			public void onClick(InventoryClickEvent e) {
				new ListInventory(getPlayer(), hasAdminMode()).openInventory(MMOHorsesAPI.getPlugin());
			}
		});
		pane = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 13, "GREEN_STAINED_GLASS_PANE");
		im = pane.getItemMeta();
		im.setDisplayName(MessagesUtils.color("&aConfirm"));
		pane.setItemMeta(im);
		setSlot(44, new Button(pane) {
			@Override
			public void onClick(InventoryClickEvent e) {
				ItemStack result = e.getInventory().getItem(24);
				if (result != null && result.getType() != Material.AIR) {
					ItemStack[] recipe = new ItemStack[9];
					for (int j = 0; j < 9;) {
						recipe[j] = e.getInventory().getItem(11 + j + j / 3 * 6);
						j++;
					}
					item.unloadRecipe();
					item.setCompleteRecipe(recipe);
					item.setResult(result);
					item.loadRecipe();
					MMOHorsesAPI.getCraftModule().updateItem(item);
					new ListInventory(getPlayer(), hasAdminMode()).openInventory(MMOHorsesAPI.getPlugin());
				} else MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix("You must put a result for the recipe.")).sendMessage(getPlayer());
			}
		});
		return super.getInventory();
	}
}