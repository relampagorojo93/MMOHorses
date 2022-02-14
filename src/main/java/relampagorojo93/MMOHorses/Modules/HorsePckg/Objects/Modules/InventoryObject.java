package relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.Modules;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.inventory.ItemStack;

import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.TasksUtils;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Modules.HorsePckg.Objects.ClaimedData;

public class InventoryObject {
	private ClaimedData data;
	private ItemStack[] inventory;

	public InventoryObject(ClaimedData data, ResultSet set) throws SQLException {
		this.data = data;
		this.inventory = ItemStacksUtils.itemsParse(set.getBytes("inventory"));
	}

	public ItemStack[] getContents() {
		return inventory == null ? new ItemStack[0] : inventory;
	}

	public void setContents(ItemStack[] inventory) {
		this.inventory = inventory;
		TasksUtils.executeOnAsync(MMOHorsesAPI.getPlugin(),
				() -> MMOHorsesAPI.getSQLModule().setInventory(data.getData().getId(), inventory));
	}
}
