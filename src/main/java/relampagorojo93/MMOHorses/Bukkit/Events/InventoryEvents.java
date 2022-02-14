package relampagorojo93.MMOHorses.Bukkit.Events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Abstracts.PluginChestInventory;
import relampagorojo93.MMOHorses.Bukkit.Inventories.Horses.HorseInventory;
import relampagorojo93.MMOHorses.Modules.EntityPckg.Interfaces.Objects.MMOHorse;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingBoolean;

public class InventoryEvents implements Listener {
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent e) {
		if (e.getInventory().getHolder() instanceof Entity) {
			MMOHorse horse = MMOHorsesAPI.getUtils().toMMOHorse((Entity) e.getInventory().getHolder());
			if (horse != null && horse.isCustom()) {
				if (horse.getMMOHorseData().getClaimedData() != null) {
					e.setCancelled(true);
					new HorseInventory((Player) e.getPlayer(), horse.getMMOHorseData().getClaimedData()).openInventory(MMOHorsesAPI.getPlugin());
				}
				else if (SettingBoolean.DISABLEVANILLAINVENTORY.toBoolean()) e.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		if (e.getInventory().getHolder() instanceof PluginChestInventory) ((PluginChestInventory) e.getInventory().getHolder()).onClick(e);
	}
	@EventHandler
	public void onInvMoveItem(InventoryMoveItemEvent e) {
		if (e.getDestination().getHolder() instanceof PluginChestInventory) ((PluginChestInventory) e.getDestination().getHolder()).onMoveItem(e);
	}
	@EventHandler
	public void onInvDrag(InventoryDragEvent e) {
		if (e.getInventory().getHolder() instanceof PluginChestInventory) ((PluginChestInventory) e.getInventory().getHolder()).onDrag(e);
	}
	@EventHandler
	public void onInvClose(InventoryCloseEvent e) {
		if (e.getInventory() != null && e.getInventory().getHolder() instanceof PluginChestInventory) ((PluginChestInventory) e.getInventory().getHolder()).onClose(e);
	}
}