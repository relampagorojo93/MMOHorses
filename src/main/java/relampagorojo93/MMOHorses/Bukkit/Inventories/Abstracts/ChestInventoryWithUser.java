package relampagorojo93.MMOHorses.Bukkit.Inventories.Abstracts;

import org.bukkit.entity.Player;

import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.UserAccess;

public class ChestInventoryWithUser extends PluginChestInventory {
	private UserAccess user;

	public ChestInventoryWithUser(Player pl, UserAccess user) {
		super(pl);
		this.user = user;
	}

	public UserAccess getUserAccess() {
		return user;
	}
}
