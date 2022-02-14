package relampagorojo93.MMOHorses.Bukkit.Inventories.Abstracts;

import org.bukkit.entity.Player;

public abstract class ChestInventoryWithAdmin extends PluginChestInventory {
	private boolean hasadminmode;

	public ChestInventoryWithAdmin(Player pl, boolean hasadminmode) {
		super(pl);
		this.hasadminmode = hasadminmode;
	}

	public boolean hasAdminMode() {
		return hasadminmode;
	}
}
