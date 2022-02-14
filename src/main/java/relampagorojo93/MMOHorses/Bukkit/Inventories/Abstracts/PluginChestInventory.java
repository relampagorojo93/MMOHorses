package relampagorojo93.MMOHorses.Bukkit.Inventories.Abstracts;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.ChestInventory;

public abstract class PluginChestInventory extends ChestInventory {
	public PluginChestInventory(Player player) {
		super(player);
	}
	public PluginChestInventory(Player player, ItemStack[] background) {
		super(player);
		setBackground(background);
	}
	public PluginChestInventory(Player player, String name, int size) {
		super(player);
		setName(name);
		setSize(size);
	}
	public PluginChestInventory(Player player, String name, int size, ItemStack[] background) {
		this(player, name, size);
		setBackground(background);
	}
}
